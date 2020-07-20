package cn.jiyun.poi;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class outTest {
	@Test
	public void out() throws Exception{
		
		
		Student student = new Student();
		student.setSid(1);
		student.setSname("张三1");
		student.setSex("男");
		student.setBirthday(new Date("2020/02/02"));
		student.setHobby("LOL,WOW");
		student.setCid(1);
		
		
		Student student1 = new Student();
		student1.setSid(2);
		student1.setSname("张三2");
		student1.setSex("男");
		student1.setBirthday(new Date("2020/02/02"));
		student1.setHobby("LOL,WOW");
		student1.setCid(3);
		
		
		Student student2 = new Student();
		student2.setSid(3);
		student2.setSname("张三3");
		student2.setSex("男");
		student2.setBirthday(new Date("2020/02/02"));
		student2.setHobby("LOL,WOW");
		student2.setCid(3);
		
		List<Student> list = new ArrayList<Student>();
		list.add(student);
		list.add(student1);
		list.add(student2);
		
		
		//1.实例化工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//2.创建sheet页
		XSSFSheet sheet = workbook.createSheet("学生信息");
		//3.创建行row
		XSSFRow row0 = sheet.createRow(0);
		//4.创建单元格并赋值
		String[] title={"编号","姓名","性别","生日","爱好","班级"};
		for (int i = 0; i < title.length; i++) {
			XSSFCell cell = row0.createCell(i);
			cell.setCellValue(title[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(list.get(i).getSid());
			row.createCell(1).setCellValue(list.get(i).getSname());
			row.createCell(2).setCellValue(list.get(i).getSex());
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			row.createCell(3).setCellValue(simpleDateFormat.format(list.get(i).getBirthday()));
			row.createCell(4).setCellValue(list.get(i).getHobby());
			if(1==(list.get(i).getCid())){
				row.createCell(5).setCellValue("H1909A");
			}else if(2==(list.get(i).getCid())){
				row.createCell(5).setCellValue("H1909B");
			}else {
				row.createCell(5).setCellValue("未知");
			}
			
		}
		//5.导出
		FileOutputStream outputStream = new FileOutputStream("D:/学生信息表.xlsx");
		workbook.write(outputStream);
		
	}
	
	
	@Test
	public void in() throws Exception{
		
		FileInputStream is = new FileInputStream("D:/学生信息表.xlsx");
		//1.创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		//2.读取sheet页
		XSSFSheet sheet = workbook.getSheetAt(0);
		//3.获取行
		int lastRowNum = sheet.getLastRowNum();
		ArrayList<Student> list = new ArrayList<Student>();
		for (int i = 1; i < lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			Student student = new Student();
			student.setSid((int)row.getCell(0).getNumericCellValue());
			student.setSname(row.getCell(1).getStringCellValue());
			student.setSex(row.getCell(2).getStringCellValue());
			String bir = row.getCell(3).getStringCellValue();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			student.setBirthday(simpleDateFormat.parse(bir));
			student.setHobby(row.getCell(4).getStringCellValue());
			if("H1909A".equals(row.getCell(5).getStringCellValue())){
				student.setCid(1);
			}else if("H1909B".equals(row.getCell(5).getStringCellValue())){
				student.setCid(2);
			}else{
				student.setCid(3);
			}
			list.add(student);
			
		}
		System.out.println(list);
	}
	
}
