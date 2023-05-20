package com.astroexpress.user.model.responsemodel;

import com.astroexpress.user.model.request.ChatRequestModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ChatListResponseModel {

    @SerializedName("Result")
    @Expose
    private List<ChatRequestModel> result = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public List<ChatRequestModel> getResult() {
        return result;
    }

    public void setResult(List<ChatRequestModel> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

