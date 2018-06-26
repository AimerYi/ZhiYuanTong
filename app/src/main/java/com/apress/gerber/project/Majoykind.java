package com.apress.gerber.project;

import cn.bmob.v3.BmobObject;

/**
 * Created by Colonel on 2017/9/19.
 */


public class Majoykind extends BmobObject {
    private String kind;
    private String num;
    private String majoy;
    private String majoy_kind_NUM;
    public Majoykind(){
        this.setTableName("majoykind");
    }

    public void setkind()
    {
        this.kind=kind;
    }
    public void setnum()
    {
        this.num=num;
    }
    public void setmajoy()
    {
        this.majoy=majoy;
    }
    public void setmajoy_kind_NUM()
    {
        this.majoy_kind_NUM=majoy_kind_NUM;
    }
    public String getkind()
    {
        return kind;
    }
    public String getnum()
    {
        return num;
    }
    public String getmajoy()
    {
        return majoy;
    }
    public String getmajoy_kind_NUM()
    {
        return majoy_kind_NUM;
    }


}
