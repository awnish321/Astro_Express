package com.astroexpress.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypingStatusModel {

    @SerializedName("IsTyping")
    @Expose
    private Boolean isTyping;
    @SerializedName("IsUser")
    @Expose
    private Boolean isUser;

    public TypingStatusModel() {
        
    }

    public TypingStatusModel(Boolean isTyping, Boolean isUser) {
        this.isTyping = isTyping;
        this.isUser = isUser;
    }


    public Boolean getTyping() {
        return isTyping;
    }

    public void setTyping(Boolean typing) {
        isTyping = typing;
    }

    public Boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(Boolean isUser) {
        this.isUser = isUser;
    }
}
