package com.astroexpress.user.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.astroexpress.user.R;
import com.astroexpress.user.model.responsemodel.BannerResponseModel;
import com.astroexpress.user.utility.ImageDownloader;
import com.astroexpress.user.utility.ImageUtils;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<BannerResponseModel.Result> result;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<BannerResponseModel.Result> result) {
        this.context = context;
        this.result = result;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.item_list_dashboard_banner_1, null);

        ImageView imgBanner = view.findViewById(R.id.imgBanner);

        BannerResponseModel.Result itemData = result.get(position);

        if (itemData.getFileThumbnail() != null) {
            imgBanner.setImageBitmap(ImageUtils.convert(itemData.getFileThumbnail()));
        }

        new ImageDownloader(itemData.getFileUrl(), new ImageDownloader.ImageDownloadListener() {
            @Override
            public void onImageDownload(Bitmap bitmap) {
                imgBanner.setImageBitmap(bitmap);
            }
        }).execute();

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
