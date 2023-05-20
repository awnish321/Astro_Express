package com.astroexpress.user.model.responsemodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUnfollowResponseModel {

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
        @SerializedName("IsFollow")
        @Expose
        private Boolean isFollow;
        @SerializedName("UserFollowerId")
        @Expose
        private Integer userFollowerId;

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

        public Boolean getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(Boolean isFollow) {
            this.isFollow = isFollow;
        }

        public Integer getUserFollowerId() {
            return userFollowerId;
        }

        public void setUserFollowerId(Integer userFollowerId) {
            this.userFollowerId = userFollowerId;
        }

    }
}
