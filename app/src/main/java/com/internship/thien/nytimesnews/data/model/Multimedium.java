package com.internship.thien.nytimesnews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedium {

    @SerializedName("rank")
    @Expose
    Integer rank;
    @SerializedName("subtype")
    @Expose
    String subtype;
    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("height")
    @Expose
    Integer height;
    @SerializedName("width")
    @Expose
    Integer width;
    @SerializedName("subType")
    @Expose
    String subType;

    public Multimedium() {}

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

}

