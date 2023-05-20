package com.astroexpress.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.astroexpress.user.databinding.ActivityReferAndEarnBinding;
import com.astroexpress.user.utility.AllStaticFields;

public class ReferAndEarnActivity extends AppCompatActivity {

    ActivityReferAndEarnBinding binding;
    Context context;
    String referralCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferAndEarnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ReferAndEarnActivity.this;

        referralCode= AllStaticFields.userData.getReferralCode();
        binding.referCode.setText(referralCode);
        binding.imgWhatsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Download This Application with refer Code:- " + referralCode + "\thttps://play.google.com/store/apps/details?");
//                        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareCompat.IntentBuilder.from(ReferAndEarnActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Chooser title")
                        .setText("http://play.google.com/store/apps/details?id=" + context.getPackageName())
                        .startChooser();


            }
        });
        binding.imgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.setPackage("com.google.android.gm");
                String shareBody = "Download This Application with refer Code:- " + referralCode + "\t https://play.google.com/store/apps/details?";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Astro Express");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(sharingIntent);

            }
        });
        binding.imgAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download This Application with refer Code:- " + referralCode + "\t https://play.google.com/store/apps/details?");
                startActivity(Intent.createChooser(shareIntent, "share via"));


            }
        });
    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //Error
        }
        return false;
    }
}