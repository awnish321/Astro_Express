package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class SuggestedRemediesResponseModel {

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

            @SerializedName("RemedyId")
            @Expose
            private String remedyId;
            @SerializedName("IsOwnedRemedy")
            @Expose
            private String IsOwnedRemedy;
            @SerializedName("AstrologerId")
            @Expose
            private String astrologerId;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("ProductName")
            @Expose
            private String productName;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("Attachments")
            @Expose
            private List<String> attachments = null;
            @SerializedName("CreatedOn")
            @Expose
            private String createdOn;
            @SerializedName("RemedyCategoryId")
            @Expose
            private String remedyCategoryId;
            @SerializedName("CategoryName")
            @Expose
            private String categoryName;
            @SerializedName("FirstName")
            @Expose
            private String firstName;
            @SerializedName("LastName")
            @Expose
            private String lastName;
            @SerializedName("BookingStatusId")
            @Expose
            private String bookingStatusId;
            @SerializedName("BookingStatus")
            @Expose
            private String bookingStatus;

            public String getIsOwnedRemedy() {
                return IsOwnedRemedy;
            }

            public void setIsOwnedRemedy(String isOwnedRemedy) {
                IsOwnedRemedy = isOwnedRemedy;
            }

            public String getBookingStatusId() {
                return bookingStatusId;
            }

            public void setBookingStatusId(String bookingStatusId) {
                this.bookingStatusId = bookingStatusId;
            }

            public String getBookingStatus() {
                return bookingStatus;
            }

            public void setBookingStatus(String bookingStatus) {
                this.bookingStatus = bookingStatus;
            }

            public String getRemedyId() {
                return remedyId;
            }

            public void setRemedyId(String remedyId) {
                this.remedyId = remedyId;
            }

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

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getAttachments() {
                return attachments;
            }

            public void setAttachments(List<String> attachments) {
                this.attachments = attachments;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getRemedyCategoryId() {
                return remedyCategoryId;
            }

            public void setRemedyCategoryId(String remedyCategoryId) {
                this.remedyCategoryId = remedyCategoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
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
