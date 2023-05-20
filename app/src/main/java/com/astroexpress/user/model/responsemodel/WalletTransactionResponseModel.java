package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletTransactionResponseModel {

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

        @SerializedName("WalletTransactionId")
        @Expose
        private String walletTransactionId;
        @SerializedName("ParentId")
        @Expose
        private String ParentId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("Amount")
        @Expose
        private String amount;
        @SerializedName("DebitedForm")
        @Expose
        private String debitedForm;
        @SerializedName("StartTime")
        @Expose
        private String startTime;
        @SerializedName("EndTime")
        @Expose
        private String endTime;
        @SerializedName("TransactionFor")
        @Expose
        private String transactionFor;

        public String getWalletTransactionId() {
            return walletTransactionId;
        }

        public void setWalletTransactionId(String walletTransactionId) {
            this.walletTransactionId = walletTransactionId;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String parentId) {
            ParentId = parentId;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDebitedForm() {
            return debitedForm;
        }

        public void setDebitedForm(String debitedForm) {
            this.debitedForm = debitedForm;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTransactionFor() {
            return transactionFor;
        }

        public void setTransactionFor(String transactionFor) {
            this.transactionFor = transactionFor;
        }
    }
}
