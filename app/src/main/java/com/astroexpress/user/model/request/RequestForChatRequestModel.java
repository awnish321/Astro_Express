package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestForChatRequestModel {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
    @SerializedName("NotificationType")
    @Expose
    private String notificationType;
    @SerializedName("IsFreeSession")
    @Expose
    private Boolean isFreeSession;

    public RequestForChatRequestModel(String userId, String userName, String astrologerId, String notificationType, Boolean isFreeSession) {
        this.userId = userId;
        this.userName = userName;
        this.astrologerId = astrologerId;
        this.notificationType = notificationType;
        this.isFreeSession = isFreeSession;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAstrologerId() {
        return astrologerId;
    }

    public void setAstrologerId(String astrologerId) {
        this.astrologerId = astrologerId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Boolean getFreeSession() {
        return isFreeSession;
    }

    public void setFreeSession(Boolean freeSession) {
        isFreeSession = freeSession;
    }
}
