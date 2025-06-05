<script setup>
import{ref,onMounted } from 'vue'
import {ElMessage,ElMessageBox} from 'element-plus'
import { useRouter } from 'vue-router'
import { updateApi}  from '@/api/emp'
import { EditPen } from '@element-plus/icons-vue'

//当前登录的员工
const loginName = ref('')
const router = useRouter()
const dialogVisible = ref(false)
const formLabelWidth = ref('120px')
const passwordFormRef = ref(null)


const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})
//密码校验规则
const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入旧密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在6到20个字符', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在6到20个字符', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value === passwordForm.value.oldPassword) {
          callback(new Error('新密码不能与旧密码相同'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.value.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const change = () => {
  passwordFormRef.value?.resetFields()
  dialogVisible.value = true
  //
}


//钩子函数
onMounted(() => {
  //获取当前登录的员工
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser && loginUser.name) {
    loginName.value = loginUser.name
  }
})
//退出登录
const logout = () => {
  ElMessageBox.confirm('确定退出登录吗','提示',
    {confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning',}
  ).then(async () => {//点击确认按钮
    ElMessage.success('退出成功');
    //清空本地存储
    localStorage.removeItem('loginUser');
    //跳转到登录页面
    router.push('/login');
  }).catch(() => {//点击取消按钮
      ElMessage.info('取消退出')
    })
}
//修改密码
const handleConfirm =  async () => {
  //1.校验表单,校验通过后提交
  await passwordFormRef.value.validate()
  //2.调用updateApi接口
  const result = await updateApi(passwordForm.value.newPassword)
  //3.提示修改成功，关闭对话框，清空token令牌，跳转到登录页面
  if(result.code){
    ElMessage.success('修改成功')
    dialogVisible.value = false
    //清空token令牌
    localStorage.removeItem('loginUser')
    //跳转到登录页面
    router.push('/login')
  }else{
    ElMessage.error(result.msg || '修改失败') 
  }
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Tlias智能学习辅助系统</span>
        <span class="right_tool">
          <a href="javascript:;" @click="change" class="password-link">
            <el-icon><EditPen /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
        </a>
        <!-- 密码修改对话框 -->
         <el-dialog v-model="dialogVisible" title="修改密码" width="500px">
          <el-form :model="passwordForm" :rules=" passwordRules">
          <el-form-item label="旧密码" prop="oldPassword" :label-width="formLabelWidth">
        <el-input 
          v-model="passwordForm.oldPassword" 
          
          type="password" 
          show-password
          placeholder="请输入旧密码"
        />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword" :label-width="formLabelWidth">
            <el-input 
              v-model="passwordForm.newPassword" 
              type="password" 
              show-password
              placeholder="请输入新密码"
            />
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmPassword" :label-width="formLabelWidth">
            <el-input 
              v-model="passwordForm.confirmPassword" 
              type="password" 
              show-password
              placeholder="请再次输入新密码"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleConfirm">确定</el-button>
          </div>
        </template>
      </el-dialog>
        
          <a href="javasript:;" @click ="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录【{{loginName}}】
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <!-- 左侧菜单栏 -->
      <el-menu router>
        <!-- 首页菜单 -->
          <el-menu-item index="/index">
          <el-icon><Promotion /></el-icon> 首页
        </el-menu-item>
        
        
        <!-- 班级管理菜单 -->
        <el-sub-menu index="/manage">
          <template #title>
            <el-icon><Menu /></el-icon> 班级学员管理
          </template>
          <el-menu-item index="/clazz">
            <el-icon><HomeFilled /></el-icon>班级管理
          </el-menu-item>
          <el-menu-item index="/stu">
            <el-icon><UserFilled /></el-icon>学员管理
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 系统信息管理 -->
        <el-sub-menu index="/system">
          <template #title>
            <el-icon><Tools /></el-icon>系统信息管理
          </template>
          <el-menu-item index="/dept">
            <el-icon><HelpFilled /></el-icon>部门管理
          </el-menu-item>
          <el-menu-item index="/emp">
            <el-icon><Avatar /></el-icon>员工管理
          </el-menu-item>
        </el-sub-menu>

        <!-- 数据统计管理 -->
        <el-sub-menu index="/report">
          <template #title>
            <el-icon><Histogram /></el-icon>数据统计管理
          </template>
          <el-menu-item index="/empReport">
            <el-icon><InfoFilled /></el-icon>员工信息统计
          </el-menu-item>
          <el-menu-item index="/stuReport">
            <el-icon><Share /></el-icon>学员信息统计
          </el-menu-item>
          <el-menu-item index="/log">
            <el-icon><Document /></el-icon>日志信息统计
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>


</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
.password-link {
  color: var(white);
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  cursor: pointer;
  transition: color 0.2s;
}

.password-link:hover {
  color: var(--el-color-primary-light-3);
  text-decoration: none;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}


</style>
