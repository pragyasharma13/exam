package com.db.schoolapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamEnrollModels {

    @SerializedName("status")
    private  String status;

    @SerializedName("data_result")
    @Expose
    private  List<DataResultModel> dataResultModels;

    }
