package org.example.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.time.LocalDate;
import java.util.List;
@Mapper
public interface ClazzMapper {

    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    void deleteById(Integer id);

    void add(Clazz clazz);

    Clazz getInfo(Integer id);

    void update(Clazz clazz);

    List<Clazz> findAll();
}
