<script setup>
  import { ref } from 'vue'
  import { loginApi } from '@/api/login';
  import { ElMessage } from 'element-plus';
  import { useRouter } from 'vue-router';// 导入路由
  
  const loginForm = ref({
  oldPassword: '',
  newPassword: ''
  })

  const loginFormRef = ref(null)  // 添加表单引用
  const router = useRouter(); // 定义路由
  
 

  // 表单验证规则
  const rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
    ]
  }

  // 登录
  const login = async () => {
    if (!loginFormRef.value) return;
    
    try {
      await loginFormRef.value.validate()
      const result = await loginApi(loginForm.value);
      if(result.code === 1){  // 明确检查返回码
        //1.提示信息
        ElMessage.success('登录成功');
        //2.存储当前用户的登录信息
        localStorage.setItem('loginUser',JSON.stringify(result.data));
        //3.跳转页面-首页(路由跳转)
        router.push('/index');
      } else {
        ElMessage.error(result.msg || '登录失败');
      }
    } catch (error) {
      console.error('Login error:', error);
      ElMessage.error('登录失败，请检查输入');
    }
  }

  // 重置
  const clear = () => {
    if (loginFormRef.value) {
      loginFormRef.value.resetFields();
      loginForm.value = {username:'', password:''};  // 确保数据也被重置
    }
  }
</script>

<template>
  <div id="container">
    <div class="login-form">
      <el-form 
        ref="loginFormRef"
        :model="loginForm"
        :rules="rules"
        label-width="80px">
        <p class="title">Tlias智能学习辅助系统</p>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" @keyup.enter="login"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">登 录</el-button>
          <el-button class="button" type="info" @click="clear">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
#container {
  padding: 10%;
  height: 410px;
  background-image: url('../../assets/bg1.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}

.login-form {
  max-width: 400px;
  padding: 30px;
  margin: 0 auto;
  border: 1px solid #74971c;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(30, 92, 46, 0.5);
  background-color: rgb(57, 124, 139);
}

.title {
  font-size: 30px;
  font-family: '楷体';
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
}

.button {
  margin-top: 30px;
  width: 120px;
}
</style>