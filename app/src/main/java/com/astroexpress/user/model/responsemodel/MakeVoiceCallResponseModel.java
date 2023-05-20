package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MakeVoiceCallResponseModel {

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

        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("KNumber")
        @Expose
        private String kNumber;
        @SerializedName("CallerId")
        @Expose
        private String callerId;
        @SerializedName("VoiceCallHistoryId")
        @Expose
        private Integer voiceCallHistoryId;

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getKNumber() {
            return kNumber;
        }

        public void setKNumber(String kNumber) {
            this.kNumber = kNumber;
        }

        public String getCallerId() {
            return callerId;
        }

        public void setCallerId(String callerId) {
            this.callerId = callerId;
        }

        public Integer getVoiceCallHistoryId() {
            return voiceCallHistoryId;
        }

        public void setVoiceCallHistoryId(Integer voiceCallHistoryId) {
            this.voiceCallHistoryId = voiceCallHistoryId;
        }

    }

}
