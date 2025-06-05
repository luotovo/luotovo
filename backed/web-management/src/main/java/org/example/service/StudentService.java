package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentParam;

import java.util.List;
import java.util.Map;

public interface StudentService {
    PageResult<Student> page(StudentParam studentParam);

    void deleteById(List<Integer> ids);

    void save(Student student);


    Student getInfo(Integer id);

    void update(Student student);


    void violationHandleById(Integer id, Short violationScore);

    List<Map<String, Object>> getStudentDegreeData();
}
