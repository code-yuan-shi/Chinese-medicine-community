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
        if(user != null){
            int num = messageService.getUnreadMsgCountByUserID(user.getAccountId());
            Map map = new HashMap();
            map.put("count",num);
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
        return new Result().ok(MsgEnum.OK);
    }
        /*
        移除消息
         */
    @PostMapping("remove")
    @ResponseBody
    public Result readMsg(Boolean all,Integer id ,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
         messageService.delMessage(all,user.getAccountId(),id);
        return new Result().ok(MsgEnum.DELETE_SUCCESS);
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
        if(num > 0){
            Map map = new HashMap();
            map.put("action","");
            return new Result().ok(MsgEnum.MESSAGE_SUCCESS,map);
        }else{
            return new Result().error(MsgEnum.SEND_MYSELF);
        }
    }

}
