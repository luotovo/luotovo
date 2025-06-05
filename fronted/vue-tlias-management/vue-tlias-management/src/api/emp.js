import request from "@/utils/request";

//查询员工列表
export const queryPageApi = (name,gender,bengin,end,page,pageSize) => 
    request.get(`/emps?name=${name}&gender=${gender}&bengin=${bengin}&end=${end}&page=${page}&pageSize=${pageSize}`)

//添加
export const addApi = (emp) => request.post('/emps',emp)

//根据id查询
export const queryInfoApi = (id) => request.get(`/emps/${id}`)
//修改
export const updateApi = (emp) => request.put('/emps',emp)
//删除
export const deleteApi = (ids) => request.delete(`/emps?ids=${ids}`)