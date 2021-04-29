import { get, post, deletes } from '../http'
import  { jwtServerInstance }  from '../index'
import {getTokenUser} from "@/lib/utils";
import qs from	'qs'
// 添加用户信息
export const addUser = (params) => post(`sysuser/add`,params)

//JWT登录认证接口
export const loginIn = (userName,passWord) => {
    return jwtServerInstance.request({
      url:'/authentication',
      method:'post',
      data:{
        username: userName,
        password: passWord
      }
    })
  }
  //获取用户信息接口(个人中心)
export const getUserInfo = () => {
    return jwtServerInstance.request({
      url:'/sysuser/info',
      method:'get',
      params:{
        username: getTokenUser()
      }
    })
  }

 //更新用户的一条数据记录
export const updateUser = (userForm) => {
    return jwtServerInstance.request({
      url:'/sysuser/update',
      method:'post',
      data:userForm
    })
  }