package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.*;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.model.MessageExt;
import com.bbgu.zmz.community.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageExtMapper messageExtMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TopicinfoMapper topicinfoMapper;
    @Autowired
    private CommentMapper commentMapper;


    /*
    获取消息通知
     */
    public int getUnreadMsgCountByUserID(Long userId){
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("recvUserId",userId).andEqualTo("isRead",0);
        int num = messageMapper.selectCountByExample(example);
        return num;
    }

    /*
    点击消息通知设置为已读状态
     */

    public void updateUserMsgReadState(Long userId){
        Message message = new Message();
        message.setIsRead(1);
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("recvUserId",userId);
        messageMapper.updateByExampleSelective(message,example);
    }

    /*
    删除通知消息
     */
    public Result delMessage(Boolean all, Long userId, Integer id){
        if(all == null){
            messageMapper.deleteByPrimaryKey(id);
            return new Result().ok(MsgEnum.OK);
        }else{
            Example example = new Example(Message.class);
            example.createCriteria().andEqualTo("recvUserId",userId);
            messageMapper.deleteByExample(example);
            return new Result().ok(MsgEnum.OK);
        }
    }

    /*
    添加通知信息
     */
    public int insMessage(Long sendUserId, Long recvUserId,Long topicId,Integer type,String content,Long commentId){

        if(!sendUserId.equals(recvUserId)){
            Message message = new Message();
            message.setSendUserId(sendUserId);
            message.setRecvUserId(recvUserId);
            message.setTopicId(topicId);
            message.setContent(content);
            message.setCommentId(commentId);
            message.setType(type);
            message.setIsRead(0);
            message.setMessageCreate(System.currentTimeMillis());
            return messageMapper.insertSelective(message);
        }else{
            return 0;
        }
    }
    /*
    查询通知消息
     */
    public List<MessageExt> selMessage(Long recvUserId){

        List<MessageExt> messageExtList = messageExtMapper.findMessage(recvUserId);
        for(MessageExt messageExt:messageExtList){
            if (messageExt.getType() == 1) {
                int start = messageExt.getContent().indexOf(" ");
                String str = messageExt.getContent().substring(start + 1);
                messageExt.setContent(str);
            }
            messageExt.setTime(StringDate.getStringDate(new Date(messageExt.getMessageCreate())));
        }
        return messageExtList;
    }

}
