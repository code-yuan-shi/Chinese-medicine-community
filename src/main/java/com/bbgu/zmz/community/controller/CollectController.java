package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
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
    public @ResponseBody Result addCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user.getStatus() != 0){
             return userService.addCollect(user,cid);
        }else{
            return new Result().error(MsgEnum.ALLOWLIMIT);
        }

    }

    /*
    取消收藏
     */
    @PostMapping("remove")
    public @ResponseBody Result removeCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return userService.removeCollect(user,cid);
    }
    /*
    查询帖子是否收藏
     */
    @PostMapping("find")
    public @ResponseBody Result findCollect(Long cid, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return userService.findCollect(user,cid);

    }

}
