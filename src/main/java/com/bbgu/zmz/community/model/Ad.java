package com.bbgu.zmz.community.model;

import javax.persistence.*;

public class Ad {
    @Id
    private Integer id;

    private String title;

    private String url;

    private String image;

    @Column(name = "ad_start")
    private Long adStart;

    @Column(name = "ad_end")
    private Long adEnd;

    @Column(name = "ad_create")
    private Long adCreate;

    @Column(name = "ad_modified")
    private Long adModified;

    private Integer status;

    private String pos;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * @return ad_start
     */
    public Long getAdStart() {
        return adStart;
    }

    /**
     * @param adStart
     */
    public void setAdStart(Long adStart) {
        this.adStart = adStart;
    }

    /**
     * @return ad_end
     */
    public Long getAdEnd() {
        return adEnd;
    }

    /**
     * @param adEnd
     */
    public void setAdEnd(Long adEnd) {
        this.adEnd = adEnd;
    }

    /**
     * @return ad_create
     */
    public Long getAdCreate() {
        return adCreate;
    }

    /**
     * @param adCreate
     */
    public void setAdCreate(Long adCreate) {
        this.adCreate = adCreate;
    }

    /**
     * @return ad_modified
     */
    public Long getAdModified() {
        return adModified;
    }

    /**
     * @param adModified
     */
    public void setAdModified(Long adModified) {
        this.adModified = adModified;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return pos
     */
    public String getPos() {
        return pos;
    }

    /**
     * @param pos
     */
    public void setPos(String pos) {
        this.pos = pos == null ? null : pos.trim();
    }
}