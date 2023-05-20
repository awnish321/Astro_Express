package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestForChatResponseModel {

    @SerializedName("MessageId")
    @Expose
    private Long messageId;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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