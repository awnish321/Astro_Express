package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityLoginSignupBinding;
import com.astroexpress.user.model.request.LoginSignupRequestModel;
import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignupActivity extends AppCompatActivity {

    ActivityLoginSignupBinding binding;
    Context context;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    String verificationId, phoneNumber;
    boolean OTP0, OTP1, OTP2, OTP3, OTP4, OTP5;
    String OTP, UserId;
    private EditText[] otpn;
    String referAndEarn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(context);

        binding = ActivityLoginSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = LoginSignupActivity.this;

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtMobile.getText().toString().trim().length() != 10) {
                    Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                } else {

                    phoneNumber = binding.edtMobile.getText().toString().trim();

                    callLoginApi(phoneNumber);

                    binding.llSendOtpPage.setVisibility(View.GONE);
                    binding.lottieProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(LoginSignupActivity.this, NewDashboardActivity.class);
                startActivity(intent4);
                finish();
            }
        });

        binding.txtChangeMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llOtpVerifyPage.setVisibility(View.GONE);
                binding.llSendOtpPage.setVisibility(View.VISIBLE);
            }
        });

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForValidation();
            }
        });

        otpn = new EditText[]{binding.edtOTP0, binding.edtOTP1, binding.edtOTP2, binding.edtOTP3, binding.edtOTP4, binding.edtOTP5};

        binding.edtOTP0.addTextChangedListener(new PinTextWatcher(0));
        binding.edtOTP1.addTextChangedListener(new PinTextWatcher(1));
        binding.edtOTP2.addTextChangedListener(new PinTextWatcher(2));
        binding.edtOTP3.addTextChangedListener(new PinTextWatcher(3));
        binding.edtOTP4.addTextChangedListener(new PinTextWatcher(4));
        binding.edtOTP5.addTextChangedListener(new PinTextWatcher(5));

        binding.edtOTP0.setOnKeyListener(new PinOnKeyListener(0));
        binding.edtOTP1.setOnKeyListener(new PinOnKeyListener(1));
        binding.edtOTP2.setOnKeyListener(new PinOnKeyListener(2));
        binding.edtOTP3.setOnKeyListener(new PinOnKeyListener(3));
        binding.edtOTP4.setOnKeyListener(new PinOnKeyListener(4));
        binding.edtOTP5.setOnKeyListener(new PinOnKeyListener(5));

    }

    private void callVerifyOTP() {

        binding.llOtpVerifyPage.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    UserId = user.getUid();
                    callSignUpApi();

                } else {
                    binding.llOtpVerifyPage.setVisibility(View.VISIBLE);
                    binding.lottieProgressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Invalid OTP \n Please enter valid Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callSignUpApi() {

        LoginSignupRequestModel loginSignupRequestModel = new LoginSignupRequestModel(
                null,
                null,
                null,
                null,
                null,
                UserId,
                null,
                null,
                phoneNumber,
                referAndEarn
        );

        RetrofitAPIService.getApiClient().signup(loginSignupRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {
//                        binding.lottieProgressBar.setVisibility(View.GONE);
                        if (response.body().getCode().equals("200") || response.body().getCode().equals("1062")) {
                            SharedPreferenceManager.setUserData(context, response.body().getResult());
                            AllStaticFields.userData = response.body().getResult();
                            startActivity(new Intent(context, NewDashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkForValidation() {

        if (binding.edtOTP0.getText().toString().isEmpty()) {
            binding.edtOTP0.setError("otp required");
            binding.edtOTP0.requestFocus();
            OTP0 = false;

        } else {
            OTP0 = true;
        }
        if (binding.edtOTP1.getText().toString().isEmpty()) {
            binding.edtOTP1.setError("otp required");
            binding.edtOTP1.requestFocus();
            OTP1 = false;

        } else {
            OTP1 = true;
        }
        if (binding.edtOTP2.getText().toString().isEmpty()) {
            binding.edtOTP2.setError("otp required");
            binding.edtOTP2.requestFocus();
            OTP2 = false;

        } else {
            OTP2 = true;
        }
        if (binding.edtOTP3.getText().toString().isEmpty()) {
            binding.edtOTP3.setError("otp required");
            binding.edtOTP3.requestFocus();
            OTP3 = false;

        } else {
            OTP3 = true;
        }
        if (binding.edtOTP4.getText().toString().isEmpty()) {
            binding.edtOTP4.setError("otp required");
            binding.edtOTP4.requestFocus();
            OTP4 = false;

        } else {
            OTP4 = true;
        }
        if (binding.edtOTP5.getText().toString().isEmpty()) {
            binding.edtOTP5.setError("otp required");
            binding.edtOTP5.requestFocus();
            OTP5 = false;

        } else {
            OTP5 = true;
        }
        if (OTP0 && OTP1 && OTP2 && OTP3 && OTP4 && OTP5) {
            OTP = binding.edtOTP0.getText().toString().trim() + "" +
                    binding.edtOTP1.getText().toString().trim() + "" +
                    binding.edtOTP2.getText().toString().trim() + "" +
                    binding.edtOTP3.getText().toString().trim() + "" +
                    binding.edtOTP4.getText().toString().trim() + "" +
                    binding.edtOTP5.getText().toString().trim();

            callVerifyOTP();

        }

    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == otpn.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            otpn[currentIndex].removeTextChangedListener(this);
            otpn[currentIndex].setText(text);
            otpn[currentIndex].setSelection(text.length());
            otpn[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                otpn[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                otpn[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                otpn[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : otpn)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (otpn[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    otpn[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }

    private void callLoginApi(String phoneNumber) {
        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        binding.llSendOtpPage.setVisibility(View.VISIBLE);
                        binding.lottieProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onVerificationFailed: ",e );
                        binding.llSendOtpPage.setVisibility(View.VISIBLE);
                        binding.lottieProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                        resendToken = forceResendingToken;
                        binding.txtMobile.setText("+91" + "" + phoneNumber);
                        binding.llOtpVerifyPage.setVisibility(View.VISIBLE);
                        binding.lottieProgressBar.setVisibility(View.GONE);
                        binding.edtOTP0.requestFocus();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

}