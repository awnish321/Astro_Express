package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletTransactionRequestModel {

    @SerializedName("WalletTransactionId")
    @Expose
    private String walletTransactionId;
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

    public WalletTransactionRequestModel(String walletTransactionId, String userId, String astrologerId, String amount, String debitedForm, String startTime, String endTime, String transactionFor) {
        this.walletTransactionId = walletTransactionId;
        this.userId = userId;
        this.astrologerId = astrologerId;
        this.amount = amount;
        this.debitedForm = debitedForm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.transactionFor = transactionFor;
    }

    public String getWalletTransactionId() {
        return walletTransactionId;
    }

    public void setWalletTransactionId(String walletTransactionId) {
        this.walletTransactionId = walletTransactionId;
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

