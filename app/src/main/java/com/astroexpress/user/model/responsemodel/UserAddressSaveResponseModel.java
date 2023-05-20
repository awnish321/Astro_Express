package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class UserAddressSaveResponseModel {

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

            @SerializedName("Address1")
            @Expose
            private String address1;
            @SerializedName("Address2")
            @Expose
            private String address2;
            @SerializedName("Address3")
            @Expose
            private String address3;
            @SerializedName("Landmark")
            @Expose
            private String landmark;
            @SerializedName("Area")
            @Expose
            private String area;
            @SerializedName("Pincode")
            @Expose
            private String pincode;
            @SerializedName("PersonName")
            @Expose
            private String personName;
            @SerializedName("MobileNumber")
            @Expose
            private String mobileNumber;
            @SerializedName("CountryCode")
            @Expose
            private String countryCode;
            @SerializedName("Remark")
            @Expose
            private String remark;
            @SerializedName("ParentId")
            @Expose
            private String parentId;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("ResidenceTypeId")
            @Expose
            private String residenceTypeId;
            @SerializedName("IsPrimary")
            @Expose
            private Boolean isPrimary;
            @SerializedName("CreatedOn")
            @Expose
            private String createdOn;
            @SerializedName("ModifiedOn")
            @Expose
            private String modifiedOn;
            @SerializedName("UserAddressId")
            @Expose
            private Integer userAddressId;

            public String getAddress1() {
                return address1;
            }

            public void setAddress1(String address1) {
                this.address1 = address1;
            }

            public String getAddress2() {
                return address2;
            }

            public void setAddress2(String address2) {
                this.address2 = address2;
            }

            public String getAddress3() {
                return address3;
            }

            public void setAddress3(String address3) {
                this.address3 = address3;
            }

            public String getLandmark() {
                return landmark;
            }

            public void setLandmark(String landmark) {
                this.landmark = landmark;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getPersonName() {
                return personName;
            }

            public void setPersonName(String personName) {
                this.personName = personName;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getResidenceTypeId() {
                return residenceTypeId;
            }

            public void setResidenceTypeId(String residenceTypeId) {
                this.residenceTypeId = residenceTypeId;
            }

            public Boolean getIsPrimary() {
                return isPrimary;
            }

            public void setIsPrimary(Boolean isPrimary) {
                this.isPrimary = isPrimary;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getModifiedOn() {
                return modifiedOn;
            }

            public void setModifiedOn(String modifiedOn) {
                this.modifiedOn = modifiedOn;
            }

            public Integer getUserAddressId() {
                return userAddressId;
            }

            public void setUserAddressId(Integer userAddressId) {
                this.userAddressId = userAddressId;
            }

        }

    }
