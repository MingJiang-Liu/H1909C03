package com.jiyun.bean;

public class Dept {
	private Integer depid;
	private String dname;
	public Integer getDepid() {
		return depid;
	}
	public void setDepid(Integer depid) {
		this.depid = depid;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	@Override
	public String toString() {
		return "Dept [depid=" + depid + ", dname=" + dname + "]";
	}
	
	
}
