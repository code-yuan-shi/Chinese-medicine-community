package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.QiandaoMapper;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.mapper.QiandaoExtMapper;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.model.QiandaoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignService {

    @Autowired
    private QiandaoMapper qiandaoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QiandaoExtMapper qiandaoExtMapper;

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
    public Result findSign() {
        List<QiandaoExt> qiandaoExtNew = qiandaoExtMapper.getNewSign();
        List<QiandaoExt> qiandaoExtToday = qiandaoExtMapper.getTodaySign();
        List<QiandaoExt> qiandaoExtTotal = qiandaoExtMapper.getTotalSign();
        List<Object> list = new ArrayList<>();
        list.add(qiandaoExtNew);
        list.add(qiandaoExtToday);
        list.add(qiandaoExtTotal);
        return new Result().ok(MsgEnum.OK,list);

    }



}
