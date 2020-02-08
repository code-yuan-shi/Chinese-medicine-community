package com.bbgu.zmz.community.model;

public class Kind {
    private Long id;

    private String kindname;

    private Long categoryId;

    private Long kindCreate;

    private Long kindModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname == null ? null : kindname.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getKindCreate() {
        return kindCreate;
    }

    public void setKindCreate(Long kindCreate) {
        this.kindCreate = kindCreate;
    }

    public Long getKindModified() {
        return kindModified;
    }

    public void setKindModified(Long kindModified) {
        this.kindModified = kindModified;
    }
}