package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActionEmail {

    @SerializedName("data")
    @Expose
    private ActionEmailData data;

    public void setData( ActionEmailData data){
        this.data = data;
    }

    public ActionEmailData getData(){
        return data;
    }
}