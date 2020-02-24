package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_id")
    private Long topicId;

    private String content;

    private Integer type;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "comment_create")
    private Long commentCreate;

    @Column(name = "comment_modified")
    private Long commentModified;

    @Column(name = "agree_num")
    private Long agreeNum;

    @Column(name = "is_accept")
    private Integer isAccept;

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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return comment_create
     */
    public Long getCommentCreate() {
        return commentCreate;
    }

    /**
     * @param commentCreate
     */
    public void setCommentCreate(Long commentCreate) {
        this.commentCreate = commentCreate;
    }

    /**
     * @return comment_modified
     */
    public Long getCommentModified() {
        return commentModified;
    }

    /**
     * @param commentModified
     */
    public void setCommentModified(Long commentModified) {
        this.commentModified = commentModified;
    }

    /**
     * @return agree_num
     */
    public Long getAgreeNum() {
        return agreeNum;
    }

    /**
     * @param agreeNum
     */
    public void setAgreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
    }

    /**
     * @return is_accept
     */
    public Integer getIsAccept() {
        return isAccept;
    }

    /**
     * @param isAccept
     */
    public void setIsAccept(Integer isAccept) {
        this.isAccept = isAccept;
    }
}