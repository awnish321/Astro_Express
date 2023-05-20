package com.astroexpress.user.model.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class ChatEndResponseModel {

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

            @SerializedName("AvailbleAmount")
            @Expose
            private String availbleAmount;
            @SerializedName("AvailbleMinute")
            @Expose
            private String availbleMinute;
            @SerializedName("BillingDetail")
            @Expose
            private BillingDetail billingDetail;

            public String getAvailbleAmount() {
                return availbleAmount;
            }

            public void setAvailbleAmount(String availbleAmount) {
                this.availbleAmount = availbleAmount;
            }

            public String getAvailbleMinute() {
                return availbleMinute;
            }

            public void setAvailbleMinute(String availbleMinute) {
                this.availbleMinute = availbleMinute;
            }

            public BillingDetail getBillingDetail() {
                return billingDetail;
            }

            public void setBillingDetail(BillingDetail billingDetail) {
                this.billingDetail = billingDetail;
            }

            public static class BillingDetail {

                @SerializedName("UserId")
                @Expose
                private String userId;
                @SerializedName("AstrologerId")
                @Expose
                private String astrologerId;
                @SerializedName("Amount")
                @Expose
                private Integer amount;
                @SerializedName("AstrologerEarnAmount")
                @Expose
                private Float astrologerEarnAmount;
                @SerializedName("DebitedForm")
                @Expose
                private String debitedForm;
                @SerializedName("StartTime")
                @Expose
                private String startTime;
                @SerializedName("EndTime")
                @Expose
                private String endTime;
                @SerializedName("TransactionFor")
                @Expose
                private String transactionFor;
                @SerializedName("ParentId")
                @Expose
                private Integer parentId;
                @SerializedName("TDSCharges")
                @Expose
                private Float tDSCharges;
                @SerializedName("PGCharges")
                @Expose
                private Float pGCharges;
                @SerializedName("BoostedCharges")
                @Expose
                private Integer boostedCharges;
                @SerializedName("AdminShare")
                @Expose
                private Integer adminShare;
                @SerializedName("WalletTransactionId")
                @Expose
                private Integer walletTransactionId;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getAstrologerId() {
                    return astrologerId;
                }

                public void setAstrologerId(String astrologerId) {
                    this.astrologerId = astrologerId;
                }

                public Integer getAmount() {
                    return amount;
                }

                public void setAmount(Integer amount) {
                    this.amount = amount;
                }

                public Float getAstrologerEarnAmount() {
                    return astrologerEarnAmount;
                }

                public void setAstrologerEarnAmount(Float astrologerEarnAmount) {
                    this.astrologerEarnAmount = astrologerEarnAmount;
                }

                public String getDebitedForm() {
                    return debitedForm;
                }

                public void setDebitedForm(String debitedForm) {
                    this.debitedForm = debitedForm;
                }

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getTransactionFor() {
                    return transactionFor;
                }

                public void setTransactionFor(String transactionFor) {
                    this.transactionFor = transactionFor;
                }

                public Integer getParentId() {
                    return parentId;
                }

                public void setParentId(Integer parentId) {
                    this.parentId = parentId;
                }

                public Float getTDSCharges() {
                    return tDSCharges;
                }

                public void setTDSCharges(Float tDSCharges) {
                    this.tDSCharges = tDSCharges;
                }

                public Float getPGCharges() {
                    return pGCharges;
                }

                public void setPGCharges(Float pGCharges) {
                    this.pGCharges = pGCharges;
                }

                public Integer getBoostedCharges() {
                    return boostedCharges;
                }

                public void setBoostedCharges(Integer boostedCharges) {
                    this.boostedCharges = boostedCharges;
                }

                public Integer getAdminShare() {
                    return adminShare;
                }

                public void setAdminShare(Integer adminShare) {
                    this.adminShare = adminShare;
                }

                public Integer getWalletTransactionId() {
                    return walletTransactionId;
                }

                public void setWalletTransactionId(Integer walletTransactionId) {
                    this.walletTransactionId = walletTransactionId;
                }

            }


        }

    }


