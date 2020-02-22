package com.bbgu.zmz.community.model;

import com.bbgu.zmz.community.model.Comment;
import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.User;
import lombok.Data;

@Data
public class MessageExt {
    private Integer id;

    private Long sendUserId;

    private Long recvUserId;

    private Long topicId;

    private Long commentId;

    private Integer type;

    private Integer isRead;

    private Long messageCreate;

    private String content;

    private Topicinfo topicinfo;

    private User user;

    private Comment comment;

    private String time;

    private Long newId;

}