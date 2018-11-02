package com.bean;

import java.util.List;

public class Major {
    private Integer majorid;

    private String majorname;

    private Integer departid;

    private List<UserTb> userTb;

    public List<UserTb> getUserTb() {
        return userTb;
    }

    public void setUserTb(List<UserTb> userTb) {
        this.userTb = userTb;
    }

    public Integer getMajorid() {
        return majorid;
    }

    public void setMajorid(Integer majorid) {
        this.majorid = majorid;
    }

    public String getMajorname() {
        return majorname;
    }

    public void setMajorname(String majorname) {
        this.majorname = majorname == null ? null : majorname.trim();
    }

    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }
}