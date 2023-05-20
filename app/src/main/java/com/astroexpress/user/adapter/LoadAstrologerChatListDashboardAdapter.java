package com.astroexpress.user.adapter;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;

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
import com.astroexpress.user.databinding.ItemListDashboardOnlineChatAstrologerBinding;
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

public class LoadAstrologerChatListDashboardAdapter extends RecyclerView.Adapter<LoadAstrologerChatListDashboardAdapter.ImageViewHolder> {
    private Context context;
    private List<AstrologerResponseModel.Result> resultList;

    public LoadAstrologerChatListDashboardAdapter(Context context, List<AstrologerResponseModel.Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_online_chat_astrologer, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        AstrologerResponseModel.Result itemData = resultList.get(position);

            holder.binding.txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
            holder.binding.txtSpacelist.setText(itemData.getSpeciality());
            if (itemData.getProfileThumbnail() != null) {
                holder.binding.imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
            }
            if (itemData.getProfileImageUrl() != null) {

                Glide.with(context)
                        .load(itemData.getProfileImageUrl())
                        .apply(new RequestOptions().override(250, 250))
                        .into(holder.binding.imgProfile);
            }

        holder.binding.imageContainer.setOnClickListener(new View.OnClickListener() {
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

        ItemListDashboardOnlineChatAstrologerBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListDashboardOnlineChatAstrologerBinding.bind(itemView);
        }
    }
}

