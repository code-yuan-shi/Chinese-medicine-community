package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.TopicInfoDTO;
import com.bbgu.zmz.community.model.WeekList;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ListService listService;

    @GetMapping("/")
    public String index(Model model){

       List<TopicInfoDTO> topicInfoDTOList = topicService.topicTop(1,4);  //置顶帖子
        List<TopicInfoDTO> topicInfoDTOList2 = topicService.topicTop(0,10);  //综合帖子
        List<TopicinfoExt> topicinfoExtList = listService.weekTopic();  //本周热议
        model.addAttribute("topictops",topicInfoDTOList);
        model.addAttribute("topicalls",topicInfoDTOList2);
        model.addAttribute("weektopics",topicinfoExtList);

        return "index";
    }

}
