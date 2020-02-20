package com.bbgu.zmz.community.enums;


public enum MsgEnum {

    UPLOAD_SUCCESS(0,"上传成功"),
    UPLOAD_FAILE(1,"上传失败!"),
    OK(0,""),
    ADDCOLLECT(0,"已收藏"),
    HAVACOLLECT(1,"收藏记录已存在！"),
    NOTALLOWCOLLECT(1,"未审核，无法收藏！"),
    NOTLOGIN(1,"请登录后再操作！"),
    ALLOWLIMIT(1,"权限不足！"),
    HAVADONE(0,"已取消"),
    DETAIL_SUCCESS(0,"发表成功！"),
    KISS_NOT_ENOUGHT(1,"飞吻不足！"),
    CHANGE(0,"修改成功"),
    CODE_INCORRECT(1,"验证码错误！"),
    REPLY_SUCCESS(0,"回复成功"),
    NOT_ALLOW_COMMENT(1,"该帖未审核，无法评论！"),
    SHENHE_SUCCESS(0,"已审核通过"),
    STATUS_SUCCESS(0,"状态已更改"),
    DELETE_TOPIC(0,"该帖已删除"),
    ZAN_SUCCESS(0,"点赞成功"),
    ZAN_CANCEL(0,"点赞已取消"),
    UPDATE_COMMENT(0,"评论已更新"),
    DELETE_COMMENT(0,"该评论已删除"),
    MESSAGE_SUCCESS(0,"发送成功"),
    MESSAGE_FAILE(1,"服务器冒烟了，请稍后再试！"),
    INTERNEET_ERROR(1,"网络错误！"),
    CODE_SUCCESS(0,"验证码已发送"),
    USER_EMAIL_ERROR(1,"账号或者邮箱验证错误！"),
    REPASS_SUCCESS(0,"密码已重置,请使用新密码登录"),
    USER_EXIT(1,"用户已存在！"),
    ALLOW_REG(0,"恭喜,可以注册"),
    ACCOUNTID_NOT_ALLOW_EMPTY(1,"账号不能为空！"),
    ACCOUNTID_NUM_LIMIT(1,"账号不能少于6位数！"),
    EMAIL_EXIT(1,"抱歉，该邮箱已被使用！"),
    EMAIL_ALLOW(0,"恭喜，该邮箱可用"),
    EMAIL_NOT_ALLOW_EMPTY(1,"请先输入邮箱！"),
    EMAIL_INCORRECT(1,"邮箱格式不正确！"),
    REG_SUCCESS(0,"注册成功，激活邮件已经发送至您的邮箱"),
    PWD_ATYPISM(1,"两次输入的密码不一致！"),
    EMAIL_RESEND(0,"已成功将激活链接发送到了您的邮箱，接受可能会稍有延迟，请注意查收"),
    SEND_EMAIL_FAILE(1,"邮件发送失败，请更换邮箱！"),
    LOGIN_SUCCESS(0,"登录成功"),
    USER_PWD_INCORRECT(1,"用户名或者密码错误！"),
    OLD_PWD_INCORRECT(1,"原密码错误！"),




    ;

    private int code;
    private String message;

    MsgEnum(int code,String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
