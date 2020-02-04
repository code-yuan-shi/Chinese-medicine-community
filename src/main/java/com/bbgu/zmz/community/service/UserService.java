package com.bbgu.zmz.community.service;


import com.bbgu.zmz.community.dto.Data;
import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.mapper.CollectExtMapper;
import com.bbgu.zmz.community.mapper.CollectMapper;
import com.bbgu.zmz.community.mapper.TopicinfoMapper;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.Collect;
import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.model.UserExample;
import com.bbgu.zmz.community.util.MD5Utils;
import com.bbgu.zmz.community.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            user.setUserCreate(System.currentTimeMillis());
            user.setUserModified(user.getUserCreate());
            user.setPwd(MD5Utils.getMd5("123456"));
            userMapper.insertSelective(user);
        }else{
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setUserModified(System.currentTimeMillis());
           // updateUser.setAvatarUrl(user.getAvatarUrl());
           // updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setRole(user.getRole());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
    /*
    查询用户是否存在
     */
    public RegRespObj checkUser(Long accoundId){
           RegRespObj regRespObj = new RegRespObj();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(accoundId);
            List<User> userList = userMapper.selectByExample(userExample);
            if (userList.size() != 0) {
                regRespObj.setStatus(1);
                regRespObj.setMsg("用户已存在！");
            } else {
                regRespObj.setStatus(0);
                regRespObj.setMsg("恭喜，可以注册！");
            }
            return regRespObj;
    }

    /*
    检查验证邮箱是否已经存在！
     */
    public RegRespObj checkEmail(String email){
        RegRespObj regRespObj = new RegRespObj();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() != 0) {
            regRespObj.setStatus(1);
            regRespObj.setMsg("抱歉，该邮箱已被使用！");
        } else {
            regRespObj.setStatus(0);
            regRespObj.setMsg("恭喜，该邮箱可用！");
        }
        return regRespObj;
    }

    /*
    注册账户
     */
    public RegRespObj doreg(User user,String repass,String url){
        RegRespObj regRespObj = new RegRespObj();
        if(user.getPwd().equals(repass)){
            Long time = System.currentTimeMillis();
            //创建激活密钥，利用邮箱+密码+时间
            String accode = MD5Utils.getMd5(user.getEmail()+user.getPwd()+time);
            //创建激活有效时间
            Long actime = time+1000*60*5;
            //加密密码
            String pwd = MD5Utils.getMd5(user.getPwd());
            user.setPwd(pwd);
            user.setActiveCode(accode);
            user.setActiveTime(actime);
            user.setAvatarUrl("/images/avatar/1.jpg");
            user.setUserCreate(System.currentTimeMillis());
            user.setUserModified(user.getUserCreate());
            user.setRole("社区用户");
            try {
                MailUtil.sendActiveMail(user.getEmail(),accode);
                userMapper.insertSelective(user);
                regRespObj.setStatus(0);
                regRespObj.setMsg("激活邮件已发送！");
                regRespObj.setAction(url);
            } catch (Exception e) {
                regRespObj.setStatus(1);
                regRespObj.setMsg("网络异常,请重试！");
            }
        }else{
            regRespObj.setStatus(1);
            regRespObj.setMsg("两次密码输入不一致！");
        }

        return regRespObj;
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
                return 1;
            }else{
                UserExample userExample2 = new UserExample();
                userExample2.createCriteria().andActiveCodeEqualTo(acode);
                userMapper.deleteByExample(userExample2);
                return 0;
            }
        }else{
            return 2;
        }
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
    public RegRespObj addCollect(User user,Long cid){
        RegRespObj regRespObj = new RegRespObj();
        if(user != null){
            Topicinfo topicinfo = topicinfoMapper.selectByPrimaryKey(cid);
            if(topicinfo.getStatus() == 1){
                Map<String,Long> map = new HashMap<>();
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
                    regRespObj.setStatus(0);
                    regRespObj.setMsg("收藏成功！");
                }else{
                    regRespObj.setStatus(1);
                    regRespObj.setMsg("已收藏！");
                }
            }else{
                regRespObj.setStatus(1);
                regRespObj.setMsg("未审核，无法收藏！");
            }
        }
        return regRespObj;
    }

    /*
   取消收藏
    */
    public RegRespObj removeCollect(User user,Long cid){
        RegRespObj regRespObj = new RegRespObj();
        if(user != null){
            Map<String,Long> map = new HashMap<>();
            map.put("topicid",cid);
            map.put("userid",user.getAccountId());
            collectExtMapper.delCollectInfo(map);
            regRespObj.setStatus(0);
            regRespObj.setMsg("取消成功！");
        }
        return regRespObj;
    }
    /*
    查询收藏
     */
    public RegRespObj findCollect(User user,Long cid){
        int count = 0;
        RegRespObj regRespObj = new RegRespObj();
        if(user != null){
            Map<String,Long> map = new HashMap<>();
            map.put("topicid",cid);
            map.put("userid",user.getAccountId());
            count = collectExtMapper.getIsCollectInfo(map);
            regRespObj.setStatus(0);
            Data data = new Data();
            data.setCollection(count == 1 ? true:false);
            regRespObj.setData(data);
        }
        return regRespObj;
    }

    /*
    用户修改我的资料
     */
    public RegRespObj modifyUserInfo(User user){
        RegRespObj regRespObj = new RegRespObj();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        userMapper.updateByExampleSelective(user,userExample);
        regRespObj.setStatus(0);
        regRespObj.setMsg("修改成功！");
        return regRespObj;
    }

    /*
    用户修改头像
     */
    public RegRespObj modifyUserAvatar(Long accountId,String avatar){
        RegRespObj regRespObj = new RegRespObj();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        User user = new User();
        user.setAvatarUrl(avatar);
        userMapper.updateByExampleSelective(user,userExample);
        regRespObj.setStatus(0);
        regRespObj.setMsg("修改头像成功！");
        return regRespObj;
    }

    /*
    用户修改密码
     */
    public RegRespObj modifyUserPassword(String nowpass, String pass,String repass,Long accountId){
        RegRespObj regRespObj = new RegRespObj();
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
                regRespObj.setStatus(0);
                regRespObj.setMsg("密码已修改！");
            }else{
                regRespObj.setStatus(1);
                regRespObj.setMsg("当前密码验证错误！");
            }
        }else{
            regRespObj.setStatus(1);
            regRespObj.setMsg("两次输入的密码不一致！");
        }
        return regRespObj;
    }
}
