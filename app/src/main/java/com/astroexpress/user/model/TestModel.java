package com.astroexpress.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestModel {

    public TestModel(String astrologerId, String firstName, String lastName, String mobile, String email, String profileImageUrl, String profileThumbnail, String speciality, String language, String experience, String chargePerMinute, String currencyType, String rating, String ratingCount, String followers, String totalCallMinuts, String totalChatMinuts, String aboutUs, boolean isOnlineForCallEM, boolean isOnlineForChatEM, boolean isOnlineForChat, boolean isOnlineForCall, String countryCode) {
        this.astrologerId = astrologerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.profileThumbnail = profileThumbnail;
        this.speciality = speciality;
        this.language = language;
        this.experience = experience;
        this.chargePerMinute = chargePerMinute;
        this.currencyType = currencyType;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.followers = followers;
        this.totalCallMinuts = totalCallMinuts;
        this.totalChatMinuts = totalChatMinuts;
        this.aboutUs = aboutUs;
        this.isOnlineForCallEM = isOnlineForCallEM;
        this.isOnlineForChatEM = isOnlineForChatEM;
        this.isOnlineForChat = isOnlineForChat;
        this.isOnlineForCall = isOnlineForCall;
        this.countryCode = countryCode;
    }

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
        @SerializedName("IsOnlineForCallEM")
        @Expose
        private boolean isOnlineForCallEM;
        @SerializedName("IsOnlineForChatEM")
        @Expose
        private boolean isOnlineForChatEM;
        @SerializedName("IsOnlineForChat")
        @Expose
        private boolean isOnlineForChat;
        @SerializedName("IsOnlineForCall")
        @Expose
        private boolean isOnlineForCall;
        @SerializedName("CountryCode")
        @Expose
        private String countryCode;

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

        public boolean isOnlineForCallEM() {
            return isOnlineForCallEM;
        }

        public void setOnlineForCallEM(boolean onlineForCallEM) {
            isOnlineForCallEM = onlineForCallEM;
        }

        public boolean isOnlineForChatEM() {
            return isOnlineForChatEM;
        }

        public void setOnlineForChatEM(boolean onlineForChatEM) {
            isOnlineForChatEM = onlineForChatEM;
        }

        public boolean isOnlineForChat() {
            return isOnlineForChat;
        }

        public void setOnlineForChat(boolean onlineForChat) {
            isOnlineForChat = onlineForChat;
        }

        public boolean isOnlineForCall() {
            return isOnlineForCall;
        }

        public void setOnlineForCall(boolean onlineForCall) {
            isOnlineForCall = onlineForCall;
        }

        public String getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(String ratingCount) {
            this.ratingCount = ratingCount;
        }


}
