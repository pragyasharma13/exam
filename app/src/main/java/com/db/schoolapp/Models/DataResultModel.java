package com.db.schoolapp.Models;

import com.google.gson.annotations.SerializedName;

public class DataResultModel {
    @SerializedName("user_exam_id")
    private  String user_exam_id;
    @SerializedName("status")
    private  String status;
    @SerializedName("exam_id")
    private  String exam_id;
    @SerializedName("exam_name")
    private  String exam_name;
    @SerializedName("exam_price")
    private  String exam_price;
    @SerializedName("class_name")
    private  String class_name;
    @SerializedName("registered_on")
    private  String registered_on;

    public String getRegistered_on() {
        return registered_on;
    }

    public void setRegistered_on(String registered_on) {
        this.registered_on = registered_on;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @SerializedName("img")
    private String img;

    public String getExam_price() {
        return exam_price;
    }

    public void setExam_price(String exam_price) {
        this.exam_price = exam_price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getExam_desc() {
        return exam_desc;
    }

    public void setExam_desc(String exam_desc) {
        this.exam_desc = exam_desc;
    }

    @SerializedName("exam_desc")
    private  String exam_desc;

    public String getUser_exam_id() {
        return user_exam_id;
    }

    public void setUser_exam_id(String user_exam_id) {
        this.user_exam_id = user_exam_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }
}
