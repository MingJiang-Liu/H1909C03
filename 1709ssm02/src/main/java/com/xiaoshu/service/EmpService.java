package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpExample;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class EmpService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	EmpMapper em;
	@Autowired
	DeptMapper dm;

	// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};

	// 数量
	public int countUser(User t) throws Exception {
		return userMapper.selectCount(t);
	};

	// 通过ID查询
	public User findOneUser(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	};

	// 新增
	public void addEmp(Emp emp) throws Exception {
		em.insert(emp);
	};

	// 修改
	public void updateEmp(Emp emp) throws Exception {
		em.updateByPrimaryKeySelective(emp);
	};

	// 删除
	public void deleteEmp(Integer id) throws Exception {
		em.deleteByPrimaryKey(id);
	};

	// 登录
	public User loginUser(User user) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
	public Emp existUserWithEmpName(String eName) throws Exception {
		EmpExample example = new EmpExample();
		com.xiaoshu.entity.EmpExample.Criteria criteria = example.createCriteria();
		criteria.andEnameEqualTo(eName);
		List<Emp> eList = em.selectByExample(example);
		return eList.isEmpty()?null:eList.get(0);
	};

	// 通过角色判断是否存在
	public User existUserWithRoleId(Integer roleId) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}

	public PageInfo<Emp> findEmpPage(Emp emp, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"eid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		List<Emp> eList = em.findAll(emp);
		PageInfo<Emp> pageInfo = new PageInfo<Emp>(eList);
		return pageInfo;
	}

	public List<Dept> findAllDept() {
		// TODO Auto-generated method stub
		List<Dept> dList = dm.selectAll();
		return dList;
	}

	public List<Emp> daochu(){
		
		return em.findAll(null);
	}

	public List<Emp> findAllEmp() {
		// TODO Auto-generated method stub
		List<Emp> list = em.findAllEmp();
		return list;
	}
	

}
