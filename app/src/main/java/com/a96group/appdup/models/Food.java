
package com.a96group.appdup.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("protien")
    @Expose
    private String protien;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("item")
    @Expose
    private String item;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProtien() {
        return protien;
    }

    public void setProtien(String protien) {
        this.protien = protien;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
