package com.bbgu.zmz.community.dto;

import com.bbgu.zmz.community.enums.MsgEnum;

public class Result {

    private int status;
    private String msg;
    private Object data;

    // 几个构造方法
    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    public Result ok(MsgEnum msgEnum){
        return  new Result(msgEnum.getCode(), msgEnum.getMessage());
    }

    public Result ok(Object data) {
        return new Result(data);
    }

    public Result ok(MsgEnum msgEnum,Object data){
        return  new Result(msgEnum.getCode(), msgEnum.getMessage(),data);
    }


    public Result error(MsgEnum msgEnum) {
        return new Result(msgEnum.getCode(), msgEnum.getMessage());
    }

    public Result error(Object data) {
        return new Result(data);
    }

    public Result error(MsgEnum msgEnum,Object data) {
        return new Result(msgEnum.getCode(), msgEnum.getMessage(),data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
