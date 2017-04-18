package com.a96group.appdup.models;

/**
 * Created by ss on 18/4/17.
 */

public class User {
    public String _id;
    public String name;
    public String mobile;
    public String bmi;
    public String diabetes;
    public String veg;


    public User(
            String name,
            String mobile,
            String bmi,
            String diabetes,
            String veg
    ) {
        this._id = "";
        this.name = name;
        this.mobile = mobile;
        this.bmi = bmi;
        this.diabetes = diabetes;
        this.veg = veg;
    }

    public String getId() {
        return _id;
    }
}
