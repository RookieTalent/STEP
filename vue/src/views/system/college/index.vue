<template>
    <div class="app-container">
      <!--搜索栏-->
      <el-form :inline="true">
        <el-form-item label="学院名称">
          <el-input
            v-model="queryParams.collegeName"
            placeholder="请输入学院名称"
            clearable
            size="small"
          />
        </el-form-item>
        <!--TODO 做成数字字典选项卡式-->
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="学院状态" clearable size="small">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :value="dict.dictValue"
              :label="dict.dictLabel"
            />
          </el-select>
        </el-form-item>
        <!--TODO 按钮事件-->
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
          >搜索</el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增</el-button>
        </el-form-item>
      </el-form>

      <!--数据表格树-->
      <el-table
        v-loading="loading"
        :data="collegeList"
        row-key="id"
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="collegeName" label="学院名称" width="260"></el-table-column>
        <el-table-column prop="leader" label="负责人" width="200"></el-table-column>
        <el-table-column prop="mobile" label="联系电话" width="200"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
        <el-table-column label="状态" width="200" prop="is_disabled" align="center">
          <!--TODO 存在转型的问题-->
          <template slot-scope="scope">
            <span>{{ scope.row.is_disabled == 1 ? '禁用' : '正常'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="gmtCreate" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <!--TODO 权限  按钮事件  -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-plus"
              v-if="scope.row.parentId === '0'"
              @click="handleAdd(scope.row)"
            >新增</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--添加或修改部门对话表-->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="24" v-if="form.parentId !== '0'">
              <el-form-item label="上级学院" prop="parentId">
                <treeselect v-model="form.parentId" :options="collegeOptions" :normalizer="normalizer" placeholder="选择上级学院, 不选择则设置为顶级节点" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="学院名称" prop="collegeName">
                <el-input v-model="form.collegeName" placeholder="请输入部门名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人" prop="leader">
                <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <!--TODO 确认负责人之后 自动显示负责人的电话与邮箱-->
              <el-form-item label="联系电话" prop="mobile">
                <el-input v-model="form.mobile" placeholder="请输入联系电话" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <!--TODO 确认负责人之后 自动显示负责人的电话与邮箱-->
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <!--注意这里-->
              <el-form-item label="部门状态">
                <el-radio-group v-model="form.status">
                  <el-radio
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
    import CollegeApi from '@/api/system/college';
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
      name: "index",
      components: { Treeselect },
      data() {
          return{
            //遮罩层
            loading: true,
            // 对话框标题
            title: "",
            // 是否显示弹出框
            open: false,
            // 表单参数
            form:{},
            // 表格树数据
            collegeList: [],
            // 学院树选项
            collegeOptions: [],
            // 用户树选择
            userOptions: [],
            // 查询参数
            queryParams:{
              collegeName: undefined,
              status: undefined
            },
            //TODO 后期从数字字典从查询
            statusOptions:[],
            // 表单验证
            rules: {
              parentId: [
                { required: true, message:"上级学院不能为空", trigger: "blur" }
              ],
              collegeName: [
                { required: true, message: "学院名称不能为空", trigger: "blur" }
              ],
              email: [
                { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }
              ],
              mobile: [
                {
                  pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                  message: "请输入正确的手机号码",
                  trigger: "blur"
                }
              ]

            }
          }
      },

      created() {
        this.getList();
        this.getDicts("sys_normal_disable").then(response =>{
          this.statusOptions = response.data.list;
        });
      },

      methods:{

        /** 取消按钮*/
        cancel(){
          this.open = false;
          this.reset();
        },

        //查询事件
        handleQuery(){
          this.getList();
        },

        /** 查询学院列表 */
        getList(){
          this.loading = true;
          CollegeApi.listCollege(this.queryParams).then(response =>{
            this.collegeList = this.handleTree(response.data.collegeList, 'id');
            this.loading = false;
          })
        },

        // 表单重置
        reset(){
          this.form = {
            id: undefined,
            parentId: undefined,
            collegeName: undefined,
            leader: undefined,
            mobile: undefined,
            email: undefined,
            status: 1
          };
          this.resetForm("form");
        },

        /** 新增按钮操作*/
        handleAdd(row){
          this.reset();
          // 有两种情况
          if(row != undefined){
            this.form.parentId = row.id;
          }

          this.open = true;
          this.title = "添加学院";

          // 学院上级选项卡
          CollegeApi.listCollege().then(response =>{
            /*this.collegeOptions = this.handleTree(response.data.collegeList, 'id');*/
            const treeData = response.data.collegeList.filter(father =>{
              return father.parentId === '0';
            });
            this.collegeOptions = treeData;
          });
        },

        /** 修改按钮操作*/
        handleUpdate(row){
          this.reset();
          CollegeApi.getCollege(row.id).then(response =>{
            this.form = response.data.college;
            this.open = true;
            this.title = "修改部门";
          });
          // 这里只是二级关系， 所以仍然只要一级分类即可
          CollegeApi.listCollege().then(response =>{
            /*this.collegeOptions = this.handleTree(response.data.collegeList, 'id');*/
            const treeData = response.data.collegeList.filter(father =>{
              return father.parentId === '0';
            });
            this.collegeOptions = treeData;
          });
        },

        /** 删除按钮操作*/
        handleDelete(row){
          this.$confirm('是否确认删除名称为"' + row.collegeName + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return CollegeApi.deleteCollege(row.id);
          }).then(() => {
            this.getList();
            this.$message({type: 'success',message: '删除成功!'});
          }).catch(function() {});
        },

        /** 转换部门数据*/
        normalizer(node){
          if (node.children && !node.children.length) {
            delete node.children;
          }
          return {
            id: node.id,
            label: node.collegeName,
            children: node.children
          };
        },

        /** 提交表单*/
        submitForm(){
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                CollegeApi.updateCollege(this.form).then(response =>{
                  if(response.code === 0){
                    this.$message({type: 'success',message: '修改学院信息成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }else{
              CollegeApi.addCollege(this.form).then(response =>{
                if(response.code === 0){
                  this.$message({type: 'success',message: '新增学院成功!'});
                  this.open = false;
                  this.getList();
                }
              });
            }
          });
        },


      }
    }
</script>

<style scoped>

</style>
