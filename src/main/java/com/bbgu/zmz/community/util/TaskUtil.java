package com.bbgu.zmz.community.util;


import com.bbgu.zmz.community.mapper.CommentMapper;
import com.bbgu.zmz.community.mapper.CommentagreeMapper;
import com.bbgu.zmz.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TaskUtil {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommentMapper commentMapper;


    /**
     *  每天0点执行
     */

   @Scheduled(cron="0 0 0 * * ?")
    public void updateAgree() {

       Map<String,String> map = redisUtil.getMap("agreeCount");
       for(Map.Entry entry: map.entrySet()) {
           Comment comment = new Comment();
           comment.setId(Long.parseLong(entry.getKey().toString()));
           comment.setAgreeNum(Long.parseLong(entry.getValue().toString()));
           commentMapper.updateByPrimaryKeySelective(comment);
          // System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
       }
        redisUtil.delAll();
        System.out.println("定时器执行了一次！");
   }
}
