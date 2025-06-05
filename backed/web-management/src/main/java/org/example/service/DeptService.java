package org.example.service;

import org.example.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DeptService {
    /**
     * 查询所有部门
     * @return
     */
    List<Dept> findAll();

    /*
    根据ID删除部门
     */
    void deleteById(Integer id);

    /*
    添加部门
     */
    void add(Dept dept);

    Dept findById(Integer id);

    void update(Dept dept);
}
