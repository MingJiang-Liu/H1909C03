package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StudentMapper extends BaseMapper<Student> {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    List<Student> selectByExample(StudentExample example);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

	List<Student> findAll(@Param("name")String name, @Param("grade")Integer gtade,@Param("start")String start,@Param("end")String end );

	List<Student> findStuByIds(@Param("ids") String[] ids);

	List<Map> findInfo();
}