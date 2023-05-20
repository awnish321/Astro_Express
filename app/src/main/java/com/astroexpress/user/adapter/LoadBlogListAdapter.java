package com.astroexpress.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListBlogBinding;
import com.astroexpress.user.databinding.ItemListNewDashboardBlogBinding;
import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.ui.activity.BlogActivity;
import com.astroexpress.user.utility.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

    public class LoadBlogListAdapter extends RecyclerView.Adapter<com.astroexpress.user.adapter.LoadBlogListAdapter.ImageViewHolder> {
        private Context context;
        private List<BlogResponseModel.Result> resultList;

        public LoadBlogListAdapter(Context context, List<BlogResponseModel.Result> resultList) {
            this.context = context;
            this.resultList = resultList;
        }

        @NonNull
        @Override
        public com.astroexpress.user.adapter.LoadBlogListAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_blog, parent, false);
            return new com.astroexpress.user.adapter.LoadBlogListAdapter.ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.astroexpress.user.adapter.LoadBlogListAdapter.ImageViewHolder holder, int position) {

            BlogResponseModel.Result itemData = resultList.get(position);

            holder.binding.txtBlogTittle.setText(itemData.getTitle());
            holder.binding.txtBlogBody.setText(itemData.getDescription());
//        holder.binding.txtRatingCount.setText(itemData.getRating());

//        if (itemData.getFileThumbnail() != null) {
//            holder.binding.imgBlog.setImageBitmap(ImageUtils.convert(itemData.getFileThumbnail()));
//        }

//        if (itemData.getFileUrl() != null) {
//
//            Glide.with(context)
//                    .load(itemData.getFileUrl())
//                    .apply(new RequestOptions().override(400, 400))
//                    .into(holder.binding.imgBlog);
//        }else {
//            holder.binding.imgBlog.setImageResource(R.drawable.helpbackground);
//        }

//            holder.binding.imgBlog.setImageResource(R.drawable.helpbackground);

            holder.binding.llFullBlogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, BlogActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return resultList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ItemListBlogBinding binding;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemListBlogBinding.bind(itemView);
            }
        }
    }
