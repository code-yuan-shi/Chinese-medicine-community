package com.bbgu.zmz.community.dto;

import com.bbgu.zmz.community.model.User;

import java.util.Date;
import java.util.List;

@lombok.Data
public class Data {
    private boolean collection;
    private long days;
    private int experience;
    private Boolean signed;
    private List<Object> list;

   /* public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }*/
}
