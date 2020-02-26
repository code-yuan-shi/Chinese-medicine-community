package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.CommentExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentExtMapper {
    List<CommentExt> findComment(Long userId);
    List<CommentExt> getCommentByTid(@Param("tid") Long tid,
                                     @Param("offset") Integer offset,
                                     @Param("size") Integer size
                                     );
}