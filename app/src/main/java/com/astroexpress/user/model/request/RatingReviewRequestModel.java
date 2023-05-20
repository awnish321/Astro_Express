package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingReviewRequestModel {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
    @SerializedName("RatingCount")
    @Expose
    private String ratingCount;
    @SerializedName("Review")
    @Expose
    private String review;

    public RatingReviewRequestModel(String userId, String astrologerId, String ratingCount, String review) {
        this.userId = userId;
        this.astrologerId = astrologerId;
        this.ratingCount = ratingCount;
        this.review = review;
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

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
