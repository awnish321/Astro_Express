package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class UpdateRemedyBookingStatusRequestModel {

        @SerializedName("RemedyId")
        @Expose
        private String remedyId;

        public UpdateRemedyBookingStatusRequestModel(String remedyId) {
            this.remedyId = remedyId;
        }

        public String getRemedyId() {
            return remedyId;
        }

        public void setRemedyId(String remedyId) {
            this.remedyId = remedyId;
        }

    }



