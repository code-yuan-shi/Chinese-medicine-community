package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("sign")
public class SignControllder {

    @PostMapping("/status")
    @ResponseBody
    public void signStatus(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {


        } else {

        }
    }
    @PostMapping("/in")
    @ResponseBody
    public RegRespObj signIn(HttpServletRequest request){
       User  user =  (User)request.getSession().getAttribute("user");

       return null;
    }
}

