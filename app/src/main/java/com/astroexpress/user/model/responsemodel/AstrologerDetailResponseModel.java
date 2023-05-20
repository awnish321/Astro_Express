package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class AstrologerDetailResponseModel
    {
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

            @SerializedName("AstrologerId")
            @Expose
            private String astrologerId;
            @SerializedName("FirstName")
            @Expose
            private String firstName;
            @SerializedName("LastName")
            @Expose
            private String lastName;
            @SerializedName("IsBoosted")
            @Expose
            private Boolean isBoosted;
            @SerializedName("IsBusyOnCall")
            @Expose
            private Boolean isBusyOnCall;
            @SerializedName("CountryCode")
            @Expose
            private String countryCode;
            @SerializedName("Mobile")
            @Expose
            private String mobile;
            @SerializedName("Email")
            @Expose
            private String email;
            @SerializedName("ProfileImageUrl")
            @Expose
            private String profileImageUrl;
            @SerializedName("ProfileThumbnail")
            @Expose
            private Object profileThumbnail;
            @SerializedName("Speciality")
            @Expose
            private String speciality;
            @SerializedName("Language")
            @Expose
            private String language;
            @SerializedName("Experience")
            @Expose
            private String experience;
            @SerializedName("ChargePerMinute")
            @Expose
            private String chargePerMinute;
            @SerializedName("TotalCallMinuts")
            @Expose
            private String totalCallMinuts;
            @SerializedName("TotalChatMinuts")
            @Expose
            private String totalChatMinuts;
            @SerializedName("AboutUs")
            @Expose
            private String aboutUs;
            @SerializedName("IsOnlineForChat")
            @Expose
            private Boolean isOnlineForChat;
            @SerializedName("IsOnlineForCall")
            @Expose
            private Boolean isOnlineForCall;
            @SerializedName("IsOnlineForChatEM")
            @Expose
            private Boolean isOnlineForChatEM;
            @SerializedName("IsOnlineForCallEM")
            @Expose
            private Boolean isOnlineForCallEM;
            @SerializedName("IsOfferApplied")
            @Expose
            private Boolean isOfferApplied;
            @SerializedName("DiscountAmount")
            @Expose
            private String discountAmount;
            @SerializedName("DiscountType")
            @Expose
            private String discountType;
            @SerializedName("RatingCount")
            @Expose
            private String ratingCount;
            @SerializedName("Rating")
            @Expose
            private String rating;
            @SerializedName("ConsultantCount")
            @Expose
            private String consultantCount;
            @SerializedName("RemaningFreeSession")
            @Expose
            private Integer remaningFreeSession;

            public String getConsultantCount() {
                return consultantCount;
            }

            public void setConsultantCount(String consultantCount) {
                this.consultantCount = consultantCount;
            }

            public Boolean getBoosted() {
                return isBoosted;
            }

            public void setBoosted(Boolean boosted) {
                isBoosted = boosted;
            }

            public Boolean getBusyOnCall() {
                return isBusyOnCall;
            }

            public void setBusyOnCall(Boolean busyOnCall) {
                isBusyOnCall = busyOnCall;
            }

            public Boolean getOnlineForChat() {
                return isOnlineForChat;
            }

            public void setOnlineForChat(Boolean onlineForChat) {
                isOnlineForChat = onlineForChat;
            }

            public Boolean getOnlineForCall() {
                return isOnlineForCall;
            }

            public void setOnlineForCall(Boolean onlineForCall) {
                isOnlineForCall = onlineForCall;
            }

            public Boolean getOnlineForChatEM() {
                return isOnlineForChatEM;
            }

            public void setOnlineForChatEM(Boolean onlineForChatEM) {
                isOnlineForChatEM = onlineForChatEM;
            }

            public Boolean getOnlineForCallEM() {
                return isOnlineForCallEM;
            }

            public void setOnlineForCallEM(Boolean onlineForCallEM) {
                isOnlineForCallEM = onlineForCallEM;
            }

            public Boolean getOfferApplied() {
                return isOfferApplied;
            }

            public void setOfferApplied(Boolean offerApplied) {
                isOfferApplied = offerApplied;
            }

            public String getRatingCount() {
                return ratingCount;
            }

            public void setRatingCount(String ratingCount) {
                this.ratingCount = ratingCount;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getAstrologerId() {
                return astrologerId;
            }

            public void setAstrologerId(String astrologerId) {
                this.astrologerId = astrologerId;
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

            public Boolean getIsBoosted() {
                return isBoosted;
            }

            public void setIsBoosted(Boolean isBoosted) {
                this.isBoosted = isBoosted;
            }

            public Boolean getIsBusyOnCall() {
                return isBusyOnCall;
            }

            public void setIsBusyOnCall(Boolean isBusyOnCall) {
                this.isBusyOnCall = isBusyOnCall;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getProfileImageUrl() {
                return profileImageUrl;
            }

            public void setProfileImageUrl(String profileImageUrl) {
                this.profileImageUrl = profileImageUrl;
            }

            public Object getProfileThumbnail() {
                return profileThumbnail;
            }

            public void setProfileThumbnail(Object profileThumbnail) {
                this.profileThumbnail = profileThumbnail;
            }

            public String getSpeciality() {
                return speciality;
            }

            public void setSpeciality(String speciality) {
                this.speciality = speciality;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public String getChargePerMinute() {
                return chargePerMinute;
            }

            public void setChargePerMinute(String chargePerMinute) {
                this.chargePerMinute = chargePerMinute;
            }

            public String getTotalCallMinuts() {
                return totalCallMinuts;
            }

            public void setTotalCallMinuts(String totalCallMinuts) {
                this.totalCallMinuts = totalCallMinuts;
            }

            public String getTotalChatMinuts() {
                return totalChatMinuts;
            }

            public void setTotalChatMinuts(String totalChatMinuts) {
                this.totalChatMinuts = totalChatMinuts;
            }

            public String getAboutUs() {
                return aboutUs;
            }

            public void setAboutUs(String aboutUs) {
                this.aboutUs = aboutUs;
            }

            public Boolean getIsOnlineForChat() {
                return isOnlineForChat;
            }

            public void setIsOnlineForChat(Boolean isOnlineForChat) {
                this.isOnlineForChat = isOnlineForChat;
            }

            public Boolean getIsOnlineForCall() {
                return isOnlineForCall;
            }

            public void setIsOnlineForCall(Boolean isOnlineForCall) {
                this.isOnlineForCall = isOnlineForCall;
            }

            public Boolean getIsOnlineForChatEM() {
                return isOnlineForChatEM;
            }

            public void setIsOnlineForChatEM(Boolean isOnlineForChatEM) {
                this.isOnlineForChatEM = isOnlineForChatEM;
            }

            public Boolean getIsOnlineForCallEM() {
                return isOnlineForCallEM;
            }

            public void setIsOnlineForCallEM(Boolean isOnlineForCallEM) {
                this.isOnlineForCallEM = isOnlineForCallEM;
            }

            public Boolean getIsOfferApplied() {
                return isOfferApplied;
            }

            public void setIsOfferApplied(Boolean isOfferApplied) {
                this.isOfferApplied = isOfferApplied;
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

            public Integer getRemaningFreeSession() {
                return remaningFreeSession;
            }

            public void setRemaningFreeSession(Integer remaningFreeSession) {
                this.remaningFreeSession = remaningFreeSession;
            }
        }
    }
