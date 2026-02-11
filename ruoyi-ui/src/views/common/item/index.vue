<template>
  <div class="app-container">
    <!-- 统计信息 -->
    <el-row :gutter="20" class="summary-stats mb10" v-if="itemList && itemList.length > 0">
      <el-col :span="6">
        <div class="stat-card bg-purchase">
          <div class="stat-title">购买总价格</div>
          <div class="stat-value">¥{{ formatSummaryPrice(totalPurchasePrice) }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card bg-sell">
          <div class="stat-title">售出总价格</div>
          <div class="stat-value">¥{{ formatSummaryPrice(totalSellPrice) }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card" :class="totalProfitLoss >= 0 ? 'bg-profit' : 'bg-loss'">
          <div class="stat-title">总盈亏</div>
          <div class="stat-value">¥{{ formatSummaryPrice(totalProfitLoss) }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card bg-count">
          <div class="stat-title">物品总数</div>
          <div class="stat-value">{{ totalItemCount }}</div>
        </div>
      </el-col>
    </el-row>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入物品名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物品种类" prop="itemType">
        <el-select v-model="queryParams.itemType" placeholder="请选择物品种类" clearable style="width: 240px" @change="handleQuery">
          <el-option label="实体物品" value="0" />
          <el-option label="虚拟物品" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="购买时间" @change="handleQuery">
        <el-date-picker
          v-model="dateRangePurchase"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="售出时间" @change="handleQuery">
        <el-date-picker
          v-model="dateRangeSell"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['common:item:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['common:item:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['common:item:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>



    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="物品编码" align="center" prop="itemCode" v-if="columns[0].visible" :show-overflow-tooltip="true" />
      <el-table-column label="物品名称" align="center" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="物品种类" align="center" prop="itemType" v-if="columns[2].visible">
        <template slot-scope="scope">
          <el-tag :type="scope.row.itemType === '0' ? 'primary' : 'success'">
            {{ scope.row.itemType === '0' ? '实体物品' : '虚拟物品' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" width="120" v-if="columns[3].visible">
        <template slot-scope="scope">
          <div class="image-cell">
            <img v-if="scope.row.imagePath" :src="getImageUrl(scope.row.imagePath)" class="image-preview" alt="物品图片" @click="previewImage(scope.row.imagePath)" />
            <span v-else>暂无图片</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="购买价格" align="center" prop="purchasePrice" v-if="columns[4].visible" :formatter="formatPrice" />
      <el-table-column label="购买时间" align="center" prop="purchaseTime" width="160" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="售出价格" align="center" prop="sellPrice" v-if="columns[6].visible" :formatter="formatPrice" />
      <el-table-column label="售出时间" align="center" prop="sellTime" width="160" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sellTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="差价" align="center" prop="priceDifference" v-if="columns[8].visible" :formatter="formatPrice" />
      <el-table-column label="盈亏" align="center" prop="profitLoss" v-if="columns[9].visible" :formatter="formatPrice" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" v-if="columns[10].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['common:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['common:item:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改物品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品编码" prop="itemCode" v-if="form.itemCode">
              <el-input v-model="form.itemCode" placeholder="物品编码" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物品名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入物品名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品种类" prop="itemType">
              <el-select v-model="form.itemType" placeholder="请选择物品种类" clearable style="width: 100%">
                <el-option label="实体物品" value="0" />
                <el-option label="虚拟物品" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="物品图片">
              <el-upload
                class="image-uploader"
                :action="uploadImgUrl"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleImageSuccess"
                :before-upload="beforeImageUpload"
                accept=".jpg,.jpeg,.png,.gif,.bmp,.webp"
                >
                <img v-if="form.imagePath" :src="getImageUrl(form.imagePath)" class="image-preview" alt="物品图片" />
                <i v-else class="el-icon-plus image-uploader-icon"></i>
              </el-upload>
              <div class="image-tip">支持JPG、PNG、GIF等格式，大小不超过5MB</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购买价格">
              <el-input-number v-model="form.purchasePrice" :precision="2" :step="0.01" placeholder="请输入购买价格" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购买时间">
              <el-date-picker clearable
                v-model="form.purchaseTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择购买时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="售出价格">
              <el-input-number v-model="form.sellPrice" :precision="2" :step="0.01" placeholder="请输入售出价格" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="售出时间">
              <el-date-picker clearable
                v-model="form.sellTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择售出时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="差价">
              <el-input-number v-model="form.priceDifference" :precision="2" :step="0.01" placeholder="差价自动计算" style="width: 100%" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盈亏">
              <el-input-number v-model="form.profitLoss" :precision="2" :step="0.01" placeholder="盈亏自动计算" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看物品详情对话框 -->
    <el-dialog :title="detailTitle" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-form ref="detailForm" :model="detailForm" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品编码:">
              <span>{{ detailForm.itemCode }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物品名称:">
              <span>{{ detailForm.name }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品种类:">
              <span>{{ detailForm.itemType === '0' ? '实体物品' : '虚拟物品' }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="物品图片:">
              <img v-if="detailForm.imagePath" :src="getImageUrl(detailForm.imagePath)" class="image-preview-large" alt="物品图片" />
              <span v-else>暂无图片</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购买价格:">
              <span>{{ formatPrice(null, null, detailForm.purchasePrice) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购买时间:">
              <span>{{ parseTime(detailForm.purchaseTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="售出价格:">
              <span>{{ formatPrice(null, null, detailForm.sellPrice) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="售出时间:">
              <span>{{ parseTime(detailForm.sellTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="差价:">
              <span>{{ formatPrice(null, null, detailForm.priceDifference) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盈亏:">
              <span>{{ formatPrice(null, null, detailForm.profitLoss) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注:">
          <span>{{ detailForm.remark }}</span>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="创建者:">
              <span>{{ detailForm.createBy }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间:">
              <span>{{ parseTime(detailForm.createTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="更新者:">
              <span>{{ detailForm.updateBy }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间:">
              <span>{{ parseTime(detailForm.updateTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listItem, getItem, addItem, updateItem, delItem } from "@/api/system/item";
import { getToken } from '@/utils/auth'

export default {
  name: "Item",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物品表格数据
      itemList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      detailOpen: false,
      // 详情弹出层标题
      detailTitle: "",
      // 图片上传URL
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      // 图片上传头部
      uploadHeaders: { Authorization: "Bearer " + getToken() },
      // 购买时间范围
      dateRangePurchase: [],
      // 售出时间范围
      dateRangeSell: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null
      },
      // 列信息
      columns: [
        { key: 0, label: `物品编码`, visible: true },
        { key: 1, label: `物品名称`, visible: true },
        { key: 2, label: `物品种类`, visible: true },
        { key: 3, label: `图片`, visible: true },
        { key: 4, label: `购买价格`, visible: true },
        { key: 5, label: `购买时间`, visible: true },
        { key: 6, label: `售出价格`, visible: true },
        { key: 7, label: `售出时间`, visible: true },
        { key: 8, label: `差价`, visible: true },
        { key: 9, label: `盈亏`, visible: true },
        { key: 10, label: `创建时间`, visible: true }
      ],
      // 表单参数
      form: {},
      // 详情表单参数
      detailForm: {},
      // 统计数据
      totalPurchasePrice: 0,
      totalSellPrice: 0,
      totalProfitLoss: 0,
      totalItemCount: 0,
      // 表单校验
      rules: {
        name: [
          { required: true, message: "物品名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 获取图片完整URL */
    getImageUrl(imagePath) {
      if (!imagePath) {
        return '';
      }
      if (imagePath.startsWith('http')) {
        return imagePath;
      }
      return process.env.VUE_APP_BASE_API + imagePath;
    },

    /** 预览图片 */
    previewImage(imagePath) {
      if (imagePath) {
        window.open(this.getImageUrl(imagePath), '_blank');
      }
    },

    /** 格式化价格 */
    formatPrice(row, column, cellValue) {
      if (cellValue == null) {
        return '';
      }
      return '¥' + parseFloat(cellValue).toFixed(2);
    },

    /** 图片上传成功回调 */
    handleImageSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.form.imagePath = response.fileName; // 假设后端返回的图片路径字段名为fileName
        this.$message.success("图片上传成功");
      } else {
        this.$message.error(response.msg || "图片上传失败");
      }
    },

    /** 图片上传前验证 */
    beforeImageUpload(file) {
      const isImage = file.type.indexOf('image/') !== -1;
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isImage) {
        this.$message.error('只能上传图片文件!');
        return false;
      }
      if (!isLt5M) {
        this.$message.error('图片大小不能超过5MB!');
        return false;
      }
      return true;
    },

    /** 查看物品详情 */
    handleView(row) {
      this.detailTitle = "物品详情";
      this.detailForm = JSON.parse(JSON.stringify(row)); // Deep copy to avoid reference issues
      this.detailOpen = true;
    },

    /** 查询物品列表 */
    getList() {
      this.loading = true;
      // 处理购买时间范围
      if (this.dateRangePurchase && this.dateRangePurchase.length === 2) {
        this.queryParams.purchaseTimeBegin = this.dateRangePurchase[0];
        this.queryParams.purchaseTimeEnd = this.dateRangePurchase[1];
      } else {
        this.queryParams.purchaseTimeBegin = null;
        this.queryParams.purchaseTimeEnd = null;
      }

      // 处理售出时间范围
      if (this.dateRangeSell && this.dateRangeSell.length === 2) {
        this.queryParams.sellTimeBegin = this.dateRangeSell[0];
        this.queryParams.sellTimeEnd = this.dateRangeSell[1];
      } else {
        this.queryParams.sellTimeBegin = null;
        this.queryParams.sellTimeEnd = null;
      }

      listItem(this.queryParams).then(response => {
        this.itemList = response.rows;
        this.total = response.total;
        this.loading = false;

        // 计算统计信息
        this.calculateSummaryStats();
      });
    },

    /** 计算汇总统计信息 */
    calculateSummaryStats() {
      if (!this.itemList || this.itemList.length === 0) {
        this.totalPurchasePrice = 0;
        this.totalSellPrice = 0;
        this.totalProfitLoss = 0;
        this.totalItemCount = 0;
        return;
      }

      let purchaseSum = 0;
      let sellSum = 0;
      let profitLossSum = 0;

      this.itemList.forEach(item => {
        // 累加购买价格
        if (item.purchasePrice) {
          purchaseSum += parseFloat(item.purchasePrice);
        }

        // 累加售出价格
        if (item.sellPrice) {
          sellSum += parseFloat(item.sellPrice);
        }

        // 累加盈亏
        if (item.profitLoss) {
          profitLossSum += parseFloat(item.profitLoss);
        }
      });

      this.totalPurchasePrice = purchaseSum;
      this.totalSellPrice = sellSum;
      this.totalProfitLoss = profitLossSum;
      this.totalItemCount = this.itemList.length;
    },

    /** 格式化汇总价格 */
    formatSummaryPrice(value) {
      if (value == null) {
        return '0.00';
      }
      return parseFloat(value).toFixed(2);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        itemId: null,
        itemCode: null,
        name: null,
        itemType: null,
        imagePath: null,
        purchasePrice: null,
        purchaseTime: null,
        sellPrice: null,
        sellTime: null,
        priceDifference: null,
        profitLoss: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.dateRangePurchase = [];
      this.dateRangeSell = [];
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.itemId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const itemId = row.itemId || this.ids;
      getItem(itemId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.itemId != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItem(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const itemIds = row.itemId || this.ids;
      this.$modal.confirm('是否确认删除物品编号为"' + itemIds + '"的数据项？').then(function() {
        return delItem(itemIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
.image-preview {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
  cursor: pointer;
}

.image-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.image-uploader:hover {
  border-color: #409eff;
}

.image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.image-uploader .el-upload {
  border: 1px solid #ccc;
  display: block;
  width: 178px;
  height: 178px;
}

.image-uploader .el-upload img {
  width: 178px;
  height: 178px;
  display: block;
}

.image-tip {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.image-preview-large {
  max-width: 300px;
  max-height: 300px;
  object-fit: contain;
  border-radius: 4px;
}

/* 固定表格行高以防止布局抖动 */
.el-table .el-table__row {
  height: 80px;
}

.el-table td {
  padding: 8px 0;
}

/* 确保图片单元格高度一致 */
.image-cell {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
}

/* 固定操作列高度 */
.el-table .el-table__fixed-right {
  height: 80px !important;
}

/* 统计卡片样式 */
.summary-stats {
  margin-bottom: 20px;
}

.stat-card {
  padding: 20px;
  border-radius: 8px;
  color: white;
  text-align: center;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-title {
  font-size: 14px;
  margin-bottom: 10px;
  opacity: 0.9;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
}

.bg-purchase {
  background: linear-gradient(135deg, #74b9ff, #0984e3);
}

.bg-sell {
  background: linear-gradient(135deg, #00b894, #00a085);
}

.bg-profit {
  background: linear-gradient(135deg, #fdcb6e, #e17055);
}

.bg-loss {
  background: linear-gradient(135deg, #ff7675, #d63031);
}

.bg-count {
  background: linear-gradient(135deg, #a29bfe, #6c5ce7);
}

.mb10 {
  margin-bottom: 10px;
}
</style>