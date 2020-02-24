package com.bbgu.zmz.community.mapper;

import com.bbgu.zmz.community.model.MessageExt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageExtMapper {
    List<MessageExt> findMessage(@Param("userId") Long userId);
}