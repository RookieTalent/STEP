<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm"  :inline="true">
        <!--TODO 按钮与事件-->
        <el-form-item label="系统模块" prop="operation">
          <el-input
            v-model="queryParams.operation"
            placeholder="请输入系统模块"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="操作人员" prop="nickname">
          <el-input
            v-model="queryParams.nickname"
            placeholder="请输入操作人员"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="methodMode">
          <el-select
            v-model="queryParams.methodMode"
            placeholder="操作类型"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间">
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

      <!--按钮事件-->
      <el-row :gutter="10" class="mb8">
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
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="handleClean"
          >清空</el-button>
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

      <!--表格-->
      <el-table v-loading="loading" :data="list" style="margin-top: 20px" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="日志编号" align="center" prop="id" />
        <el-table-column label="系统模块" align="center" prop="operation" />
        <el-table-column label="请求方法" align="center" prop="method" />
        <el-table-column label="请求方式" align="center" prop="methodMode"/>
        <el-table-column label="请求参数" align="center" prop="params"/>
        <el-table-column label="操作人员" align="center" prop="nickname" />
        <el-table-column label="ip地址" align="center" prop="ip" :show-overflow-tooltip="true" />
        <el-table-column label="操作日期" align="center" prop="gmtCreate" width="180"/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleView(scope.row, scope.index)"
            >详细</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页栏-->
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


      <!--操作详细日志-->
      <el-dialog title="操作日志详细" :visible.sync="open" width="700px" append-to-body>
        <el-form ref="form" :model="form" label-width="100px" size="mini">
          <el-row>
            <el-col :span="12">
              <el-form-item label="操作模块：">{{form.operation}}</el-form-item>
              <el-form-item label="登录信息：">
                {{ form.nickname }} / {{ form.ip }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="耗时：">{{ form.time }}</el-form-item>
              <el-form-item label="请求方式：">{{ form.methodMode }}</el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="请求方法：">
                {{ form.method }}
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="请求参数：">
                {{ form.params }}
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="操作时间：">
                {{ form.gmtCreate }}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer">
          <el-button @click="open = false">关 闭</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
    import LogApi from "@/api/system/operlog";


    export default {
      name: "index",
      data(){
          return{
            //遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非多个多选禁用
            multiple: true,
            // 总条数
            total: 0,
            // 是否显示弹出层
            open: false,
            // 表格数据
            list: [],
            // 类型数据字典
            typeOptions: [],
            // 日期范围
            dateRange: [],
            // 表单参数
            form: {},
            // 查询参数
            queryParams: {
              pageNum: 1,
              pageSize: 10,
              operation: undefined,
              nickname: undefined,
              methodMode: undefined,
            }
          }
      },

      created() {
        this.getDicts("sys_oper_type").then(response =>{
          this.typeOptions = response.data.list;
        });
        this.getList();
      },

      methods:{
        /** 处理多选*/
        handleSelectionChange(selection){
          this.ids = selection.map(item => item.id);
          this.multiple = !selection.length;
        },

        /** 重置按钮操作*/
        resetQuery(){
          this.dateRange = [];
          this.resetForm("queryForm");
          this.handleQuery();
        },

        /** 列表*/
        getList(pageNum = 1){
          this.queryParams.pageNum = pageNum;
          this.loading = true;
          LogApi.listLog(this.addDateRange(this.queryParams, this.dateRange)).then(response =>{
            this.list = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },

        /** 搜索*/
        handleQuery(){
          this.queryParams.pageNum = 1;
          this.getList();
        },

        /** 详细按钮操作*/
        handleView(row){
          this.open = true;
          this.form = row;
        },

        /** 批量删除*/
        handleDelete(row){
          const operIds = row.id || this.ids;
          this.$confirm('是否确认删除日志标号为"' + operIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return LogApi.delLog(operIds);
          }).then(() =>{
            this.getList();
            this.$notify({type: 'success',message: '删除日志成功!'});
          }).catch(function () {});
        },

        /** 清空*/
        handleClean(){
          this.$confirm('是否确认清空日志?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return LogApi.cleanLog();
          }).then(() =>{
            this.getList();
            this.$notify({type: 'success',message: '清空成功!'});
          }).catch(function () {});
        },

        /** 导出*/
        handleExport(){
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出日志?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return LogApi.exportLog();
          }).then(response =>{
            this.download(response.msg);
          }).catch(function () {});
        }

      }
    }
</script>

<style scoped>

</style>
