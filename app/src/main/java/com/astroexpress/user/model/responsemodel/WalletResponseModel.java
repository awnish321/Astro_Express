package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class WalletResponseModel {

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

            @SerializedName("WalletId")
            @Expose
            private String walletId;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("RechargeAmount")
            @Expose
            private String rechargeAmount;
            @SerializedName("MinuteAmount")
            @Expose
            private String minuteAmount;
            @SerializedName("ModifiedOn")
            @Expose
            private String modifiedOn;

            public String getWalletId() {
                return walletId;
            }

            public void setWalletId(String walletId) {
                this.walletId = walletId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRechargeAmount() {
                return rechargeAmount;
            }

            public void setRechargeAmount(String rechargeAmount) {
                this.rechargeAmount = rechargeAmount;
            }

            public String getMinuteAmount() {
                return minuteAmount;
            }

            public void setMinuteAmount(String minuteAmount) {
                this.minuteAmount = minuteAmount;
            }

            public String getModifiedOn() {
                return modifiedOn;
            }

            public void setModifiedOn(String modifiedOn) {
                this.modifiedOn = modifiedOn;
            }

        }
    }
