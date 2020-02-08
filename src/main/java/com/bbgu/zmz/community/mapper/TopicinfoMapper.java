package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.TopicinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TopicinfoMapper {
    long countByExample(TopicinfoExample example);

    int deleteByExample(TopicinfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Topicinfo record);

    int insertSelective(Topicinfo record);

    List<Topicinfo> selectByExampleWithBLOBsWithRowbounds(TopicinfoExample example, RowBounds rowBounds);

    List<Topicinfo> selectByExampleWithBLOBs(TopicinfoExample example);

    List<Topicinfo> selectByExampleWithRowbounds(TopicinfoExample example, RowBounds rowBounds);

    List<Topicinfo> selectByExample(TopicinfoExample example);

    Topicinfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Topicinfo record, @Param("example") TopicinfoExample example);

    int updateByExampleWithBLOBs(@Param("record") Topicinfo record, @Param("example") TopicinfoExample example);

    int updateByExample(@Param("record") Topicinfo record, @Param("example") TopicinfoExample example);

    int updateByPrimaryKeySelective(Topicinfo record);

    int updateByPrimaryKeyWithBLOBs(Topicinfo record);

    int updateByPrimaryKey(Topicinfo record);
}