package com.jiyun.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiyun.bean.Emp;
import com.jiyun.service.EmpService;


@Controller
@RequestMapping("emp")
public class EmpController {
	@Autowired
	EmpService empService;
	
	@RequestMapping("toshow")
	public String toshow() {
		return "show";
	}
	@RequestMapping("findAll")
	@ResponseBody
	public List<Emp> findAll() {
		List<Emp> elist = empService.findAll();
		return elist;
	}
}
