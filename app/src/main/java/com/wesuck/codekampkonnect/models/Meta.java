package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 10-05-2016.
 */
public class Meta {
    @SerializedName("meta")
    @Expose
    private Pagination pagination;
    public void setPagination(Pagination pagination){
        this.pagination = pagination;
    }
    public Pagination getPagination(){
        return pagination;
    }

}
