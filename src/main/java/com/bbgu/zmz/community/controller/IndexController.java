package com.bbgu.zmz.community.controller;
import com.alibaba.fastjson.JSON;
import com.bbgu.zmz.community.model.Category;
import com.bbgu.zmz.community.model.Kind;
import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.TopicService;
import com.bbgu.zmz.community.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "首页", tags = {"首页展示的相关接口"})
@Controller
public class IndexController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ListService listService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "GET")
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        //直接查数据库
        List<TopicinfoExt> topicInfoExtListTop = topicService.topicTop(1,0,5);  //置顶帖子
        List<TopicinfoExt> topicInfoExtListAll = topicService.topicTop(0,0,12);  //综合帖子
        List<TopicinfoExt> topicinfoExtListWeek = listService.weekTopic();  //本周热议
        List<Category> categoryList = topicService.findCate();  //查询一级分类
        List<Kind> kindList = topicService.findKind();  //查询二级分类
        List<Topicinfo> topicinfoList = topicService.findTopicStatus(); //未审核
        request.getServletContext().setAttribute("notcheck",topicinfoList);  //未审核
        model.addAttribute("topictops",topicInfoExtListTop);
        model.addAttribute("topicalls",topicInfoExtListAll);
        //从缓存中取
        request.getServletContext().setAttribute("kinds",kindList);            //二级分类
        request.getServletContext().setAttribute("categorys",categoryList);    //一级分类
        request.getServletContext().setAttribute("weektopics",topicinfoExtListWeek);  //本周热议

        return "index";
    }
    @ApiOperation(value = "搜索帖子", notes = "搜索帖子", httpMethod = "GET")
    @GetMapping("/search")
    public String searchResult(
            @ApiParam(name = "q", value = "标题关键字", required = true)
            @RequestParam(value = "q") String q,
            @ApiParam(name = "page", value = "当前页", required = true)
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @ApiParam(name = "size", value = "分页数量", required = true)
            @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model){
        PageHelper.startPage(page, size);
        List<TopicinfoExt> topicInfoExtList = topicService.searchTopic(q);
        long count = new PageInfo<>(topicInfoExtList).getTotal();
        Map map = new HashMap();
        map.put("count",count);
        map.put("page",page);
        map.put("size",size);
        map.put("topics",topicInfoExtList);
        model.addAttribute("searchMap",map);
        return "/jie/search";
    }


}
