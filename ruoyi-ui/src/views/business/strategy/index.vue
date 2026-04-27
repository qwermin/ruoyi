<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="攻略编码" prop="strategyCode">
        <el-input
          v-model="queryParams.strategyCode"
          placeholder="请输入攻略编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="攻略名称" prop="strategyName">
        <el-input
          v-model="queryParams.strategyName"
          placeholder="请输入攻略名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="攻略类型" prop="strategyType">
        <el-select
          v-model="queryParams.strategyType"
          placeholder="请选择攻略类型"
          clearable
          @change="handleQuery"
        >
          <el-option
            v-for="dict in dict.type.strategy_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:strategy:add']"
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
          v-hasPermi="['system:strategy:edit']"
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
          v-hasPermi="['system:strategy:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="strategyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="攻略ID" align="center" prop="strategyId" v-if="columns.strategyId.visible" />
      <el-table-column label="攻略编码" align="center" prop="strategyCode" v-if="columns.strategyCode.visible" />
      <el-table-column label="攻略名称" align="center" prop="strategyName" v-if="columns.strategyName.visible" />
      <el-table-column label="攻略类型" align="center" prop="strategyType" v-if="columns.strategyType.visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.strategy_type" :value="scope.row.strategyType"/>
        </template>
      </el-table-column>
      <el-table-column label="攻略地址链接" align="center" prop="strategyUrl" width="200" v-if="columns.strategyUrl.visible" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-link :href="scope.row.strategyUrl" target="_blank" type="primary" :underline="false">{{ scope.row.strategyUrl }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns.createTime.visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns.action.visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleJump(scope.row)"
            v-hasPermi="['business:strategy:query']"
          >跳转</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:strategy:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:strategy:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学习攻略对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="攻略编码" prop="strategyCode">
          <el-input v-model="form.strategyCode" placeholder="请输入攻略编码" readonly />
        </el-form-item>
        <el-form-item label="攻略名称" prop="strategyName">
          <el-input v-model="form.strategyName" placeholder="请输入攻略名称" />
        </el-form-item>
        <el-form-item label="攻略类型" prop="strategyType">
          <el-select v-model="form.strategyType" placeholder="请选择攻略类型">
            <el-option
              v-for="dict in dict.type.strategy_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="攻略地址链接" prop="strategyUrl">
          <el-input v-model="form.strategyUrl" placeholder="请输入攻略地址链接" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStrategy, getStrategy, delStrategy, addStrategy, updateStrategy } from "@/api/system/strategy";


export default {
  name: "StudyStrategy",
  dicts: ['strategy_type'],
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
      // 学习攻略表格数据
      strategyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 列显隐信息
      columns: {
        strategyId: { label: `攻略ID`, visible: false },
        strategyCode: { label: `攻略编码`, visible: true },
        strategyName: { label: `攻略名称`, visible: true },
        strategyType: { label: `攻略类型`, visible: true },
        strategyUrl: { label: `攻略地址链接`, visible: false }, // 默认隐藏链接列
        createTime: { label: `创建时间`, visible: true },
        action: { label: `操作`, visible: true }
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        strategyCode: null,
        strategyName: null,
        strategyType: null,
        strategyUrl: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        strategyCode: [
          { required: true, message: "攻略编码不能为空", trigger: "blur" }
        ],
        strategyName: [
          { required: true, message: "攻略名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学习攻略列表 */
    getList() {
      this.loading = true;
      listStrategy(this.queryParams).then(response => {
        this.strategyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        strategyId: null,
        strategyCode: null,
        strategyName: null,
        strategyType: null,
        strategyUrl: null,
        createTime: null,
        updateTime: null
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
      this.queryParams.strategyType = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.strategyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学习攻略";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const strategyId = row.strategyId || this.ids
      getStrategy(strategyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学习攻略";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.strategyId != null) {
            updateStrategy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStrategy(this.form).then(response => {
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
      const strategyIds = row.strategyId || this.ids;
      this.$modal.confirm('是否确认删除学习攻略编号为"' + strategyIds + '"的数据项？').then(function() {
        return delStrategy(strategyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 跳转按钮操作 */
    handleJump(row) {
      window.open(row.strategyUrl, '_blank');
    }
  }
};
</script>