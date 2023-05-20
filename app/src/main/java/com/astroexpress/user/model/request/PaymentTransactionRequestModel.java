package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentTransactionRequestModel {

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

    public PaymentTransactionRequestModel(String userId, String amount, String orderId, String paymentTransactionId, String paymentGateway, String paymentStatus, String requestJson, String responseJson, String remark, String note1, String note2) {
        this.userId = userId;
        this.amount = amount;
        this.orderId = orderId;
        this.paymentTransactionId = paymentTransactionId;
        this.paymentGateway = paymentGateway;
        this.paymentStatus = paymentStatus;
        this.requestJson = requestJson;
        this.responseJson = responseJson;
        this.remark = remark;
        this.note1 = note1;
        this.note2 = note2;
    }

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

}
