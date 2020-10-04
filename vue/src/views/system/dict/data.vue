<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="字典名称" prop="dictType">
          <el-select v-model="queryParams.dictType" size="small">
            <el-option
              v-for="item in typeOptions"
              :key="item.id"
              :label="item.dictName"
              :value="item.dictType"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="字典标签" prop="dictLabel">
          <el-input
            v-model="queryParams.dictLabel"
            placeholder="请输入字典标签"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="数据状态"clearable size="small">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <!--TODO 按钮事件-->
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!--TODO 按钮事件-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
          >导出</el-button>
        </el-col>
      </el-row>

      <!--数据表格-->
      <el-table v-loading="loading" :data="dataList" style="margin-top: 20px"  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="字典编码" align="center" prop="dictCode"/>
        <el-table-column label="字典标签" align="center" prop="dictLabel"/>
        <el-table-column label="字典键值" align="center" prop="dictValue"/>
        <el-table-column label="字典排序" align="center" prop="dictSort"/>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <span>{{ scope.row.status === 1 ? '正常' : '禁用' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
        <el-table-column label="创建时间" align="center" prop="gmtCreate" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
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

      <!--添加或修改-->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="字典类型">
            <el-input v-model="form.dictType" :disabled="true"/>
          </el-form-item>
          <el-form-item label="数据标签" prop="dictLabel">
            <el-input v-model="form.dictLabel" placeholder="请输入数据标签" />
          </el-form-item>
          <el-form-item label="数据键值" prop="dictValue">
            <el-input v-model="form.dictValue" placeholder="请输入数据键值" />
          </el-form-item>
          <el-form-item label="显示排序" prop="dictSort">
            <el-input-number v-model="form.dictSort" controls-position="right" :min="0" />
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
    import DataApi from "@/api/system/dict/data";
    import TypeApi from "@/api/system/dict/type";

    export default {
      name: "data",
      data(){
          return{
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 弹出层标题
            title: "",
            // 页数
            total: 0,
            // 是否显示弹出层
            open: false,
            //TODO  状态数据字典 暂时手写
            statusOptions: [
              {dictValue: 1, dictLabel: '正常'},
              {dictValue: 0, dictLabel: '停用'},
            ],
            // 类型数据字典
            typeOptions: [],
            // 默认字典类型
            defaultDictType: "",
            // 字典表格数据
            dataList: [],
            // 查询参数
            queryParams: {
              pageNum: 1,
              pageSize: 10,
              dictLabel: undefined,
              dictType: undefined,
              status: undefined
            },
            // 数字字典类型查询参数
            TypeQueryParams: {
              pageNum: 1,
              pageSize: 10
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
              dictLabel: [
                { required: true, message: "数据标签不能为空", trigger: "blur" }
              ],
              dictValue: [
                { required: true, message: "数据键值不能为空", trigger: "blur" }
              ],
              dictSort: [
                { required: true, message: "数据顺序不能为空", trigger: "blur" }
              ]
            }
          }
      },

      created() {
        const dictId = this.$route.params && this.$route.params.dictId;
        this.getType(dictId);
        this.getTypeList();
      },

      methods:{
        /** 表单重置*/
        reset() {
          this.form = {
            dictCode: undefined,
            dictLabel: undefined,
            dictValue: undefined,
            dictSort: 0,
            status: 1,
            remark: undefined
          };
          this.resetForm("form");
        },

        /** 撤销按钮操作*/
        cancel(){
          this.open = false;
          this.reset();
        },

        /** 搜索按钮操作 */
        handleQuery() {
          this.queryParams.pageNum = 1;
          this.getList();
        },

        /** 重置按钮操作 */
        resetQuery() {
          this.resetForm("queryForm");
          this.queryParams.dictType = this.defaultDictType;
          this.handleQuery();
        },

        /** 查询字典类型详细*/
        getType(dictId){
          TypeApi.getType(dictId).then(response =>{
            const dict = response.data.dictType;
            this.queryParams.dictType = dict.dictType;
            this.defaultDictType = dict.dictType;
            this.getList();
          })
        },

        /** 查询字典类型列表*/
        getTypeList(){
          TypeApi.getList(this.TypeQueryParams).then(response =>{
            this.typeOptions = response.rows;
          });
        },

        /** 查询字典数据列表*/
        getList(pageNum = 1){
          this.loading = true;
          DataApi.listData(this.queryParams).then(response =>{
            this.dataList = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        },

        /** 多选框选中数据 */
        handleSelectionChange(selection){
          this.ids = selection.map(item => item.dictCode);
          this.single = selection.length!=1;
          this.multiple = !selection.length;
        },

        /** 新增按钮操作 */
        handleAdd(){
          this.reset();
          this.open=true;
          this.title = "添加字典数据";
          this.form.dictType = this.queryParams.dictType;
        },

        /** 修改按钮操作*/
        handleUpdate(row){
          this.reset();
          const dictCode = row.dictCode || this.ids
          DataApi.getData(dictCode).then(response =>{
            this.form = response.data.dictData;
            this.open = true;
            this.title = "修改字典数据";
          })
        },

        /** 删除按钮操作 */
        handleDelete(row) {
          const dictCodes = row.dictCode || this.ids;
          this.$confirm('是否确认删除字典编码为"' + dictCodes + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return DataApi.delData(dictCodes);
          }).then(() => {
            this.getList();
            this.$notify({type: 'success',message: '删除数字字典数据成功!', title: '成功'});
          }).catch(function() {});
        },

        /** 表单提交*/
        submitForm:function () {
          this.$refs["form"].validate(valid =>{
            if(valid){
              if (this.form.dictCode != undefined){
                DataApi.updateData(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '修改数字字典数据成功!', title: '成功'});
                    this.open = false;
                    this.getList();
                  }
                })
              }else{
                DataApi.addData(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '新增数字字典数据成功!', title: '成功'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }
          });
        },

        /** 导出按钮操作 */
        handleExport() {
          this.$confirm('是否确认导出所有数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return DataApi.exportData();
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        }

      }
    }
</script>

<style scoped>

</style>
