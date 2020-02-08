package com.bbgu.zmz.community.model;

public class Message {
    private Integer id;

    private Long sendUserId;

    private Long recvUserId;

    private String content;

    private Long topicId;

    private Long commentId;

    private Integer type;

    private Integer isRead;

    private Long messageCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getRecvUserId() {
        return recvUserId;
    }

    public void setRecvUserId(Long recvUserId) {
        this.recvUserId = recvUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Long getMessageCreate() {
        return messageCreate;
    }

    public void setMessageCreate(Long messageCreate) {
        this.messageCreate = messageCreate;
    }
}