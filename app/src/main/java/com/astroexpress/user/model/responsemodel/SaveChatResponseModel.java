package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveChatResponseModel {

    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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

    public static class Result {

        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("ChatMessage")
        @Expose
        private String chatMessage;
        @SerializedName("FileUrl")
        @Expose
        private String fileUrl;
        @SerializedName("FileThumbnail")
        @Expose
        private String fileThumbnail;
        @SerializedName("IsSentByUser")
        @Expose
        private Boolean isSentByUser;
        @SerializedName("SeenStatus")
        @Expose
        private String seenStatus;
        @SerializedName("ChatId")
        @Expose
        private Integer chatId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public String getChatMessage() {
            return chatMessage;
        }

        public void setChatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
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

        public Boolean getIsSentByUser() {
            return isSentByUser;
        }

        public void setIsSentByUser(Boolean isSentByUser) {
            this.isSentByUser = isSentByUser;
        }

        public String getSeenStatus() {
            return seenStatus;
        }

        public void setSeenStatus(String seenStatus) {
            this.seenStatus = seenStatus;
        }

        public Integer getChatId() {return chatId;}

        public void setChatId(Integer chatId) {
            this.chatId = chatId;
        }
    }

}

