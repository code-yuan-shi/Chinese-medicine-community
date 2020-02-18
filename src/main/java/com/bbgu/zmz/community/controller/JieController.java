package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.dto.ReplyDTO;
import com.bbgu.zmz.community.dto.Rows;
import com.bbgu.zmz.community.dto.TopicInfoDTO;
import com.bbgu.zmz.community.mapper.MessageMapper;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.service.ListService;
import com.bbgu.zmz.community.service.MessageService;
import com.bbgu.zmz.community.service.TopicService;
import com.bbgu.zmz.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/jie")
public class JieController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private ListService listService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/index")
    public String jieIndex(){
        return "jie/index";
    }

    /*
    查看帖子详情
     */
    @GetMapping("/detail/{id}")
    public String jieDetail(@PathVariable(name = "id")  Long id,
                            @RequestParam(name = "page",defaultValue = "1") Integer page,
                            @RequestParam(name = "size",defaultValue = "5") Integer size,
                            Model model,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setId(id);
        topicinfo.setViewCount(1);
        topicService.incView(topicinfo);
        TopicInfoDTO topicInfoDTO = topicService.showDetail(id); //帖子详情
        List<ReplyDTO> replyDTOList = topicService.findComment(id,page,size,user);//查询用户评论
        ReplyDTO replyDTO = topicService.findAcceptComment(id,user);   //查询是否存在采纳
        List<TopicinfoExt> topicinfoExtList = listService.weekTopic();  //本周热议
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        model.addAttribute("kinds",kindList);
        model.addAttribute("categorys",categoryList);
        model.addAttribute("pageid",page);
        model.addAttribute("size",size);
        model.addAttribute("detail",topicInfoDTO);
        model.addAttribute("reply",replyDTOList);
        model.addAttribute("commenta",replyDTO);
        model.addAttribute("weektopics",topicinfoExtList);
        return "jie/detail";
    }

    /*
    跳转发帖页面
     */
    @GetMapping("/add")
    public String jieAdd(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            response.getWriter().write("<script>alert('请先登录！')</script>");
            return "user/login";
        }
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        model.addAttribute("cates",categoryList);
        model.addAttribute("kinds",kindList);
        return "jie/add";

    }
    /*
    编辑帖子
     */
    @GetMapping("/add/{id}")
    public String jieEdit(@PathVariable Long id, Model model){
        List<Category> categoryList = topicService.findCate();
        List<Kind> kindList = topicService.findKind();
        Topicinfo topicinfo = topicService.findTopicById(id);

        model.addAttribute("cates",categoryList);
        model.addAttribute("kinds",kindList);
        model.addAttribute("topic",topicinfo);

        return "jie/add";

    }

    /*
    发表帖子
     */
    @PostMapping("/doadd")
    @ResponseBody
    public RegRespObj jieDoAdd(Topicinfo topicinfo,String check, HttpServletRequest request){
        RegRespObj regRespObj = new RegRespObj();
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        String saveCheck = (String)httpSession.getAttribute("check");
    if(check.equals(saveCheck)){
        if(topicinfo.getId() == null){  //新增
            topicinfo.setUserId(user.getAccountId());
            topicinfo.setTopicCreate(System.currentTimeMillis());
            topicinfo.setTopicModified(topicinfo.getTopicCreate());

            if(topicinfo.getExperience() > user.getKissNum()){
                regRespObj.setStatus(1);
                regRespObj.setMsg("飞吻不够");
            }else{
                int result = topicService.addTopic(topicinfo);
                Long id = topicinfo.getId();
                user.setKissNum(user.getKissNum() - topicinfo.getExperience());
                userService.updateKiss(user);
                if(result > 0){
                    regRespObj.setStatus(0);
                    regRespObj.setMsg("发表成功！");
                    regRespObj.setAction(request.getServletContext().getContextPath() + "/jie/detail/"+id);
                }

            }
        }else{
           int result = topicService.addTopic(topicinfo);
           Long id = topicinfo.getId();
           if(result == 10){
               regRespObj.setStatus(0);
               regRespObj.setMsg("更改成功！");
               regRespObj.setAction(request.getServletContext().getContextPath() + "/jie/detail/"+id);
           }
        }
    }else{
        regRespObj.setStatus(1);
        regRespObj.setMsg("验证码错误！");
    }

        return regRespObj;
    }

    /*
    回复帖子
     */
   @PostMapping("/reply")
    @ResponseBody
    public RegRespObj reply(Comment comment,Long recvUserId,int type,String replyto,HttpServletRequest request){
       Long ids = comment.getId();
        RegRespObj regRespObj = new RegRespObj();
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        if(user != null){
            TopicInfoDTO topicInfoDTO = topicService.showDetail(comment.getTopicId());
            if(topicInfoDTO.getTopicinfo().getStatus() == 1){
                if(type == 1){
                    String content = replyto +" "+ comment.getContent();
                    comment.setContent(content);
                }
               comment.setCommentCreate(System.currentTimeMillis());
               comment.setCommentModified(comment.getCommentCreate());
               comment.setUserId(user.getAccountId());
               comment.setType(0);
               Long id =topicService.insertComment(comment);
               if(type == 1){
                   //二级回复
                   messageService.insMessage(user.getAccountId(),recvUserId,comment.getTopicId(),type,comment.getContent(),ids);
               }else{
                   messageService.insMessage(user.getAccountId(),recvUserId,comment.getTopicId(),type,comment.getContent(),id);
               }
               regRespObj.setStatus(0);
               regRespObj.setCode(0);
               regRespObj.setMsg("回复成功！");
            }else{
                regRespObj.setStatus(1);
                regRespObj.setMsg("该帖未审核，无法评论！");
            }

        }else{
            regRespObj.setStatus(1);
            regRespObj.setMsg("请登录后再操作！");
        }
        return regRespObj;
    }

    /*
    审核帖子
     */
    @PostMapping("/shenhe")
    public @ResponseBody RegRespObj shenhe(Long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
       User user = (User) request.getSession().getAttribute("user");
       RegRespObj regRespObj = new RegRespObj();
       if(user.getRole().equals("管理员") || user.getRole().equals("社区管理员")){
           topicService.shenhe(id);
           regRespObj.setStatus(0);
           regRespObj.setMsg("已审核通过！");
       }else{
           regRespObj.setStatus(1);
           regRespObj.setMsg("没有权限！！！");
       }
        return regRespObj;
    }

    /*
    设置置顶、加精
     */
    @PostMapping("/setstatus")
    public @ResponseBody RegRespObj setTopAndGood(Long id,Integer rank,String field){
        topicService.setTopAndGood(id,rank,field);
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setMsg("状态已更改");
        return regRespObj;
    }

    /*
    删除帖子
     */
    @PostMapping("/delete")
    public @ResponseBody RegRespObj delTopicAndComment(Long id){
        topicService.delTopicAndComment(id);
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setMsg("该帖已删除！");
        return regRespObj;
    }

    /*
    点赞
     */
    @PostMapping("/zan")
  public @ResponseBody RegRespObj jiedaZan(Long id, Boolean ok, HttpServletRequest request){

        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        RegRespObj regRespObj = topicService.jiedaZan(user,id,ok);
        return regRespObj;
    }
    /*
    采纳评论
     */
    @PostMapping("/accept")
    public @ResponseBody RegRespObj acceptComment(Long id,HttpServletRequest request){
       User user =  (User)request.getSession().getAttribute("user");
       RegRespObj regRespObj = topicService.acceptComment(id,user);
        return regRespObj;
    }

    /*
    编辑回复
     */
    @PostMapping("/edit")
    public @ResponseBody RegRespObj editComment(Long id){
        RegRespObj regRespObj = new RegRespObj();
        Rows rows = new Rows();
        String content = topicService.getCommentContentById(id);
        rows.setContent(content);
        regRespObj.setStatus(0);
        regRespObj.setRows(rows);
        return regRespObj;
    }

    /*
    提交评论更改
     */
    @PostMapping("/editsubmit")
    public @ResponseBody RegRespObj editCommentSub(Long id,String content){
        RegRespObj regRespObj = topicService.editCommentSub(id,content);
        return regRespObj;

    }

    /*
    删除评论
     */
    @PostMapping("/delcomment")
    public @ResponseBody RegRespObj delComment(Long id){
        RegRespObj regRespObj = topicService.delComment(id);
        return regRespObj;
    }



}
