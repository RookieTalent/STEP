<template>
    <div class="app-container">
      <el-row :gutter="20">
        <!--搜索栏-->
        <el-col :span="24" :xs="24">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
            <el-form-item label="学院名称" prop="collegeName">
              <!--TODO 回车直接搜索-->
              <el-input
                v-model="queryParams.collegeName"
                placeholder="请输入学院名称"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <el-form-item label="课程名称" prop="courseName">
              <!--TODO 回车直接搜索-->
              <el-input
                v-model="queryParams.courseName"
                placeholder="请输入课程名称"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini">重置</el-button>
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
              >修改</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
              >删除</el-button>
            </el-col>
            <!--TODO 先给关起来-->
            <el-col :span="1.5">
              <el-button
                type="info"
                icon="el-icon-upload2"
                size="mini"
                disabled
              >导入</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-download"
                size="mini"
                disabled
              >导出</el-button>
            </el-col>
          </el-row>

          <!--TODO 表格数据 多项选择函数-->
          <el-table v-loading="loading" :data="courseList" >

          </el-table>

          <!--TODO 分页-->

        </el-col>
      </el-row>

      <!--添加或者修改-->
      <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
        <el-steps :active="active" style="margin-bottom: 20px">
          <el-step title="步骤 1" icon="el-icon-edit"></el-step>
          <el-step title="步骤 2" icon="el-icon-upload"></el-step>
          <el-step title="步骤 3" icon="el-icon-s-promotion"></el-step>
        </el-steps>

        <!--章节总体-->
        <el-card class="app-container" v-if="active === 1">
          <el-form ref="form" :rules="rules" :model="form" label-width="100px">
            <el-row>
              <el-col :span="24">
                <el-form-item label="课程标题" prop="title">
                  <el-input v-model="form.title" placeholder="请输入课程标题"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="讲师" prop="userId">
                  <treeselect v-model="form.userId" :options="userOptions" :disable-branch-nodes="true" :show-count="true" placeholder="请选择授课讲师" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学院" prop="collegeId">
                  <treeselect
                    v-model="form.collegeId"
                    :options="collegeOptions"
                    :normalizer="normalizer"
                    :show-count="true"
                    placeholder="选择学院信息"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="总课时" prop="lessonNum">
                  <el-input-number v-model="form.lessonNum" controls-position="right" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="课程介绍" prop="description">
                  <el-input v-model="form.description" type="textarea" autosize placeholder="请输入简便讲解课程内容"/>
                </el-form-item>
              </el-col>
            </el-row>
            <!--TODO 课程封面上传-->
            <!--<el-row>
              <el-col :span="24">
                <el-form-item label="课程封面" prop="cover">
                  <course-cover :form="form"></course-cover>
                </el-form-item>
              </el-col>
            </el-row>-->
          </el-form>
        </el-card>

        <!--上传章节-->
        <el-card class="app-container" v-if="active === 2">
          <insert-course :chapterForm="chapterForm"></insert-course>
        </el-card>

        <!--确定内容-->
        <el-card class="app-container" v-if="active === 3">

        </el-card>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">{{submitbutton}}</el-button>
          <el-button type="danger" v-if="active !== 1" @click="returnBack">上一步</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
  import UserApi from "@/api/system/user";
  import CollegeApi from "@/api/system/college";
  import courseCover from "@/views/system/course/chapter/courseCover";
  import publishCourse from "@/views/system/course/chapter/publishCourse";
  import insertCourse from "@/views/system/course/chapter/insertCourse";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
      name: "index",
      components: { Treeselect, insertCourse, courseCover, publishCourse },
      data(){
          return{
            //TODO 遮罩层 false->true
            loading: false,
            // 是否显示弹出层
            open: false,
            // 弹出层标题
            title: "",
            // 上传确认按钮
            submitbutton: "",
            // 课程表格数据
            courseList: null,
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 步骤条
            active: 1,
            // 用户树信息
            userOptions: undefined,
            // 学院树信息
            collegeOptions: undefined,
            // 表单参数
            form:{},
            // 章节表单参数
            chapterForm:{},
            // 查询参数
            queryParams:{
              collegeName: undefined,
              courseName: undefined
            },
            // 表单校验
            rules:{
              title: [
                { required: true, message: "课程名称不能为空", trigger: "blur" }
              ],
              lessonNum: [
                { required: true, message: "课程数量不能为空", trigger: "blur" }
              ],
              description: [
                { required: true, message: "课程描述不能为空", trigger: "blur" }
              ]
            }
          }
      },

      created() {
      },

      methods:{
        /** 取消按钮*/
        cancel(){
          this.open=false;
          this.active = 1;
          //TODO reset表单
        },

        /** 转换菜单数据结构*/
        normalizer(node){
          if (node.children && !node.children.length) {
            delete node.children;
          }
          return{
            id: node.id,
            label: node.collegeName,
            children: node.children
          }
        },

        /** 表单重置*/
        reset(){
          this.form = {
            title: undefined,
            userId: undefined,
            collegeId: undefined,
            lessonNum: 0,
            description: undefined
          };
          this.resetForm("form");
        },

        /** 菜单树选项*/
        getTreeSelect(){
          UserApi.treeselect().then(response =>{
            this.userOptions = response.data.userList;
          });
          CollegeApi.listCollege().then(response =>{
            this.collegeOptions = [];
            const college = {id: 0, collegeName: '主类目', children: [] };
            college.children = this.handleTree(response.data.collegeList, "id");
            this.collegeOptions.push(college);
          });
        },

        /** 新增按钮操作*/
        handleAdd(){
          this.reset();
          this.getTreeSelect();

          this.open=true;
          this.title="发布新课程";
          this.submitbutton = "下一步";
        },

        // 返回上一步操作
        returnBack(){
          this.active--;
          this.submitbutton = "下一步";
        },

        /** 提交*/
        submitForm(){
          switch (this.active) {
            case 1:
              this.active++;
              break;
            case 2:
              this.active++;
              this.submitbutton = "提 交";
              break;
            case 3:
              console.log(this.form);
              console.log(this.chapterForm);
              this.open = false;
              break;
          }
        }

      }
    }
</script>

<style scoped>

</style>
