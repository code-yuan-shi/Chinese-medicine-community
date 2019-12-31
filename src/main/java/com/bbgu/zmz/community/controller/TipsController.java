package com.bbgu.zmz.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tips")
public class TipsController {
    @GetMapping("/regsuccess")
    public String regSuccess(Model model){
        model.addAttribute("reginfo","注册成功，请前往邮箱激活账号！");
        return "/other/tips";
    }

}
