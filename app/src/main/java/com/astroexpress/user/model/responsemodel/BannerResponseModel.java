package com.astroexpress.user.model.responsemodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerResponseModel {

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

        @SerializedName("BannerId")
        @Expose
        private String bannerId;
        @SerializedName("FileUrl")
        @Expose
        private String fileUrl;
        @SerializedName("FileThumbnail")
        @Expose
        private String fileThumbnail;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("VisibleIndex")
        @Expose
        private String visibleIndex;
        @SerializedName("RedirectURL")
        @Expose
        private String redirectURL;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
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

        public String getVisibleIndex() {
            return visibleIndex;
        }

        public void setVisibleIndex(String visibleIndex) {
            this.visibleIndex = visibleIndex;
        }

        public String getRedirectURL() {
            return redirectURL;
        }

        public void setRedirectURL(String redirectURL) {
            this.redirectURL = redirectURL;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

    }
}
