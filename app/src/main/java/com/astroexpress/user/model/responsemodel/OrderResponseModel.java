package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponseModel {

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

    public class Result {

        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("OrderType")
        @Expose
        private String orderType;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("CGST")
        @Expose
        private String cgst;
        @SerializedName("SGST")
        @Expose
        private String sgst;
        @SerializedName("DiscountAmount")
        @Expose
        private String discountAmount;
        @SerializedName("FinalAmount")
        @Expose
        private String finalAmount;
        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("EffectiveAmount")
        @Expose
        private String effectiveAmount;

        public String getEffectiveAmount() {
            return effectiveAmount;
        }

        public void setEffectiveAmount(String effectiveAmount) {
            this.effectiveAmount = effectiveAmount;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCgst() {
            return cgst;
        }

        public void setCgst(String cgst) {
            this.cgst = cgst;
        }

        public String getSgst() {
            return sgst;
        }

        public void setSgst(String sgst) {
            this.sgst = sgst;
        }

        public String getFinalAmount() {
            return finalAmount;
        }

        public void setFinalAmount(String finalAmount) {
            this.finalAmount = finalAmount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }
    }

}

