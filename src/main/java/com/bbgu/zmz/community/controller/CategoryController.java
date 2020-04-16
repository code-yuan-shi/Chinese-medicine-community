package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.TopicService;
import com.bbgu.zmz.community.util.StringDate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ListService listService;

    /*
    获取板块的所有帖子信息
     */
    @GetMapping("cate/{column}")
    public String findCateInfo(@PathVariable(name = "column")  Long id,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               Model model
                               ){
        PageHelper.startPage(page, size);
        List<TopicinfoExt> topicinfoExtList = transTime(topicService.findCateTopic(id,"all"));  //查询一级分类
        PageInfo<?> pageList = new PageInfo<>(topicinfoExtList);
        //int count = topicService.findCateCount(id,"not");
        Map map = new HashMap<>();
        map.put("topics",topicinfoExtList);
        map.put("column",id);
        map.put("page",page);
        map.put("size",size);
        map.put("count",pageList.getTotal());
        model.addAttribute("topicMap",map);
        return "jie/index";
    }
    @GetMapping("cates/{column}/{status}")
    public String findCateInfo(@PathVariable(name = "column")  Long id,
                               @PathVariable(name = "status")  String status,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               Model model){
        PageHelper.startPage(page, size);
        List<TopicinfoExt> topicinfoExtList = transTime(topicService.findCateTopic(id,status));  //查询一级分类
        long count = new PageInfo<>(topicinfoExtList).getTotal();
        Map map = new HashMap<>();
        map.put("topics",topicinfoExtList);
        map.put("column",id);
        map.put("page",page);
        map.put("size",size);
        map.put("count",count);
        map.put("status",status);
        model.addAttribute("topicMap",map);
        return "jie/index";
    }
    @GetMapping("cate/{column}/{kind}")
    public String findKindInfo(@PathVariable(name = "column")  Long cid,
                               @PathVariable(name = "kind")  Long kid,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               Model model){
        PageHelper.startPage(page, size);
        List<TopicinfoExt> topicinfoExtList = transTime(topicService.findKindTopic(cid,kid,"all"));  //查询二级分类
        long count = new PageInfo<>(topicinfoExtList).getTotal();
        Map map = new HashMap<>();
        map.put("topics",topicinfoExtList);
        map.put("column",cid);
        map.put("page",page);
        map.put("size",size);
        map.put("count",count);
        map.put("fenlei",kid);
        model.addAttribute("topicMap",map);
        return "jie/index";
    }
    @GetMapping("cate/{column}/{kind}/{status}")
    public String findKindInfo(@PathVariable(name = "column")  Long cid,
                               @PathVariable(name = "kind")  Long kid,
                               @PathVariable(name = "status")  String status,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               Model model){
        PageHelper.startPage(page, size);
        List<TopicinfoExt> topicinfoExtList = transTime(topicService.findKindTopic(cid,kid,status));  //查询二级分类
        long count = new PageInfo<>(topicinfoExtList).getTotal();
        Map map = new HashMap<>();
        map.put("topics",topicinfoExtList);
        map.put("column",cid);
        map.put("page",page);
        map.put("size",size);
        map.put("count",count);
        map.put("fenlei",kid);
        map.put("status",status);
        model.addAttribute("topicMap",map);
        return "jie/index";
    }

    /*
    转换时间
     */
    public List<TopicinfoExt> transTime(List<TopicinfoExt> topicinfoExtList){
        for (TopicinfoExt topicinfoExt:topicinfoExtList){
            topicinfoExt.setTime(StringDate.getStringDate(new Date(topicinfoExt.getTopicCreate())));
        }
        return topicinfoExtList;
    }


}
