package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Kind;
import com.bbgu.zmz.community.model.KindExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface KindMapper {
    long countByExample(KindExample example);

    int deleteByExample(KindExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Kind record);

    int insertSelective(Kind record);

    List<Kind> selectByExampleWithRowbounds(KindExample example, RowBounds rowBounds);

    List<Kind> selectByExample(KindExample example);

    Kind selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Kind record, @Param("example") KindExample example);

    int updateByExample(@Param("record") Kind record, @Param("example") KindExample example);

    int updateByPrimaryKeySelective(Kind record);

    int updateByPrimaryKey(Kind record);
}