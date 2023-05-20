package com.astroexpress.user.model.request;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRequestModel {

    @SerializedName("ChatId")
    @Expose
    private String chatId;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
    @SerializedName("ChatMessage")
    @Expose
    private String chatMessage;
    @SerializedName("IsSentByUser")
    @Expose
    private Boolean isSentByUser;
    @SerializedName("SeenStatus")
    @Expose
    private String seenStatus;
    @SerializedName("ChatType")
    @Expose
    private String chatType;
    @SerializedName("FirebaseChatId")
    @Expose
    private String firebaseChatId;
    @SerializedName("FileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("FileThumbnail")
    @Expose
    private String fileThumbnail;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("SessionId")
    @Expose
    private String sessionId;

    public ChatRequestModel() {
    }

    public ChatRequestModel(String userId, String astrologerId, String chatMessage, Boolean isSentByUser, String seenStatus, String chatType, String firebaseChatId, String fileUrl, String fileThumbnail,String sessionId) {
        this.userId = userId;
        this.astrologerId = astrologerId;
        this.chatMessage = chatMessage;
        this.isSentByUser = isSentByUser;
        this.seenStatus = seenStatus;
        this.chatType = chatType;
        this.firebaseChatId = firebaseChatId;
        this.fileUrl = fileUrl;
        this.fileThumbnail = fileThumbnail;
        this.sessionId = sessionId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getSentByUser() {
        return isSentByUser;
    }

    public void setSentByUser(Boolean sentByUser) {
        isSentByUser = sentByUser;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileThumbnail() {
        return fileThumbnail;
    }

    public void setFileThumbnail(String fileThumbnail) {
        this.fileThumbnail = fileThumbnail;
    }

    @PropertyName("UserId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("UserId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("AstrologerId")
    public String getAstrologerId() {
        return astrologerId;
    }

    @PropertyName("AstrologerId")
    public void setAstrologerId(String astrologerId) {
        this.astrologerId = astrologerId;
    }

    @PropertyName("ChatMessage")
    public String getChatMessage() {
        return chatMessage;
    }

    @PropertyName("ChatMessage")
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @PropertyName("IsSentByUser")
    public Boolean getIsSentByUser() {
        return isSentByUser;
    }

    @PropertyName("IsSentByUser")
    public void setIsSentByUser(Boolean isSentByUser) {
        this.isSentByUser = isSentByUser;
    }

    @PropertyName("SeenStatus")
    public String getSeenStatus() {
        return seenStatus;
    }

    @PropertyName("SeenStatus")
    public void setSeenStatus(String seenStatus) {
        this.seenStatus = seenStatus;
    }

    @PropertyName("FirebaseChatId")
    public String getFirebaseChatId() {
        return firebaseChatId;
    }

    @PropertyName("FirebaseChatId")
    public void setFirebaseChatId(String firebaseChatId) {
        this.firebaseChatId = firebaseChatId;
    }

    @PropertyName("ChatType")
    public String getChatType() {
        return chatType;
    }

    @PropertyName("ChatType")
    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    @PropertyName("ChatId")
    public String getChatId() {return chatId;}

    @PropertyName("ChatId")
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
