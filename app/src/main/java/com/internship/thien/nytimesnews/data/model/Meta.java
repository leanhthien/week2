package com.internship.thien.nytimesnews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Meta {

    @SerializedName("hits")
    @Expose
    Integer hits;
    @SerializedName("offset")
    @Expose
    Integer offset;
    @SerializedName("time")
    @Expose
    Integer time;

    public Meta(){}

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
