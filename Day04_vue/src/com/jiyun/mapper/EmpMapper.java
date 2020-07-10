package com.jiyun.mapper;

import java.util.List;

import com.jiyun.bean.Dept;
import com.jiyun.bean.Emp;

public interface EmpMapper {

	List<Emp> findAll();

	List<Dept> findDept();

	int add(Emp emp);

	int update(Emp emp);

	int deleteAll(Integer[] ids);
	
}
