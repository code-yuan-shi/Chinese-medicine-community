package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("collection")
public class CollectController {

    @Autowired
    private UserService userService;

    /*
    添加收藏
     */
    @PostMapping("add")
    public @ResponseBody RegRespObj addCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        RegRespObj regRespObj = userService.addCollect(user,cid);
        return regRespObj;
    }

    /*
    取消收藏
     */
    @PostMapping("remove")
    public @ResponseBody RegRespObj removeCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        RegRespObj regRespObj = userService.removeCollect(user,cid);
        return regRespObj;
    }
    /*
    查询帖子是否收藏
     */
    @PostMapping("find")
    public @ResponseBody RegRespObj findCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        RegRespObj regRespObj = userService.findCollect(user,cid);
        return regRespObj;
    }

}
