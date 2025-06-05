package org.example.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.StudentMapper;
import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentParam;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public PageResult<Student> page(StudentParam studentParam) {
        //1.设置分页参数（pageHelper)
        PageHelper.startPage(studentParam.getPage(), studentParam.getPageSize());

        //2.执行查询
        List<Student> studentList = studentMapper.list(studentParam);

        //3.封装到PageResult对象中，返回
        Page<Student> P  = (Page<Student>) studentList;
        return new PageResult<>(P.getTotal(), P.getResult());
    }

    @Override
    public void deleteById(List<Integer> ids) {
        studentMapper.deleteById(ids);
    }

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }
        studentMapper.save(student);

    }

    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getInfo(id);

    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void violationHandleById(Integer id, Short violationScore) {
        Student student = studentMapper.getInfo(id);
        if(student == null){
            throw new RuntimeException("学生不存在");
        }
        student.setViolationScore((short) (student.getViolationScore() + violationScore));
        student.setViolationCount((short) (student.getViolationCount() + 1));
        studentMapper.violationHandleById(student);
        studentMapper.update(student);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();


    }


}
