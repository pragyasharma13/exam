package com.db.schoolapp.Model;

public class Homelist {
    private String exam,name,prize,class1;


    public Homelist(String exam, String name, String prize, String class1) {
        this.exam = exam;
        this.name = name;
        this.prize = prize;
        this.class1 = class1;

    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }





}

