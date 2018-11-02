package com.bean;

import java.util.Date;

public class Information {
    private Integer informationid;

    private String informationname;

    private Integer typeid;

    private Date date;

    private String filetype;

    private String uploader;

    private Infotype infotype;



    public Information() {
    }

    public Information(Integer informationid, String informationname, Integer typeid, Date date, String filetype, String uploader, Infotype infotype) {
        this.informationid = informationid;
        this.informationname = informationname;
        this.typeid = typeid;
        this.date = date;
        this.filetype = filetype;
        this.uploader = uploader;
        this.infotype = infotype;
    }

    public Infotype getInfotype() {
        return infotype;
    }

    public void setInfotype(Infotype infotype) {
        this.infotype = infotype;
    }

    public Integer getInformationid() {
        return informationid;
    }

    public void setInformationid(Integer informationid) {
        this.informationid = informationid;
    }

    public String getInformationname() {
        return informationname;
    }

    public void setInformationname(String informationname) {
        this.informationname = informationname == null ? null : informationname.trim();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }
}