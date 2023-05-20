package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class BlogResponseModel {

        @SerializedName("Result")
        @Expose
        private List<Result> result;
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

            @SerializedName("BlogsId")
            @Expose
            private String blogsId;
            @SerializedName("Title")
            @Expose
            private String title;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("FileUrl")
            @Expose
            private String fileUrl;
            @SerializedName("FileThumbnail")
            @Expose
            private String fileThumbnail;
            @SerializedName("VisibleIndex")
            @Expose
            private Object visibleIndex;
            @SerializedName("RedirectURL")
            @Expose
            private String redirectURL;
            @SerializedName("CreatedOn")
            @Expose
            private Object createdOn;
            @SerializedName("ModifiedOn")
            @Expose
            private Object modifiedOn;
            @SerializedName("SuperUserId")
            @Expose
            private String superUserId;

            public String getBlogsId() {
                return blogsId;
            }

            public void setBlogsId(String blogsId) {
                this.blogsId = blogsId;
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

            public Object getVisibleIndex() {
                return visibleIndex;
            }

            public void setVisibleIndex(Object visibleIndex) {
                this.visibleIndex = visibleIndex;
            }

            public String getRedirectURL() {
                return redirectURL;
            }

            public void setRedirectURL(String redirectURL) {
                this.redirectURL = redirectURL;
            }

            public Object getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(Object createdOn) {
                this.createdOn = createdOn;
            }

            public Object getModifiedOn() {
                return modifiedOn;
            }

            public void setModifiedOn(Object modifiedOn) {
                this.modifiedOn = modifiedOn;
            }

            public String getSuperUserId() {
                return superUserId;
            }

            public void setSuperUserId(String superUserId) {
                this.superUserId = superUserId;
            }

        }
    }
