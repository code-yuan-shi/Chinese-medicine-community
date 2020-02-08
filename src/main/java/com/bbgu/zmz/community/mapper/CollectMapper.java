package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Collect;
import com.bbgu.zmz.community.model.CollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExampleWithRowbounds(CollectExample example, RowBounds rowBounds);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
}