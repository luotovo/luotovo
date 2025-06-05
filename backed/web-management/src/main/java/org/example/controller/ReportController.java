package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.service.ReportService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private StudentService studentService;

    /*
    * 员工数据统计
     */
    @RequestMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("员工数据统计");
        JobOption jobOption= reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    /*
    * 员工性别数据统计
     */
    @RequestMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("员工性别数据统计");
        List<Map<String,Object>> genderList= reportService.getEmpGenderData();
        return Result.success(genderList);

    }
    /*
    学员学历统计
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("学员学历统计");
        List<Map<String,Object>> degreeList= studentService.getStudentDegreeData();
        return Result.success(degreeList);
    }
    /*
    班级人数统计
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("班级人数统计");
        ClazzOption countList = reportService.getStudentCountData();
        return Result.success(countList);
    }
}
