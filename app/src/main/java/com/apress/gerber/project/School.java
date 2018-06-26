package com.apress.gerber.project;

import cn.bmob.v3.BmobObject;

/**
 * Created by Colonel on 2017/9/19.
 */

public class School extends BmobObject {

    School()
    {
        this.setTableName("2017");
    }

    private String s_id,location,s_name,majors_name;
    private String num;
    private int score,rank,number,majors_id,f_id;

    public void sets_id(String s_id)
    {
        this.s_id=s_id;
    }
    public String gets_id()
    {
        return s_id;
    }
    public void set_num(String num) {
        this.num = num;
    }
    public String get_num()
    {
        return num;
    }


    public void setlocation(String location)
    {
        this.location=location;
    }
    public String getlocation()
    {
        return location;
    }



    public void sets_name(String s_name)
    {
        this.s_name=s_name;
    }
    public String gets_name()
    {
        return s_name;
    }


    public void setmajors_name(String majors_name)
    {
        this.majors_name=majors_name;
    }
    public String getmajors_name()
    {
        return majors_name;
    }


    public void setscore(int score)
    {
        this.score=score;
    }
    public int getscore()
    {
        return score;
    }


    public void setrank(int rank)
    {
        this.rank=rank;
    }
    public int getrank()
    {
        return rank;
    }


    public void setnumber(int number)
    {
        this.number=number;
    }
    public int getnumber()
    {
        return number;
    }


    public void setmajors_id(int majors_id)
    {
        this.majors_id=majors_id;
    }
    public int getmajors_id()
    {
        return majors_id;
    }


    public void setf_id(int f_id)
    {
        this.f_id=f_id;
    }
    public int getf_id()
    {
        return f_id;
    }

}
