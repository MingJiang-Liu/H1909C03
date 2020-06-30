package com.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
