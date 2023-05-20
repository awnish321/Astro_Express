package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CallResponseModel {

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

    public  static class Result {

        @SerializedName("VoiceCallHistoryId")
        @Expose
        private String voiceCallHistoryId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("IVRStatus")
        @Expose
        private String iVRStatus;
        @SerializedName("IVRCallId")
        @Expose
        private String iVRCallId;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("ModifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("Remark")
        @Expose
        private String remark;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Duration")
        @Expose
        private String duration;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getVoiceCallHistoryId() {
            return voiceCallHistoryId;
        }

        public void setVoiceCallHistoryId(String voiceCallHistoryId) {
            this.voiceCallHistoryId = voiceCallHistoryId;
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

        public String getIVRStatus() {
            return iVRStatus;
        }

        public void setIVRStatus(String iVRStatus) {
            this.iVRStatus = iVRStatus;
        }

        public String getIVRCallId() {
            return iVRCallId;
        }

        public void setIVRCallId(String iVRCallId) {
            this.iVRCallId = iVRCallId;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

    }

    }

