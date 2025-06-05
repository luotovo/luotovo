package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam
    );
    /**
     * 删除班级
     * @param id
     */
    void delete(Integer id);

    /*
    * 添加班级
     */
    void add(Clazz clazz);
    /*
    * 根据id查询班级信息
     */
    Clazz getInfo(Integer id);
    /*
    * 更新班级信息
     */
    void update(Clazz clazz);

    List<Clazz> findAll();
}
