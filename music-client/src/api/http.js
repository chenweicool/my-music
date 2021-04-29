/* eslint-disable */
import axios from 'axios';
import {getJwtToken} from "../lib/utils";
import {Message} from 'element-ui'

axios.defaults.timeout = 5000;  //超时时间设置
axios.defaults.withCredentials = true;  //true允许跨域
//Content-Type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

// 设置环境
if (process.env.NODE_ENV === 'production') {
  /*第二层if，根据.env文件中的VUE_APP_FLAG判断是生产环境还是测试环境*/
  if (process.env.VUE_APP_FLAG === 'pro') {
    //production 生产环境
    axios.defaults.baseURL = 'http://localhost:9999';
  } else {
    //test 测试环境
    axios.defaults.baseURL = 'http://localhost:9999';
  }
} else {
  //dev 开发环境
  axios.defaults.baseURL = 'http://localhost:9999';
}

//添加请求的token
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  if (config.url !== "/authentication") {
    // 让每个请求携带token
    config.headers['JWTHeaderName'] = getJwtToken();

    //将token的值存储起来
    localStorage.setItem("JWTHeaderName",getJwtToken());
    }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
})

// 响应拦截器
axios.interceptors.response.use(
  response => {
    const res = response.data;
    if (!res.isok) {
         //console.log(res)
        throw res;
    } else {
        return res;
    }
  },

  error => {
    if (error && error.response) {
        switch (error.response.code) {
            case 400:
                error.message = error.response.data.message;
                break;
            case 401:
                error.message = '未授权，请重新登录';
                break;
            case 403:
                error.message = '拒绝访问';
                break;
            case 404:
                error.message = '请求错误,未找到该资源';
                break;
            case 405:
                error.message = '请求方法未允许';
                break;
            case 408:
                error.message = '请求超时';
                break;
            case 500:
                error.message = error.response.data.message;
                break;
            case 501:
                error.message = '网络未实现';
                break;
            case 502:
                error.message = '网络错误';
                break;
            case 503:
                error.message = '服务不可用';
                break;
            case 504:
                error.message = '网络超时';
                break;
            case 505:
                error.message = 'http版本不支持该请求';
                break;
            default:
                error.message = error.response.code;
        }
    } else {
        error.message = "连接到服务器失败";
    }
    Message({message: error.message, type: 'error'});
    return Promise.reject(error);
}
  );



/**
   * 封装get方法
   * @param url
   * @param data
   * @returns {Promise}
   */

export function get(url, params = {}) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    })
      .then(response => {
        resolve(response.data);
      })
      .catch(err => {
        reject(err)
      })
  })
}


/**
   * 封装post请求
   * @param url
   * @param data
   * @returns {Promise}
   */

export function post(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.post(url, data)
      .then(response => {
        resolve(response.data);
      }, err => {
        reject(err)
      })
  })
}

/**
   * 封装delete请求
   * @param url
   * @param data
   * @returns {Promise}
   */

export function deletes(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.delete(url, data)
      .then(response => {
        resolve(response.data);
      }, err => {
        reject(err)
      })
  })
}

/**
   * 封装put请求
   * @param url
   * @param data
   * @returns {Promise}
   */

export function put(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.put(url, data)
      .then(response => {
        resolve(response.data)
      }, err => {
        reject(err)
      })
  })
}
