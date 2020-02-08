package com.bbgu.zmz.community.model;

public class Comment {
    private Long id;

    private Long topicId;

    private String content;

    private Integer type;

    private Long userId;

    private Long commentCreate;

    private Long commentModified;

    private Long agreeNum;

    private Integer isAccept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommentCreate() {
        return commentCreate;
    }

    public void setCommentCreate(Long commentCreate) {
        this.commentCreate = commentCreate;
    }

    public Long getCommentModified() {
        return commentModified;
    }

    public void setCommentModified(Long commentModified) {
        this.commentModified = commentModified;
    }

    public Long getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Integer getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(Integer isAccept) {
        this.isAccept = isAccept;
    }
}