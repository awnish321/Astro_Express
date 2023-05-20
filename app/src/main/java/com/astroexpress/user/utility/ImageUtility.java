package com.astroexpress.user.utility;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class ImageUtility {

    public static Bitmap decodeSampledBitmapFromPath(String picturePathIMG) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate inSampleSize
        options.inSampleSize = 8;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return ImageUtils.checkImageRotation(picturePathIMG, BitmapFactory.decodeFile(picturePathIMG, options));
    }

    public static String convertImageToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    public static Bitmap convertStringToImage(String imageString) {
        byte[] imageBytes;
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    public static Bitmap getCircleImage200X200(Bitmap bitmap) {
        int width, height, newWidth = 200, newHeight = 200;
        Matrix matrix;
        Bitmap resizedBitmap = null;
        float scaleWidth, scaleHeight;
        Bitmap originalImage;

        originalImage = bitmap;

        if (originalImage.getWidth() >= originalImage.getHeight()) {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    originalImage.getWidth() / 2 - originalImage.getHeight() / 2,
                    0,
                    originalImage.getHeight(),
                    originalImage.getHeight()
            );

        } else {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    0,
                    originalImage.getHeight() / 2 - originalImage.getWidth() / 2,
                    originalImage.getWidth(),
                    originalImage.getWidth()
            );
        }

        width = originalImage.getWidth();
        height = originalImage.getHeight();
        matrix = new Matrix();

        scaleWidth = ((float) newWidth) / width;

        scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);

        resizedBitmap = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return covertCircleImage(resizedBitmap);
    }

    public static Bitmap convertCircleImage(Bitmap bitmap, Context context, int width, int height) {
        int widthOld, heightOld, newWidth = (int) AllStaticMethods.convertDpToPixel(width, context), newHeight = (int) AllStaticMethods.convertDpToPixel(height, context);
        Matrix matrix;
        Bitmap resizedBitmap = null;
        float scaleWidth, scaleHeight;
        Bitmap originalImage;

        originalImage = bitmap;

        if (originalImage.getWidth() >= originalImage.getHeight()) {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    originalImage.getWidth() / 2 - originalImage.getHeight() / 2,
                    0,
                    originalImage.getHeight(),
                    originalImage.getHeight()
            );

        } else {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    0,
                    originalImage.getHeight() / 2 - originalImage.getWidth() / 2,
                    originalImage.getWidth(),
                    originalImage.getWidth()
            );
        }

        widthOld = originalImage.getWidth();
        heightOld = originalImage.getHeight();
        matrix = new Matrix();

        scaleWidth = ((float) newWidth) / widthOld;

        scaleHeight = ((float) newHeight) / heightOld;

        matrix.postScale(scaleWidth, scaleHeight);

        resizedBitmap = Bitmap.createBitmap(originalImage, 0, 0, widthOld, heightOld, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return covertCircleImage(resizedBitmap);
    }

    public static Bitmap convertResizeImage(Bitmap bitmap, Context context, int width, int height) {
        int widthOld, heightOld, newWidth = (int) AllStaticMethods.convertDpToPixel(width, context), newHeight = (int) AllStaticMethods.convertDpToPixel(height, context);
        Matrix matrix;
        Bitmap resizedBitmap = null;
        float scaleWidth, scaleHeight;
        Bitmap originalImage;

        originalImage = bitmap;

        if (originalImage.getWidth() >= originalImage.getHeight()) {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    originalImage.getWidth() / 2 - originalImage.getHeight() / 2,
                    0,
                    originalImage.getHeight(),
                    originalImage.getHeight()
            );

        } else {

            originalImage = Bitmap.createBitmap(
                    originalImage,
                    0,
                    originalImage.getHeight() / 2 - originalImage.getWidth() / 2,
                    originalImage.getWidth(),
                    originalImage.getWidth()
            );
        }

        widthOld = originalImage.getWidth();
        heightOld = originalImage.getHeight();
        matrix = new Matrix();

        scaleWidth = ((float) newWidth) / widthOld;

        scaleHeight = ((float) newHeight) / heightOld;

        matrix.postScale(scaleWidth, scaleHeight);

        resizedBitmap = Bitmap.createBitmap(originalImage, 0, 0, widthOld, heightOld, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return resizedBitmap;
    }

    public static Bitmap covertCircleImage(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Bitmap getBitmapFromUri(Context context, String picturePathIMG) {
        return BitmapFactory.decodeFile(new File(picturePathIMG).getAbsolutePath());

    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    public static String resizeBase64Image(String base64image) {
        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 400, 400, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static String resizeBitmapImage(Bitmap image) {


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return convertImageToString(image);
        }
        image = Bitmap.createScaledBitmap(image, 400, 400, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static String getBase64ImageFromFile(Context context, File file, Uri uri) throws Exception {

        InputStream fileInputStream = context.getContentResolver().openInputStream(uri);
        byte[] imageBytes = new byte[(int) file.length()];
        fileInputStream.read(imageBytes, 0, imageBytes.length);
        fileInputStream.close();
//        String imageStr =  Base64.encodeToString(imageBytes, Base64.DEFAULT);
        String imageStr = resizeBase64Image(Base64.encodeToString(imageBytes, Base64.DEFAULT));
        return imageStr;
    }

}

