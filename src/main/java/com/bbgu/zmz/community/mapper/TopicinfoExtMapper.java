package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.TopicinfoExt;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TopicinfoExtMapper {
   int incView(Topicinfo topicinfo);
   List<TopicinfoExt> getTop10Topics();
   List<TopicinfoExt> getUserTopic(Long id);
   //查询一级分类
   List<TopicinfoExt> getCateTopic(@Param("categoryId") Long categoryId);
   //查询未结
   List<TopicinfoExt> getCateTopicNotEnd(@Param("categoryId") Long categoryId);
   //查询已结
   List<TopicinfoExt> getCateTopicIsEnd(@Param("categoryId") Long categoryId);
   //查询精华
   List<TopicinfoExt> getCateTopicIsGood(@Param("categoryId") Long categoryId);

   List<TopicinfoExt> getAllTopic();
   //查询未结
   List<TopicinfoExt> getAllTopicNotEnd();
   //查询已结
   List<TopicinfoExt> getAllTopicIsEnd();
   //查询精华
   List<TopicinfoExt> getAllTopicIsGood();
   //查询二级分类
   List<TopicinfoExt> getAllTopicByKindId(@Param("kindId") Long kindId);
   //查询未结
   List<TopicinfoExt> getAllTopicByKindIdNotEnd(@Param("kindId") Long kindId);
   //查询已结
   List<TopicinfoExt> getAllTopicByKindIdIsEnd(@Param("kindId") Long kindId);
   //查询精华
   List<TopicinfoExt> getAllTopicByKindIdIsGood(@Param("kindId") Long kindId);

   //查询满足一二级分类
   List<TopicinfoExt> getKindTopic(@Param("categoryId") Long categoryId,
                                @Param("kindId") Long kindId);
   //查询未结
   List<TopicinfoExt> getKindTopicNotEnd(@Param("categoryId") Long categoryId,
                                   @Param("kindId") Long kindId);
   //查询已结
   List<TopicinfoExt> getKindTopicIsEnd(@Param("categoryId") Long categoryId,
                                   @Param("kindId") Long kindId);
   //查询精华
   List<TopicinfoExt> getKindTopicIsGood(@Param("categoryId") Long categoryId,
                                   @Param("kindId") Long kindId);

   //搜索
   List<TopicinfoExt> searchTopic(@Param("q") String  q);

   //查询置顶帖子
   List<TopicinfoExt> getTopTopic( @Param("istop") Integer istop,
                                 @Param("offset") Integer offset,
                                @Param("size") Integer size);

   //查询帖子详情
   TopicinfoExt getTopicDetails( @Param("id") Long id);
}

