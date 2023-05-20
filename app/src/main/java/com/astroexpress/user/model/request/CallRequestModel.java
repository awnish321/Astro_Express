package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallRequestModel {

    @SerializedName("k_number")
    @Expose
    private String kNumber;
    @SerializedName("agent_number")
    @Expose
    private String agentNumber;
    @SerializedName("customer_number")
    @Expose
    private String customerNumber;
    @SerializedName("caller_id")
    @Expose
    private String callerId;
    @SerializedName("additional_params")
    @Expose
    private AdditionalParams additionalParams;

    public CallRequestModel(String kNumber, String agentNumber, String customerNumber, String callerId, AdditionalParams additionalParams) {
        this.kNumber = kNumber;
        this.agentNumber = agentNumber;
        this.customerNumber = customerNumber;
        this.callerId = callerId;
        this.additionalParams = additionalParams;
    }

    public String getkNumber() {
        return kNumber;
    }

    public void setkNumber(String kNumber) {
        this.kNumber = kNumber;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public AdditionalParams getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(AdditionalParams additionalParams) {
        this.additionalParams = additionalParams;
    }

    public static class AdditionalParams {

        @SerializedName("max_talktime")
        @Expose
        private String maxTalktime;

        public String getMaxTalktime() {
            return maxTalktime;
        }

        public void setMaxTalktime(String maxTalktime) {
            this.maxTalktime = maxTalktime;
        }

    }


}
