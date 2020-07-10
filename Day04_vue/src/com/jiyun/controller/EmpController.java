package com.jiyun.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiyun.bean.Dept;
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
	
	//跳转到添加
	@RequestMapping("toadd")
	public String toadd() {
		return "add";
	}
	//查询所有部门
	@RequestMapping("findDept")
	@ResponseBody
	public List<Dept> findDept(){
		List<Dept> dlist = empService.findDept();
		return dlist;
	}
	//添加
	@RequestMapping("add")
	@ResponseBody
	public int add(@RequestBody Emp emp) {
		int i = empService.add(emp);
		return i;
	}
	//修改
	@RequestMapping("update")
	@ResponseBody
	public int update(@RequestBody Emp emp) {
		int i = empService.update(emp);
		return i;
	}
	//删除
	@RequestMapping("deleteAll")
	@ResponseBody
	public int update(@RequestBody Integer[] ids) {
		int i = empService.deleteAll(ids);
		return i;
	}
}
