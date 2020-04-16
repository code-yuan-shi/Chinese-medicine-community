package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Reward {
    @Id
    private Long id;

    @Column(name = "reward_user_id")
    private Long rewardUserId;

    @Column(name = "receive_user_id")
    private Long receiveUserId;

    @Column(name = "reward_num")
    private Long rewardNum;

    @Column(name = "reward_create")
    private Long rewardCreate;

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
     * @return reward_user_id
     */
    public Long getRewardUserId() {
        return rewardUserId;
    }

    /**
     * @param rewardUserId
     */
    public void setRewardUserId(Long rewardUserId) {
        this.rewardUserId = rewardUserId;
    }

    /**
     * @return receive_user_id
     */
    public Long getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * @param receiveUserId
     */
    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    /**
     * @return reward_num
     */
    public Long getRewardNum() {
        return rewardNum;
    }

    /**
     * @param rewardNum
     */
    public void setRewardNum(Long rewardNum) {
        this.rewardNum = rewardNum;
    }

    /**
     * @return reward_create
     */
    public Long getRewardCreate() {
        return rewardCreate;
    }

    /**
     * @param rewardCreate
     */
    public void setRewardCreate(Long rewardCreate) {
        this.rewardCreate = rewardCreate;
    }
}