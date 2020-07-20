package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper extends BaseMapper<Emp> {
    long countByExample(EmpExample example);

    int deleteByExample(EmpExample example);

    List<Emp> selectByExample(EmpExample example);

    int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

    int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

	List<Emp> findAll(Emp emp);

	List<Emp> findAllEmp();
}