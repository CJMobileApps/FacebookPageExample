package com.cjfreelancing.facebookexample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Data {

    @SerializedName("created_time")
    @Expose
    private String createdTime;

    private String name;

    private String id;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
