package com.xiaoshu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.xiaoshu.entity.Grade;
import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.GradeService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StuService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("stu")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private StuService stuService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private JedisPool jedisPool;
	
	
	//菜单id
	Integer mid=0;
	//页码
	Integer pNum=0;
	//是否使用Redis
	Boolean userRedis= false;
	
	
	
	//柱状图
	@RequestMapping("showBar")
	public void showBar(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			List<Map> list = stuService.findInfo();
	
			result.put("list", list);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("展示错误",e);
			result.put("errorMsg", "对不起，展示失败");
		}
		WriterUtil.write(response, result.toString());
	}

	
	//导入
	@RequestMapping("inExcel")
	public String inExcel(HttpServletRequest request,Integer menuid,MultipartFile excle ) throws Exception{
		InputStream inputStream = excle.getInputStream();
		//实例化工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		//获取sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		//获取行
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			Student student = new Student();
			student.setName(row.getCell(1).getStringCellValue());
			student.setSex(row.getCell(2).getStringCellValue());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			student.setBirthday(dateFormat.parse(row.getCell(3).getStringCellValue()));
			student.setHobby(row.getCell(4).getStringCellValue());
			
			if(Objects.equals(row.getCell(5).getStringCellValue(), "H1908A")){
				student.setGrade(1);
			}
			student.setSex(row.getCell(2).getStringCellValue());
			stuService.addStu(student);
		}
		
		return "forward:show.htm";
	}
	//导出
	@RequestMapping("outStu")
	public void outStu(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<Student> list = stuService.findStuByIds(ids);
			
			//实例化工作簿
			XSSFWorkbook workbook = new XSSFWorkbook();
			//创建工作表
			XSSFSheet sheet = workbook.createSheet();
			//创建表头 row1
			XSSFRow row0 = sheet.createRow(0);
			//赋值
			row0.createCell(0).setCellValue("学生编号");
			row0.createCell(1).setCellValue("学生姓名");
			row0.createCell(2).setCellValue("性别");
			row0.createCell(3).setCellValue("生日");
			row0.createCell(4).setCellValue("爱好");
			row0.createCell(5).setCellValue("班级");
			//循环赋值
			for (int i = 0; i < list.size(); i++) {
				XSSFRow rowi= sheet.createRow(i+1);
				
				rowi.createCell(0).setCellValue(list.get(i).getId());
				rowi.createCell(1).setCellValue(list.get(i).getName());
				rowi.createCell(2).setCellValue(list.get(i).getSex());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				rowi.createCell(3).setCellValue(format.format(list.get(i).getBirthday()));
				rowi.createCell(4).setCellValue(list.get(i).getHobby());
				rowi.createCell(5).setCellValue(list.get(i).getGrades().getGname());
			}
			//输出
			FileOutputStream outputStream = new FileOutputStream("D:/stuInfo.xlsx");
			
			workbook.write(outputStream);
			//关流
			outputStream.close();
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出错误",e);
			result.put("errorMsg", "对不起，导出失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	//导出所有
		@RequestMapping("outAll")
		public void outAll(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
			    String excelName = "学生信息"+time;
				Student student = new Student();
				List<Student> list = stuService.findAll();
				String[] handers = {"学生编号","学生姓名","性别","生日","爱好","班级"};
				// 1导入硬盘
				ExportExcelToDisk(request,handers,list, excelName);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出错误",e);
				result.put("errorMsg", "对不起，导出失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
		// 导出到硬盘
		@SuppressWarnings("resource")
		private void ExportExcelToDisk(HttpServletRequest request,
				String[] handers, List<Student> list, String excleName) throws Exception {
			
			try {
				HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
				HSSFSheet sheet = wb.createSheet("学生信息");//第一个sheet
				HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
				rowFirst.setHeight((short) 500);
				for (int i = 0; i < handers.length; i++) {
					sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
				}
				//写标题了
				for (int i = 0; i < handers.length; i++) {
				    //获取第一行的每一个单元格
				    HSSFCell cell = rowFirst.createCell(i);
				    //往单元格里面写入值
				    cell.setCellValue(handers[i]);
				}
				for (int i = 0;i < list.size(); i++) {
				    //获取list里面存在是数据集对象
				    Student stu = list.get(i);
				    //创建数据行
				    HSSFRow row = sheet.createRow(i+1);
				    //设置对应单元格的值
				    row.setHeight((short)400);   // 设置每行的高度
				    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
				    row.createCell(0).setCellValue(stu.getId());
				    row.createCell(1).setCellValue(stu.getName());
				    row.createCell(2).setCellValue(stu.getSex());
				    row.createCell(3).setCellValue(stu.getBirthday());
				    row.createCell(4).setCellValue(stu.getHobby());
				    row.createCell(5).setCellValue(stu.getGrades().getGname());
				}
				//写出文件（path为文件路径含文件名）
					OutputStream os;
					File file = new File(request.getSession().getServletContext().getRealPath("/")+"logs"+File.separator+"backup"+File.separator+excleName+".xls");
					
					if (!file.exists()){//若此目录不存在，则创建之  
						file.createNewFile();  
						logger.debug("创建文件夹路径为："+ file.getPath());  
		            } 
					os = new FileOutputStream(file);
					wb.write(os);
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
		}
		
	@RequestMapping("show")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		if(menuid==null ||menuid==0){
			menuid=mid;
		}else{
			mid=menuid;
		}
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Grade> gradeList = gradeService.findAllGrade();
		request.setAttribute("operationList", operationList);
		request.setAttribute("gradeList", gradeList);
		return "stu";
	}
	
	
	@RequestMapping(value="stuList",method=RequestMethod.POST)
	public void stuList(String name ,Integer grade,String start,String end ,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			//获取redis链接
			Jedis jedis = jedisPool.getResource();
			//页码
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			
			
			String infoInRedis = jedis.get("info");
			if(infoInRedis!=null && infoInRedis!=""&&pNum==pageNum&&userRedis){
				System.out.println("redis查询!");
				WriterUtil.write(response,infoInRedis);
			}else{
				String order = request.getParameter("order");
				String ordername = request.getParameter("ordername");
				pNum = pageNum;
				PageInfo<Student> stuList= stuService.findStuPage(name , grade,start , end ,pageNum,pageSize,ordername,order);
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("total",stuList.getTotal() );
				jsonObj.put("rows", stuList.getList());
				String info = jsonObj.toString();
				//建查询结果存入redis
				jedis.set("info", info);
				userRedis=true;
				System.out.println("mysql查询!");
		        WriterUtil.write(response,info);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("addStu")
	public void addStu(HttpServletRequest request,Student stu,String[] hobbys,HttpServletResponse response){
		Integer id = stu.getId();
		String hobby = StringUtils.join(hobbys, ",");
		//String hobby = ArrayUtils.toString(hobbys,",");
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				Student stu1 = stuService.findStuByName(stu.getName());
				if(stu1 != null && stu1.getId().compareTo(id)==0){
					stu.setHobby(hobby);
					stuService.updateStu(stu);
					userRedis=false;
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(stuService.findStuByName(stu.getName())==null){  // 没有重复可以添加
					stu.setHobby(hobby);
					stuService.addStu(stu);
					userRedis=false;
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
	
	
	@RequestMapping("deleteStu")
	public void deleteStu(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				stuService.deleteStu(Integer.parseInt(id));
			}
			result.put("success", true);
			userRedis=false;
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
