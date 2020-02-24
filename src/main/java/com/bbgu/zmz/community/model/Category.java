package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Category {
    @Id
    private Long id;

    private String catename;

    @Column(name = "category_create")
    private Long categoryCreate;

    @Column(name = "category_modified")
    private Long categoryModified;

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
     * @return catename
     */
    public String getCatename() {
        return catename;
    }

    /**
     * @param catename
     */
    public void setCatename(String catename) {
        this.catename = catename == null ? null : catename.trim();
    }

    /**
     * @return category_create
     */
    public Long getCategoryCreate() {
        return categoryCreate;
    }

    /**
     * @param categoryCreate
     */
    public void setCategoryCreate(Long categoryCreate) {
        this.categoryCreate = categoryCreate;
    }

    /**
     * @return category_modified
     */
    public Long getCategoryModified() {
        return categoryModified;
    }

    /**
     * @param categoryModified
     */
    public void setCategoryModified(Long categoryModified) {
        this.categoryModified = categoryModified;
    }
}