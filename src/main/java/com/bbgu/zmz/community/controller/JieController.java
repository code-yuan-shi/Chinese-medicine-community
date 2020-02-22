package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.*;
import com.bbgu.zmz.community.enums.MsgEnum;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        topicService.incView(topicinfo);                            //更新浏览数
        TopicinfoExt TopicinfoExt = topicService.showDetail(id);    //帖子详情
        List<CommentExt> CommentExtList = topicService.findComment(id,page,size,user);  //查询用户评论
        //ReplyDTO replyDTO = topicService.findAcceptComment(id,user);   //查询是否存在采纳
        model.addAttribute("pageid",page);
        model.addAttribute("size",size);
        model.addAttribute("detail",TopicinfoExt);
        model.addAttribute("reply",CommentExtList);
        model.addAttribute("count",TopicinfoExt.getCommentNum());
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
    public Result jieDoAdd(Topicinfo topicinfo, String check, HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        String saveCheck = (String)httpSession.getAttribute("check");
    if(check.equals(saveCheck)){
        if(topicinfo.getId() == null){  //新增
            topicinfo.setUserId(user.getAccountId());
            topicinfo.setTopicCreate(System.currentTimeMillis());
            topicinfo.setTopicModified(topicinfo.getTopicCreate());
            if(topicinfo.getExperience() > user.getKissNum()){
                return new Result().error(MsgEnum.KISS_NOT_ENOUGHT);
            }else{
                int result = topicService.addTopic(topicinfo);
                Long id = topicinfo.getId();
                user.setKissNum(user.getKissNum() - topicinfo.getExperience());
                userService.updateKiss(user);
                if(result > 0){
                    Map map = new HashMap<>();
                    map.put("action",request.getServletContext().getContextPath() + "/jie/detail/"+id);
                    return new Result().ok(MsgEnum.DETAIL_SUCCESS,map);
                }

            }
        }else{
           int result = topicService.addTopic(topicinfo);
           Long id = topicinfo.getId();
           if(result == 10){
               Map map = new HashMap<>();
               map.put("action",request.getServletContext().getContextPath() + "/jie/detail/"+id);
               return new Result().ok(MsgEnum.CHANGE,map);
           }
        }
    }else{
        return new Result().error(MsgEnum.CODE_INCORRECT);
    }
        return new Result();
    }

    /*
    回复帖子
     */
   @PostMapping("/reply")
    @ResponseBody
    public Result reply(Comment comment,Long recvUserId,int type,String replyto,HttpServletRequest request){
       Long ids = comment.getId();
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        if(user != null){
            if(user.getStatus() == 0){
                return new Result().error(MsgEnum.ALLOWLIMIT);
            }
            TopicinfoExt TopicinfoExt = topicService.showDetail(comment.getTopicId());
            if(TopicinfoExt.getStatus() == 1){
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
                Map map = new HashMap<>();
                map.put("action","/jie/detail/"+comment.getTopicId());
                return new Result().ok(MsgEnum.REPLY_SUCCESS,map);
            }else{
                return new Result().error(MsgEnum.NOT_ALLOW_COMMENT);
            }

        }else{
            return new Result().error(MsgEnum.NOTLOGIN);
        }
    }

    /*
    审核帖子
     */
    @PostMapping("/shenhe")
    public @ResponseBody Result shenhe(Long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
       User user = (User) request.getSession().getAttribute("user");
       if(user.getRole().equals("管理员") || user.getRole().equals("社区管理员")){
           topicService.shenhe(id);
       return new Result().ok(MsgEnum.SHENHE_SUCCESS);
       }else{
           return new Result().error(MsgEnum.ALLOWLIMIT);
       }
    }

    /*
    设置置顶、加精
     */
    @PostMapping("/setstatus")
    public @ResponseBody Result setTopAndGood(Long id,Integer rank,String field){
        topicService.setTopAndGood(id,rank,field);
        return new Result().ok(MsgEnum.STATUS_SUCCESS);
    }

    /*
    删除帖子
     */
    @PostMapping("/delete")
    public @ResponseBody Result delTopicAndComment(Long id){
        topicService.delTopicAndComment(id);
        return  new Result().ok(MsgEnum.DELETE_SUCCESS);
    }

    /*
    点赞
     */
    @PostMapping("/zan")
  public @ResponseBody Result jiedaZan(Long id, Boolean ok, HttpServletRequest request){

        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("user");
        return topicService.jiedaZan(user,id,ok);
    }
    /*
    采纳评论
     */
    @PostMapping("/accept")
    public @ResponseBody Result acceptComment(Long id){
       return topicService.acceptComment(id);
    }

    /*
    编辑回复
     */
    @PostMapping("/edit")
    public @ResponseBody Result editComment(Long id){
        String content = topicService.getCommentContentById(id);
        Map map = new HashMap<>();
        map.put("content",content);
        return new Result().ok(MsgEnum.OK,map);
    }

    /*
    提交评论更改
     */
    @PostMapping("/editsubmit")
    public @ResponseBody Result editCommentSub(Long id,String content){
        return topicService.editCommentSub(id,content);
    }

    /*
    删除评论
     */
    @PostMapping("/delcomment")
    public @ResponseBody Result delComment(Long id){
        return topicService.delComment(id);
    }



}
