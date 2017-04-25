
package com.a96group.appdup.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("din")
    @Expose
    private List<Food> din = null;
    @SerializedName("even")
    @Expose
    private List<Food> even = null;
    @SerializedName("lunch")
    @Expose
    private List<Food> lunch = null;
    @SerializedName("break")
    @Expose
    private List<Food> _break = null;
    @SerializedName("early")
    @Expose
    private List<Food> early = null;

    public List<Food> getDin() {
        return din;
    }

    public List<Food> getEven() {
        return even;
    }

    public List<Food> get_break() {
        return _break;
    }

    public List<Food> getLunch() {
        return lunch;
    }

    public List<Food> getEarly() {
        return early;
    }
}
