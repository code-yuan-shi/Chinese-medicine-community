package com.bbgu.zmz.community.model;

import lombok.Data;

@Data
public class TopicinfoExt {

    private Long id;
    private String title;
    private Long topicCreate;
    private Long topicModified;
    private Long userId;
    private Long categoryId;
    private Long kindId;
    private Integer viewCount;
    private Integer isGood;
    private Integer isEnd;
    private Integer isTop;
    private Integer status;
    private Long experience;
    private String content;
    private Long commentNum;
    private String avatarUrl;
    private String name;
    private String role;
    private String catename;
    private String kindname;
    private String time;

}