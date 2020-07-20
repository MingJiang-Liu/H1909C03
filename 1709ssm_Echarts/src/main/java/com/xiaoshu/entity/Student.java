package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {
    @Id
    private Integer id;

    private String name;

    private String sex;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private String hobby;

    private Integer grade;
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Transient
    private Grade grades;
    

	private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
     * @return grade
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Grade getGrades() {
		return grades;
	}

	public void setGrades(Grade grades) {
		this.grades = grades;
	}
    @Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", hobby=" + hobby
				+ ", grade=" + grade + ", grades=" + grades + "]";
	}
}