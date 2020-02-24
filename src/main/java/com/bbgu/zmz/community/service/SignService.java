package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.QiandaoExtMapper;
import com.bbgu.zmz.community.mapper.QiandaoMapper;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.Qiandao;
import com.bbgu.zmz.community.model.QiandaoExt;
import com.bbgu.zmz.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        Example example = new Example(Qiandao.class);
        example.createCriteria().andEqualTo("userId",userid);
        List<Qiandao> qiandaoList =  qiandaoMapper.selectByExample(example);
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
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("accountId",userid);
        List<User> users = userMapper.selectByExample(example);
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
