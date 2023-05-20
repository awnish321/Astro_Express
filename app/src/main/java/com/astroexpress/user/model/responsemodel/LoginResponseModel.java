package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

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

        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("ReferralCode")
        @Expose
        private String referralCode;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("CountryCode")
        @Expose
        private String countryCode;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("FirebaseUserId")
        @Expose
        private String firebaseUserId;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("DateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("MaritalStatus")
        @Expose
        private String maritalStatus;
        @SerializedName("PreferredLanguage")
        @Expose
        private String preferredLanguage;
        @SerializedName("Occupation")
        @Expose
        private String occupation;
        @SerializedName("PlaceOfBirth")
        @Expose
        private String placeOfBirth;
        @SerializedName("ProblemArea")
        @Expose
        private String problemArea;
        @SerializedName("CurrentAddress")
        @Expose
        private String currentAddress;
        @SerializedName("PermanentAddress")
        @Expose
        private String permanentAddress;
        @SerializedName("StateId")
        @Expose
        private String stateId;
        @SerializedName("CityId")
        @Expose
        private String cityId;
        @SerializedName("PermanentStateName")
        @Expose
        private String permanentStateName;
        @SerializedName("PermanentCityName")
        @Expose
        private String permanentCityName;
        @SerializedName("CurrentStateName")
        @Expose
        private String currentStateName;
        @SerializedName("TimeOfBirth")
        @Expose
        private String timeOfBirth;
        @SerializedName("CurrentCityName")
        @Expose
        private String currentCityName;
        @SerializedName("CountryId")
        @Expose
        private String countryId;
        @SerializedName("PermanentCountryName")
        @Expose
        private String permanentCountryName;
        @SerializedName("CurrentCountryName")
        @Expose
        private String currentCountryName;
        @SerializedName("ProfileImageUrl")
        @Expose
        private String profileImageUrl;
        @SerializedName("ProfileThumbnail")
        @Expose
        private String profileThumbnail;
        @SerializedName("Pass")
        @Expose
        private String pass;
        @SerializedName("UsrName")
        @Expose
        private String usrName;

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getUsrName() {
            return usrName;
        }

        public void setUsrName(String usrName) {
            this.usrName = usrName;
        }

        public String getTimeOfBirth() {
            return timeOfBirth;
        }

        public void setTimeOfBirth(String timeOfBirth) {
            this.timeOfBirth = timeOfBirth;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
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

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirebaseUserId() {
            return firebaseUserId;
        }

        public void setFirebaseUserId(String firebaseUserId) {
            this.firebaseUserId = firebaseUserId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getPreferredLanguage() {
            return preferredLanguage;
        }

        public void setPreferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        public void setPlaceOfBirth(String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
        }

        public String getProblemArea() {
            return problemArea;
        }

        public void setProblemArea(String problemArea) {
            this.problemArea = problemArea;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public String getPermanentAddress() {
            return permanentAddress;
        }

        public void setPermanentAddress(String permanentAddress) {
            this.permanentAddress = permanentAddress;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getPermanentStateName() {
            return permanentStateName;
        }

        public void setPermanentStateName(String permanentStateName) {
            this.permanentStateName = permanentStateName;
        }

        public String getPermanentCityName() {
            return permanentCityName;
        }

        public void setPermanentCityName(String permanentCityName) {
            this.permanentCityName = permanentCityName;
        }

        public String getCurrentStateName() {
            return currentStateName;
        }

        public void setCurrentStateName(String currentStateName) {
            this.currentStateName = currentStateName;
        }

        public String getCurrentCityName() {
            return currentCityName;
        }

        public void setCurrentCityName(String currentCityName) {
            this.currentCityName = currentCityName;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getPermanentCountryName() {
            return permanentCountryName;
        }

        public void setPermanentCountryName(String permanentCountryName) {
            this.permanentCountryName = permanentCountryName;
        }

        public String getCurrentCountryName() {
            return currentCountryName;
        }

        public void setCurrentCountryName(String currentCountryName) {
            this.currentCountryName = currentCountryName;
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

    }
}
