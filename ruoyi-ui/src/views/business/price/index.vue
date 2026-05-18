<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="物品名称" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="请输入物品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="服务器" prop="serverName">
        <el-input v-model="queryParams.serverName" placeholder="请输入服务器" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="物品类型" prop="itemType">
        <el-input v-model="queryParams.itemType" placeholder="请输入物品类型" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['business:price:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['business:price:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:price:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="priceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="priceId" width="80" />
      <el-table-column label="物品名称" align="center" prop="itemName" />
      <el-table-column label="服务器" align="center" prop="serverName" />
      <el-table-column label="物品类型" align="center" prop="itemType" />
      <el-table-column label="物品金额" align="center" prop="itemAmount" />
      <el-table-column label="换算金额" align="center" prop="convertedAmount" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:price:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['business:price:remove']">删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="物品名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="服务器" prop="serverName">
          <el-input v-model="form.serverName" placeholder="请输入服务器" />
        </el-form-item>
        <el-form-item label="物品类型" prop="itemType">
          <el-input v-model="form.itemType" placeholder="请输入物品类型" />
        </el-form-item>
        <el-form-item label="物品金额" prop="itemAmount">
          <el-input-number v-model="form.itemAmount" :precision="2" :step="0.01" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="换算金额" prop="convertedAmount">
          <el-input-number v-model="form.convertedAmount" :precision="2" :step="0.01" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
import { listPrice, getPrice, addPrice, updatePrice, delPrice } from "@/api/system/price";

export default {
  name: "Price",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      priceList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemName: null,
        serverName: null,
        itemType: null
      },
      form: {},
      rules: {
        itemName: [{ required: true, message: "物品名称不能为空", trigger: "blur" }],
        serverName: [{ required: true, message: "服务器不能为空", trigger: "blur" }],
        itemType: [{ required: true, message: "物品类型不能为空", trigger: "blur" }],
        itemAmount: [{ required: true, message: "物品金额不能为空", trigger: "blur" }],
        convertedAmount: [{ required: true, message: "换算金额不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listPrice(this.queryParams).then(response => {
        this.priceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        priceId: null,
        itemName: null,
        serverName: null,
        itemType: null,
        itemAmount: null,
        convertedAmount: null,
        remark: null
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.priceId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增物价";
    },
    handleUpdate(row) {
      this.reset();
      const priceId = row.priceId || this.ids;
      getPrice(priceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物价";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.priceId != null) {
            updatePrice(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrice(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const priceIds = row.priceId || this.ids;
      this.$modal.confirm('是否确认删除物价编号为"' + priceIds + '"的数据项？').then(function() {
        return delPrice(priceIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
