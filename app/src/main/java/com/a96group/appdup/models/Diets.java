
package com.a96group.appdup.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diets {

    @SerializedName("plan")
    @Expose
    private String plan;
    @SerializedName("monday")
    @Expose
    private Day monday;

    public String getPlan() {
        return plan;
    }

    public Day getDay() {
        return monday;
    }

}
