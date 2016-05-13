package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleAction {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("sms")
    @Expose
    private Sms sms;
    @SerializedName("email")
    @Expose
    private ActionEmail email;

    /**
     *
     * @return
     * The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     *
     * @return
     * The type
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The sms
     */
    public Sms getSms() {
        return sms;
    }

    /**
     *
     * @param sms
     * The sms
     */
    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public ActionEmail getEmail(){
        return email;
    }

    public void setEmail(ActionEmail email){
        this.email = email;
    }
}