<template>
<div class="login-in">
  <login-logo/>
  <div class="login">
    <div class="login-head">
      <span>帐号登录</span>
    </div>
    <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="demo-ruleForm" >
      <el-alert :title="loginForm.errorMsg" type="error" v-show="loginForm.errorVisible">
      </el-alert>
      <el-form-item prop="username">
        <el-input placeholder="用户名" v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" placeholder="密码" v-model="loginForm.password" @keyup.enter.native="loginIn"></el-input>
      </el-form-item>
      <div class="login-btn">
        <el-button @click="goSignUp()">注册</el-button>
        <el-button type="primary" @click="handleleLoginIn">登录</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
import { mixin } from '../mixins'
import LoginLogo from '../components/LoginLogo'
import { loginIn,getUserInfo } from '../api/system/sysuser'
import {setJwtToken}from '../lib/utils'

export default {
  name: 'login-in',
  components: {
    LoginLogo
  },
  mixins: [mixin],
  data: function () {
    let validateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'))
      } else {
        callback()
      }
    }
    let validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    return {
      loginForm: { // 登录用户名密码
        username: '',
        password: '',
        errorMsg:"",
        errorVisible: false
      },
      rules: {
        username: [
          { validator: validateName, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { validator: validatePassword, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.changeIndex('登录')
  },
  methods: {
    changeIndex (value) {
      this.$store.commit('setActiveName', value)
    },

    //实现用户登录    
    handleleLoginIn () {
      let _this = this
      loginIn(this.loginForm.username,this.loginForm.password)
        .then(res => {
             setJwtToken(res.data)
            _this.$store.commit('setLoginIn', true)
            _this.getUser()
            localStorage.setItem("username",this.loginForm.username);
            this.changeIndex('首页')
             _this.$router.push({path: '/'})
             _this.$router.go(0)
        }).catch(err => {
                    this.loginForm.errorMsg = err.message;
                    this.loginForm.errorVisible = true;
                });
    },

    // 根据用户名反查用户的详细信息并存储。
     getUser(){
      getUserInfo().then(res =>{
         console.log(res);
        this.setdata(res) 
      })
    },
   
   setdata(item){
      this.$store.commit('setUserId', item.id)
      this.$store.commit('setUsername', item.username)
      this.$store.commit('setAvator', item.avator)
   },

   // 注册的页面
    goSignUp () {
      this.$router.push({path: '/sign-up'})
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/login-in.scss';
</style>
