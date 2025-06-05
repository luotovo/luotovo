package org.example.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Student;
import org.example.pojo.StudentParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {


   public List<Student> list(StudentParam studentParam);

   void deleteById(List<Integer> ids);

   void save(Student student);

   Student getInfo(Integer id);

   void update(Student student);

    void violationHandleById(Student student);
    @MapKey("degree")
    List<Map<String, Object>> getStudentDegreeData();
    @MapKey("count")
    List<Map<String, Object>> getStudentCountData();
}
