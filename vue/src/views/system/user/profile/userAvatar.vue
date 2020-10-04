<template>
    <div>
      <img v-bind:src="options.img" title="点击上传头像" class="img-circle img-lg">

    </div>
</template>

<script>
    import store from "@/store";
    import { VueCropper } from "vue-cropper";

    export default {
      name: "userAvatar",
      components: { VueCropper },
      props:{
        user: {
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
          title: "修改头像",
          options: {
            img: store.getters.avatar, //裁剪图片的地址
            autoCropWidth: 200, // 默认生成截图框宽度
            autoCropHeight: 200, // 默认生成截图框高度
            fixedBox: true // 固定截图框大小 不允许改变
          },

        }
      },
      methods:{

      }
    }
</script>

<style scoped>
  /* image */
  .img-circle {
    border-radius: 50%;
  }

  .img-lg {
    width: 120px;
    height: 120px;
  }
</style>
