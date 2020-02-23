package com.bbgu.zmz.community.service;


import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.mapper.*;
import com.bbgu.zmz.community.mapper.CollectExtMapper;
import com.bbgu.zmz.community.mapper.CommentExtMapper;
import com.bbgu.zmz.community.mapper.TopicinfoExtMapper;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.model.CollectExt;
import com.bbgu.zmz.community.model.CommentExt;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.model.UserExt;
import com.bbgu.zmz.community.util.MD5Utils;
import com.bbgu.zmz.community.util.MailUtil;
import com.bbgu.zmz.community.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CollectExtMapper collectExtMapper;
    @Autowired
    private TopicinfoMapper topicinfoMapper;
    @Autowired
    private TopicinfoExtMapper topicinfoextMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            user.setUserCreate(System.currentTimeMillis());
            user.setUserModified(user.getUserCreate());
            user.setPwd(MD5Utils.getMd5("123456"));
            user.setBio("该用户很懒，什么都没有留下！");
            userMapper.insertSelective(user);
        }else{
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setUserModified(System.currentTimeMillis());
           // updateUser.setAvatarUrl(user.getAvatarUrl());
           // updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            //updateUser.setRole(user.getRole());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
    /*
    查询用户是否存在
     */
    public Result checkUser(Long accoundId){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(accoundId);
            List<User> userList = userMapper.selectByExample(userExample);
            if (userList.size() != 0) {
               return new Result().error(MsgEnum.USER_EXIT);
            } else {
               return  new Result().ok(MsgEnum.ALLOW_REG);
            }
    }

    /*
    检查验证邮箱是否已经存在！
     */
    public Result checkEmail(String email){
        //RegRespObj regRespObj = new RegRespObj();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() != 0) {
          return new Result().error(MsgEnum.EMAIL_EXIT);
        } else {
           return  new Result().ok(MsgEnum.EMAIL_ALLOW);
        }
    }

    /*
    注册账户
     */
    public int doReg(UserExt userext) throws Exception {
            User user = new User();
            Long time = System.currentTimeMillis();
            //创建激活密钥，利用邮箱+密码+时间
            String accode = MD5Utils.getMd5(userext.getEmail()+userext.getPwd()+time);
            //创建激活有效时间
            Long actime = time+1000*60*5;
            //加密密码
            String pwd = MD5Utils.getMd5(userext.getPwd());
            user.setAccountId(userext.getAccountId());
            user.setName(userext.getName());
            user.setEmail(userext.getEmail());
            user.setBio("该用户很懒，什么都没有留下！");
            user.setPwd(pwd);
            user.setActiveCode(accode);
            user.setActiveTime(actime);
            user.setAvatarUrl("/static/images/avatar/1.jpg");
            user.setUserCreate(System.currentTimeMillis());
            user.setUserModified(user.getUserCreate());
            user.setRole("社区用户");
            Result result = MailUtil.sendActiveMail(user.getEmail(),accode,0);
               if(result.getStatus() != 1){
                   userMapper.insertSelective(user);
                   return 0;
               }else{
                   return 1;
               }
    }

    /*
    重新发送激活邮件
     */

    public int resend(User user){
        Long time = System.currentTimeMillis();
        //创建激活密钥，利用邮箱+密码+时间
        String accode = MD5Utils.getMd5(user.getEmail()+user.getPwd()+time);
        //创建激活有效时间
        Long actime = time+1000*60*5;
        //加密密码
        user.setActiveCode(accode);
        user.setActiveTime(actime);
        Result result = MailUtil.sendActiveMail(user.getEmail(),accode,0);
        if(result.getStatus() != 1){
            userMapper.updateByPrimaryKeySelective(user);
            return 0;
        }else{
            return 1;
        }

    }
    /*
    激活用户
     */

    public int activeUser(String acode){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andActiveCodeEqualTo(acode);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        Long now = System.currentTimeMillis();
        Long actime = user.getActiveTime();
        if(user.getStatus().equals(0)){
            if(now < actime){
                user.setStatus(1);
                UserExample userExample1 = new UserExample();
                userExample1.createCriteria().andActiveCodeEqualTo(acode);
                userMapper.updateByExampleSelective(user,userExample1);
                return 0;  //激活成功
            }else{
                return 1;    //验证失效
            }
        }
        return 2;    //已激活
    }

    /*
    根据授权码查询用户信息
     */
    public User findUserByAccode(String accode){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andActiveCodeEqualTo(accode);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        return user;
    }

    /*
    用户登录验证
     */

    public User loginCheck(User user){
        String pwd =  MD5Utils.getMd5(user.getPwd());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId()).andPwdEqualTo(pwd);
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size() != 0){
            User user1 = userList.get(0);
            String token = UUID.randomUUID().toString();
            user1.setToken(token);
            userMapper.updateByPrimaryKeySelective(user1);
            return user1;
        }else{
            return null;
        }
    }

    /*
    更新飞吻数量
     */
    public void updateKiss(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    /*
    添加收藏
     */
    public Result addCollect(User user,Long cid){
        if(user != null){
            Topicinfo topicinfo = topicinfoMapper.selectByPrimaryKey(cid);
            if(topicinfo.getStatus() == 1){
                Map<String,Long> map = new HashMap<String,Long>();
                map.put("topicid",cid);
                map.put("userid",user.getAccountId());
                int count = collectExtMapper.getIsCollectInfo(map);
                if(count <1){
                    Collect collect = new Collect();
                    collect.setTopicId(cid);
                    collect.setUserId(user.getAccountId());
                    collect.setCollectCreate(System.currentTimeMillis());
                    collect.setCollectModified(collect.getCollectCreate());
                    collectMapper.insertSelective(collect);
                    return new Result().ok(MsgEnum.ADDCOLLECT);
                }else{
                    return new Result().error(MsgEnum.HAVACOLLECT);
                }
            }else{
                return new Result().error(MsgEnum.NOTALLOWCOLLECT);
            }
        }
        return new Result().error(MsgEnum.NOTLOGIN);
    }

    /*
   取消收藏
    */
    public Result removeCollect(User user,Long cid){
        if(user != null){
            Map<String,Long> map = new HashMap<>();
            map.put("topicid",cid);
            map.put("userid",user.getAccountId());
            collectExtMapper.delCollectInfo(map);
        return new Result().ok(MsgEnum.HAVADONE);
        }
        return new Result().error(MsgEnum.NOTLOGIN);
    }
    /*
    查询收藏
     */
    public Result findCollect(User user,Long cid){
        int count = 0;
        if(user != null){
            Map<String,Long> map = new HashMap<>();
            map.put("topicid",cid);
            map.put("userid",user.getAccountId());
            count = collectExtMapper.getIsCollectInfo(map);
            //regRespObj.setStatus(0);
            HashMap<String,Boolean> map1 = new HashMap<>();
            map1.put("collection",count == 1 ? true:false);
            return new Result().ok(MsgEnum.OK,map1);
        }
        return new Result().error(MsgEnum.NOTLOGIN);
    }
    /*
    用户查询我的资料
     */
    public User findUserInfoById(Long accountId){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.get(0);
    }

    public User findUserInfoByName(String name){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.get(0);
    }

    /*
    用户修改我的资料
     */
    public Result modifyUserInfo(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        userMapper.updateByExampleSelective(user,userExample);
        Map map = new HashMap();
        map.put("action","");
        return new Result().ok(MsgEnum.CHANGE,map);
    }

    /*
    用户修改头像
     */
    public Result modifyUserAvatar(Long accountId,String avatar){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        User user = new User();
        user.setAvatarUrl(avatar);
        userMapper.updateByExampleSelective(user,userExample);
        return new Result().ok(MsgEnum.CHANGE);
    }

    /*
    用户修改密码
     */
    public Result modifyUserPassword(String nowpass, String pass,String repass,Long accountId){
        String pwd = MD5Utils.getMd5(nowpass);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> userinfos = userMapper.selectByExample(userExample);
        User userinfo = userinfos.get(0);
        if(pass.equals(repass)){
            if(userinfo.getPwd().equals(pwd)){
                User user = new User();
                user.setPwd(MD5Utils.getMd5(pass));
                userMapper.updateByExampleSelective(user,userExample);
                Map map = new HashMap();
                map.put("action","");
                return new Result().ok(MsgEnum.CHANGE,map);
            }else{
              return new Result().error(MsgEnum.OLD_PWD_INCORRECT);
            }
        }else{
            return new Result().error(MsgEnum.PWD_ATYPISM);
        }
    }
    /*
    用户重置密码
     */
    public void resetUserPwd(Long accountId,String password){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        User user = new User();
        user.setPwd(MD5Utils.getMd5(password));
        userMapper.updateByExampleSelective(user,userExample);
    }

    /*
    查询我发表的帖子
     */
    public List<TopicinfoExt> getUserTopic(Long id){

        List<TopicinfoExt> topicinfoExtList = topicinfoextMapper.getUserTopic(id);
        for(TopicinfoExt topicinfoExt:topicinfoExtList){
            topicinfoExt.setTime(StringDate.getStringDate(new Date(topicinfoExt.getTopicCreate())));
        }
        return topicinfoExtList;
    }

    /*
    查询收藏
     */
    public List<CollectExt> getUserCollectTopic(Long id){

         List<CollectExt> collectExtList = collectExtMapper.getUserCollectTopic(id);
        List<CollectExt> collectExtList1 = new ArrayList<>();
         for(CollectExt collectExt:collectExtList){
             collectExt.setTime(StringDate.getStringDate(new Date(collectExt.getCollectCreate())));
             collectExtList1.add(collectExt);
         }
        return collectExtList1;
    }

    /*
      查询用户评论
     */
   public List<CommentExt> findComment(Long userId){

       List<CommentExt>  commentExtList =  commentExtMapper.findComment(userId);
       List<CommentExt> commentExtList1 = new ArrayList<>();
       for (CommentExt commentExt:commentExtList){
           commentExt.setTime(StringDate.getStringDate(new Date(commentExt.getCommentCreate())));
           commentExtList1.add(commentExt);
       }
        return commentExtList1;
    }

    /*
    重置用户密码
     */
    public User findUserByEmailAndAccountId(Long accountId,String email){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId).andEmailEqualTo(email);
        List<User> userList =  userMapper.selectByExample(userExample);
        if(userList.size() != 0){
            return userList.get(0);
        }else{
            return null;
        }
    }


}
