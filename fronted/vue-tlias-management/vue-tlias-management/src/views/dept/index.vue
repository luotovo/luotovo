<script setup>
  import { ref,onMounted} from 'vue';
  import {queryAllApi,addApi,queryByIdApi,updateApi,deleteByIdApi } from '@/api/dept';
  import { ElMessage,ElMessageBox } from 'element-plus';

  
//钩子函数
onMounted(()=>{
  search();
})

  //查询
  const search = async () => {
    // const result = await anxios.get('/depts')
    // if(result.data.code){//JS隐式类型转换 0为false 其他为true ''为 其他为true
    //   tableData.value = result.data.data;
    // }
    const result = await queryAllApi();
    if(result.code){//JS隐式类型转换 0为false 其他为true ''为 其他为true
      deptList.value = result.data;
    }
  
  }
  const deptList = ref([]);

  //DIALOG对话框
  const dialogFormVisible = ref(false);
  const dept  = ref({name:''});
  const formTitle = ref('');
  //保存
  const save = async () => { 
    //校验表单数据
    if (!deptFormRef.value) {
      return;}
    deptFormRef.value.validate(async valid => {
      if (valid) {
        let result;
       if(dept.value.id) {
         result = await updateApi(dept.value);
       }else{result = await addApi(dept.value);}

       if(result.code){ 
        ElMessage.success('操作成功');
        dialogFormVisible.value = false;
        search();
       }else{
          ElMessage.error(result.msg); 
       }
      }else{
        ElMessage.error('操作失败');
      }
    })
    
  };
//
  const addDept = () => {
    dialogFormVisible.value = true; 
    formTitle.value = '新增部门';
    dept.value = {name:''};
    //重置表单的校验规则
    if (!deptFormRef.value) {
  deptFormRef.resetFields()
  }
}
  //表单校验
  const rules = ref({
  name: [
    { required: true, message: '部门名称是必须填', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称的长度应该在2-10位', trigger: 'blur' },
  ]
})
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  formTitle.value = '修改部门';
  const result = await queryByIdApi(id); 
  if(result.code){
    dialogFormVisible.value = true;
  }//显示对话框
  if (deptFormRef.value) {
  deptFormRef.value.resetFields();}

  dept.value = result.data ;
};
//删除
const deleteById = async (id) => {
  //确认框
  ElMessageBox.confirm('确定删除该部门吗','提示',
    {confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning',}
  ).then(async () => {//点击确认按钮
      const result = await deleteByIdApi(id);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);//服务器的错误信息
      }
    }).catch(() => {//点击取消按钮
      ElMessage.info('取消删除')
    })



}



</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept">+新增部门</el-button>
  </div>

  <div class="container">
    <el-table :data="deptList" border style="width: 100%" >
    <el-table-column type="index" label="序号" width="100"align ="center" />
    <el-table-column prop="name" label="部门名称" width="200"align ="center" />
    <el-table-column prop="updateTime" label="最后操作时间" width="300"align ="center"/>
    <el-table-column prop="address" label="操作" align ="center">
      <template #default="scope">
        <el-button type="primary" size ="small" @click ="edit(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
        <el-button type="danger" size ="small" @click="deleteById(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  </div>
  <!-- Diolog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules ="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width= " 80px " prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>


</template>

<style scoped>
.container {
  margin-top: 10px;
}
</style>
