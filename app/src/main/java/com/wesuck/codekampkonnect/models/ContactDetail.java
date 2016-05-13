package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 13-05-2016.
 */
public class ContactDetail {


    @SerializedName("data")
    @Expose
    private Contact data;

    public Contact getData() {
        return data;
    }

    public void setData(Contact data) {
        this.data = data;
    }

}
