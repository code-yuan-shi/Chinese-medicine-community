package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.CollectExt;

import java.util.List;
import java.util.Map;

public interface CollectExtMapper {
   void delCollectInfo(Map map);
   int getIsCollectInfo(Map map);
   List<CollectExt> getUserCollectTopic(Long id);
}