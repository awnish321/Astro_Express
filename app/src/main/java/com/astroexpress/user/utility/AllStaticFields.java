package com.astroexpress.user.utility;

import com.astroexpress.user.model.firebaseNotification.FirebaseNotificationForAllResponseModel;
import com.astroexpress.user.model.firebaseNotification.FirebaseNotificationForFollowedAstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerDetailResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerMultipleImageResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.model.responsemodel.CouponResponseModel;
import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.model.responsemodel.PaymentTransactionListResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.model.responsemodel.UserTestimonialResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;

import java.util.ArrayList;
import java.util.List;

public class AllStaticFields {

    public static String call_id = null;
    public static String sessionId = null;
    public static String astrologerId = null;
    public static String userId = null;
    public static Long minuteTime = null;

    public static LoginResponseModel.Result userData = null;
    public static OrderResponseModel.Result orderData = null;
    public static AstrologerResponseModel.Result astrologerData = null;
    public static ChatHistoryResponseModel.Result chatHistoryData = null;
    public static AstrologerDetailResponseModel.Result astrologerDetailData = null;
    public static List<AstrologerResponseModel.Result> astrologerDataAllList = new ArrayList<>();
    public static List<AstrologerResponseModel.Result> astrologerDataOnlineChatList = new ArrayList<>();
    public static List<AstrologerResponseModel.Result> astrologerDataOnlineCallList = new ArrayList<>();
    public static List<AstrologerResponseModel.Result> astrologerDataBoostedAstrologerList = new ArrayList<>();
    public static List<BlogResponseModel.Result> blogAllData = new ArrayList<>();
    public static List<UserTestimonialResponseModel.Result> testimonialAllData = new ArrayList<>();
    public static CouponResponseModel.Result couponData = null;
    public static WalletResponseModel.Result walletData = null;
    public static AstrologerMultipleImageResponseModel MultipleImage = null;
    public static PaymentTransactionListResponseModel.Result paymentTransactionData = null;

    public static String uName;
    public static String uPass;
    public static FirebaseNotificationForAllResponseModel firebaseNotificationForAllResponseModel = null;

    public static FirebaseNotificationForFollowedAstrologerResponseModel firebaseNotificationForFollowedAstrologerResponseModel=null;
}
