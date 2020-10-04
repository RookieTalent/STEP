<template>
    <div class="app-container">
      <el-row :gutter="20">
        <!--学院数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              v-model="collegeName"
              placeholder="请输入学院名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div>
            <el-tree
              default-expand-all
              :data="collegeOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              ref="tree"
              :filter-node-method="filterNode"
              @node-click="handleNodeClick"/>
          </div>
        </el-col>

        <!--用户数据-->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" :inline="true" label-width="68px">
            <el-form-item label="用户昵称" prop="nickname">
              <el-input
                v-model="queryParams.nickname"
                placeholder="请输入用户昵称"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <el-form-item label="手机号码" prop="mobile">
              <el-input
                v-model="queryParams.mobile"
                placeholder="请输入手机号码"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <!--做成选项卡式的-->
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.isDisabled"
                placeholder="用户状态"
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
            <el-form-item>
              <!--TODO 搜索方法-->
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <!--用户操作栏-->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <!--TODO 新增可以选择学院-->
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
                type="info"
                icon="el-icon-upload2"
                size="mini"
                @click="handleImport"
              >导入</el-button>
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

          <!--用户数据表-->
          <el-table stripe v-loading="loading" :data="userList" style="margin-top: 20px" @selection-change="handleSelectionChange">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" style="color: #00a8e9">
                  <el-form-item label="老师昵称">
                    <span>{{ props.row.nickname }}</span>
                  </el-form-item>
                  <el-form-item label="资历">
                    <span>{{ props.row.info.career }}</span>
                  </el-form-item>
                  <el-form-item label="头衔">
                    <span>{{ props.row.info.level === 1 ? '教授' : '副教授' }}</span>
                  </el-form-item>
                  <el-form-item label="讲师简介">
                    <span>{{ props.row.info.intro }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <!--以下是首列-->
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="用户编号" align="center" prop="id" />
            <el-table-column label="用户昵称" align="center" prop="nickname" :show-overflow-tooltip="true" />
            <!--TODO 后期-->
            <!--<el-table-column label="学院" align="center" prop="college.collegeName" :show-overflow-tooltip="true" />-->
            <el-table-column label="手机号码" align="center" prop="mobile" width="120" />
            <el-table-column label="性别" align="center" prop="sex" width="120" >
              <template slot-scope="scope">
                <span>{{scope.row.sex === 1 ? '男' : '女'}}</span>
              </template>
            </el-table-column>
            <el-table-column label="状态" align="center">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.isDisabled"
                  :active-value="1"
                  :inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="gmtCreate" width="160" >
              <template slot-scope="scope">
                <span>{{ scope.row.gmtCreate }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
              <!--TODO 记得要加权限-->
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
                  icon="el-icon-key"
                  @click="handleDelete(scope.row)"
                >删除</el-button>
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
        </el-col>
      </el-row>

      <!--额外提示框-->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <!--TODO  如果是更新的话 还需要做额外的处理-->
            <el-col :span="12">
              <el-form-item label="用户昵称" prop="nickname">
                <el-input v-model="form.nickname" placeholder="请输入用户昵称"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.id == undefined" label="用户密码" prop="password"><!--TODO  目前无法show密码  原因未知-->
                <el-input v-model="form.password" type="password" placeholder="请输入用户密码" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="手机号" prop="mobile">
                <el-input v-model="form.mobile" placeholder="请输入手机号"  maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户性别">
                <el-select v-model="form.sex" placeholder="请选择">
                  <el-option
                    v-for="dict in sexOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态">
                <el-radio-group v-model="form.isDisabled">
                  <el-radio
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="年龄" prop="age">
                <el-input v-model="form.age" placeholder="请输入年龄"/>
              </el-form-item>
            </el-col>
          </el-row>
          <!--TODO  还有学院  角色   职位-->
        </el-form>
        <!--确认与取消-->
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确定</el-button>
          <el-button @click="cancel">取消</el-button>
        </div>
      </el-dialog>

      <!--删除验证码-->
      <el-dialog title="是否删除该用户" :visible.sync="openDelete" width="600px">
        <el-input placeholder="验证码" v-model="sms_code">
          <template slot="prepend">请输入</template>
          <el-button slot="append" style="width: 100px" type="text" @click="getCode" :disabled="sending">{{codeLable}}</el-button>
        </el-input>
        <div slot="footer">
          <el-button type="primary" @click="submitDelete">确 定</el-button>
          <el-button @click="cancelDelete">取 消</el-button>
        </div>
      </el-dialog>

      <!--用户导入对话框-->
      <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
        <el-upload
          ref="upload"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
            <el-link type="info" style="font-size:12px; color: red;" @click="importTemplate">点击下载模板</el-link>
          </div>
          <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
        </el-upload>
        <div slot="footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
    import UserApi from '@/api/system/user';
    import CollegeApi from '@/api/system/college';
    import {getToken} from "@/utils/auth";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";


    export default {
      name: "index",
      components: { Treeselect },
      data(){
        return{
          //遮罩层
          loading: true,
          //非单个禁用
          single: true,
          //非多个禁用
          multiple: true,
          //是否显示修改增加弹出层
          open:false,
          //是否显示删除弹出层
          openDelete:false,
          //弹窗标题
          title:'',
          //获取验证码
          codeLable:'获取验证码',
          //是否发送验证码
          sending:false,
          //倒计时
          second:60,
          //短信验证码
          sms_code:'',
          //短信验证码手机号
          sms_mobile:'',
          //选中数组
          ids:[],
          //选中单位
          id:'',
          //学院名称
          collegeName:'',
          //分页总数
          total:0,
          //性别选项卡
          sexOptions:[],
          // 状态数据字典
          statusOptions: [],
          //表单参数
          form:{},
          //用户数据表格
          userList:null,
          //查询参数
          queryParams:{
            pageNum: 1,
            pageSize: 10,
            nickname: undefined,
            mobile: undefined,
            isDisabled: undefined,
            collegeId: undefined
          },
          //用户导入参数
          upload:{
            // 是否显示弹出层（用户导入）
            open: false,
            // 弹出层标题 （用户导入）
            title: "",
            // 是否禁用上传
            isUploading: false,
            // 是否更新已经存在的用户数据
            updateSupport: 0,
            // 设置上传的请求头部
            headers: [
              {Authorization: getToken()}
            ],
            // 上传的地址
            url: process.env.BASE_API + '/system/user/importData'
          },
          //学院树选项
          collegeOptions: undefined,
          defaultProps: {
            children: 'children',
            label: 'label'
          },
          //表单校验
          rules:{
            nickname:[
              {required: true, message: "用户昵称不能为空", trigger: "blur"}
            ],
            password:[
              { required: true, message: "用户密码不能为空", trigger: "blur" }
            ],
            email: [
              { required: true, message: "邮箱地址不能为空", trigger: "blur" },
              {
                type: "email",
                message: "'请输入正确的邮箱地址",
                trigger: ["blur", "change"]
              }
            ],
            mobile:[
              { required: true, message: "手机号码不能为空", trigger: "blur" },
              {
                pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                message: "请输入正确的手机号码",
                trigger: "blur"
              }
            ]
          },
        }
      },

      created(){
        //获取数据
        this.getList();
        this.getTreeselect();
        this.getDicts("sys_normal_disable").then(response =>{
          this.statusOptions = response.data.list;
        });
        this.getDicts("sys_user_sex").then(response =>{
          this.sexOptions = response.data.list;
        });
      },

      methods:{
        //节点单机时间
        handleNodeClick(data) {
          this.queryParams.collegeId = data.id;
          this.getList();
        },

        /** 修改增加form表单撤销*/
        cancel(){
          this.open=false;
          this.reset();
        },

        /** 删除撤销*/
        cancelDelete(){
          this.openDelete=false;
          //TODO code值重置
        },

        /** 重置搜索栏 */
        resetQuery(){
          this.queryParams={
            pageNum: 1,
            pageSize: 10,
            nickname: undefined,
            mobile: undefined,
            isDisabled: undefined,
            collegeId: undefined
          };
          //刷新首页
          this.getList();
        },

        /**  表单重置*/
        reset(){
          this.form = {
            userId: undefined,
            nickname: undefined,
            password: undefined,
            mobile: undefined,
            email: undefined,
            sex: undefined,
            age: undefined
            //TODO 可以引入头像 后期还有角色 学院 职位等
          }
        },

        /** 多选框设置*/
        handleSelectionChange(selection){
          this.ids = selection.map(item => item.id);
          this.single = selection.length != 1;  //只能修改一个
          this.multiple = !selection.length;
        },

        /** 搜索按钮*/
        handleQuery(){
          this.queryParams.pageNum = 1;
          this.getList();
        },

        /**  新增用户打开窗口*/
        handleAdd(){
          this.reset();
          this.open = true;
          this.title = "添加用户";
        },

        /** 修改用户打开窗口*/
        handleUpdate(row){
          this.reset();
          //TODO 学院 角色 职称
          const id = row.id || this.ids;
          UserApi.getUser(id).then(response =>{
            this.form = response.data.user;
            this.open=true;
            this.title="修改用户";
          });
        },

        /** 删除用户打开窗口*/
        handleDelete(row){
          this.id = row.id;
          this.openDelete = true;
        },

        /** 导入按钮操作*/
        handleImport(){
          this.upload.title = "用户导入";
          this.upload.open = true;
        },

        /** 文件上传中处理*/
        handleFileUploadProgress(event, file, fileList){
          this.upload.isUploading = true;
        },

        /** 文件上传成功处理*/
        handleFileSuccess(response, file, fileList) {
          this.upload.open = false;
          this.upload.isUploading = false;
          this.$refs.upload.clearFiles();
          this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
          this.getList();
        },

        /** 提交上传文件*/
        submitFileForm() {
          this.$refs.upload.submit();
        },

        /**TODO  提交更新或新增用户信息  这里要判断是否存在用户ID*/
        submitForm:function(){
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                UserApi.updateUser(this.form).then(response =>{
                  if(response.code === 0){
                    this.$message({type: 'success',message: '修改用户成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }else{
                UserApi.addUser(this.form).then(response =>{
                  if(response.code === 0){
                    this.$message({type: 'success',message: '新增用户成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }
          });
        },

        /** 设计倒计时*/
        timeDown:function(){
          this.sending = true;
          var result = setInterval(() =>{
            this.second--;
            this.codeLable = this.second + 's后重新获取';
            if(this.second <= 0){
              this.sending = false;
              clearInterval(result);
              this.second = 60;
              this.codeLable = '获取验证码';
            }
          }, 1000);
        },

        /**  根据token获取当前用户的手机号 去获取验证码*/
        getCode(){
          UserApi.getCode().then(response =>{
            this.sms_mobile = response.data.mobile;
            this.timeDown();
          })
        },


        /** 批量或单个删除用户*/
        submitDelete(){
          let userIds = undefined;
          if(this.id === undefined && this.ids.length > 0){
            userIds = this.ids;
          }else{
            userIds = this.id;
          }
          UserApi.deleUser(userIds, this.sms_code, this.sms_mobile).then(response =>{
            this.$message({type: 'success',message: '删除用户成功!'});
            this.openDelete = false;
            this.getList();
          }).catch(function () {});
        },


        /**TODO 逻辑错乱 用户状态修改*/
        handleStatusChange(row){
          let text = row.isDisabled === "1" ? "停用" : "启用";
          this.$confirm('确认要"' + text + '""' + row.nickname + '"用户吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            //TODO 修改状态
          }).then(() =>{
            this.$message({type: 'success',message: '修改成功!'})
          }).catch(function () {
            row.isDisabled = row.isDisabled === "1" ? "1" : "0";
          });
        },

        /** 查询用户列表*/
        getList(pageNum = 1){
          this.queryParams.pageNum = pageNum;
          this.loading = true;
          UserApi.listUser(this.queryParams).then(response =>{
            this.userList = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        },

        /** 查询部门树*/
        getTreeselect(){
          CollegeApi.treeselect().then(response =>{
            this.collegeOptions = response.data.collegeList;
          });
        },

        // 筛选节点
        filterNode(value, data) {
          if (!value) return true;
          return data.label.indexOf(value) !== -1;
        },

        /** 下载模板*/
        importTemplate() {
          UserApi.importTemplate().then(response =>{
            this.download(response.msg);
          });
        },

        /** 导出按钮操作 */
        handleExport(){
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有用户数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return UserApi.exportUser(queryParams);
          }).then(response =>{
            this.download(response.msg);
          }).catch(function() {});
        },

      }
    }
</script>

<style scoped>
  .app-container{
    padding: 20px;
  }
</style>
