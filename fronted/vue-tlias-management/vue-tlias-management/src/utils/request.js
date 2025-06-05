import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '../router';

//创建axios实例对象
const request = axios.create({
  baseURL: 'api',
  timeout: 600000
})
//axios的请求 request 拦截器-获取localstorage中的token，添加到请求头中
request.interceptors.request.use(
 (config) => { //成功回调
    try {
      //获取token 
      const loginUserStr = localStorage.getItem('loginUser');
      if (loginUserStr) {
        const loginUser = JSON.parse(loginUserStr);
        if (loginUser && loginUser.token) { //判断token是否存在
          config.headers.token = loginUser.token; //添加到请求头中
        }
      }
    } catch (error) {
      console.error('Error processing token:', error);
    }
    return config;
 },
 (error) => { //失败回调
    return Promise.reject(error)
 }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    if(error.response.status === 401){ //token过期
      ElMessage.error('登录超时，请重新登录'); //提示
      localStorage.removeItem('loginUser'); //删除token
      router.push('/login'); //跳转到登录页面
    }else{ //其他错误
      ElMessage.error(error.response.data.message); //提示
    return Promise.reject(error);
  }
}
);

export default request