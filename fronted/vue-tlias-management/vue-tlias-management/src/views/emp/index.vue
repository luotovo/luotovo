<script setup>
  import { ref,watch,onMounted } from 'vue'
  import { queryPageApi,addApi,queryInfoApi,updateApi,deleteApi } from '@/api/emp'
  import { queryAllApi as queryAllDeptApi } from '@/api/dept'
  import { ElMessage,ElMessageBox } from 'element-plus'
  //元数据
  //职位列表数据
  const jobs = ref([{ name: '班主任', value: 1 },{ name: '讲师', value: 2 },{ name: '学工主管', value: 3 },{ name: '教研主管', value: 4 },{ name: '咨询师', value: 5 },{ name: '其他', value: 6 }])
  //性别列表数据
  const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
  //部门列表数据
  const depts = ref([])

  const token =ref('')//token

  //搜索表单
  const searchEmp = ref({ name: '', gender: '', date: [],begin :'',end:'' })
  const empList =ref([])

  //侦听searchEmp的date属性
  watch( ()=> searchEmp.value.date ,(newVal, oldVal)=>{
    if(newVal.length  == 2){
      searchEmp.value.begin = newVal[0]
      searchEmp.value.end = newVal[1]
    }else{
      searchEmp.value.begin = ''
      searchEmp.value.end = ''
    }
  } )
  
  //查询
  const search = async () => {
    const result = await queryPageApi(searchEmp.value.name,searchEmp.value.gender,
      searchEmp.value.begin,searchEmp.value.end,currentPage.value,pageSize.value);
    if(result.code){
      empList.value = result.data.rows;
      total.value = result.data.total

    }
  }
  //清空
  const clear = () => { 
    searchEmp.value = { name: '', gender: '', date: [] ,begin :'',end:''};
    search();//清空后查询
  }
  //分页操作的数据模型

  const currentPage = ref(1);//当前页码
  const pageSize = ref(10);//每页显示的条数
  const background = ref(true);//是否显示背景
  const total = ref(0);//总条数

  const handleSizeChange = (val) => {
    search();
    console.log(`每页展示 ${val} 条记录 `);
}
const handleCurrentChange = (val) => {
  search();
  console.log(`当前页码：${val}`)
}
//页面加载完成后，自动调用查询
onMounted(()=>{
  search();//查询员工列表
  queryAllDepts();//查询部门列表
  getToken();//获取token
})

//获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
}

// 查询所有部门
const queryAllDepts = async () => {
  const result = await queryAllDeptApi();
  if(result.code){
    depts.value = result.data; 
  }
}
//侦听employee的工作经历信息


//
//新增/修改表单
const employeeFormRef = ref(null)
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')
const resetForm = () => {
  if (employeeFormRef.value) {
    employeeFormRef.value.resetFields(); // 清除验证状态
  }
  dialogVisible.value = false;
  employee.value = {
    username: '',
    name: '',
    gender: '',
    phone: '',
    job: '',
    salary: '',
    deptId: '',
    entryDate: '',
    image: '',
    exprList: []
  };
}

//文件上传
// const imageUrl = ref('')
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data;
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}
// 新增员工
const addEmp = () => {
 dialogVisible.value = true;
 dialogTitle.value = '新增员工';
 if(employeeFormRef.value){
  employeeFormRef.value.resetFields();//重置表单 
 }

 
}

//添加工作经历
const addExprItem = () => {
 employee.value.exprList.push({company:'',start:'',end:'',job:'',exprDate:[],
 get begin() { return this.exprDate[0] },
  get end() { return this.exprDate[1] }
})}
//删除工作经历
const delExprItem = (index) => {
  employee.value.exprList.splice(index,1);//(从第几个开始，删除几个)
}
//保存员工
const save = async () => {
  // 验证表单
  if(!empFormRef.value)return;
  empFormRef.value.validate(async valid => {
    if(valid){//验证通过
      let result ;
      if(employee.value.id){
        //修改
        result = await updateApi(employee.value);
      }else{
        //新增
         result = await addApi(employee.value);
      }

      if(result.code){
        ElMessage.success('操作成功');
        dialogVisible.value = false;
        search();//查询员工列表
      }else{
        ElMessage.error(result.msg);
      }
    }else{
      ElMessage.error('操作失败');
    }
  })
}
//表单的引用
const empFormRef = ref();

// const save = async () => {
//   const data = { ...employee.value };
//   if (!data.image) delete data.image; // 移除空图片字段
//   const result = await addApi(data);
//   // ...后续逻辑
//表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    /*\
    正则表达式：
    / ... / //表单校验规则 ^ 表示以什么开头 $ 表示以什么结尾
    [3-9] 匹配3到9之间的数字
    \d 匹配数字
    {9} 匹配9个数字
    1 匹配数字1
    */
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});
// 编辑
const  edit = async (id) => { 
  const result = await queryInfoApi(id);
  if(result.code){
    dialogVisible.value = true;
    dialogTitle.value = '修改员工';
    employee.value = result.data;

    // 处理工作经历数据
    let exprList = employee.value.exprList;
    if(exprList && exprList.length > 0){
      exprList.forEach((expr) => {
       expr.exprDate = [expr.begin, expr.end];
      })
    }
  }
};
// 删除
const deleteById = async (id) => { 
  ElMessageBox.confirm('确定删除该员工吗','提示',
    {confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning',}
  ).then(async () => {//点击确认按钮
      const result = await deleteApi(id);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);//服务器的错误信息
      }
    }).catch(() => {//点击取消按钮
      ElMessage.info('取消删除')
    })
};
// 记录勾选的员工id
const selectIds = ref([]);

//复选框勾选发生变化时触发 val为当前选中的行数据(数组)
const handleSelectionChange = (selection) => {
 selectIds.value= selection.map(item => item.id);
}
//批量删除
const deleteByIds = async () => {
  ElMessageBox.confirm('确定删除这些员工吗','提示',
    {confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning',}
  ).then(async () => {//点击确认按钮
    if(selectIds.value.length >0 && selectIds.value){
      const result = await deleteApi(selectIds.value);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);//服务器的错误信息
      }
    }else{
      ElMessage.error('请选择要删除的员工');
    }
  }
  ).catch(() => {//点击取消按钮
      ElMessage.info('取消删除')
    })
}

</script>

<template>
  <h1>员工管理</h1>
  <!-- 搜索表单 -->
  <div class="container"> 
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" clearable />
    </el-form-item>

    <el-form-item label="性别">
      <el-select
        v-model="searchEmp.gender"
        placeholder="请选择性别"
        clearable
      >
        <el-option label="男" value="1" />
        <el-option label="女" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="入职时间">
      <el-date-picker
        v-model="searchEmp.date"
        type="daterange"
        range-separator="到"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="YYYY-MM-DD"

      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button type="info" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>
  </div>
  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">-批量删除</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="empList" border style="width: 100% " @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align ="center" />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column prop="gender" label="性别" width="120"  align="center">
        <template #default="scope">
          <span v-if="scope.row.gender === 1">男</span>
          <span v-else-if="scope.row.gender === 2">女</span>
          <span v-else>未知</span>
          </template>
      </el-table-column> 
      <el-table-column prop="image" label="头像" width="120"  align="center">
        <template #default="scope">
          <img :src ="scope.row.image" height="40px">
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120"  align="center">
        <template #default="scope">
          {{ depts.find(d => d.id === scope.row.deptId)?.name || '未知部门'}}
        </template>
      </el-table-column>
      <el-table-column prop="job" label="职位" width="120"  align="center">
        <template #default="scope">
          <span v-if="scope.row.job==1">班主任</span>
          <span v-else-if="scope.row.job==2">讲师</span>
          <span v-else-if="scope.row.job==3">学工主管</span>
          <span v-else-if="scope.row.job==4">教研主管</span>
          <span v-else-if="scope.row.job==5">咨询师</span>
          <span v-else >其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="salary" label="薪资" width="120"  align="center"/>
      <el-table-column prop="entryDate" label="入职日期" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作"  align="center">
        <template #default="scope">
        <el-button type="primary" size ="small" @click ="edit(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
        <el-button type="danger" size ="small" @click="deleteById(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
      </template>
    </el-table-column>
    </el-table>
  </div>
  <!-- 分页条   -->
   <div class="container">
   <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5,10, 20, 30, 50,75,100]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
   </div>
   <!-- 新增/修改员工的对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle">
      <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
        <!-- 基本信息 --
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
                
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
                
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for=" d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token': token} "
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="employee.image" :src="employee.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>     
        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3"v-for="(expr,index) in employee.exprList" :key="index">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称"v-model="expr.company" ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger"@click="delExprItem(index)" >- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      
      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
    </el-dialog>



</template>



<style scoped>
.container {

  margin: 15px ; 
}
.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>