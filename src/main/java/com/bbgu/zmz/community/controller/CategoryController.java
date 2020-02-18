package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.model.Category;
import com.bbgu.zmz.community.model.Kind;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ListService listService;

    @GetMapping("cate/{column}")
    public String findCateInfo(@PathVariable(name = "column")  Long id,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        Integer offset = (page - 1) * size;
        List<TopicinfoExt> topicinfoExtList = topicService.findCateTopic(id,offset,size,"all");  //查询一级分类
        Long count = topicService.findCateCount(id,"not");
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        List<TopicinfoExt> topicinfoHotList = listService.weekTopic();  //本周热议
        model.addAttribute("weektopics",topicinfoHotList);
        model.addAttribute("kinds",kindList);
        model.addAttribute("categorys",categoryList);
        model.addAttribute("topics",topicinfoExtList);
        model.addAttribute("column",id);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("count",count);
        return "jie/index";
    }
    @GetMapping("cates/{column}/{status}")
    public String findCateInfo(@PathVariable(name = "column")  Long id,
                               @PathVariable(name = "status")  String status,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        Integer offset = (page - 1) * size;
        List<TopicinfoExt> topicinfoExtList = topicService.findCateTopic(id,offset,size,status);  //查询一级分类
        Long count = topicService.findCateCount(id,status);
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        List<TopicinfoExt> topicinfoHotList = listService.weekTopic();  //本周热议
        model.addAttribute("weektopics",topicinfoHotList);
        model.addAttribute("kinds",kindList);
        model.addAttribute("categorys",categoryList);
        model.addAttribute("topics",topicinfoExtList);
        model.addAttribute("column",id);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("count",count);
        model.addAttribute("status",status);
        return "jie/index";
    }
    @GetMapping("cate/{column}/{kind}")
    public String findKindInfo(@PathVariable(name = "column")  Long cid,
                               @PathVariable(name = "kind")  Long kid,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        Integer offset = (page - 1) * size;
        List<TopicinfoExt> topicinfoExtList = topicService.findKindTopic(cid,kid,offset,size,"all");  //查询二级分类
        Long count = topicService.findKindCount(cid,kid,"not");
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        List<TopicinfoExt> topicinfoHotList = listService.weekTopic();  //本周热议
        model.addAttribute("weektopics",topicinfoHotList);
        model.addAttribute("kinds",kindList);
        model.addAttribute("categorys",categoryList);
        model.addAttribute("topics",topicinfoExtList);
        model.addAttribute("column",cid);
        model.addAttribute("fenlei",kid);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("count",count);
        return "jie/index";
    }
    @GetMapping("cate/{column}/{kind}/{status}")
    public String findKindInfo(@PathVariable(name = "column")  Long cid,
                               @PathVariable(name = "kind")  Long kid,
                               @PathVariable(name = "status")  String status,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        Integer offset = (page - 1) * size;
        List<TopicinfoExt> topicinfoExtList = topicService.findKindTopic(cid,kid,offset,size,status);  //查询二级分类
        Long count = topicService.findKindCount(cid,kid,status);
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        List<TopicinfoExt> topicinfoHotList = listService.weekTopic();  //本周热议
        model.addAttribute("weektopics",topicinfoHotList);
        model.addAttribute("kinds",kindList);
        model.addAttribute("categorys",categoryList);
        model.addAttribute("topics",topicinfoExtList);
        model.addAttribute("column",cid);
        model.addAttribute("fenlei",kid);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("count",count);
        model.addAttribute("status",status);
        return "jie/index";
    }
}
