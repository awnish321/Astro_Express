package com.astroexpress.user.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeVoiceCallRequestModel

{
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
}
