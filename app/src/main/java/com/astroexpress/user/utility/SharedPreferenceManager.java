package com.astroexpress.user.utility;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.google.gson.Gson;

public class SharedPreferenceManager {

    public static String mySf = "mySf";
    public static String USER_DATA = "userdata";

    public static void setUserData(Context context, LoginResponseModel.Result result) {
        SharedPreferences sp = context.getSharedPreferences(mySf, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_DATA, new Gson().toJson(result));
        editor.apply();
        AllStaticFields.userData = result;

        if (result!= null)
        {
            AllStaticFields.uName = result.getUsrName();
            AllStaticFields.uPass = result.getPass();
        }
    }

    public static LoginResponseModel.Result getUserData(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(mySf, MODE_PRIVATE);
        LoginResponseModel.Result result =  new Gson().fromJson(sp.getString(USER_DATA, null), LoginResponseModel.Result.class);

        if (result!= null){
            AllStaticFields.uName = result.getUsrName();
            AllStaticFields.uPass = result.getPass();
        }
        return  result;
    }

    public static void logout(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mySf,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}






