package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Data;
import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.mapper.QiandaoMapper;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignService {

    @Autowired
    private QiandaoMapper qiandaoMapper;
    @Autowired
    private UserMapper userMapper;

    //获取签到信息
    public List<Qiandao> findSignUserInfo (Long userid){
        QiandaoExample qiandaoExample = new QiandaoExample();
        qiandaoExample.createCriteria().andUserIdEqualTo(userid);
        List<Qiandao> qiandaoList =  qiandaoMapper.selectByExample(qiandaoExample);
        return qiandaoList;
    }

    //更新签到信息
    public void updateSignStatus(Qiandao qiandao){
        qiandaoMapper.updateByPrimaryKey(qiandao);
    }

    //添加签到信息
    public void insertSignStatus(Qiandao qiandao){
        qiandaoMapper.insertSelective(qiandao);
    }

    //更新用户奖励
    public void updateUserKiss(Long userid,Integer kissnum){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(userid);
        List<User> users = userMapper.selectByExample(userExample);
        users.get(0).setKissNum(kissnum.longValue() + users.get(0).getKissNum());
        userMapper.updateByPrimaryKeySelective(users.get(0));
    }

       /*
        查询签到活跃榜单
       */
    public RegRespObj findSign() {
        RegRespObj regRespObj = new RegRespObj();
        QiandaoExample qiandaoExample = new QiandaoExample();
        List<Qiandao> qiandaoListFast = qiandaoMapper.selectByExampleWithRowbounds(qiandaoExample,new RowBounds(0,20));
        qiandaoExample.setOrderByClause("qiandao_create desc");
        List<Qiandao> qiandaoListNew = qiandaoMapper.selectByExampleWithRowbounds(qiandaoExample,new RowBounds(0,20));
        qiandaoExample.setOrderByClause("total desc");
        List<Qiandao> qiandaoTotal  = qiandaoMapper.selectByExampleWithRowbounds(qiandaoExample,new RowBounds(0,20));
        List<QiandaoExt> qiandaoExtFast = new ArrayList<>();
        List<QiandaoExt> qiandaoExtNew = new ArrayList<>();
        List<QiandaoExt> qiandaoExtTotal = new ArrayList<>();
        for(Qiandao qiandao:qiandaoListFast){
            QiandaoExt qiandaoExt = new QiandaoExt();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(qiandao.getUserId());
            User user = userMapper.selectByExample(userExample).get(0);
            //当前时间
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());
            //签到时间
            Calendar calendarLastTime = Calendar.getInstance();
            calendarLastTime.setTime(new Date(qiandao.getQiandaoCreate()));
            //获得当天是几号
            int date = calendarNow.get(Calendar.DATE);
            //获得签到是几号
            int lasttime = calendarLastTime.get(Calendar.DATE);
            if(date == lasttime){
                qiandaoExt.setUser(user);
                qiandaoExt.setQiandao(qiandao);
                qiandaoExtFast.add(qiandaoExt);
            }
        }
        for(Qiandao qiandao1:qiandaoTotal){
            QiandaoExt qiandaoExt = new QiandaoExt();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(qiandao1.getUserId());
            User user = userMapper.selectByExample(userExample).get(0);
            qiandaoExt.setUser(user);
            qiandaoExt.setQiandao(qiandao1);
            qiandaoExtTotal.add(qiandaoExt);
        }
        for(Qiandao qiandao2:qiandaoListNew){
            QiandaoExt qiandaoExt = new QiandaoExt();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(qiandao2.getUserId());
            User user = userMapper.selectByExample(userExample).get(0);
            qiandaoExt.setUser(user);
            qiandaoExt.setQiandao(qiandao2);
            qiandaoExtNew.add(qiandaoExt);
        }
        List<Object> list = new ArrayList<>();
        list.add(qiandaoExtNew);
        list.add(qiandaoExtFast);
        list.add(qiandaoExtTotal);
        Data data = new Data();
        data.setList(list);
        regRespObj.setData(data);
        return regRespObj;

    }



}
