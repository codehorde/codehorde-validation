package com.github.codehorde.validator.dto;

import com.github.codehorde.validator.constraints.URL;
import com.github.codehorde.validator.constraints.EnumString;
import com.github.codehorde.validator.constraints.In;
import com.github.codehorde.validator.constraints.Numeric;

/**
 * Created by baomingfeng at 2017-11-15 19:49:25
 */
public class PersonDTO
        implements java.io.Serializable {

    @URL
    private String picUrl;

    @EnumString(Dept.class)
    private String dept;

    @In({"male", "female"})
    private String gender;

    @In({"1", "2", "3", "4", "5"})
    private Integer score;

    @Numeric(min = "0.01", scale = 2)
    private String price;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
