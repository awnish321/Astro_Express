package com.astroexpress.user.api;

import com.astroexpress.user.model.request.CallRequestModel;
import com.astroexpress.user.model.request.ChatRequestModel;
import com.astroexpress.user.model.request.EditProfileRequestModel;
import com.astroexpress.user.model.request.FollowUnfollowRequestModel;
import com.astroexpress.user.model.request.OrderRequestModel;
import com.astroexpress.user.model.request.PaymentTransactionRequestModel;
import com.astroexpress.user.model.request.RatingReviewRequestModel;
import com.astroexpress.user.model.request.LoginSignupRequestModel;
import com.astroexpress.user.model.request.RequestForChatRequestModel;
import com.astroexpress.user.model.request.SaveUserAddressRequestModel;
import com.astroexpress.user.model.request.SubmitQueryRequestModel;
import com.astroexpress.user.model.request.UpdateRemedyBookingStatusRequestModel;
import com.astroexpress.user.model.request.WalletTransactionRequestModel;
import com.astroexpress.user.model.responsemodel.AstrologerDetailResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerMultipleImageResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.BannerResponseModel;
import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.model.responsemodel.CallResponseModel;
import com.astroexpress.user.model.responsemodel.ChatEndResponseModel;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.model.responsemodel.ChatListResponseModel;
import com.astroexpress.user.model.responsemodel.ChatSessionResponseModel;
import com.astroexpress.user.model.responsemodel.CouponResponseModel;
import com.astroexpress.user.model.responsemodel.FollowUnfollowResponseModel;
import com.astroexpress.user.model.responsemodel.FollowedAstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.GetAllSavedResponseModel;
import com.astroexpress.user.model.responsemodel.GetQueryResponseModel;
import com.astroexpress.user.model.responsemodel.GetRatingReviewResponseModel;
import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.astroexpress.user.model.responsemodel.MakeVoiceCallResponseModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.model.responsemodel.PaymentTransactionListResponseModel;
import com.astroexpress.user.model.responsemodel.PaymentTransactionResponseModel;
import com.astroexpress.user.model.responsemodel.RequestForChatResponseModel;
import com.astroexpress.user.model.responsemodel.SaveRatingReviewResponseModel;
import com.astroexpress.user.model.responsemodel.SaveChatImageResponseModel;
import com.astroexpress.user.model.responsemodel.SaveChatResponseModel;
import com.astroexpress.user.model.responsemodel.SubmitQueryResponseModel;
import com.astroexpress.user.model.responsemodel.SuggestedRemediesResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.model.responsemodel.UpdateRemedyBookingStatusResponseModel;
import com.astroexpress.user.model.responsemodel.UserAddressSaveResponseModel;
import com.astroexpress.user.model.responsemodel.UserStatusResponseModel;
import com.astroexpress.user.model.responsemodel.UserTestimonialResponseModel;
import com.astroexpress.user.model.responsemodel.WalletDeductionResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.model.responsemodel.WalletTransactionResponseModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAPIs {

    @POST("user/signup")
    Call<LoginResponseModel> signup(@Body LoginSignupRequestModel loginSignupRequestModel);

    @POST("user/updateRemedyBookingStatus")
    Call<UpdateRemedyBookingStatusResponseModel> updateRemedyBookingStatus(@Body UpdateRemedyBookingStatusRequestModel updateRemedyBookingStatusRequestModel);

    @GET("user/banner")
    Call<BannerResponseModel> banner(@Query("Type") String Banner);

    @GET("user/getAstrologer")
    Call<AstrologerResponseModel> getAstrologer(@Query("Status") String Status);

    @GET("user/getRatingReviewByAstrologerId")
    Call<GetRatingReviewResponseModel> getRatingReviewByAstrologerId(@Query("AstrologerId") String AstrologerId);

    @POST("user/updateUser")
    Call<LoginResponseModel> updateUser(@Body EditProfileRequestModel editProfileRequestmodel);

    @POST("user/generateOrder")
    Call<OrderResponseModel> generateOrder(@Body OrderRequestModel orderRequestModel);

    @GET("user/getCoupon")
    Call<CouponResponseModel> getCoupon(@Query("CouponCode") String CouponCode, @Query("CouponType") String CouponType);

    @POST("user/savePaymentTransaction")
    Call<PaymentTransactionResponseModel> saveTransaction(@Body PaymentTransactionRequestModel paymentTransactionRequestModel);

    @GET("user/getWallet")
    Call<WalletResponseModel> getWallet(@Query("UserId") String UserId);

    @GET("user/getAllQuery")
    Call<GetQueryResponseModel> getAllQuery(@Query("UserId") String UserId);

    @GET("user/getPaymentTransaction")
    Call<PaymentTransactionListResponseModel> getPaymentTransaction(@Query("UserId") String UserId);

    @POST("user/saveChat")
    Call<SaveChatResponseModel> saveChat(@Body ChatRequestModel chatRequestModel);

    @GET("user/getAllAddress")
    Call<GetAllSavedResponseModel> getAllAddress(@Query("UserId") String UserId);

    @POST("user/saveUserAddress")
    Call<UserAddressSaveResponseModel> saveUserAddress(@Body SaveUserAddressRequestModel saveUserAddressRequestModel);

    @POST("user/updateChat")
    Call<SaveChatResponseModel> updateChat(@Body List<ChatRequestModel> chatRequestModelList);

    @GET("user/getChat")
    Call<ChatListResponseModel> getChatList(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId);

    @GET("user/getSuggestedRemedy")
    Call<SuggestedRemediesResponseModel> getSuggestedRemedy(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId);

    @POST("user/saveUpdateWalletTransaction")
    Call<WalletTransactionResponseModel> saveUpdateWalletTransaction(@Body WalletTransactionRequestModel walletTransactionRequestModel);

    @GET("user/getWalletTransaction")
    Call<WalletDeductionResponseModel> getWalletTransaction(@Query("UserId") String UserId);

    @GET("user/getRecentAstrologer")
    Call<ChatHistoryResponseModel> getChatHistory(@Query("UserId") String UserId);

    @GET("user/getAllBlogs")
    Call<BlogResponseModel> getAllBlogs();

    @GET("user/getAllUserTestimonial")
    Call<UserTestimonialResponseModel> getAllUserTestimonial();
    @GET("user/testimonial")
    Call<TestimonialResponseModels> getTestimonial();

    @GET("user/userStatus")
    Call<UserStatusResponseModel> userStatus(@Query("UserId") String UserId);

    @POST("user/submitQuery")
    Call<SubmitQueryResponseModel> submitQuery(@Body SubmitQueryRequestModel submitQueryRequestModel);

    @POST("user/followUnfollow")
    Call<FollowUnfollowResponseModel> followUnfollow(@Body FollowUnfollowRequestModel followUnfollowRequestModel);

    @POST("user/saveUserRating")
    Call<SaveRatingReviewResponseModel> saveUserRating(@Body RatingReviewRequestModel ratingReviewRequestModel);

    @POST("user/requestForChat_V01")
    Call<RequestForChatResponseModel> requestForChat(@Body RequestForChatRequestModel requestForChatRequestModel);

    @POST("user/makeVoiceCall_V01")
    Call<MakeVoiceCallResponseModel> makeVoiceCall(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId, @Query("IsFreeSession") Boolean IsFreeSession);

    @GET("user/getVoiceCall")
    Call<CallResponseModel> getVoiceCall(@Query("UserId") String UserId);

    @GET("user/frequentAstrologerContent")
    Call<AstrologerMultipleImageResponseModel> frequentAstrologerContent(@Query("AstrologerId") String AstrologerId);

    @GET("user/getFinishChatDetail")
    Call<ChatEndResponseModel> getFinishChatDetail(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId, @Query("SessionId") String SessionId);

    @GET("user/getSessionIdForChat")
    Call<ChatSessionResponseModel> getSessionIdForChat(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId);

    @GET("user/getChatRunningSession")
    Call<ChatSessionResponseModel> getChatRunningSession(@Query("UserId") String UserId);

    @GET("user/getAstrologerDetail")
    Call<AstrologerDetailResponseModel> getAstrologerDetail(@Query("AstrologerId") String AstrologerId);

    @GET("user/getFollowedAstrologer")
    Call<FollowedAstrologerResponseModel> getFollowedAstrologer(@Query("UserId") String UserId);

    //    @Headers("Content-Type: application/json")
    @POST("user/saveChatImage")
    Call<SaveChatImageResponseModel> saveChatImage(@Body RequestBody requestBody);

//----------------------------------------------------------------------------------------------------------------------
//    @Headers({
//            "Authorization:92b55a1c-7449-4e2f-958a-96fecdc5bd8b",
//            "x-api-key:1yWupUw5p26qOU7Vc9Un85GoDw09X0mP8c4CYlW5",
//            "Content-Type:application/json"
//    })

    @POST("Basic/v1/account/call/makecall")
    Call<CallResponseModel> saveCallTransaction(@Body CallRequestModel callRequestModel);

}
