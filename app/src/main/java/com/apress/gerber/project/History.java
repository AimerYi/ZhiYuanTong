package com.apress.gerber.project;

import cn.bmob.v3.BmobObject;

/**
 * Created by Colonel on 2017/9/23.
 */

public class History extends BmobObject {

    private String id;
    private String majoy;
    private String s_name;
    private String socoure;
    private String record;
    private long  time;

    public void setid(String id) {
        this.id=id;
    }
    public String getid() {
        return id;
    }

    public void setmajoy(String majoy) {
        this.majoy=majoy;
    }
    public String getmajoy() {
        return majoy;
    }

    public void sets_name(String s_name) {
        this.s_name=s_name;
    }
    public String gets_name() {
        return s_name;
    }

    public void setsocoure(String socoure) {
        this.socoure=socoure;
    }
    public String getsocoure() {
        return socoure;
    }


    public void setrecord(String record) {
        this.record=record;
    }
    public String getrecord() {
        return record;
    }


    public void settime() {
        time =System.currentTimeMillis();
    }
    public long gettime() {
        return time;
    }

}
