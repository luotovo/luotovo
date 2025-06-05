package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.Utils.JwtUtils;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;


//    @Override
//    public PageResult<Emp> page(@RequestParam(defaultValue = "1") Integer page,
//                                @RequestParam(defaultValue = "10") Integer pageSize) {
//        //1.调用mapper接口，查询记录数
//        Long total = empMapper.count();
//        //2.调用mapper接口，查询分页数据
//        Integer start = (page-1)*pageSize;
//        List<Emp> rows = empMapper.list(start,pageSize);
//
//        //3.封装到PageResult对象中，返回
//        return new PageResult<Emp>(total,rows
//        );



    /*
     基于pageHelper分页插件实现分页查询
     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize,
//                                String name, Integer gender,
//                                LocalDate begin, LocalDate end
//    ) {
//        //1.设置分页参数（pageHelper)
//        PageHelper.startPage(page,pageSize);
//
//        //2.执行查询
//        List<Emp> empList = empMapper.list(name,gender,begin,end);
//
//        //3.封装到PageResult对象中，返回
//        Page<Emp> P  = (Page<Emp>) empList;
//        return new PageResult<>(P.getTotal(),P.getResult());
//
//
//    }
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页参数（pageHelper)
        PageHelper.startPage( empQueryParam.getPage(),empQueryParam.getPageSize());

        //2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        //3.封装到PageResult对象中，返回
        Page<Emp> P  = (Page<Emp>) empList;
        return new PageResult<>(P.getTotal(),P.getResult());


    }
    @Transactional(rollbackFor = {Exception.class })//事务注解
    @Override
    public void save(Emp emp) {
        try {
            //1.保存员工的基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.inset(emp);

//        if(true){
//            throw new RuntimeException("模拟事务回滚");
//        }


            //2.储存员工的详细信息
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合，为empId赋值
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);

            }
        } finally { //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(),"新增员工："+emp);
            empLogService.insertLog(empLog);
        }

        }


    @Transactional(rollbackFor = {Exception.class })
    @Override
    public void delete(List<Integer> ids) {
        //1.批量删除员工基本信息
        empMapper.deleteByIds(ids);

        //2.批量删除员工详细信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getInfo(id);
    }

    @Transactional(rollbackFor = {Exception.class })
    @Override
    public void update(Emp emp) {
        //1.根据id修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2.根据id修改工作信息
        //2.1先删除
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        //2.2再插入
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1.调用mapper接口，根据用户名和密码查询
        Emp e =empMapper.selectByUernameAndPassword(emp);
        //2.判断用户名密码是否正确，如果存在，组装登录信息
        if(e!=null){
            log.info("登录成功：{}",e);
            //生成jwt令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());

            String jwt = JwtUtils.generateToken(claims);
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }
        //3.不存在，返回null
        return null;
    }

    @Override
    public List<Emp> find(Integer empId, String name) {
        return empMapper.find(empId,name);

    }


}
































