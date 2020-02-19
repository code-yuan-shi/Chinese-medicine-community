package com.bbgu.zmz.community.controller;


import com.alibaba.fastjson.JSON;
import com.bbgu.zmz.community.dto.Data;
import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.Qiandao;
import com.bbgu.zmz.community.model.QiandaoExample;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public RegRespObj signStatus(HttpServletRequest request) {
        RegRespObj regRespObj = new RegRespObj();
        Data data = new Data();
        regRespObj.setData(data);
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
                    data.setSigned(true);
                    //取得签到获得的奖励数量
                    int kissnum = KissUtils.getKissNum(qiandaoList.get(0).getTotal().intValue());
                    data.setExperience(kissnum);
                    data.setDays(qiandaoList.get(0).getTotal());
                }else if(date - lasttime == 1){
                    data.setSigned(false);
                    int kissnum = KissUtils.getKissNum(1+qiandaoList.get(0).getTotal().intValue());
                    data.setExperience(kissnum);
                    data.setDays(qiandaoList.get(0).getTotal());
                }else{
                    data.setSigned(false);
                    data.setExperience(5);
                    data.setDays(0);
                }
            }else {
                data.setSigned(false);
                data.setExperience(5);
                data.setDays(0);
            }
        }
        return regRespObj;
    }
    /*
    签到
     */
    @PostMapping("/in")
    @ResponseBody
    public RegRespObj signIn(HttpServletRequest request){
        RegRespObj regRespObj = new RegRespObj();
        Data data = new Data();
        regRespObj.setData(data);
        data.setDays(1);
        data.setExperience(5);
        data.setSigned(true);
        User  user =  (User)request.getSession().getAttribute("user");
        if(user == null)
        {
            regRespObj.setStatus(1);
            regRespObj.setMsg("请先登录");
        }else{
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
                    data.setDays(qiandaoList.get(0).getTotal());
                    //设置今天获得的签到值
                    data.setExperience(kissnum);
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
       return regRespObj;
    }

    /*
    查询签到活跃榜单
     */
    @GetMapping("/getSign")
    @ResponseBody
    public RegRespObj findSign(){
        RegRespObj regRespObj = signService.findSign();
        return regRespObj;
    }
}

