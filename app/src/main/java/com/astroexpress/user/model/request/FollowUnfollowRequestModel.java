package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUnfollowRequestModel {

    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("AstrologerId")
    @Expose
    private String AstrologerId;
    @SerializedName("IsFollow")
    @Expose
    private Boolean IsFollow;

    public FollowUnfollowRequestModel(String userId, String astrologerId, Boolean isFollow) {
        UserId = userId;
        AstrologerId = astrologerId;
        IsFollow = isFollow;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAstrologerId() {
        return AstrologerId;
    }

    public void setAstrologerId(String astrologerId) {
        AstrologerId = astrologerId;
    }

    public Boolean getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(Boolean isFollow) {
        IsFollow = isFollow;
    }
}


