package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Emp implements Serializable {
    @Id
    private Integer eid;

    private String ename;

    private String sex;

    private String hobby;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bir;

    private Integer depid;
    
    @Transient
    private Dept dept;
    @Transient
    private String start;
    @Transient
    private String end;
    
    public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

    /**
     * @return eid
     */
    public Integer getEid() {
        return eid;
    }

    /**
     * @param eid
     */
    public void setEid(Integer eid) {
        this.eid = eid;
    }

    /**
     * @return ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * @param ename
     */
    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    /**
     * @return bir
     */
    public Date getBir() {
        return bir;
    }

    /**
     * @param bir
     */
    public void setBir(Date bir) {
        this.bir = bir;
    }

    /**
     * @return depid
     */
    public Integer getDepid() {
        return depid;
    }

    /**
     * @param depid
     */
    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    @Override
	public String toString() {
		return "Emp [eid=" + eid + ", ename=" + ename + ", sex=" + sex + ", hobby=" + hobby + ", bir=" + bir
				+ ", depid=" + depid + ", dept=" + dept + "]";
	}
}