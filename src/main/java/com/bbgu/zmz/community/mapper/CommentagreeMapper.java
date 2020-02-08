package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Commentagree;
import com.bbgu.zmz.community.model.CommentagreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CommentagreeMapper {
    long countByExample(CommentagreeExample example);

    int deleteByExample(CommentagreeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Commentagree record);

    int insertSelective(Commentagree record);

    List<Commentagree> selectByExampleWithRowbounds(CommentagreeExample example, RowBounds rowBounds);

    List<Commentagree> selectByExample(CommentagreeExample example);

    Commentagree selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Commentagree record, @Param("example") CommentagreeExample example);

    int updateByExample(@Param("record") Commentagree record, @Param("example") CommentagreeExample example);

    int updateByPrimaryKeySelective(Commentagree record);

    int updateByPrimaryKey(Commentagree record);
}