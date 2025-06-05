package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper  empMapper;
    @Autowired
    private StudentMapper  studentMapper;

    @Override
    public JobOption getEmpJobData() {
        //1.调用mapper接口获取数据
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        // map:pos=教研主管，num= 1

        //2.组装结果返回
        List<Object> jobList = list.stream().map(map ->
                map.get("pos")).toList();
        List<Object> dataList = list.stream().map(map ->
                map.get("num")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {

        return empMapper.countEmpGenderData();
    }

    @Override
    public void getStudentDegreeData() {
        studentMapper.getStudentDegreeData();
    }

    @Override
    public ClazzOption getStudentCountData() {
        List<Map<String, Object>> list = studentMapper.getStudentCountData();
        List<Object> clazzList = list.stream().map(map ->
                map.get("name")).toList();
        List<Object> dataList = list.stream().map(map ->
                map.get("value")).toList();

        return new ClazzOption(clazzList,dataList);
    }
}
