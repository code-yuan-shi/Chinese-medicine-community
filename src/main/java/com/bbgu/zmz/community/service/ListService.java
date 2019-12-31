package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.model.WeekList;
import com.bbgu.zmz.community.mapper.*;
import com.bbgu.zmz.community.model.TopicinfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TopicinfoMapper topicinfoMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private TopicinfoExtMapper topicinfoExtMapper;

    /*
    回帖周榜
     */
    public List<WeekList> weekList(){
        List<WeekList> weekListList = userExtMapper.getTopUsers();
       return  weekListList;
    }

    /*
    本周热议
     */
    public List<TopicinfoExt> weekTopic(){
        List<TopicinfoExt> topicinfoExtList = topicinfoExtMapper.getTop10Topics();
        return  topicinfoExtList;
    }

}
