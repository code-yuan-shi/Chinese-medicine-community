package com.bbgu.zmz.community.dto;


import com.bbgu.zmz.community.model.WeekList;

import java.util.List;

public class RegRespObj {
    private int status;
    private String msg;
    private int code;
    private String action;
    private String url;
    private int count;
    private Data data;
    private Rows rows;
    private List<WeekList> weekList;

    public List<WeekList> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<WeekList> weekList) {
        this.weekList = weekList;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Rows getRows() {
        return rows;
    }

    public void setRows(Rows rows) {
        this.rows = rows;
    }
}
