package com.bbgu.zmz.community.model;

import lombok.Data;

@Data
public class CollectExt {
    private Long id;
    private Long userId;
    private Long topicId;
    private Long collectCreate;
    private Long collectModified;
    private String title;
    private String time;

}