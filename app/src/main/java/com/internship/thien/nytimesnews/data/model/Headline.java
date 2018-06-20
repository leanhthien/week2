package com.internship.thien.nytimesnews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Headline {

    @SerializedName("main")
    @Expose
    String main;
    @SerializedName("kicker")
    @Expose
    String kicker;

    public Headline() {}

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }


}