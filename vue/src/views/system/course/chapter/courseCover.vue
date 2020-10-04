<template>
    <div>
      <!--记得改动-->
      <img v-bind:src="img" title="点击上传封面" @click="editCropper" class="img-circle img-lg"/>
      <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @opened="modalOpened">
        <el-row>
          <el-col :xs="24" :md="12" :style="{height: '350px'}">
            <vue-cropper
              ref="cropper"
              :img="img"
              :info="true"
              :autoCrop="options.autoCrop"
              :autoCropWidth="options.autoCropWidth"
              :autoCropHeight="options.autoCropHeight"
              :fixedBox="options.fixedBox"
              @realTime="realTime"
              v-if="visible"
            />
          </el-col>
          <el-col :xs="24" :md="12" :style="{height: '350px'}">
            <div class="avatar-upload-preview">
              <img :src="previews.url" :style="previews.img" />
            </div>
          </el-col>
        </el-row>

        <br/>

        <el-row>
          <el-col :lg="2" :md="2">
            <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
              <el-button size="small">
                上传
                <i class="el-icon-upload el-icon--right"></i>
              </el-button>
            </el-upload>
          </el-col>
          <el-col :lg="{span: 1, offset: 2}" :md="2">
            <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
          </el-col>
          <el-col :lg="{span: 1, offset: 1}" :md="2">
            <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
          </el-col>
          <el-col :lg="{span: 1, offset: 1}" :md="2">
            <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft"></el-button>
          </el-col>
          <el-col :lg="{span: 1, offset: 1}" :md="2">
            <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight"></el-button>
          </el-col>
          <el-col :lg="{span: 2, offset: 6}" :md="2">
            <el-button type="primary" size="small">提 交</el-button>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
</template>

<script>
    import store from "@/store";
    import { VueCropper } from "vue-cropper";

    export default {
      name: "courseCover",
      components: { VueCropper },
      props:{
        form: {
          type: Object
        }
      },

      data(){
        return{
          // 是否显示弹出层
          open: false,
          // 是否显示cropper
          visible: false,
          // 弹出层标题
          title: "修改/选择封面",
          //TODO 先设置一个默认的
          img: store.getters.avatar,
          options:{
            autoCrop: true, // 是否默认生成截图框
            autoCropWidth: 200, // 默认生成截图框宽度
            autoCropHeight: 200, // 默认生成截图框高度
            fixedBox: true // 固定截图框大小 不允许改变
          },
          previews: {}
        }
      },

      created() {
      },

      methods:{
        // 编辑头像
        editCropper() {
          this.open = true;
        },

        // 覆盖默认的上传行为
        requestUpload() {
        },

        // 向左旋转
        rotateLeft() {
          this.$refs.cropper.rotateLeft();
        },
        // 向右旋转
        rotateRight() {
          this.$refs.cropper.rotateRight();
        },
        // 图片缩放
        changeScale(num) {
          num = num || 1;
          this.$refs.cropper.changeScale(num);
        },

        //打开弹出层时结束回调
        modalOpened(){
          this.visible = true;
        },

        // 实时预览
        realTime(data) {
          this.previews = data;
        },

        // 上传预处理
        beforeUpload(file) {
          if (file.type.indexOf("image/") == -1) {
            this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
          } else {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
              this.img = reader.result;
            };
          }
        },

        //TODO  上传图片

      }
    }
</script>

<style scoped>
  .avatar-upload-preview {
    position: absolute;
    top: 50%;
    transform: translate(50%, -50%);
    width: 200px;
    height: 200px;
    box-shadow: 0 0 4px #ccc;
    overflow: hidden;
  }
</style>
