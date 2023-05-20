package com.astroexpress.user.model.request;

import android.text.Editable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitQueryRequestModel {

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

    public SubmitQueryRequestModel(String loginId, String fullName, String title, String description, String email, String mobile) {
        this.loginId = loginId;
        this.fullName = fullName;
        this.title = title;
        this.description = description;
        this.email = email;
        this.mobile = mobile;
    }

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

}
