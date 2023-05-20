package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ActivityTransactionDetailBinding;
import com.astroexpress.user.databinding.ActivityWalletBinding;
import com.astroexpress.user.model.responsemodel.PaymentTransactionListResponseModel;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.NetworkChange;
import com.ibm.icu.text.RuleBasedNumberFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransactionDetailActivity extends AppCompatActivity {

    Context context;
    MyProgressDialog myProgressDialog;
    ActivityTransactionDetailBinding binding;
    NetworkChange networkChange =new NetworkChange();
    PaymentTransactionListResponseModel.Result paymentTransactionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = TransactionDetailActivity.this;
        myProgressDialog = new MyProgressDialog(context);

        binding.layoutToolbar.toolbar.setTitle("Transaction Detail");
        setSupportActionBar(binding.layoutToolbar.toolbar);

//        double value = Integer.parseInt(AllStaticFields.paymentTransactionData.getAmount().trim());
        double value = Double.parseDouble(AllStaticFields.paymentTransactionData.getAmount().trim());
        String english=convertIntoWords(value,"en","US");

        binding.txtAmount.setText("\u20b9 " +AllStaticFields.paymentTransactionData.getAmount());
        binding.txtAmountWord.setText(english);
        binding.txtOrderId.setText(AllStaticFields.paymentTransactionData.getOrderId());
        binding.txtUserName.setText(AllStaticFields.userData.getFirstName());
        binding.txtOrderType.setText(AllStaticFields.paymentTransactionData.getOrderType());
        binding.txtDate.setText(dateFormatter(AllStaticFields.paymentTransactionData.getTransactionDateTime()));
        binding.txtTime.setText(timeFormatter(AllStaticFields.paymentTransactionData.getTransactionDateTime()));

        if (AllStaticFields.paymentTransactionData.getPaymentMode().equals("N/A")){
            binding.llPaymentMode.setVisibility(View.GONE);
        }else {
            binding.txtPaymentMode.setText(AllStaticFields.paymentTransactionData.getPaymentMode());
        }
        if (AllStaticFields.paymentTransactionData.getPaymentStatus().equals("authorized"))
        {
            binding.txtRechargeStatus.setText("Failed");
            binding.txtRechargeStatus.setTextColor(getResources().getColor(R.color.red));
        }else
        {
            binding.txtRechargeStatus.setText(AllStaticFields.paymentTransactionData.getPaymentStatus());
        }

    }

    private String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
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

    public static String dateFormatter(String unFormattedDate){
        String datetime=null;
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat inputFormat= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(unFormattedDate);
            datetime = outputFormat.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;

    }

    public static String timeFormatter(String unFormattedTime) {

        String time = null;
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date convertedDate = inputFormat.parse(unFormattedTime);
            time = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return time;
    }


}