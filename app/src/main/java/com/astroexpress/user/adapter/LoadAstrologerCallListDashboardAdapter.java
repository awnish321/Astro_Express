package com.astroexpress.user.adapter;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CALL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListAstrologerForSelectionTestBinding;
import com.astroexpress.user.databinding.ItemListDashboardOnlineCallAstrologerBinding;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.ui.activity.AstrologerProfileActivity;
import com.astroexpress.user.ui.activity.LoginSignupActivity;
import com.astroexpress.user.ui.activity.UpdateRegistrationFormActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadAstrologerCallListDashboardAdapter extends RecyclerView.Adapter<LoadAstrologerCallListDashboardAdapter.ImageViewHolder> {
    private Context context;
    private List<AstrologerResponseModel.Result> resultList;

    public LoadAstrologerCallListDashboardAdapter(Context context, List<AstrologerResponseModel.Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_online_call_astrologer, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        AstrologerResponseModel.Result itemData = resultList.get(position);

        holder.binding.txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
        holder.binding.txtSpacelist.setText(itemData.getSpeciality());
        holder.binding.txtLanguage.setText(itemData.getLanguage());
        holder.binding.txtExperience.setText(itemData.getExperience() + " Yrs");
        holder.binding.txtCharge.setText("\u20B9 " + itemData.getChargePerMinute() + " Per Minute");

        if (itemData.getProfileThumbnail() != null) {
            holder.binding.imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
        }

        if (itemData.getProfileImageUrl() != null) {

            Glide.with(context)
                    .load(itemData.getProfileImageUrl())
                    .apply(new RequestOptions().override(250, 250))
                    .into(holder.binding.imgProfile);
        }

        holder.binding.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int dataPosition= holder.getPosition();
                boolean ValidForFree = false;
                if (dataPosition<5){
                    ValidForFree=true;
               }

                context.startActivity(new Intent(context, AstrologerProfileActivity.class)
                                .putExtra("AstrologerId", itemData.getAstrologerId())
                                .putExtra("ValidForFree",ValidForFree)

                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemListDashboardOnlineCallAstrologerBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListDashboardOnlineCallAstrologerBinding.bind(itemView);
        }
    }
}

