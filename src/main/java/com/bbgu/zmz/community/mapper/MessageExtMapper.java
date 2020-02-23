package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.Message;
import com.bbgu.zmz.community.model.MessageExample;
import com.bbgu.zmz.community.model.MessageExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MessageExtMapper {
    List<MessageExt> findMessage(@Param("userId") Long userId);
}