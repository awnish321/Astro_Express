package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileRequestModel {
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("StateId")
    @Expose
    private String stateId;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("CountryId")
    @Expose
    private String countryId;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DateOfBirth")
    @Expose
    private String dob;
    @SerializedName("MaritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("TimeOfBirth")
    @Expose
    private String timeOfBirth;
    @SerializedName("PreferredLanguage")
    @Expose
    private String preferredLanguage;
    @SerializedName("Occupation")
    @Expose
    private String occupation;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("PlaceOfBirth")
    @Expose
    private String placeOfBirth;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("PermanentAddress")
    @Expose
    private String permanentAddress;
    @SerializedName("PermanentStateName")
    @Expose
    private String permanentStateName;
    @SerializedName("PermanentCityName")
    @Expose
    private String permanentCityName;
    @SerializedName("PermanentCountryName")
    @Expose
    private String permanentCountryName;
    @SerializedName("CurrentAddress")
    @Expose
    private String currentAddress;
    @SerializedName("CurrentStateName")
    @Expose
    private String currentStateName;
    @SerializedName("CurrentCityName")
    @Expose
    private String currentCityName;
    @SerializedName("CurrentCountryName")
    @Expose
    private String currentCountryName;
    @SerializedName("ProblemArea")
    @Expose
    private String problemArea;


    public EditProfileRequestModel(String userId,String problemArea, String stateId, String cityId , String countryId, String firstName, String lastName, String gender, String dob, String maritalStatus, String timeOfBirth, String preferredLanguage, String occupation, String email, String placeOfBirth, String mobile, String permanentAddress, String permanentStateName, String permanentCityName, String permanentCountryName, String currentAddress, String currentStateName, String currentCityName, String currentCountryName) {
        this.userId = userId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.countryId = countryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.maritalStatus = maritalStatus;
        this.timeOfBirth = timeOfBirth;
        this.preferredLanguage = preferredLanguage;
        this.occupation = occupation;
        this.email = email;
        this.placeOfBirth = placeOfBirth;
        this.mobile = mobile;
        this.permanentAddress = permanentAddress;
        this.permanentStateName = permanentStateName;
        this.permanentCityName = permanentCityName;
        this.permanentCountryName = permanentCountryName;
        this.currentAddress = currentAddress;
        this.currentStateName = currentStateName;
        this.currentCityName = currentCityName;
        this.currentCountryName = currentCountryName;
        this.problemArea = problemArea;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(String timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
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

    public String getPermanentCountryName() {
        return permanentCountryName;
    }

    public void setPermanentCountryName(String permanentCountryName) {
        this.permanentCountryName = permanentCountryName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
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

    public String getCurrentCountryName() {
        return currentCountryName;
    }

    public void setCurrentCountryName(String currentCountryName) {
        this.currentCountryName = currentCountryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProblemArea() {
        return problemArea;
    }

    public void setProblemArea(String problemArea) {
        this.problemArea = problemArea;
    }

}


