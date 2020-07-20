package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Clazz;
import com.xiaoshu.entity.ClazzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClazzMapper extends BaseMapper<Clazz> {
    long countByExample(ClazzExample example);

    int deleteByExample(ClazzExample example);

    List<Clazz> selectByExample(ClazzExample example);

    int updateByExampleSelective(@Param("record") Clazz record, @Param("example") ClazzExample example);

    int updateByExample(@Param("record") Clazz record, @Param("example") ClazzExample example);
}