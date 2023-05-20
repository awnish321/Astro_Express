package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AstrologerMultipleImageResponseModel {

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

        @SerializedName("FrequentContentId")
        @Expose
        private String frequentContentId;
        @SerializedName("FileUrls")
        @Expose
        private String fileUrls;
        @SerializedName("VideoUrls")
        @Expose
        private String videoUrls;
        @SerializedName("FileThumbnail")
        @Expose
        private Object fileThumbnail;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("VisibleIndex")
        @Expose
        private Object visibleIndex;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

        public String getFrequentContentId() {
            return frequentContentId;
        }

        public void setFrequentContentId(String frequentContentId) {
            this.frequentContentId = frequentContentId;
        }

        public String getFileUrls() {
            return fileUrls;
        }

        public void setFileUrls(String fileUrls) {
            this.fileUrls = fileUrls;
        }

        public String getVideoUrls() {
            return videoUrls;
        }

        public void setVideoUrls(String videoUrls) {
            this.videoUrls = videoUrls;
        }

        public Object getFileThumbnail() {
            return fileThumbnail;
        }

        public void setFileThumbnail(Object fileThumbnail) {
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

        public Object getVisibleIndex() {
            return visibleIndex;
        }

        public void setVisibleIndex(Object visibleIndex) {
            this.visibleIndex = visibleIndex;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

    }

}
