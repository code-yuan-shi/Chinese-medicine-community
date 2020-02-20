package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("message")
public class MessageController {


    @Autowired
    private MessageService messageService;

    /*
    获取是否有新通知
     */
    @PostMapping("nums")
    @ResponseBody
    public Result getMsgCount(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        //RegRespObj regRespObj = new RegRespObj();
        if(user != null){
            Long num = messageService.getUnreadMsgCountByUserID(user.getAccountId());
           /* regRespObj.setStatus(0);
            regRespObj.setCount(num.intValue());*/
            Map map = new HashMap();
            map.put("count",num.intValue());
            return new Result().ok(MsgEnum.OK,map);
        }else {
            Map map = new HashMap();
            map.put("count",0);
            return new Result().ok(MsgEnum.OK,map);
        }
    }

    /*
    读取消息
     */
    @PostMapping("read")
    @ResponseBody
    public Result readMsg(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        messageService.updateUserMsgReadState(user.getAccountId());
    /*    RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        return  regRespObj;*/
        return new Result().ok(MsgEnum.OK);
    }
        /*
        移除消息
         */
    @PostMapping("remove")
    @ResponseBody
    public Result readMsg(Boolean all,Integer id ,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        return  messageService.delMessage(all,user.getAccountId(),id);
    }

    /*
    发送消息
     */
    @PostMapping("send")
    @ResponseBody
    public Result sendMsg(Long recvUserId,String content,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user.getStatus()==0){
            return new Result().error(MsgEnum.ALLOWLIMIT);
        }
        int num = messageService.insMessage(user.getAccountId(),recvUserId,null,2,content,null);
       // RegRespObj regRespObj = new RegRespObj();
        if(num > 0){
            /*regRespObj.setStatus(0);
            regRespObj.setMsg("发送成功！");*/
            Map map = new HashMap();
            map.put("action","");
            return new Result().ok(MsgEnum.MESSAGE_SUCCESS,map);
        }else{
           /* regRespObj.setStatus(1);
            regRespObj.setMsg("服务器冒烟了,请稍后再试！");*/
           return new Result().error(MsgEnum.MESSAGE_FAILE);
        }
    }



    }
