package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Message {
    @Id
    private Integer id;

    @Column(name = "send_user_id")
    private Long sendUserId;

    @Column(name = "recv_user_id")
    private Long recvUserId;

    private String content;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "comment_id")
    private Long commentId;

    private Integer type;

    @Column(name = "is_read")
    private Integer isRead;

    @Column(name = "message_create")
    private Long messageCreate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return send_user_id
     */
    public Long getSendUserId() {
        return sendUserId;
    }

    /**
     * @param sendUserId
     */
    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * @return recv_user_id
     */
    public Long getRecvUserId() {
        return recvUserId;
    }

    /**
     * @param recvUserId
     */
    public void setRecvUserId(Long recvUserId) {
        this.recvUserId = recvUserId;
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
     * @return comment_id
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * @param commentId
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
     * @return is_read
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * @return message_create
     */
    public Long getMessageCreate() {
        return messageCreate;
    }

    /**
     * @param messageCreate
     */
    public void setMessageCreate(Long messageCreate) {
        this.messageCreate = messageCreate;
    }
}