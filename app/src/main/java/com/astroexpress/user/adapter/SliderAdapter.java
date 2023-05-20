package com.astroexpress.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.astroexpress.user.R;
import com.astroexpress.user.model.responsemodel.BannerResponseModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<BannerResponseModel.Result> sliderItems;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<BannerResponseModel.Result> sliderItems, ViewPager2 viewPager2)
    {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if (position==sliderItems.size()-2)
        {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends  RecyclerView.ViewHolder {
        private  RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void  setImage (BannerResponseModel.Result sliderItem)
        {
            Picasso.get().load(sliderItem.getFileUrl()).fit().noFade().into(imageView);
        }
    }

    private Runnable runnable =new Runnable() {
        @Override
        public void run()
        {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}
