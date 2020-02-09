package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.CommentExt;

import java.util.List;

public interface CommentExtMapper {
    List<CommentExt> findComment(Long userId);




}