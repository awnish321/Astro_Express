package com.astroexpress.user.adapter;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListAstrologerForSelectionTestBinding;
import com.astroexpress.user.databinding.ItemListNewDashboardUserTestimonialBinding;
import com.astroexpress.user.model.responsemodel.UserTestimonialResponseModel;
import com.astroexpress.user.ui.activity.AstrologerProfileActivity;
import com.astroexpress.user.ui.activity.LoginSignupActivity;
import com.astroexpress.user.ui.activity.UpdateRegistrationFormActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadUserTestimonialListAdapter extends RecyclerView.Adapter<LoadUserTestimonialListAdapter.ImageViewHolder> {
    private Context context;
    private List<UserTestimonialResponseModel.Result> resultList;

    public LoadUserTestimonialListAdapter(Context context, List<UserTestimonialResponseModel.Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_new_dashboard_user_testimonial, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        UserTestimonialResponseModel.Result itemData = resultList.get(position);

//        holder.binding.txtUserName.setText(itemData.getUserName());
//        holder.binding.txtDescription.setText(itemData.getDescription());
//        holder.binding.ratingBarCount.setRating(Float.parseFloat(itemData.getRatingCount()));
        if (itemData.getFileUrl() != null) {

            Picasso.get().load(itemData.getFileUrl().trim())
                    .into(holder.binding.imgProfile);
        }

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ItemListNewDashboardUserTestimonialBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListNewDashboardUserTestimonialBinding.bind(itemView);
        }
    }
}

