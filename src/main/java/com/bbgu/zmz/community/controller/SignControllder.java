package com.bbgu.zmz.community.controller;


import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.model.Qiandao;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.service.SignService;
import com.bbgu.zmz.community.util.KissUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("sign")
public class SignControllder {


    @Autowired
    private SignService signService;

    /*
    查询签到状态
     */
    @PostMapping("/status")
    @ResponseBody
    public Result signStatus(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("signed",false);
        map.put("experience",5);
        map.put("days",0);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            List<Qiandao> qiandaoList = signService.findSignUserInfo(user.getAccountId());
            if(qiandaoList.size() != 0){
                //当前时间
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(new Date());
                //签到时间
                Calendar calendarLastTime = Calendar.getInstance();
                calendarLastTime.setTime(new Date(qiandaoList.get(0).getQiandaoCreate()));
                //获得当天是几号
                int date = calendarNow.get(Calendar.DATE);
                //获得签到是几号
                int lasttime = calendarLastTime.get(Calendar.DATE);
                if(date == lasttime){
                    //取得签到获得的奖励数量
                    int kissnum = KissUtils.getKissNum(qiandaoList.get(0).getTotal().intValue());
                    map.put("signed",true);
                    map.put("experience",kissnum);
                    map.put("days",qiandaoList.get(0).getTotal());
                }else if(date - lasttime == 1){
                    //获得连续签到信息
                    int kissnum = KissUtils.getKissNum(1+qiandaoList.get(0).getTotal().intValue());
                    map.put("signed",false);
                    map.put("experience",kissnum);
                    map.put("days",qiandaoList.get(0).getTotal());
                }else{
                    map.put("signed",false);
                    map.put("experience",5);
                    map.put("days",0);
                }
            }else {
                map.put("signed",false);
                map.put("experience",5);
                map.put("days",0);
            }
        }
        return new Result().ok(MsgEnum.OK,map);
    }
    /*
    签到
     */
    @PostMapping("/in")
    @ResponseBody
    public Result signIn(HttpServletRequest request){
        Map map = new HashMap();
        map.put("signed",true);
        map.put("experience",5);
        map.put("days",1);
        User  user =  (User)request.getSession().getAttribute("user");
        if(user == null)
        {
            return new Result().error(MsgEnum.NOTLOGIN);
        }else if(user.getStatus() == 0){
            return new Result().error(MsgEnum.ALLOWLIMIT);
        }

        else{
            List<Qiandao> qiandaoList = signService.findSignUserInfo(user.getAccountId());
            if(qiandaoList.size() != 0){
                //当前时间
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(new Date());
                //签到时间
                Calendar calendarLastTime = Calendar.getInstance();
                calendarLastTime.setTime(new Date(qiandaoList.get(0).getQiandaoCreate()));
                //当前时间减一天
                calendarNow.add(Calendar.DATE, -1);
                //获得当天是几号
                int date = calendarNow.get(Calendar.DATE);
                //获得签到是几号
                int lasttime = calendarLastTime.get(Calendar.DATE);
                //如果签到那天和今天号数相同。
                if(date == lasttime)
                {
                    //更新签到时间
                    qiandaoList.get(0).setQiandaoCreate(System.currentTimeMillis());
                    //更新签到总天数
                    qiandaoList.get(0).setTotal(1 + qiandaoList.get(0).getTotal());
                    signService.updateSignStatus(qiandaoList.get(0));
                    //取得签到获得的奖励数量
                    int kissnum = KissUtils.getKissNum(1 + qiandaoList.get(0).getTotal().intValue());

                    //更新用户奖励数量
                    signService.updateUserKiss(user.getAccountId(),kissnum);
                    //取得签到总天数
                    //data.setDays(qiandaoList.get(0).getTotal());
                    map.put("days",qiandaoList.get(0).getTotal());
                    //设置今天获得的签到值
                    //data.setExperience(kissnum);
                    map.put("experience",kissnum);
                }else{
                    //更新签到时间
                    qiandaoList.get(0).setQiandaoCreate(System.currentTimeMillis());
                    //更新签到总天数
                    qiandaoList.get(0).setTotal(1l);
                    signService.updateSignStatus(qiandaoList.get(0));
                    //更新用户奖励数量
                    signService.updateUserKiss(user.getAccountId(),5);
                }
            }else{
                //添加签到信息
                Qiandao qiandao = new Qiandao();
                qiandao.setUserId(user.getAccountId());
                qiandao.setQiandaoCreate(System.currentTimeMillis());
                qiandao.setTotal(1l);
                signService.insertSignStatus(qiandao);
                //更新用户奖励数量
                signService.updateUserKiss(user.getAccountId(),5);
            }
        }
       return new Result().ok(MsgEnum.OK,map);
    }

    /*
    查询签到活跃榜单
     */
    @GetMapping("/getSign")
    @ResponseBody
    public Result findSign(){
        return signService.findSign();
    }
}

