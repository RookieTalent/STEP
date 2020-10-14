<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="字典名称" prop="dictName">
          <!--TODO 按下查询按钮-->
          <el-input
            v-model="queryParams.dictName"
            placeholder="请输入字典名称"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input
            v-model="queryParams.dictType"
            placeholder="请输入字典类型"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="字典状态"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
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
        <!--TODO 事件与权限-->
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:dict:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:dict:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete"
            v-hasPermi="['system:dict:remove']"
            :disabled="multiple"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:dict:export']"
          >导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-refresh"
            size="mini"
            v-hasPermi="['system:dict:remove']"
          >清理缓存</el-button>
        </el-col>
      </el-row>

      <!--显示表单-->
      <el-table v-loading="loading" :data="typeList" style="margin-top: 20px"  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="字典编号" align="center" prop="id"/>
        <el-table-column label="字典名称" align="center" prop="dictName"/>
        <el-table-column label="字典类型" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <router-link :to="'/dict/type/data/' + scope.row.id" class="link-type">
              <span>{{ scope.row.dictType }}</span>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <span>{{ scope.row.status === 1 ? '正常' : '禁用' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
        <el-table-column label="创建时间" align="center" prop="gmtCreate" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <!--TODO 事件与权限-->
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:dict:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:dict:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination class="app-container"
                     style="text-align: right"
                     v-show="total>0"
                     :total="total"
                     :current-page="queryParams.pageNum"
                     :page-size="queryParams.pageSize"
                     background
                     layout="total, sizes, prev, pager, next, jumper"
                     @current-change="getList"
      />

      <!--添加或修改参数对话框-->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="字典名称" prop="dictName">
            <el-input v-model="form.dictName" placeholder="请输入字典名称"/>
          </el-form-item>
          <el-form-item label="字典类型" prop="dictType">
            <el-input v-model="form.dictType" placeholder="请输入字典类型"/>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    import TypeApi from "@/api/system/dict/type";

    export default {
      name: "index",
      data(){
          return{
            // 遮罩层
            loading: true,
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 是否弹出层
            open: false,
            // 弹出层标题
            title: "",
            // 页数
            total: 0,
            //TODO  状态数据字典 暂时手写
            statusOptions: [
              {dictValue: 1, dictLabel: '正常'},
              {dictValue: 0, dictLabel: '停用'},
            ],
            // 选中数组
            ids: [],
            // 字典表格数据
            typeList: [],
            // 日期范围
            dateRange: [],
            // 查询参数
            queryParams: {
              pageNum: 1,
              pageSize: 10,
              dictName: undefined,
              dictType: undefined,
              status: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
              dictName: [
                { required: true, message: "字典名称不能为空", trigger: "blur" }
              ],
              dictType: [
                { required: true, message: "字典类型不能为空", trigger: "blur" }
              ]
            }
          }
      },

      created() {
        this.getList();
      },

      methods:{
        /** 取消按钮 */
        cancel(){
          this.open=false;
          this.reset();
        },

        /** 重置表单 */
        reset(){
          this.form={
            id: undefined,
            dictName: undefined,
            dictType: undefined,
            status: 1,
            remark: undefined
          };
          this.resetForm("form");
        },

        /** 获取数字字典类型列表 */
        getList(pageNum = 1){
          this.loading = true;
          TypeApi.getList(this.addDateRange(this.queryParams, this.dateRange)).then(response =>{
            this.typeList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },

        /** 重置按钮操作*/
        resetQuery(){
          this.dateRange = [];
          this.resetForm("queryForm");
          this.handleQuery();
        },

        /** 搜索按钮 */
        handleQuery(){
          this.queryParams.pageNum = 1;
          this.getList();
        },

        /** 多选框选中数据 */
        handleSelectionChange(selection){
          this.ids = selection.map(item => item.id);
          this.single = selection.length!=1;
          this.multiple = !selection.length;
        },

        /** 新增按钮 */
        handleAdd(row){
          this.reset();
          this.open = true;
          this.title = "添加字典类型";
        },

        /** 修改按钮 */
        handleUpdate(row){
          this.reset();
          const dictId = row.id || this.ids;
          TypeApi.getType(dictId).then(response =>{
            this.form = response.data.dictType;
            this.open = true;
            this.title = '修改字典类型';
          })
        },

        /** 提交表单*/
        submitForm:function () {
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                TypeApi.updateType(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '修改数字字典类型成功!', title: '成功'});
                    this.open = false;
                    this.getList();
                  }
                })
              }else{
                TypeApi.addType(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '新增数字字典类型成功!', title: '成功'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }
          })
        },

        /** 删除按钮 */
        handleDelete(row){
          const dictIds = row.id || this.ids;
          this.$confirm('是否确认删除字典编号为"' + dictIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return TypeApi.delType(dictIds);
          }).then(() =>{
            this.getList();
            this.$notify({type: 'success',message: '删除数字字典类型成功!', title: '成功'});
          }).catch(function () {});
        },

        /** 导出按钮操作 */
        handleExport() {
          this.$confirm('是否确认导出所有数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return TypeApi.exportType();
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        },

        /** 清空缓存*/
        handleClearCache(){
          TypeApi.clearCache().then(response=>{
            if(response.code === 0){
              this.$message({type: 'success',message: '清理成功'});
            }
          })
        }

      }
    }
</script>

<style scoped>
  .link-type{
    color: red;
    cursor: pointer;
  }
</style>
