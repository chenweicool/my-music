<template>
  <div class="info">
    <p class="title">编辑个人资料</p>
    <hr/>
    <div class="personal">
      <el-form :model="registerForm" class="demo-ruleForm" label-width="80px">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <!-- <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
        </el-form-item> -->
        <el-form-item label="性别">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机">
          <el-input  placeholder="手机" v-model="registerForm.phoneNum" ></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名">
          <el-input  type="textarea" placeholder="签名" v-model="registerForm.introduction" ></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区">
          <el-select v-model="registerForm.location" placeholder="地区" style="width:100%">
            <el-option
              v-for="item in cities"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    <div class="btn">
      <div @click="saveMsg()">保存</div>
      <div @click="goback">取消</div>
    </div>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import { cities } from '../assets/data/form'
import { updateUserMsg, getUserOfId } from '../api/index'
import { getUserInfo,updateUser } from '../api/system/sysuser'

export default {
  name: 'info',
  data: function () {
    return {
      registerForm: { // 注册
        id:'',
        username: '',
        password: '',
        sex: '',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: '',
        avator:'',  // 用户头像
      },
      cities: []
    }
  },
  computed: {
    // ...mapGetters([
    //   'userId'
    // ])
  },
  created () {
    this.cities = cities
     this.getUser()
  },
  mounted () {
  },
  methods: {
    getUser(){
      getUserInfo().then(res =>{
        this.setdata(res) 
      })
    },

    // 设置数据的值的信息
    setdata(res){
         console.log(res)
       if(res.isok){
         this.registerForm.id = res.data.id
         this.registerForm.username = res.data.username;
         this.registerForm.sex = res.data.sex
         this.registerForm.phoneNum = res.data.phone
         this.registerForm.email = res.data.email
         this.registerForm.location = res.data.location
         this.registerForm.birth = res.data.birth
         this.registerForm.introduction = res.data.introduction
        // this.registerForm.introduction = res.data.avator

          // 存储用户的头像信息和id
         localStorage.setItem("avator",res.data.avator)
         localStorage.setItem("userId",res.data.id)
       }
    },

    goback () {
      this.$router.go(-1)
    },
    saveMsg () {
        updateUser(this.registerForm)
          .then(res => {
            this.$message({message: res.data, type: 'success'});
          })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
