package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveRatingReviewResponseModel {

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
        private Integer userId;
        @SerializedName("AstrologerId")
        @Expose
        private Integer astrologerId;
        @SerializedName("RatingCount")
        @Expose
        private Double ratingCount;
        @SerializedName("Review")
        @Expose
        private String review;
        @SerializedName("UserRatingId")
        @Expose
        private Integer userRatingId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(Integer astrologerId) {
            this.astrologerId = astrologerId;
        }

        public Double getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(Double ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public Integer getUserRatingId() {
            return userRatingId;
        }

        public void setUserRatingId(Integer userRatingId) {
            this.userRatingId = userRatingId;
        }

    }

}
