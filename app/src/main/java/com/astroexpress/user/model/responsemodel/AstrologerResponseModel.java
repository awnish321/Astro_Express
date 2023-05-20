package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AstrologerResponseModel {

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

        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
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
        private String profileThumbnail;
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
        @SerializedName("CurrencyType")
        @Expose
        private String currencyType;
        @SerializedName("Rating")
        @Expose
        private String rating;
        @SerializedName("RatingCount")
        @Expose
        private String ratingCount;
        @SerializedName("Followers")
        @Expose
        private String followers;
        @SerializedName("TotalCallMinuts")
        @Expose
        private String totalCallMinuts;
        @SerializedName("TotalChatMinuts")
        @Expose
        private String totalChatMinuts;
        @SerializedName("AboutUs")
        @Expose
        private String aboutUs;
        @SerializedName("IsBusyOnCall")
        @Expose
        private Boolean isBusyOnCall;
        @SerializedName("IsOnlineForCallEM")
        @Expose
        private Boolean isOnlineForCallEM;
        @SerializedName("IsBoosted")
        @Expose
        private Boolean isBoosted;
        @SerializedName("IsOfferApplied")
        @Expose
        private Boolean isOfferApplied;
        @SerializedName("IsOnlineForChatEM")
        @Expose
        private Boolean isOnlineForChatEM;
        @SerializedName("IsOnlineForChat")
        @Expose
        private Boolean isOnlineForChat;
        @SerializedName("IsOnlineForCall")
        @Expose
        private Boolean isOnlineForCall;
        @SerializedName("CountryCode")
        @Expose
        private String countryCode;
        @SerializedName("DiscountType")
        @Expose
        private String discountType;
        @SerializedName("DiscountAmount")
        @Expose
        private String discountAmount;
        @SerializedName("WaitTime")
        @Expose
        private Integer waitTime;
        @SerializedName("RemaningFreeSession")
        @Expose
        private Integer remaningFreeSession;
        @SerializedName("IsValidForFree")
        @Expose
        private Boolean isValidForFree;

        public Integer getRemaningFreeSession() {
            return remaningFreeSession;
        }

        public void setRemaningFreeSession(Integer remaningFreeSession) {
            this.remaningFreeSession = remaningFreeSession;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Boolean getOfferApplied() {
            return isOfferApplied;
        }

        public void setOfferApplied(Boolean offerApplied) {
            isOfferApplied = offerApplied;
        }

        public Integer getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(Integer waitTime) {
            this.waitTime = waitTime;
        }

        public Boolean getBusyOnCall() {
            return isBusyOnCall;
        }

        public void setBusyOnCall(Boolean busyOnCall) {
            isBusyOnCall = busyOnCall;
        }

        public Boolean getOnlineForCallEM() {
            return isOnlineForCallEM;
        }

        public Boolean getBoosted() {
            return isBoosted;
        }

        public Boolean getOnlineForChatEM() {
            return isOnlineForChatEM;
        }

        public Boolean getOnlineForChat() {
            return isOnlineForChat;
        }

        public Boolean getOnlineForCall() {
            return isOnlineForCall;
        }

        public Boolean isBoosted() {
            return isBoosted;
        }

        public void setBoosted(Boolean boosted) {
            isBoosted = boosted;
        }

        public Result(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
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

        public String getProfileThumbnail() {
            return profileThumbnail;
        }

        public void setProfileThumbnail(String profileThumbnail) {
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

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
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

        public Boolean isOnlineForCallEM() {
            return isOnlineForCallEM;
        }

        public void setOnlineForCallEM(Boolean onlineForCallEM) {
            isOnlineForCallEM = onlineForCallEM;
        }

        public Boolean isOnlineForChatEM() {
            return isOnlineForChatEM;
        }

        public void setOnlineForChatEM(Boolean onlineForChatEM) {
            isOnlineForChatEM = onlineForChatEM;
        }

        public Boolean isOnlineForChat() {
            return isOnlineForChat;
        }

        public void setOnlineForChat(Boolean onlineForChat) {
            isOnlineForChat = onlineForChat;
        }

        public Boolean isOnlineForCall() {
            return isOnlineForCall;
        }

        public void setOnlineForCall(Boolean onlineForCall) {
            isOnlineForCall = onlineForCall;
        }

        public String getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(String ratingCount) {
            this.ratingCount = ratingCount;
        }

        public Boolean getValidForFree() {
            if (isValidForFree == null) {
                isValidForFree = false;
            }
            return isValidForFree;
        }

        public void setValidForFree(Boolean validForFree) {
            isValidForFree = validForFree;
        }
    }

}


