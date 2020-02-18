package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.TopicInfoDTO;
import com.bbgu.zmz.community.model.Category;
import com.bbgu.zmz.community.model.Kind;
import com.bbgu.zmz.community.model.WeekList;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        model.addAttribute("kinds",kindList);
         model.addAttribute("categorys",categoryList);
        model.addAttribute("topictops",topicInfoDTOList);
        model.addAttribute("topicalls",topicInfoDTOList2);
        model.addAttribute("weektopics",topicinfoExtList);

        return "index";
    }
    @GetMapping("/search")
    public String searchResult(@RequestParam(value = "q") String q,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        Integer offset = (page - 1)*size;
        Long count = topicService.searchTopicCount(q);
        List<TopicinfoExt> topicinfoExtList = topicService.searchTopic(q,offset,size);
        model.addAttribute("count",count);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("topics",topicinfoExtList);
        return "/jie/search";
    }


}
