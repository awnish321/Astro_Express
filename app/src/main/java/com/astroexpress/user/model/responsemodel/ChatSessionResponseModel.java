package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class ChatSessionResponseModel {

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

            @SerializedName("ChatSessionId")
            @Expose
            private String chatSessionId;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("AstrologerId")
            @Expose
            private String astrologerId;
            @SerializedName("SessionId")
            @Expose
            private String sessionId;
            @SerializedName("IsTerminated")
            @Expose
            private String isTerminated;
            @SerializedName("TerminatedAt")
            @Expose
            private String terminatedAt;
            @SerializedName("RemainingTime")
            @Expose
            private String remainingTime;

            public String getChatSessionId() {
                return chatSessionId;
            }

            public void setChatSessionId(String chatSessionId) {
                this.chatSessionId = chatSessionId;
            }

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

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getIsTerminated() {
                return isTerminated;
            }

            public void setIsTerminated(String isTerminated) {
                this.isTerminated = isTerminated;
            }

            public String getTerminatedAt() {
                return terminatedAt;
            }

            public void setTerminatedAt(String terminatedAt) {
                this.terminatedAt = terminatedAt;
            }

            public String getRemainingTime() {
                return remainingTime;
            }

            public void setRemainingTime(String remainingTime) {
                this.remainingTime = remainingTime;
            }

        }

    }