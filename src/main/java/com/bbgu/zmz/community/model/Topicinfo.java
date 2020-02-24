package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Topicinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "topic_create")
    private Long topicCreate;

    @Column(name = "topic_modified")
    private Long topicModified;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "kind_id")
    private Long kindId;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "is_good")
    private Integer isGood;

    @Column(name = "is_end")
    private Integer isEnd;

    @Column(name = "is_top")
    private Integer isTop;

    private Integer status;

    private Long experience;

    private String content;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return topic_create
     */
    public Long getTopicCreate() {
        return topicCreate;
    }

    /**
     * @param topicCreate
     */
    public void setTopicCreate(Long topicCreate) {
        this.topicCreate = topicCreate;
    }

    /**
     * @return topic_modified
     */
    public Long getTopicModified() {
        return topicModified;
    }

    /**
     * @param topicModified
     */
    public void setTopicModified(Long topicModified) {
        this.topicModified = topicModified;
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
     * @return category_id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return kind_id
     */
    public Long getKindId() {
        return kindId;
    }

    /**
     * @param kindId
     */
    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    /**
     * @return view_count
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return is_good
     */
    public Integer getIsGood() {
        return isGood;
    }

    /**
     * @param isGood
     */
    public void setIsGood(Integer isGood) {
        this.isGood = isGood;
    }

    /**
     * @return is_end
     */
    public Integer getIsEnd() {
        return isEnd;
    }

    /**
     * @param isEnd
     */
    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * @return is_top
     */
    public Integer getIsTop() {
        return isTop;
    }

    /**
     * @param isTop
     */
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return experience
     */
    public Long getExperience() {
        return experience;
    }

    /**
     * @param experience
     */
    public void setExperience(Long experience) {
        this.experience = experience;
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
}