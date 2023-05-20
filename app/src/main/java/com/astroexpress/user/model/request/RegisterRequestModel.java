package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class RegisterRequestModel {

        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("DateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Occupation")
        @Expose
        private String occupation;
        @SerializedName("PlaceOfBirth")
        @Expose
        private String placeOfBirth;
        @SerializedName("TimeOfBirth")
        @Expose
        private String timeOfBirth;
        @SerializedName("CurrentCityName")
        @Expose
        private String currentCityName;
        @SerializedName("ProblemArea")
        @Expose
        private String problemArea;
        @SerializedName("UserId")
        @Expose
        private String userId;

        public RegisterRequestModel(String firstName, String lastName, String dateOfBirth, String gender, String occupation, String placeOfBirth, String timeOfBirth, String currentCityName, String problemArea, String userId) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.occupation = occupation;
            this.placeOfBirth = placeOfBirth;
            this.timeOfBirth = timeOfBirth;
            this.currentCityName = currentCityName;
            this.problemArea = problemArea;
            this.userId = userId;
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

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getTimeOfBirth() {
            return timeOfBirth;
        }

        public void setTimeOfBirth(String timeOfBirth) {
            this.timeOfBirth = timeOfBirth;
        }

        public String getCurrentCityName() {
            return currentCityName;
        }

        public void setCurrentCityName(String currentCityName) {
            this.currentCityName = currentCityName;
        }

        public String getProblemArea() {
            return problemArea;
        }

        public void setProblemArea(String problemArea) {
            this.problemArea = problemArea;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }


