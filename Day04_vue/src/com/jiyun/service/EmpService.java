package com.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiyun.bean.Dept;
import com.jiyun.bean.Emp;
import com.jiyun.mapper.EmpMapper;

@Service
public class EmpService {
	@Autowired
	private EmpMapper empMapper;

	public List<Emp> findAll() {
		// TODO Auto-generated method stub
		List<Emp> elist = empMapper.findAll();
		return elist;
	}

	public List<Dept> findDept() {
		// TODO Auto-generated method stub
		List<Dept> dlist = empMapper.findDept();
		return dlist;
	}

	public int add(Emp emp) {
		// TODO Auto-generated method stub
		int i = empMapper.add(emp);
		return i;
	}

	public int update(Emp emp) {
		// TODO Auto-generated method stub
		int i = empMapper.update(emp);
		return i;
	}

	public int deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		int i = empMapper.deleteAll(ids);
		return i;
	}
}
