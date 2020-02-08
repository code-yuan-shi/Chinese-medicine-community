package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Qiandao;
import com.bbgu.zmz.community.model.QiandaoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QiandaoMapper {
    long countByExample(QiandaoExample example);

    int deleteByExample(QiandaoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Qiandao record);

    int insertSelective(Qiandao record);

    List<Qiandao> selectByExampleWithRowbounds(QiandaoExample example, RowBounds rowBounds);

    List<Qiandao> selectByExample(QiandaoExample example);

    Qiandao selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Qiandao record, @Param("example") QiandaoExample example);

    int updateByExample(@Param("record") Qiandao record, @Param("example") QiandaoExample example);

    int updateByPrimaryKeySelective(Qiandao record);

    int updateByPrimaryKey(Qiandao record);
}