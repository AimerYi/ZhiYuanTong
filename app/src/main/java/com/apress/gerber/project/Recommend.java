package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/21.
 */

public class Recommend implements Comparable<Recommend>{
    private String name;
    private String gender;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public int compareTo(Recommend o) {
        int abs1=Math.abs(Integer.parseInt(this.getAge())- Integer.parseInt(MainActivity.ed));
        int abs2=Math.abs(Integer.parseInt(o.getAge())- Integer.parseInt(MainActivity.ed));
        int i=abs1-abs2;
        if (i == 0){
            int j=this.getAge().compareTo(o.getAge());
            if(j==0)
            {
                return this.getName().compareTo(o.getName());
            }
            else
                return j;
        }
        else
            return i;



    }





}