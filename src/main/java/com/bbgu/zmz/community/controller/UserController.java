package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.MessageExt;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.model.UserExt;
import com.bbgu.zmz.community.service.MessageService;
import com.bbgu.zmz.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;





    @GetMapping("/login")
    public String login(@RequestParam(name="callback",defaultValue = "/")String url,Model model){
        model.addAttribute("callback",url);
        return "user/login";
    }
    @GetMapping("/reg")
    public String reg(){
        return "user/reg";
    }
    @GetMapping("/forget")
    public String forget(){
        return "user/forget";
    }

    /*
    检查用户名
     */

    @PostMapping("/checkuser")
    public @ResponseBody RegRespObj checkUser(String accountId){
        Boolean is = isNumeric(accountId);
        if(is == true && accountId != "" && accountId.length()>=6){
         Long accountid = Long.parseLong(accountId);
         RegRespObj regRespObj = userService.checkUser(accountid);
         return regRespObj;
        }else if(accountId == ""){
            RegRespObj regRespObj = new RegRespObj();
            regRespObj.setStatus(1);
            regRespObj.setMsg("账号不能为空！！！");
            return regRespObj;
        }else{
            RegRespObj regRespObj = new RegRespObj();
            regRespObj.setStatus(1);
            regRespObj.setMsg("账号不能少于6位数");
            return regRespObj;
        }
    }

    /*
    检查邮箱
     */
    @PostMapping("/checkemail")
    public @ResponseBody RegRespObj checkEmail(String email){
        Boolean is = isEmail(email);
        if (is){
            RegRespObj regRespObj = userService.checkEmail(email);
            return  regRespObj;
        }else if(email == ""){
            RegRespObj regRespObj = new RegRespObj();
            regRespObj.setStatus(1);
            regRespObj.setMsg("请先输入邮箱！");
            return regRespObj;
        }else{
            RegRespObj regRespObj = new RegRespObj();
            regRespObj.setStatus(1);
            regRespObj.setMsg("邮箱格式不正确！");
            return regRespObj;
        }
    }

    /*
    注册用户
     */
    @PostMapping("/doreg")
    public String doReg(UserExt userext,HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        if (userext.getPwd().equals(userext.getRepass())) {
            int code = userService.doReg(userext);
            if(code == 0){
                return "redirect:/tips/regsuccess";
            }else{
                response.getWriter().write("<script>alert('很抱歉，失败了,请重试！')</script>");
                return "/user/reg";
            }
        }else{
            response.getWriter().write("<script>alert('两次输入密码不一致！')</script>");
            return "/user/reg";
        }

    }

    /*
    激活用户
     */
    @GetMapping("/activemail/{acode}")
    public String activemail(@PathVariable(name = "acode") String acode, Model model,HttpServletRequest request,HttpServletResponse response){
        int i =userService.activeUser(acode);
        if(i==1) {
            User user = userService.findUserByAccode(acode);
            request.getSession().setAttribute("user",user);
            model.addAttribute("reginfo", "您的账号已成功激活！");
            return "other/tips";
        }else if(i==0){
            model.addAttribute("reginfo", "验证链接已超时，激活失败，请重新提交注册！");
            return "other/tips";
        }else{
            model.addAttribute("reginfo", "该用户已激活，激活链接已失效！");
            return "other/tips";
        }
    }

    /*
    用户登录验证
     */
    @PostMapping("dologin")
    public @ResponseBody RegRespObj dologin(User user,String url,HttpServletRequest request){
        RegRespObj regRespObj = new RegRespObj();
       User user1 =  userService.loginCheck(user);
       if(user1 != null && user1.getStatus().equals(1)){
            regRespObj.setStatus(0);
            regRespObj.setMsg("登录成功！");
            regRespObj.setAction(url);
            request.getSession().setAttribute("user",user1);
            return regRespObj;
       }else if(user1 != null && user1.getStatus().equals(0)){
           regRespObj.setStatus(1);
           regRespObj.setMsg("账号需激活后才能使用！");
           return regRespObj;
       }else{
           regRespObj.setStatus(1);
           regRespObj.setMsg("用户名或者密码错误！");
           return regRespObj;
       }

    }
    /*
    用户中心
     */
    //基本设置
    @GetMapping("set")
    public String userSet(HttpServletRequest request,Model model){
        User user = (User)request.getSession().getAttribute("user");
        User userinfo = userService.findUserInfo(user);
        request.getSession().setAttribute("user",userinfo);
        model.addAttribute("info",userinfo);
        return "user/set";
    }

    //修改我的资料
    @PostMapping("modifyuserinfo")
    @ResponseBody
    public RegRespObj modifyUserInfo(User user,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        user.setAccountId(userinfo.getAccountId());
        RegRespObj regRespObj = userService.modifyUserInfo(user);
        return regRespObj;
    }

    //修改头像
    @PostMapping("modifyuseravatar")
    @ResponseBody
    public RegRespObj modifyUserAvatar(String avatar,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        RegRespObj regRespObj = userService.modifyUserAvatar(userinfo.getAccountId(),avatar);
        return regRespObj;
    }

    //修改密码
    @PostMapping("modifypass")
    @ResponseBody
    public RegRespObj modifyUserPassword(String nowpass,String pass,String repass,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        RegRespObj regRespObj =  userService.modifyUserPassword(nowpass,pass,repass,userinfo.getAccountId());
        return regRespObj;
    }

    @GetMapping("message")
    public String userMessage(Model model,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<MessageExt> messageExt= messageService.selMessage(user.getAccountId());
        model.addAttribute("messageList",messageExt);
        return "user/message";
    }

    @GetMapping("home")
    public String userHome(){
        return "user/home";
    }

    @GetMapping("index")
    public String userIndex(){
        return "user/index";
    }

    /*
判断输入是否为数字
 */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    /*
    判断是否为邮箱
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }


}
