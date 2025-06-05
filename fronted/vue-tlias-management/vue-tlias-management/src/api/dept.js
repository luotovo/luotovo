import request from "@/utils/request";

//c获取部门列表
export const queryAllApi = () => request.get('/depts');

//添加部门
export const addApi = (dept) => request.post('/depts',dept);
//根据id查询部门
export const queryByIdApi = (id) => request.get(`/depts/${id}`);
//修改部门
export const updateApi = (dept) => request.put('/depts',dept);
//删除部门
export const deleteByIdApi = (id) => request.delete(`/depts?id=${id}`);