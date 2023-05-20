package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitQueryResponseModel {

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

        @SerializedName("LoginId")
        @Expose
        private String loginId;
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
        @SerializedName("RisedFrom")
        @Expose
        private String risedFrom;
        @SerializedName("HelpTokenId")
        @Expose
        private Integer helpTokenId;

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
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

        public String getRisedFrom() {
            return risedFrom;
        }

        public void setRisedFrom(String risedFrom) {
            this.risedFrom = risedFrom;
        }

        public Integer getHelpTokenId() {
            return helpTokenId;
        }

        public void setHelpTokenId(Integer helpTokenId) {
            this.helpTokenId = helpTokenId;
        }

    }

}

