package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
    public class UserTestimonialResponseModel {

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

            @SerializedName("UserTestimonialId")
            @Expose
            private String userTestimonialId;
            @SerializedName("Title")
            @Expose
            private String title;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("UserName")
            @Expose
            private String userName;
            @SerializedName("FileUrl")
            @Expose
            private String fileUrl;
            @SerializedName("FileThumbnail")
            @Expose
            private String fileThumbnail;
            @SerializedName("RatingCount")
            @Expose
            private String ratingCount;
            @SerializedName("CreatedOn")
            @Expose
            private String createdOn;
            @SerializedName("ModifiedOn")
            @Expose
            private String modifiedOn;
            @SerializedName("Remark")
            @Expose
            private String remark;

            public String getUserTestimonialId() {
                return userTestimonialId;
            }

            public void setUserTestimonialId(String userTestimonialId) {
                this.userTestimonialId = userTestimonialId;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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

            public String getRatingCount() {
                return ratingCount;
            }

            public void setRatingCount(String ratingCount) {
                this.ratingCount = ratingCount;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getModifiedOn() {
                return modifiedOn;
            }

            public void setModifiedOn(String modifiedOn) {
                this.modifiedOn = modifiedOn;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

        }
    }