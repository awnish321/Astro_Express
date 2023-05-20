package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponResponseModel {

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

        @SerializedName("CouponId")
        @Expose
        private String couponId;
        @SerializedName("CouponCode")
        @Expose
        private String couponCode;
        @SerializedName("CouponType")
        @Expose
        private String couponType;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Descrption")
        @Expose
        private String descrption;
        @SerializedName("DiscountAmount")
        @Expose
        private String discountAmount;
        @SerializedName("DiscountType")
        @Expose
        private String discountType;
        @SerializedName("ValidTill")
        @Expose
        private String validTill;
        @SerializedName("CouponTypeId")
        @Expose
        private String couponTypeId;

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) { this.title = title;
        }

        public String getDescrption() {
            return descrption;
        }

        public void setDescrption(String descrption) {
            this.descrption = descrption;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getValidTill() {
            return validTill;
        }

        public void setValidTill(String validTill) {
            this.validTill = validTill;
        }

        public String getCouponTypeId() {
            return couponTypeId;
        }

        public void setCouponTypeId(String couponTypeId) {
            this.couponTypeId = couponTypeId;
        }

    }


}

