package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Emails {

    @SerializedName("data")
    @Expose
    private List<ParticularEmail> data = new ArrayList<ParticularEmail>();

    /**
     *
     * @return
     * The data
     */
    public List<ParticularEmail> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ParticularEmail> data) {
        this.data = data;
    }

}