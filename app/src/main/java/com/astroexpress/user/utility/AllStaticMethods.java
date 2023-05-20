package com.astroexpress.user.utility;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllStaticMethods {
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("URI", uri + "");
        String result = uri + "";
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length - 1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getRealPathFromURI(Uri contentUri, Context context) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public static void sendToGoogleMapApp(Context context, String destinationLatitude, String destinationLongitude, String destinationAddress) {
        if (destinationLatitude == null || destinationLongitude == null || destinationLatitude.equals("0") || destinationLongitude.equals("0")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=" + destinationAddress));
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude));
            context.startActivity(intent);
        }

    }

    @Nullable
    public static Bitmap convertStringToImage(@Nullable String imageString) {
        byte[] imageBytes;
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static boolean haveInternet(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }

        return false;
    }

    public static float getScreenWidth(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return displayMetrics.widthPixels;
    }

    public static float getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getBloodGroupFromBloodGroupId(String bloodGroupId) {

        if (bloodGroupId.equals("0")) {
            return "Unknown";
        } else if (bloodGroupId.equals("1")) {
            return "A+";
        } else if (bloodGroupId.equals("2")) {
            return "A-";
        } else if (bloodGroupId.equals("3")) {
            return "B+";
        } else if (bloodGroupId.equals("4")) {
            return "B-";
        } else if (bloodGroupId.equals("5")) {
            return "AB+";
        } else if (bloodGroupId.equals("6")) {
            return "AB-";
        } else if (bloodGroupId.equals("7")) {
            return "O+";
        } else if (bloodGroupId.equals("8")) {
            return "O-";
        }

        return "";
    }

    public static String getAgeTypeTitleFromAgeTypeId(String ageTypeId) {

        if (ageTypeId.equals("1")) {
            return "Year";
        } else if (ageTypeId.equals("2")) {
            return "Month";
        } else if (ageTypeId.equals("3")) {
            return "Day";
        }

        return "";
    }

    public static String getRespRateFromRespRateId(String respRateId) {

        switch (respRateId) {
            case "1":
                return "Axillary (Armpit)";
            case "2":
                return "Oral (Mouth)";
            case "3":
                return "Tympanic (Ear)";
            case "4":
                return "Temporal (Forehead)";
            case "5":
                return "Rectal (Anus)";
        }

        return "";
    }

    public static int convertCelsiusToFahrenheit(int celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    public static String getFileExtension(String fileName) {

        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    // Converting File to Base64.encode String type using Method
    public static String convertFilePathToBase64(InputStream inputStream) {


        String encodedFile = "", lastVal;
        try {

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int prevHeight = v.getHeight();
        int targetHeight = v.getMeasuredHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(800);
        valueAnimator.start();
    }

    public static void collapse(final View v) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, 0);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(800);
        valueAnimator.start();
    }

    public static Bitmap takeScreenshot(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }


    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static Uri getUriToDrawable(@NonNull Context context,
                                       @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    public static void shareText(Context context, String subject, String shareBody) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public static void openWhatsApp(Context context) {

        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            context.startActivity(launchIntent);//null pointer check in case package name was not found

        } catch (Exception e) {
            Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();

        }

    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static File resizeFileSize(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            if (o.outWidth < 1500 || o.outHeight < 1500) {
                inputStream.close();
                return file;
            }

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean emailValidator(String email) {

        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean urlValidator(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException exception) {
            return false;
        } catch (MalformedURLException exception) {
            return false;
        }
    }

    public static Bitmap convertBimapToBase64(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convertBimapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = ((Activity) context).getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(context);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public static void expand(final View v, ImageView imgExpand, Context context) {

        if (imgExpand != null) {
            if (imgExpand.getTag() == null || imgExpand.getTag().equals("Collapse")) {
                AnimationSet animSet = new AnimationSet(true);
                animSet.setInterpolator(new DecelerateInterpolator());
                animSet.setFillAfter(true);
                animSet.setFillEnabled(true);

                RotateAnimation animRotate = new RotateAnimation(0.0f, -180.0f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                animRotate.setDuration(500L);
                animRotate.setFillAfter(true);
                animSet.addAnimation(animRotate);

                imgExpand.startAnimation(animSet);
                imgExpand.setTag("Expand");
            }
        }

        v.setVisibility(View.VISIBLE);
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getDayIdByDay(String date) {
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("MM-dd-yyyy").parse(date));

            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    return "1";
                case Calendar.MONDAY:
                    return "2";
                case Calendar.TUESDAY:
                    return "3";
                case Calendar.WEDNESDAY:
                    return "4";
                case Calendar.THURSDAY:
                    return "5";
                case Calendar.FRIDAY:
                    return "6";
                case Calendar.SATURDAY:
                    return "7";
            }

        } catch (Exception e) {

        }

        return "";
    }

    public static String getChannel(String content) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.getString("channel");
        } catch (Exception e) {

        }

        return "null";
    }

    public static String getImageUrl(String channel) {

        try {
            JSONObject jsonObject = new JSONObject(channel);
            return jsonObject.getString("ImageUrl");
        } catch (Exception e) {

        }

        return "null";
    }

    public static String getName(String channel) {

        try {
            JSONObject jsonObject = new JSONObject(channel);
            return jsonObject.getString("Name");
        } catch (Exception e) {

        }

        return "null";
    }

    public static void saveException(Exception e) {

    }
    public static String getGenderNameByGenderId(String genderId) {

        switch (genderId) {
            case "1":
                return "Male";
            case "2":
                return "Female";
            case "3":
                return "Others";
            default:
                return "";
        }

    }

    public static String loadJSONFromAsset(Activity activity,String jsonFilename){
        String json = null;
        try {
            InputStream inputStream = activity.getAssets().open(jsonFilename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}
