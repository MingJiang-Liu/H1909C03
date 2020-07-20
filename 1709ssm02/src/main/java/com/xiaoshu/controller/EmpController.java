package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Attachment;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.EmpService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("emp")
public class EmpController extends LogController{
	static Logger logger = Logger.getLogger(EmpController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	EmpService es;
	
	
	@RequestMapping("empIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Dept> dList = es.findAllDept();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("dList", dList);
		return "emp";
	}
	
	
	@RequestMapping(value="empList",method=RequestMethod.POST)
	public void userList(Emp emp,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			/*User user = new User();
			String username = request.getParameter("username");
			String roleid = request.getParameter("roleid");
			String usertype = request.getParameter("usertype");*/
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			/*if (StringUtil.isNotEmpty(username)) {
				user.setUsername(username);
			}
			if (StringUtil.isNotEmpty(roleid) && !"0".equals(roleid)) {
				user.setRoleid(Integer.parseInt(roleid));
			}
			if (StringUtil.isNotEmpty(usertype)) {
				user.setUsertype(usertype.getBytes()[0]);
			}*/
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Emp> eList= es.findEmpPage(emp,pageNum,pageSize,ordername,order);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",eList.getTotal() );
			jsonObj.put("rows", eList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	//导入
	@RequestMapping("inEmp")
	public void inEmp(HttpServletRequest request,MultipartFile file,HttpServletResponse response){
		
		JSONObject result=new JSONObject();
		
		try {
			InputStream is = file.getInputStream();
			//创建工作薄
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			//读取sheet页
			XSSFSheet sheet = workbook.getSheetAt(0);
			//读取最后一行
			int lastRowNum = sheet.getLastRowNum();
			ArrayList<Emp> list = new ArrayList<Emp>();
			for (int i = 1; i <=lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				Emp emp = new Emp();
				emp.setEname(row.getCell(1).getStringCellValue());
				emp.setSex(row.getCell(2).getStringCellValue());
				String bir = row.getCell(3).getStringCellValue();
				emp.setBir(TimeUtil.ParseTime(bir, "yyyy-MM-dd"));
				emp.setHobby(row.getCell(4).getStringCellValue());
				
				//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
				if("大数据".equals(row.getCell(5).getStringCellValue())) {
					emp.setDepid(1);
				}else if("物联网".equals(row.getCell(5).getStringCellValue())) {
					emp.setDepid(2);
				}else {
					emp.setDepid(3);
				}
				es.addEmp(emp);
				
			}
			
			result.put("success", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//导出
		
		@RequestMapping("outEmp")
		public void outEmp(HttpServletResponse response){
			
			JSONObject result=new JSONObject();
			List<Emp> list = es.findAllEmp();
			try {
				//创建工作薄
				XSSFWorkbook workbook = new XSSFWorkbook();
				//创建sheet页
				XSSFSheet sheet = workbook.createSheet();
				//创建行row
				XSSFRow row2 = sheet.createRow(0);
				//创建单元格并赋值
				String[] title = {"编号","姓名","性别","生日","爱好","部门"};
				for (int i = 0; i < title.length; i++) {
					XSSFCell cell = row2.createCell(i);
					cell.setCellValue(title[i]);
				}
				for (int i = 0; i < list.size(); i++) {
					XSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(list.get(i).getEid());
					row.createCell(1).setCellValue(list.get(i).getEname());
					row.createCell(2).setCellValue(list.get(i).getSex());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					row.createCell(3).setCellValue(format.format(list.get(i).getBir()));
					row.createCell(4).setCellValue(list.get(i).getHobby());
					row.createCell(5).setCellValue(list.get(i).getDept().getDname());
				}
				
				//导出
				FileOutputStream outputStream = new FileOutputStream("d:/员工信息表.xlsx");
				workbook.write(outputStream);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
	// 新增或修改
	@RequestMapping("reserveEmp")
	public void reserveUser(HttpServletRequest request,Emp emp,String[] hobbys,HttpServletResponse response){
		Integer eId = emp.getEid();
		JSONObject result=new JSONObject();
		String hobby = StringUtils.join(hobbys,",");
		emp.setHobby(hobby);
		try {
			 if (eId != null) {   // userId不为空 说明是修改
				Emp ename = es.existUserWithEmpName(emp.getEname());
				if(ename != null && ename.getEid().compareTo(eId)==0){
					es.updateEmp(emp);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(es.existUserWithEmpName(emp.getEname())==null){  // 没有重复可以添加
					es.addEmp(emp);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteEmp")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				es.deleteEmp(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
