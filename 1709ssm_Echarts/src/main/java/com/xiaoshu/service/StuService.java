package com.xiaoshu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;



@Service
public class StuService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	StudentMapper stuMapper;

	// 查询所有
	public List<Student> findAll() throws Exception {
		return stuMapper.findAll(null, null, null, null);
	};

	public PageInfo<Student> findStuPage(String name, Integer grade,String start,String end , int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		List<Student> stuList = stuMapper.findAll(name,grade,start,end);
		PageInfo<Student> pageInfo = new PageInfo<Student>(stuList);
		return pageInfo;
	}
// 学生姓名判重
	public Student findStuByName(String name) {
		// TODO Auto-generated method stub
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		List<Student> list = stuMapper.selectByExample(example);
		return list.isEmpty()?null:list.get(0);
	}
// 添加
	public void addStu(Student stu) {
		// TODO Auto-generated method stub
		stuMapper.insert(stu);
	}
// 修改
	public void updateStu(Student stu) {
		// TODO Auto-generated method stub
		stuMapper.updateByPrimaryKey(stu);
		
	}
// 删除学生
	public void deleteStu(int id) {
		// TODO Auto-generated method stub
		stuMapper.deleteByPrimaryKey(id);
	}
//根据id列表查询
	public List<Student> findStuByIds(String[] ids) {
		// TODO Auto-generated method stub
		List<Student> list= stuMapper.findStuByIds(ids);
		return list;
	}

	public List<Map> findInfo() {
		// TODO Auto-generated method stub
		List<Map> list = stuMapper.findInfo();
		return list;
	}

	
	


}
