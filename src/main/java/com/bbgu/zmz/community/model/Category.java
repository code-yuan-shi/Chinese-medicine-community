package com.bbgu.zmz.community.model;

public class Category {
    private Long id;

    private String catename;

    private Long categoryCreate;

    private Long categoryModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename == null ? null : catename.trim();
    }

    public Long getCategoryCreate() {
        return categoryCreate;
    }

    public void setCategoryCreate(Long categoryCreate) {
        this.categoryCreate = categoryCreate;
    }

    public Long getCategoryModified() {
        return categoryModified;
    }

    public void setCategoryModified(Long categoryModified) {
        this.categoryModified = categoryModified;
    }
}