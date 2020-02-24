package com.bbgu.zmz.community.model;

import lombok.Data;

@Data
public class MessageExt {
    private Integer id;

    private Long sendUserId;

    private Long recvUserId;

    private String content;

    private Long topicId;

    private Long commentId;

    private Integer type;

    private Integer isRead;

    private Long messageCreate;

    private String name;

    private String title;

    private String time;

    private Long newCid;

    private String oldcontent;

}