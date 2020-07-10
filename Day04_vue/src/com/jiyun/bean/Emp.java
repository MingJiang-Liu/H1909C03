package com.jiyun.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Emp {
	private Integer eid;
	private String ename;
	private String sex;
	private String pic;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date bir;
	private Integer depid;
	private Dept dept;
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Date getBir() {
		return bir;
	}
	public void setBir(Date bir) {
		this.bir = bir;
	}
	public Integer getDepid() {
		return depid;
	}
	public void setDepid(Integer depid) {
		this.depid = depid;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	@Override
	public String toString() {
		return "Emp [eid=" + eid + ", ename=" + ename + ", sex=" + sex + ", pic=" + pic + ", bir=" + bir + ", depid="
				+ depid + ", dept=" + dept + "]";
	}
	
}
