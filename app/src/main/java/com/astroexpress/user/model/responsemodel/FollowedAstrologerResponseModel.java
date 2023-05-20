package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowedAstrologerResponseModel {

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
        @SerializedName("IsOnlineForChat")
        @Expose
        private String isOnlineForChat;
        @SerializedName("IsOnlineForCall")
        @Expose
        private String isOnlineForCall;
        @SerializedName("IsOnlineForChatEM")
        @Expose
        private String isOnlineForChatEM;
        @SerializedName("IsOnlineForCallEM")
        @Expose
        private String isOnlineForCallEM;
        @SerializedName("IsOfferApplied")
        @Expose
        private String isOfferApplied;
        @SerializedName("DiscountAmount")
        @Expose
        private Object discountAmount;
        @SerializedName("DiscountType")
        @Expose
        private Object discountType;

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

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(String ratingCount) {
            this.ratingCount = ratingCount;
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

        public String getIsOnlineForChat() {
            return isOnlineForChat;
        }

        public void setIsOnlineForChat(String isOnlineForChat) {
            this.isOnlineForChat = isOnlineForChat;
        }

        public String getIsOnlineForCall() {
            return isOnlineForCall;
        }

        public void setIsOnlineForCall(String isOnlineForCall) {
            this.isOnlineForCall = isOnlineForCall;
        }

        public String getIsOnlineForChatEM() {
            return isOnlineForChatEM;
        }

        public void setIsOnlineForChatEM(String isOnlineForChatEM) {
            this.isOnlineForChatEM = isOnlineForChatEM;
        }

        public String getIsOnlineForCallEM() {
            return isOnlineForCallEM;
        }

        public void setIsOnlineForCallEM(String isOnlineForCallEM) {
            this.isOnlineForCallEM = isOnlineForCallEM;
        }

        public String getIsOfferApplied() {
            return isOfferApplied;
        }

        public void setIsOfferApplied(String isOfferApplied) {
            this.isOfferApplied = isOfferApplied;
        }

        public Object getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Object discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Object getDiscountType() {
            return discountType;
        }

        public void setDiscountType(Object discountType) {
            this.discountType = discountType;
        }

    }
}

