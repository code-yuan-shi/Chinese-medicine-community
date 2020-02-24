package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class User {
    @Id
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    private String token;

    private String pwd;

    private String email;

    private String name;

    private String city;

    private Integer sex;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String bio;

    @Column(name = "kiss_num")
    private Long kissNum;

    @Column(name = "user_create")
    private Long userCreate;

    @Column(name = "user_modified")
    private Long userModified;

    private String role;

    private Integer status;

    @Column(name = "active_time")
    private Long activeTime;

    @Column(name = "active_code")
    private String activeCode;

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
     * @return account_id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return avatar_url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * @return kiss_num
     */
    public Long getKissNum() {
        return kissNum;
    }

    /**
     * @param kissNum
     */
    public void setKissNum(Long kissNum) {
        this.kissNum = kissNum;
    }

    /**
     * @return user_create
     */
    public Long getUserCreate() {
        return userCreate;
    }

    /**
     * @param userCreate
     */
    public void setUserCreate(Long userCreate) {
        this.userCreate = userCreate;
    }

    /**
     * @return user_modified
     */
    public Long getUserModified() {
        return userModified;
    }

    /**
     * @param userModified
     */
    public void setUserModified(Long userModified) {
        this.userModified = userModified;
    }

    /**
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
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
     * @return active_time
     */
    public Long getActiveTime() {
        return activeTime;
    }

    /**
     * @param activeTime
     */
    public void setActiveTime(Long activeTime) {
        this.activeTime = activeTime;
    }

    /**
     * @return active_code
     */
    public String getActiveCode() {
        return activeCode;
    }

    /**
     * @param activeCode
     */
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode == null ? null : activeCode.trim();
    }
}