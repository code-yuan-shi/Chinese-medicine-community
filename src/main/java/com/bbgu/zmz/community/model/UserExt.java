package com.bbgu.zmz.community.model;

import lombok.Data;

@Data
public class UserExt {
    private Long id;

    private Long accountId;

    private String token;

    private String pwd;

    private String email;

    private String name;

    private String city;

    private Integer sex;

    private String avatarUrl;

    private String bio;

    private Long kissNum;

    private Long userCreate;

    private Long userModified;

    private String role;

    private Integer status;

    private Long activeTime;

    private String activeCode;

    private String repass;

    private String url;
}