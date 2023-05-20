package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentTransactionResponseModel {

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

        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("Amount")
        @Expose
        private String amount;
        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("PaymentTransactionId")
        @Expose
        private String paymentTransactionId;
        @SerializedName("PaymentGateway")
        @Expose
        private String paymentGateway;
        @SerializedName("PaymentStatus")
        @Expose
        private String paymentStatus;
        @SerializedName("RequestJson")
        @Expose
        private String requestJson;
        @SerializedName("ResponseJson")
        @Expose
        private String responseJson;
        @SerializedName("Remark")
        @Expose
        private String remark;
        @SerializedName("Note1")
        @Expose
        private String note1;
        @SerializedName("Note2")
        @Expose
        private String note2;
        @SerializedName("TransactionId")
        @Expose
        private String transactionId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPaymentTransactionId() {
            return paymentTransactionId;
        }

        public void setPaymentTransactionId(String paymentTransactionId) {
            this.paymentTransactionId = paymentTransactionId;
        }

        public String getPaymentGateway() {
            return paymentGateway;
        }

        public void setPaymentGateway(String paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getRequestJson() {
            return requestJson;
        }

        public void setRequestJson(String requestJson) {
            this.requestJson = requestJson;
        }

        public String getResponseJson() {
            return responseJson;
        }

        public void setResponseJson(String responseJson) {
            this.responseJson = responseJson;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getNote1() {
            return note1;
        }

        public void setNote1(String note1) {
            this.note1 = note1;
        }

        public String getNote2() {
            return note2;
        }

        public void setNote2(String note2) {
            this.note2 = note2;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

    }

}

