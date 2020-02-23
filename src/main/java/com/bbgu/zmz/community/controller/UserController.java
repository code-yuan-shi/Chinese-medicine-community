package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.service.MessageService;
import com.bbgu.zmz.community.service.UserService;
import com.bbgu.zmz.community.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public String login(@RequestParam(name = "callback", defaultValue = "/") String url, Model model) {
        model.addAttribute("callback", url);
        return "user/login";
    }

    @GetMapping("/reg")
    public String reg() {
        return "user/reg";
    }

    @GetMapping("/forget")
    public String forget() {
        return "user/forget";
    }

    @GetMapping("/activate")
    public String activate() {
        return "user/activate";
    }

    /*
    发送验证码
     */
    @PostMapping("/reset")
    @ResponseBody
    public Result reset(Long accountId, String email) {
        //RegRespObj regRespObj = new RegRespObj();
        User user = userService.findUserByEmailAndAccountId(accountId,email);
        if(user != null){
            int code = (int)((Math.random()*9+1)*100000);
            Result result =MailUtil.sendActiveMail(email,"code",code);
            if(result.getStatus()==1){
                Map map = new HashMap<>();
                map.put("code",code);
                return new Result().error(MsgEnum.INTERNEET_ERROR,map);
            }else{
                Map map = new HashMap<>();
                map.put("code",code);
                return new Result().ok(MsgEnum.CODE_SUCCESS,map);
            }

        }else {
            return new Result().error(MsgEnum.USER_EMAIL_ERROR);
        }
    }

    /*
    重置密码
     */
    @PostMapping("/repass")
    @ResponseBody
    public Result repass(Long accountId,String check,String code,String pwd) {
       if(check.equals(code)){
            userService.resetUserPwd(accountId,pwd);
           Map map = new HashMap();
           map.put("action","/user/login");
           return new Result().ok(MsgEnum.REPASS_SUCCESS,map);
       }else{
           return new Result().error(MsgEnum.CODE_INCORRECT);
       }
    }

    /*
    检查用户名
     */

    @PostMapping("/checkuser")
    public @ResponseBody Result checkUser(String accountId) {
        Boolean is = isNumeric(accountId);
        if (is == true && accountId != "" && accountId.length() >= 6) {
            Long accountid = Long.parseLong(accountId);
            return userService.checkUser(accountid);
        } else if (accountId == "") {
            return new Result().error(MsgEnum.ACCOUNTID_NOT_ALLOW_EMPTY);
        } else {
            return new Result().error(MsgEnum.ACCOUNTID_NUM_LIMIT);
        }
    }

    /*
    检查邮箱
     */
    @PostMapping("/checkemail")
    public @ResponseBody Result checkEmail(String email) {
        Boolean is = isEmail(email);
        if (is) {
            return   userService.checkEmail(email);
        } else if (email == "") {
        return new Result().error(MsgEnum.EMAIL_NOT_ALLOW_EMPTY);
        } else {
           return new Result().error(MsgEnum.EMAIL_INCORRECT);
        }
    }

    /*
    注册用户
     */
    @PostMapping("/doreg")
    @ResponseBody
    public Result doReg(UserExt userext, String check, HttpServletRequest request) throws Exception {
        String saveCheck = (String) request.getSession().getAttribute("check");
        if (check.equals(saveCheck)) {
            if (userext.getPwd().equals(userext.getRepass())) {
                int code = userService.doReg(userext);
                if (code == 0) {
                    Map map = new HashMap();
                    map.put("action","/");
                    return new Result().ok(MsgEnum.REG_SUCCESS,map);
                } else{
                    return new Result().error(MsgEnum.SEND_EMAIL_FAILE);
                }
            } else {
                return new Result().error(MsgEnum.PWD_ATYPISM);
            }
        } else {
            return new Result().error(MsgEnum.CODE_INCORRECT);
        }
    }

    /*
    重新发送激活邮件
     */

    @PostMapping("resend")
    @ResponseBody
    public Result resendEmail(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
       int code =  userService.resend(user);
        if (code == 0) {
            Map map = new HashMap();
            map.put("action","/");
            return new Result().ok(MsgEnum.EMAIL_RESEND,map);
        } else{
           return new Result().error(MsgEnum.SEND_EMAIL_FAILE);
        }
    }
    /*
    激活用户
     */
    @GetMapping("/activemail/{acode}")
    public String activemail(@PathVariable(name = "acode") String acode, Model model,HttpServletRequest request,HttpServletResponse response){
        int i =userService.activeUser(acode);
        if(i==0) {
            User user = userService.findUserByAccode(acode);
            request.getSession().setAttribute("user",user);
            model.addAttribute("reginfo", "您的账号已成功激活！");
            return "other/tips";
        }else if(i==1){
            model.addAttribute("reginfo", "验证链接已超时，激活失败，请重新提交验证邮件！");
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
    public @ResponseBody Result dologin(User user,String url,String check,HttpServletRequest request,HttpServletResponse response){
        String savecheck = (String)request.getSession().getAttribute("check");
        Map map = new HashMap();
       User user1 =  userService.loginCheck(user);
       if(check.toLowerCase().equals(savecheck)){
           if(user1 != null && user1.getStatus().equals(1)){
               if(url.equals("")){
                   map.put("action","/");
               }else{
                   map.put("action",url);
               }
               request.getSession().setAttribute("user",user1);
               Cookie cookie = new Cookie("token",user1.getToken());
               cookie.setMaxAge(60*60*24*7);
               cookie.setPath("/");
               response.addCookie(cookie);
               return new Result().ok(MsgEnum.LOGIN_SUCCESS,map);
           }else if(user1 != null && user1.getStatus().equals(0)){
               map.put("action","/user/activate");
               request.getSession().setAttribute("user",user1);
               Cookie cookie = new Cookie("token",user1.getToken());
               cookie.setMaxAge(60*60*24*7);
               cookie.setPath("/");
               response.addCookie(cookie);
               return new Result().ok(MsgEnum.LOGIN_SUCCESS,map);
           }else{
               return new Result().error(MsgEnum.USER_PWD_INCORRECT);
           }
       }else {
           return new Result().error(MsgEnum.CODE_INCORRECT);
       }
    }
    /*
    用户中心
     */
    //基本设置
    @GetMapping("set")
    public String userSet(HttpServletRequest request,Model model){
        User user = (User)request.getSession().getAttribute("user");
        User userinfo = userService.findUserInfoById(user.getAccountId());
        request.getSession().setAttribute("user",userinfo);
        model.addAttribute("info",userinfo);
        return "user/set";
    }

    //修改我的资料
    @PostMapping("modifyuserinfo")
    @ResponseBody
    public Result modifyUserInfo(User user,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        user.setAccountId(userinfo.getAccountId());
        return  userService.modifyUserInfo(user);
    }

    //修改头像
    @PostMapping("modifyuseravatar")
    @ResponseBody
    public Result modifyUserAvatar(String avatar,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        return userService.modifyUserAvatar(userinfo.getAccountId(),avatar);
    }

    //修改密码
    @PostMapping("modifypass")
    @ResponseBody
    public Result modifyUserPassword(String nowpass,String pass,String repass,HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        return userService.modifyUserPassword(nowpass,pass,repass,userinfo.getAccountId());
    }

    /*
    获取通知信息
     */
    @GetMapping("message")
    public String userMessage(Model model,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<MessageExt> messageExt= messageService.selMessage(user.getAccountId());
        model.addAttribute("messageList",messageExt);
        return "user/message";
    }

   //查看个人主页
    @GetMapping("home")
    public String userHome(HttpServletRequest request,Model model,@RequestParam(value = "username",defaultValue = "user") String username){
        User user = (User)request.getSession().getAttribute("user");
        if(username.equals("user")){
            User userinfo = userService.findUserInfoById(user.getAccountId());
            model.addAttribute("userinfo",userinfo);
        }else{
            User userinfo = userService.findUserInfoByName(username);
            model.addAttribute("userinfo",userinfo);
        }
       //用户信息
        List<TopicinfoExt> topicinfoExtList = userService.getUserTopic(user.getAccountId());  //发表的帖子
        List<CommentExt> commentExtList = userService.findComment(user.getAccountId());
        model.addAttribute("topicinfos",topicinfoExtList);
        model.addAttribute("comments",commentExtList);
        return "user/home";
    }
    @GetMapping("home/{id}")
    public String otherUserHome(Model model,@PathVariable(value = "id") Long id){
        User userinfo = userService.findUserInfoById(id);  //用户信息
        List<TopicinfoExt> topicinfoExtList = userService.getUserTopic(id);  //发表的帖子
        List<CommentExt> commentExtList = userService.findComment(id);
        model.addAttribute("userinfo",userinfo);
        model.addAttribute("topicinfos",topicinfoExtList);
        model.addAttribute("comments",commentExtList);
        return "user/home";
    }


    //个人中心
    @GetMapping("index")
    public String userIndex(HttpServletRequest request,Model model){
        User user = (User)request.getSession().getAttribute("user");
        List<TopicinfoExt> topicinfoExtList =userService.getUserTopic(user.getAccountId()); //用户发表的帖子
        List<CollectExt>  collectExtList = userService.getUserCollectTopic(user.getAccountId());
        model.addAttribute("topics",topicinfoExtList);
        model.addAttribute("collects",collectExtList);

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


    /*
    奖励用户经验
     */
    @PostMapping("/reward")
    @ResponseBody
    public Result rewardUser(Long userId,Long kissNum,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            if(kissNum != null){
                return userService.rewardUser(userId,kissNum,user);
            }
            return new Result().error(MsgEnum.INPUT_ERROR);
        }
        return new Result().error(MsgEnum.NOTLOGIN);
    }

}
