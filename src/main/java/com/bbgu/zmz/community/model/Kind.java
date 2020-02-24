package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Kind {
    @Id
    private Long id;

    private String kindname;

    @Column(name = "kind_create")
    private Long kindCreate;

    @Column(name = "kind_modified")
    private Long kindModified;

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
     * @return kindname
     */
    public String getKindname() {
        return kindname;
    }

    /**
     * @param kindname
     */
    public void setKindname(String kindname) {
        this.kindname = kindname == null ? null : kindname.trim();
    }

    /**
     * @return kind_create
     */
    public Long getKindCreate() {
        return kindCreate;
    }

    /**
     * @param kindCreate
     */
    public void setKindCreate(Long kindCreate) {
        this.kindCreate = kindCreate;
    }

    /**
     * @return kind_modified
     */
    public Long getKindModified() {
        return kindModified;
    }

    /**
     * @param kindModified
     */
    public void setKindModified(Long kindModified) {
        this.kindModified = kindModified;
    }
}