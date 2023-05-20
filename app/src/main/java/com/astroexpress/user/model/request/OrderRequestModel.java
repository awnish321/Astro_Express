package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequestModel {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("IsOfferApplied")
    @Expose
    private Boolean isOfferApplied;
    @SerializedName("OfferId")
    @Expose
    private String offerId;
    @SerializedName("IsCouponApplied")
    @Expose
    private Boolean isCouponApplied;
    @SerializedName("CouponCode")
    @Expose
    private String couponCode;
    @SerializedName("AddressId")
    @Expose
    private String addressId;
    @SerializedName("RemedyId")
    @Expose
    private String remedyId;

    public OrderRequestModel(String userId, String orderType, String totalAmount, Boolean isOfferApplied, String offerId, Boolean isCouponApplied, String couponCode, String remedyId, String addressId) {
        this.userId = userId;
        this.orderType = orderType;
        this.totalAmount = totalAmount;
        this.isOfferApplied = isOfferApplied;
        this.offerId=offerId;
        this.isCouponApplied = isCouponApplied;
        this.couponCode = couponCode;
        this.addressId = addressId;
        this.remedyId = remedyId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getIsOfferApplied() {
        return isOfferApplied;
    }

    public void setIsOfferApplied(Boolean isOfferApplied) {
        this.isOfferApplied = isOfferApplied;
    }

    public Boolean getIsCouponApplied() {
        return isCouponApplied;
    }

    public void setIsCouponApplied(Boolean isCouponApplied) {
        this.isCouponApplied = isCouponApplied;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
}
