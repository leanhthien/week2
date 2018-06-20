package com.internship.thien.nytimesnews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Keyword {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("value")
    @Expose
    String value;
    @SerializedName("rank")
    @Expose
    Integer rank;
    @SerializedName("major")
    @Expose
    String major;

    public Keyword() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}