package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.CommentMapper;
import com.bbgu.zmz.community.mapper.MessageMapper;
import com.bbgu.zmz.community.mapper.TopicinfoMapper;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

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
    public Long getUnreadMsgCountByUserID(Long userId){
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andRecvUserIdEqualTo(userId).andIsReadEqualTo(0);
        Long num =  messageMapper.countByExample(messageExample);
        return num;
    }

    /*
    点击消息通知设置为已读状态
     */

    public void updateUserMsgReadState(Long userId){
        Message message = new Message();
        message.setIsRead(1);
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andRecvUserIdEqualTo(userId);
        messageMapper.updateByExampleSelective(message,messageExample);

    }

    /*
    删除通知消息
     */
    public Result delMessage(Boolean all, Long userId, Integer id){
        //RegRespObj regRespObj = new RegRespObj();
        if(all == null){
            messageMapper.deleteByPrimaryKey(id);
            //regRespObj.setStatus(0);
            return new Result().ok(MsgEnum.OK);
        }else{
            MessageExample messageExample = new MessageExample();
            messageExample.createCriteria().andRecvUserIdEqualTo(userId);
            messageMapper.deleteByExample(messageExample);
            //regRespObj.setStatus(0);
            return new Result().ok(MsgEnum.OK);
        }
            //return regRespObj;

    }

    /*
    添加通知信息
     */
    public int insMessage(Long sendUserId, Long recvUserId,Long topicId,Integer type,String content,Long commentId){
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

    }
    /*
    查询通知消息
     */
    public List<MessageExt> selMessage(Long recvUserId){

        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andRecvUserIdEqualTo(recvUserId);
        messageExample.setOrderByClause("message_create desc");
        List<Message> messageList = messageMapper.selectByExample(messageExample);  //查询通知列表
        List<MessageExt> messageExtList = new ArrayList<>();
        for(Message message:messageList){
            MessageExt messageExt = new MessageExt();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(message.getSendUserId());
            List<User> userList = userMapper.selectByExample(userExample);   //获得发送者信息
            Topicinfo topicinfo = topicinfoMapper.selectByPrimaryKey(message.getTopicId());   //获得帖子信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(message.getMessageCreate());   //转换通知时间
            if (message.getType() == 1) {
                CommentExample commentExample = new CommentExample();
                commentExample.createCriteria().andContentEqualTo(message.getContent());
                List<Comment> commentList = commentMapper.selectByExample(commentExample); //取得新评论的id
                Comment comment = commentMapper.selectByPrimaryKey(message.getCommentId());    //获得评论信息
                messageExt.setNewId(commentList.get(0).getId());
                messageExt.setComment(comment);
                int start =  message.getContent().indexOf(" ");
                String str = message.getContent().substring(start+1);
                messageExt.setContent(str);
            }else{
                messageExt.setContent(message.getContent());
            }
            messageExt.setTime(StringDate.getStringDate(date));
            messageExt.setCommentId(message.getCommentId());
            messageExt.setId(message.getId());
            messageExt.setType(message.getType());
            messageExt.setUser(userList.get(0));
            messageExt.setTopicinfo(topicinfo);
            messageExtList.add(messageExt);
        }
        return messageExtList;
    }

}
