package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;





    @GetMapping("/login")
    public String login(@RequestParam(name="callback")String url,Model model){
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
        if(is == true && accountId != "" && accountId.length()>=8){
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
            regRespObj.setMsg("账号为不少于8位的数字");
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
    public @ResponseBody  RegRespObj doreg(User user, String retoken, HttpServletRequest request){
        String url = request.getServletContext().getContextPath() + "/tips/regsuccess";
        RegRespObj regRespObj= userService.doreg(user,retoken,url);
        return regRespObj;
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
    public @ResponseBody RegRespObj dologin(User user,String url, HttpServletRequest request){
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
       }else if(user1 == null){
           regRespObj.setStatus(1);
           regRespObj.setMsg("该用户不存在！");
           return regRespObj;
       } else{
           regRespObj.setStatus(1);
           regRespObj.setMsg("用户名或者密码错误！");
           return regRespObj;
       }

    }
    /*
    用户中心
     */
    @GetMapping("set")
    public String userSet(HttpServletRequest request,Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user.getEmail()==null){
            
        }
        model.addAttribute("eml",user.getEmail());
        return "user/set";
    }

    @GetMapping("message")
    public String userMessage(){
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
