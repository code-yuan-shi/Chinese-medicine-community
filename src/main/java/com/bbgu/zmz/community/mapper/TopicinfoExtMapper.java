package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.TopicinfoExt;

import java.util.List;

public interface TopicinfoExtMapper {
   int incView(Topicinfo topicinfo);
   List<TopicinfoExt> getTop10Topics();
   List<TopicinfoExt> getUserTopic(Long id);
}

