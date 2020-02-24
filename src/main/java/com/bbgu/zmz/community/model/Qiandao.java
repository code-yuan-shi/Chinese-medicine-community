package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Qiandao {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private Long total;

    @Column(name = "qiandao_create")
    private Long qiandaoCreate;

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
     * @return total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return qiandao_create
     */
    public Long getQiandaoCreate() {
        return qiandaoCreate;
    }

    /**
     * @param qiandaoCreate
     */
    public void setQiandaoCreate(Long qiandaoCreate) {
        this.qiandaoCreate = qiandaoCreate;
    }
}