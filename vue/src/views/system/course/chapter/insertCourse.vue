<template>
    <div>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="text"
            icon="el-icon-edit"
            size="mini"
            @click="handleAdd"
          >添加章节</el-button>
        </el-col>
      </el-row>

      <!--浏览列表-->
      <el-table
        :data="chapterList"
        row-key="id"
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

        <el-table-column prop="title" label="标题" sortable width="180"></el-table-column>
        <el-table-column prop="orderNum" label="排列顺序" sortable width="180"></el-table-column>
        <el-table-column prop="videoSourceId" label="云端id" sortable width="180"></el-table-column>
        <el-table-column label="操作"  align="center">
          <template slot-scope="scope">
            <!--TODO 事件-->
            <el-button
              v-if="scope.row.parentId === '0'"
              size="mini"
              type="text"
              con="el-icon-plus"
              @click="handleAdd(scope.row)"
            >新增</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
            >删除</el-button>
          </template>
        </el-table-column>

      </el-table>


      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="chapterForm" :model="form" label-width="80px">
          <el-row>
            <el-col :span="24">
              <el-form-item label="标题" prop="title">
                <el-input v-model="form.title" placeholder="请输入章节/小节标题"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否章节">
                <el-switch
                  v-model="confirm"
                  active-text="是"
                  inactive-text="否"
                  :active-value="1"
                  :inactive-value="0"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="排列顺序" prop="orderNum">
                <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="上传视频" v-if="confirm === 0">
                <el-upload
                  :file-list="fileList"
                  :action="BASE_API"
                  :limit="1">
                  <el-button size="mini" type="success">上传视频</el-button>
                  <el-tooltip placement="right-end">
                    <div slot="content">
                      最大支持1G，<br>
                      支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                      GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                      MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                      SWF、TS、VOB、WMV、WEBM 等视频格式上传
                    </div>
                    <i class="el-icon-question"/>
                  </el-tooltip>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确 认</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    export default {
      name: "insertCourse",
      props:{
        chapterForm:{
          type: Object
        }
      },
      data(){
        return{
          // 弹出层标题
          title: "",
          // 是否显示弹出层
          open: false,
          // 是否是章节 默认1=是 0=不是
          confirm: 1,
          // 接口API地址
          BASE_API: process.env.BASE_API,
          // 上传文件列表
          fileList:[],
          // 表单
          form: {},
          // 章节/小节列表
          chapterList: [],
          // 数组索引
          index: 0,
        }
      },

      created() {
      },

      methods:{
        /** 取消按钮*/
        cancel(){
          this.open = false;
        },

        /** 表单重置*/
        reset(){
          this.form = {
            id:undefined,
            parentId: undefined,
            title:undefined,
            orderNum: 0,
            videoSourceId: undefined,
          };
        },

        /** 新增按钮*/
        handleAdd(row){
          this.reset();

          if(row != null){
            this.form.parentId = row.id;
            //TODO 往数组中添加元素
          }

          this.form.id = this.buildUUID();
          this.open = true;
          this.title = "添加章节";
          // 子组件向父组件传值
          /*this.$emit('chapterForm', this.chapterForm);*/
        },

        /** 表单提交*/
        submitForm: function () {

          if (this.confirm === 1) {
            // 若想tree实现 注意parentId不能为undefined
            this.form.parentId = "0";
            this.chapterList.push(this.form);
            this.index = 0;
          }

          //this.chapterList = this.handleTree(this.chapterList, "id");

          console.log(this.chapterList);
          this.open = false;
        },



      }
    }
</script>

<style scoped>

</style>
