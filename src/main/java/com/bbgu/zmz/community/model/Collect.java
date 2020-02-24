package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Collect {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "collect_create")
    private Long collectCreate;

    @Column(name = "collect_modified")
    private Long collectModified;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return topic_id
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * @param topicId
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * @return collect_create
     */
    public Long getCollectCreate() {
        return collectCreate;
    }

    /**
     * @param collectCreate
     */
    public void setCollectCreate(Long collectCreate) {
        this.collectCreate = collectCreate;
    }

    /**
     * @return collect_modified
     */
    public Long getCollectModified() {
        return collectModified;
    }

    /**
     * @param collectModified
     */
    public void setCollectModified(Long collectModified) {
        this.collectModified = collectModified;
    }
}