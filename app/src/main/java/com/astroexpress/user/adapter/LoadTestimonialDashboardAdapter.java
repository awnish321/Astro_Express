package com.astroexpress.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListTestimonialsBinding;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class LoadTestimonialDashboardAdapter extends RecyclerView.Adapter<LoadTestimonialDashboardAdapter.ImageViewHolder> {
    private Context context;
    private List<TestimonialResponseModels.Result> result;

    public LoadTestimonialDashboardAdapter(Context context, List<TestimonialResponseModels.Result> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_testimonials, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        TestimonialResponseModels.Result itemData = result.get(position);

        holder.binding.txtName.setText(itemData.getTitle());
        holder.binding.txtSpacelist.setText(itemData.getDescription());
        if (itemData.getFileUrl() != null) {

//                Picasso.get().load(itemData.getFileUrl())
//                        .resize(250, 250).
//                        placeholder(R.drawable.image_place_holder)
//                        .into(imgProfile);
            Glide.with(context)
                    .load(itemData.getFileUrl())
//                        .thumbnail(Glide.with(context).load(R.drawable.image_place_holder))
                    .apply(new RequestOptions().override(250, 250))
                    .into(holder.binding.imgProfile);

//                new ImageDownloader(itemData.getFileUrl(), new ImageDownloader.ImageDownloadListener() {
//                    @Override
//                    public void onImageDownload(Bitmap bitmap) {
//                        imgProfile.setImageBitmap(bitmap);
//                    }
//                }).execute();
        }

        holder.binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemData.getVideoUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ItemListTestimonialsBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListTestimonialsBinding.bind(itemView);
        }
    }
}

