package com.bbgu.zmz.community.model;

public class Collect {
    private Long id;

    private Long userId;

    private Long topicId;

    private Long collectCreate;

    private Long collectModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getCollectCreate() {
        return collectCreate;
    }

    public void setCollectCreate(Long collectCreate) {
        this.collectCreate = collectCreate;
    }

    public Long getCollectModified() {
        return collectModified;
    }

    public void setCollectModified(Long collectModified) {
        this.collectModified = collectModified;
    }
}