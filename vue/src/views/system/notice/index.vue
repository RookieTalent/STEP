<template>
    <div class="app-container">
      <!--按钮事件-->
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="公告标题" prop="noticeTitle">
          <el-input
            v-model="queryParams.noticeTitle"
            placeholder="请输入公告标题"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="操作人员" prop="createBy">
          <el-input
            v-model="queryParams.createBy"
            placeholder="请输入操作人员"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="noticeType">
          <el-select v-model="queryParams.noticeType" placeholder="公告类型" size="small" clearable>
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!--TODO 权限按钮事件-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:notice:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            v-hasPermi="['system:notice:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            v-hasPermi="['system:notice:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
          >导出</el-button>
        </el-col>
      </el-row>

      <el-table v-loading="loading" :data="noticeList" style="margin-top: 20px" @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="id" width="80" />
        <el-table-column
          label="公告标题"
          align="center"
          prop="noticeTitle"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="公告类型" align="center" prop="noticeType" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.noticeType === 1 ? '通知' : '公告' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.status === 'YES' ? '开启' : '关闭' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建者" align="center" prop="createBy" width="100" />
        <el-table-column label="创建时间" align="center" prop="gmtCreate" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <!--TODO 按钮事件-->
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:notice:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:notice:remove']"
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

      <!--修改增加栏-->
      <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="发布日期">
                <el-date-picker
                  v-model="dateTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公告标题" prop="noticeTitle">
                <el-input v-model="form.noticeTitle" placeholder="请输入公告标题" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公告类型" prop="noticeType">
                <el-select v-model="form.noticeType" placeholder="请选择">
                  <el-option
                    v-for="dict in typeOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态">
                <el-radio-group v-model="form.status">
                  <el-radio
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="备注" prop="remark">
                <el-input type="textarea" placeholder="请输入备注" v-model="form.remark" autosize clearable />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="内容">
                <Editor v-model="form.noticeContent"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" style="padding-top:20px">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    import Editor from '@/components/Editor';
    import NoticeApi from '@/api/system/notice';

    export default {
      name: "index",
      components: {
        Editor
      },
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
          // 总条数
          total: 0,
          // 公告表格数据
          noticeList: [],
          // 弹出层标题
          title: "",
          // 是否显示弹出层
          open: false,
          // 类型数据字典
          statusOptions: [],
          // 状态数据字典
          typeOptions: [],
          //查询参数
          queryParams:{
            pageNum: 1,
            pageSize: 10,
            noticeTitle: undefined,
            noticeType:undefined,
            createBy: undefined,
            status: undefined
          },
          // 公告发布时间
          dateTime:undefined,
          // 表单参数
          form: {},
          // 表单校验
          rules: {
            noticeTitle: [
              { required: true, message: "公告标题不能为空", trigger: "blur" }
            ],
            noticeType: [
              { required: true, message: "公告类型不能为空", trigger: "blur" }
            ]
          }
        }
      },

      created() {
        this.getList();
        this.getDicts("sys_notice_status").then(response =>{
          this.statusOptions = response.data.list;
        });
        this.getDicts("sys_notice_type").then(response =>{
          this.typeOptions = response.data.list;
        })
      },

      methods:{
        // 多选框选中数据
        handleSelectionChange(selection) {
          this.ids = selection.map(item => item.id);
          this.single = selection.length!=1;
          this.multiple = !selection.length;
        },

        /** 列表*/
        getList(pageNum = 1){
          this.loading = true;
          this.queryParams.pageNum = pageNum;
          NoticeApi.listNotice(this.queryParams).then(response =>{
            this.noticeList = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        },

        /** 重置表单*/
        reset(){
          this.dateTime = undefined;
          this.form = {
            id: undefined,
            noticeTitle: undefined,
            noticeType: undefined,
            noticeContent: undefined,
            remark: undefined,
            status: "YES"
          };
          this.resetForm("form");
        },

        /** 重置按钮操作*/
        resetQuery(){
          this.resetForm("queryForm");
          this.handleQuery();
        },

        /** 搜索按钮操作*/
        handleQuery(){
          this.queryParams.pageNum = 1;
          this.getList();
        },

        // 取消按钮
        cancel() {
          this.open = false;
          this.reset();
        },

        /** 新增按钮操作*/
        handleAdd(){
          this.reset();
          this.open = true;
          this.title = "添加公告"
        },

        /** 修改按钮操作*/
        handleUpdate(row){
          this.reset();
          const noticeId = row.id || this.ids;
          NoticeApi.getNotice(noticeId).then(response =>{
            this.form = response.data.notice;
            this.open = true;
            this.title = "修改公告";
          })
        },

        /** 提交按钮*/
        submitForm:function () {
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                NoticeApi.updateNotice(this.addTitle(this.form, this.dateTime)).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '修改公告通知成功!'});
                    this.open = false;
                    this.getList();
                  }
                })
              }else{
                NoticeApi.addNotice(this.addTitle(this.form, this.dateTime)).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '添加公告通知成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }
          })
        },


        /** 删除按钮操作*/
        handleDelete(row){
          const noticeIds = row.id || this.ids;
          this.$confirm('是否确认删除公告编号为"' + noticeIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return NoticeApi.delNotice(noticeIds);
          }).then(() =>{
            this.getList();
            this.$notify({type: 'success',message: '删除公告通知成功!'});
          }).catch(function () {});
        }



      }
    }
</script>

<style scoped>

</style>
