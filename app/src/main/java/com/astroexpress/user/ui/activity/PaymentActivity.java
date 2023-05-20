package com.astroexpress.user.ui.activity;

import static com.astroexpress.user.utility.AllStaticMethods.saveException;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityDashboardBinding;
import com.astroexpress.user.databinding.ActivityPaymentBinding;
import com.astroexpress.user.model.request.PaymentTransactionRequestModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.model.responsemodel.PaymentTransactionResponseModel;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.astroexpress.user.utility.SharedPreferenceManager;
import com.razorpay.Checkout;
import com.razorpay.Payment;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener {
    ActivityPaymentBinding binding;
    MyProgressDialog myProgressDialog;
    Context context;
    NetworkChange networkChange =new NetworkChange();
    String action;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_SUCCESS = 2;
    public static final int STATUS_FAILED = 3;
    private OrderResponseModel.Result orderData;
    private JSONObject jsonObject;
    private static int SPLASH_SCREEN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        action = getIntent().getAction();
        context = PaymentActivity.this;
        myProgressDialog = new MyProgressDialog(context);
        orderData = AllStaticFields.orderData;

        setLoading("Processing...", STATUS_LOADING);

        binding.txtOrderAmount.setText(orderData.getFinalAmount());
        binding.txtOrderId.setText("ASTRO_TXN_000"+orderData.getOrderId());
//        binding.txtOrderDate.setText(orderData.get());
        binding.copyTransactionIdTextView.setOnClickListener(this);

        switch (action) {
            case AppConstants.RAZORPAY_PG:

                callRazorpay();
                break;
        }
    }
    private void callRazorpay() {

        Checkout checkout = new Checkout();
        checkout.setKeyID(AppConstants.RAZORPAY_KEY_ID);

        try {

             jsonObject = new JSONObject();
            jsonObject.put("name", AllStaticFields.userData.getFirstName()+" "+AllStaticFields.userData.getLastName());
            jsonObject.put("description", AllStaticFields.userData.getUserId() +": "+ "Astro Express Wallet Recharge");
            jsonObject.put("send_sms_hash", false);
            jsonObject.put("allow_rotation", false);
            jsonObject.put("image", "https://astroexpress.in/img/logo.png");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", Double.parseDouble(orderData.getFinalAmount()) *100);
            JSONObject jsonObjectPrefill = new JSONObject();
            jsonObjectPrefill.put("email", AllStaticFields.userData.getEmail());
            jsonObjectPrefill.put("contact", AllStaticFields.userData.getMobile());

            jsonObject.put("prefill", jsonObjectPrefill);

            checkout.open(PaymentActivity.this, jsonObject);

        } catch (Exception e) {
            setLoading("Payment Failed", STATUS_FAILED);
            saveException(e);
        }

    }

    public void setLoading(String title, int status) {
        binding.statusTextView.setText(title);
        binding.statusLottie.setAnimation(R.raw.payment_loading);

        switch (status) {
            case STATUS_LOADING:
                binding.statusLottie.setMinFrame(0);
                binding.statusLottie.setMaxFrame(240);
                binding.statusLottie.playAnimation();
                break;
            case STATUS_SUCCESS:
                binding.statusLottie.setMinFrame(240);
                binding.statusLottie.setMaxFrame(390);
                binding.statusLottie.loop(false);
                binding.statusLottie.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            startActivity(new Intent(context, WalletActivity.class));
                        finish();
                    }
                }, SPLASH_SCREEN);
                break;
            case STATUS_FAILED:
                binding.statusLottie.setMinFrame(657);
                binding.statusLottie.setMaxFrame(807);
                binding.statusLottie.loop(false);
                binding.statusLottie.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(context, WalletActivity.class));
                        finish();
                    }
                }, SPLASH_SCREEN);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyTransactionIdTextView:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(
                        "label",
                        binding.txtOrderId.getText()
                );
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Order ID copied to clipboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shareTextView:

                break;
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        new PaymentApiCaller(paymentData.getPaymentId()).execute();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        setLoading("Payment Failed", STATUS_FAILED);
    }

    private class PaymentApiCaller extends AsyncTask<String, Integer, Payment> {

        private String paymentId;

        public PaymentApiCaller(String paymentId) {
            this.paymentId = paymentId;
        }

        @Override
        protected Payment doInBackground(String... strings) {

            try {
                RazorpayClient razorpayClient = new RazorpayClient(AppConstants.RAZORPAY_KEY_ID, AppConstants.RAZORPAY_MERCHANT_KEY);

                return razorpayClient.Payments.fetch(paymentId);

            } catch (Exception e) {
                saveException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Payment payment) {
            try {

                if (payment != null) {

                    JSONObject resultData = payment.toJson();

                    PaymentTransactionRequestModel paymentTransactionRequestModel = new PaymentTransactionRequestModel(
                            AllStaticFields.userData.getUserId(),
                            orderData.getFinalAmount(),
                            orderData.getOrderId(),
                            resultData.getString("id"),
                            action,
                            resultData.getString("status"),
                            jsonObject.toString(),
                            resultData.toString(),
                            "",
                            "",
                            ""
                    );

                    myProgressDialog.show();
                    RetrofitAPIService.getApiClient().saveTransaction(paymentTransactionRequestModel).enqueue(new Callback<PaymentTransactionResponseModel>() {
                        @Override
                        public void onResponse(Call<PaymentTransactionResponseModel> call, Response<PaymentTransactionResponseModel> response) {
                            myProgressDialog.dismiss();

                            try {

                                if (response.isSuccessful() && response.body() != null) {

                                    if (response.body().getCode().equals("200")) {

                                        setResult(RESULT_OK);
                                        setLoading("Payment Success", STATUS_SUCCESS);

                                        //5 second redirection will be implemented

                                    } else {
                                        setLoading("Payment Failed", STATUS_FAILED);
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    setLoading("Payment Failed", STATUS_FAILED);
                                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                AllStaticMethods.saveException(e);
                                setLoading("Payment Failed", STATUS_FAILED);
                                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PaymentTransactionResponseModel> call, Throwable t) {
                            myProgressDialog.dismiss();
                            setLoading("Payment Failed", STATUS_FAILED);
                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            } catch (Exception e) {
                setLoading("Payment Failed", STATUS_FAILED);
                saveException(e);
            }

        }
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

}