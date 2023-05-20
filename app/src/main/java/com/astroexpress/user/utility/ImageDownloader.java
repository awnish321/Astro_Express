package com.astroexpress.user.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Bitmap, Bitmap> {

    String url;
    ImageDownloadListener imageDownloadListener;

    public interface ImageDownloadListener {
        void onImageDownload(Bitmap bitmap);
    }

    public ImageDownloader(String url, ImageDownloadListener imageDownloadListener) {
        this.url = url;
        this.imageDownloadListener = imageDownloadListener;
    }

    @Override
    protected Bitmap doInBackground(String... param) {
        return downloadBitmap(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageDownloadListener.onImageDownload(result);
        }
    }

    private Bitmap downloadBitmap(String url) {

        Bitmap bmp = null;

        try {

            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        } catch (Exception e) {
            e = e;
        }
        return bmp;

    }
}

