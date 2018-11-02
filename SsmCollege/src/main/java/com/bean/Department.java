package com.bean;

import java.util.List;

public class Department {
    private Integer departid;

    private String departname;

    private List<Major> major;

    public List<Major> getMajor() {
        return major;
    }

    public void setMajor(List<Major> major) {
        this.major = major;
    }


    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
    }
}