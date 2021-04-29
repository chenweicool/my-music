<template>
  <div class="upload">
    <p class="title">修改头像</p>
    <hr/>
    <div class="section">
      <el-upload
        drag
        :action="uploadUrl()"
        :headers="myHeaders"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>修改头像</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过10M</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mixin } from '../mixins'
var token = localStorage.getItem("JWTHeaderName");
export default {
  name: 'upload',
  mixins: [mixin],
  data () {
    return {
      imageUrl: '',
      userId:'',
      myHeaders:{JWTHeaderName: token},
    }
  },
  created(){
     this.userId = localStorage.getItem("userId")
  },

  methods: {
    uploadUrl () {
      return `${this.$store.state.configure.HOST}/sysuser/updatePicture?id=${this.userId}`
    },
    handleAvatarSuccess (res, file) {
      console.log(res)
      if (res.isok) {
        this.imageUrl = URL.createObjectURL(file.raw)
        this.$store.commit('setAvator', res.avator)
        this.$message({
          message: res.data,
          type: 'success'
        })
      } else {
        this.notify('修改失败', 'error')
      }
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/upload.scss';
</style>
