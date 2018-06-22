package com.internship.thien.nytimesnews.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Responses {

    @SerializedName("docs")
    @Expose
    List<News> docs = null;
    @SerializedName("meta")
    @Expose
    Meta meta;

    public Responses() {}

    public List<News> getNews() {
        return docs;
    }

    public void setNews(List<News> docs) {
        this.docs = docs;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}

