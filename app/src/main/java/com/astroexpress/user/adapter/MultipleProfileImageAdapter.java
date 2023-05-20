package com.astroexpress.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListProfileImageBinding;
import com.astroexpress.user.model.responsemodel.AstrologerMultipleImageResponseModel;
import com.astroexpress.user.ui.activity.ProfileImageActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class MultipleProfileImageAdapter extends RecyclerView.Adapter<MultipleProfileImageAdapter.ImageViewHolder>
{
        private Context context;
        private List<AstrologerMultipleImageResponseModel.Result> resultList;

    public MultipleProfileImageAdapter(Context context, List<AstrologerMultipleImageResponseModel.Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public MultipleProfileImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_profile_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleProfileImageAdapter.ImageViewHolder holder, int position) {

        AstrologerMultipleImageResponseModel.Result content = resultList.get(position);
                Glide.with(context)
                .load(content.getFileUrls())
                .centerCrop()
                .placeholder(R.color.gray)
                .into(holder.binding.imgBanner);
                int abc = position;

                holder.binding.imgBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,ProfileImageActivity.class);
                        intent.putExtra("position",String.valueOf(abc));
                        context.startActivity(intent);

                    }
                });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {

        ItemListProfileImageBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListProfileImageBinding.bind(itemView);
        }
    }
}

