package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class SaveChatImageResponseModel {

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

            @SerializedName("AstrologerId")
            @Expose
            private String astrologerId;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("Attachments")
            @Expose
            private List<String> attachments;

            public String getAstrologerId() {
                return astrologerId;
            }

            public void setAstrologerId(String astrologerId) {
                this.astrologerId = astrologerId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public List<String> getAttachments() {
                return attachments;
            }

            public void setAttachments(List<String> attachments) {
                this.attachments = attachments;
            }

        }

    }

