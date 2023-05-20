package com.astroexpress.user.model;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatCallingModel {

    @SerializedName("RequestToAstrologer")
    @Expose
    private String requestToAstrologer;
    @SerializedName("UserId")
    @Expose
    private String userId;

    @PropertyName("RequestToAstrologer")
    public String getRequestToAstrologer() {
        return requestToAstrologer;
    }

    @PropertyName("RequestToAstrologer")
    public void setRequestToAstrologer(String requestToAstrologer) {
        this.requestToAstrologer = requestToAstrologer;
    }

    @PropertyName("UserId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("UserId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
