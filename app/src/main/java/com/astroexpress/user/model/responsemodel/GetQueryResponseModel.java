package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetQueryResponseModel {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
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

        @SerializedName("HelpTokenId")
        @Expose
        private String helpTokenId;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("TokenId")
        @Expose
        private String tokenId;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("RisedFrom")
        @Expose
        private String risedFrom;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

        public String getHelpTokenId() {
            return helpTokenId;
        }

        public void setHelpTokenId(String helpTokenId) {
            this.helpTokenId = helpTokenId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRisedFrom() {
            return risedFrom;
        }

        public void setRisedFrom(String risedFrom) {
            this.risedFrom = risedFrom;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

    }

}
