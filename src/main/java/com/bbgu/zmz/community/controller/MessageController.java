package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.Message;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.messageresolver.IMessageResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("message")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @PostMapping("nums")
    @ResponseBody
    public RegRespObj getMsgCount(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        RegRespObj regRespObj = new RegRespObj();
        if(user != null){
            Long num = messageService.getUnreadMsgCountByUserID(user.getAccountId());
            regRespObj.setStatus(0);
            regRespObj.setCount(num.intValue());
        }
        return regRespObj;
    }
    @PostMapping("read")
    @ResponseBody
    public RegRespObj readMsg(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        messageService.updateUserMsgReadState(user.getAccountId());
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        return  regRespObj;
    }

    @PostMapping("remove")
    @ResponseBody
    public RegRespObj readMsg(Boolean all,Integer id ,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        RegRespObj regRespObj = messageService.delMessage(all,user.getAccountId(),id);
        return regRespObj;
    }




    }

