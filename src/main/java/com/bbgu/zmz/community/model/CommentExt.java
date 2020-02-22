package com.bbgu.zmz.community.model;

import lombok.Data;

@Data
public class CommentExt {
    private Long id;
    private Long topicId;
    private String content;
    private Integer type;
    private Long userId;
    private Long commentCreate;
    private Long commentModified;
    private Long agreeNum;
    private Integer isAccept;
    private String title;
    private String time;
    private Boolean zan;
    private String avatarUrl;
    private String name;
    private String role;
    private Integer count;
}