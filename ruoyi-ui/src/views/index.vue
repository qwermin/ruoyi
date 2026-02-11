<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <h1 class="dashboard-title">欢迎来到管理系统</h1>
      <p class="dashboard-subtitle">高效管理</p>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-cards">
      <el-col :span="6" v-for="(card, index) in statsCards" :key="index">
        <div class="stat-card" :class="card.type" @click="goToPage(card.path)">
          <div class="card-icon">
            <i :class="card.icon" :style="{color: card.color}"></i>
          </div>
          <div class="card-content">
            <h3 class="card-value">{{ card.value }}</h3>
            <p class="card-label">{{ card.label }}</p>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-section">
      <el-col :span="16">
        <div class="chart-card">
          <div class="chart-header">
            <h3>物品趋势分析</h3>
            <el-select v-model="timeRange" placeholder="选择时间范围" size="small" @change="loadChartData">
              <el-option label="最近7天" value="7"></el-option>
              <el-option label="最近30天" value="30"></el-option>
              <el-option label="最近90天" value="90"></el-option>
            </el-select>
          </div>
          <div class="chart-container">
            <div ref="lineChart" style="height: 300px;"></div>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="chart-card">
          <div class="chart-header">
            <h3>物品种类分布</h3>
          </div>
          <div class="chart-container">
            <div ref="pieChart" style="height: 300px;"></div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 最近活动 -->
    <el-row :gutter="20" class="recent-section">
      <el-col :span="24">
        <div class="recent-card">
          <div class="chart-header">
            <h3>最近添加的物品</h3>
            <el-button type="primary" size="small" @click="goToPage('/common/item')">查看全部</el-button>
          </div>
          <el-table :data="recentItems" style="width: 100%" :show-header="false">
            <el-table-column prop="itemCode" label="编码" width="150"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="itemType" label="类别" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.itemType === '0' ? 'primary' : 'success'">
                  {{ scope.row.itemType === '0' ? '实体物品' : '虚拟物品' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="purchasePrice" label="购买价格" width="120">
              <template slot-scope="scope">
                ¥{{ scope.row.purchasePrice }}
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="150">
              <template slot-scope="scope">
                {{ formatDate(scope.row.createTime) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="viewItem(scope.row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from 'echarts/lib/echarts'
import { listItem } from "@/api/system/item";

export default {
  name: "Dashboard",
  data() {
    return {
      timeRange: '30',
      statsCards: [
        {
          icon: 'el-icon-shopping-cart-full',
          label: '物品总数',
          value: 0,
          type: 'primary',
          color: '#409EFF',
          path: '/common/item'
        },
        {
          icon: 'el-icon-wallet',
          label: '总购买金额',
          value: '¥0.00',
          type: 'success',
          color: '#67C23A',
          path: '/common/item'
        },
        {
          icon: 'el-icon-money',
          label: '总售出金额',
          value: '¥0.00',
          type: 'warning',
          color: '#E6A23C',
          path: '/common/item'
        },
        {
          icon: 'el-icon-trophy',
          label: '总盈亏',
          value: '¥0.00',
          type: 'danger',
          color: '#F56C6C',
          path: '/common/item'
        }
      ],
      recentItems: [],
      chartData: {
        dates: [],
        counts: [],
        types: ['实体物品', '虚拟物品'],
        typeData: [0, 0]
      }
    }
  },
  mounted() {
    this.loadStats();
    this.loadRecentItems();
    this.loadChartData();
  },
  methods: {
    async loadStats() {
      try {
        const response = await listItem({ pageNum: 1, pageSize: 1000 }); // Get all items for stats
        const items = response.rows;
        
        // Calculate statistics
        let totalCount = items.length;
        let totalPurchase = 0;
        let totalSell = 0;
        let totalProfit = 0;
        let physicalCount = 0;
        let virtualCount = 0;
        
        items.forEach(item => {
          if (item.purchasePrice) {
            totalPurchase += parseFloat(item.purchasePrice) || 0;
          }
          if (item.sellPrice) {
            totalSell += parseFloat(item.sellPrice) || 0;
          }
          if (item.profitLoss) {
            totalProfit += parseFloat(item.profitLoss) || 0;
          }
          
          if (item.itemType === '0') {
            physicalCount++;
          } else {
            virtualCount++;
          }
        });
        
        // Update stats cards
        this.statsCards[0].value = totalCount;
        this.statsCards[1].value = `¥${totalPurchase.toFixed(2)}`;
        this.statsCards[2].value = `¥${totalSell.toFixed(2)}`;
        this.statsCards[3].value = `¥${totalProfit.toFixed(2)}`;
        
        // Update pie chart data
        this.chartData.typeData = [physicalCount, virtualCount];
        
        // Render charts
        this.renderLineChart();
        this.renderPieChart();
      } catch (error) {
        console.error('加载统计数据失败:', error);
      }
    },
    
    async loadRecentItems() {
      try {
        const response = await listItem({ 
          pageNum: 1, 
          pageSize: 5,
          orderByColumn: 'createTime',
          isAsc: 'desc'
        });
        this.recentItems = response.rows;
      } catch (error) {
        console.error('加载最近物品失败:', error);
      }
    },
    
    loadChartData() {
      // Mock data for demonstration - in real app, you would fetch from API
      const days = parseInt(this.timeRange);
      const dates = [];
      const counts = [];
      
      for (let i = days - 1; i >= 0; i--) {
        const date = new Date();
        date.setDate(date.getDate() - i);
        dates.push(`${date.getMonth() + 1}-${date.getDate()}`);
        counts.push(Math.floor(Math.random() * 10) + 1); // Random data for demo
      }
      
      this.chartData.dates = dates;
      this.chartData.counts = counts;
      
      this.renderLineChart();
    },
    
    renderLineChart() {
      this.$nextTick(() => {
        if (this.$refs.lineChart) {
          const chart = echarts.init(this.$refs.lineChart, null, { renderer: 'canvas' });
          const option = {
            tooltip: {
              trigger: 'axis'
            },
            xAxis: {
              type: 'category',
              data: this.chartData.dates
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: this.chartData.counts,
              type: 'line',
              smooth: true,
              itemStyle: {
                color: '#409EFF'
              },
              areaStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [{
                    offset: 0,
                    color: 'rgba(64, 158, 255, 0.3)'
                  }, {
                    offset: 1,
                    color: 'rgba(64, 158, 255, 0.05)'
                  }]
                }
              }
            }],
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              top: '10%',
              containLabel: true
            }
          };
          chart.setOption(option);

          // Handle window resize
          const resizeFunc = () => {
            if (chart) {
              chart.resize();
            }
          };
          window.addEventListener('resize', resizeFunc);

          // Store resize function to remove later if needed
          this.lineChartResize = resizeFunc;
        }
      });
    },

    renderPieChart() {
      this.$nextTick(() => {
        if (this.$refs.pieChart) {
          const chart = echarts.init(this.$refs.pieChart, null, { renderer: 'canvas' });
          const option = {
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'left'
            },
            series: [{
              name: '物品种类',
              type: 'pie',
              radius: '70%',
              data: [
                { value: this.chartData.typeData[0], name: '实体物品' },
                { value: this.chartData.typeData[1], name: '虚拟物品' }
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }],
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              top: '10%',
              containLabel: true
            }
          };
          chart.setOption(option);

          // Handle window resize
          const resizeFunc = () => {
            if (chart) {
              chart.resize();
            }
          };
          window.addEventListener('resize', resizeFunc);

          // Store resize function to remove later if needed
          this.pieChartResize = resizeFunc;
        }
      });
    },
    
    goToPage(path) {
      this.$router.push(path);
    },
    
    viewItem(item) {
      // You can implement view item logic here
      this.$message.info(`查看物品: ${item.name}`);
    },
    
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
    }
  },
  beforeDestroy() {
    // Clean up event listeners
    if (this.lineChartResize) {
      window.removeEventListener('resize', this.lineChartResize);
    }
    if (this.pieChartResize) {
      window.removeEventListener('resize', this.pieChartResize);
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 50px);
}

.dashboard-header {
  text-align: center;
  margin-bottom: 30px;
  
  .dashboard-title {
    font-size: 28px;
    color: #303133;
    margin-bottom: 10px;
    font-weight: 600;
  }
  
  .dashboard-subtitle {
    font-size: 16px;
    color: #909399;
  }
}

.stats-cards {
  margin-bottom: 30px;
  
  .stat-card {
    height: 100px;
    border-radius: 8px;
    padding: 20px;
    display: flex;
    align-items: center;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    
    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
    }
    
    &.primary {
      background: linear-gradient(135deg, #74b9ff, #0984e3);
      color: white;
    }
    
    &.success {
      background: linear-gradient(135deg, #00b894, #00cec9);
      color: white;
    }
    
    &.warning {
      background: linear-gradient(135deg, #fdcb6e, #e17055);
      color: white;
    }
    
    &.danger {
      background: linear-gradient(135deg, #ff7675, #d63031);
      color: white;
    }
    
    .card-icon {
      font-size: 36px;
      margin-right: 15px;
    }
    
    .card-content {
      flex: 1;
      
      .card-value {
        font-size: 24px;
        font-weight: bold;
        margin: 0;
      }
      
      .card-label {
        font-size: 14px;
        margin: 0;
        opacity: 0.8;
      }
    }
  }
}

.chart-section {
  margin-bottom: 30px;
  
  .chart-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    
    .chart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      
      h3 {
        margin: 0;
        font-size: 18px;
        color: #303133;
      }
    }
    
    .chart-container {
      border: 1px solid #ebeef5;
      border-radius: 4px;
      overflow: hidden;
    }
  }
}

.recent-section {
  .recent-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    
    .chart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      
      h3 {
        margin: 0;
        font-size: 18px;
        color: #303133;
      }
    }
  }
}

@media (max-width: 768px) {
  .stats-cards {
    .el-col {
      margin-bottom: 15px;
      
      .stat-card {
        height: auto;
        padding: 15px;
      }
    }
  }
  
  .chart-section {
    .el-col {
      margin-bottom: 20px;
    }
  }
}
</style>