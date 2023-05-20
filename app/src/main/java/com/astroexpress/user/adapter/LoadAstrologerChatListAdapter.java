package com.astroexpress.user.adapter;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ItemListAstrologerForSelectionBinding;
import com.astroexpress.user.databinding.ItemListAstrologerForSelectionTestBinding;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.ui.activity.AstrologerProfileActivity;
import com.astroexpress.user.ui.activity.LoginSignupActivity;
import com.astroexpress.user.ui.activity.UpdateRegistrationFormActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.ImageUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadAstrologerChatListAdapter extends RecyclerView.Adapter<LoadAstrologerChatListAdapter.ImageViewHolder> {
    private Context context;
    private List<AstrologerResponseModel.Result> resultList;

    public LoadAstrologerChatListAdapter(Context context, List<AstrologerResponseModel.Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_astrologer_for_selection_test, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        AstrologerResponseModel.Result itemData = resultList.get(position);

        holder.binding.txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
        holder.binding.txtSpacelist.setText(itemData.getSpeciality());
        holder.binding.txtLanguage.setText(itemData.getLanguage());
        holder.binding.txtExperience.setText(itemData.getExperience() + " Yrs");
        holder.binding.ratingBar.setRating(Float.parseFloat(itemData.getRating()));
        holder.binding.txtRating.setText(itemData.getRating() + "/5 ");

        if (AllStaticFields.userData != null)
        {
            if (itemData.getRemaningFreeSession() > 0 && !AllStaticFields.walletData.getMinuteAmount().equals("0")&&itemData.getValidForFree())
            {
                holder.binding.imgFreeCallChat.setVisibility(View.VISIBLE);
                holder.binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                holder.binding.txtDiscountCharge.setVisibility(View.GONE);
                if (itemData.getOfferApplied()) {
                    if (itemData.getDiscountType().equals("Percentage")) {
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        holder.binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));

                    } else {
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        holder.binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));
                    }

                } else {
                    holder.binding.txtCharge.setText("\u20B9 " + itemData.getChargePerMinute() + "/Min");
                }
            }
            else
            {
                holder.binding.imgFreeCallChat.setVisibility(View.GONE);
                holder.binding.frameLayoutPrice.setVisibility(View.GONE);
                holder.binding.txtDiscountCharge.setVisibility(View.VISIBLE);
                if (itemData.getOfferApplied()) {
                    holder.binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                    if (itemData.getDiscountType().equals("Percentage")) {
                        holder.binding.txtCharge.setText("\u20b9 " + itemData.getChargePerMinute());
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        holder.binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    } else {
                        holder.binding.txtCharge.setText("\u20B9 " + itemData.getChargePerMinute());
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        holder.binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    }

                } else {
                    holder.binding.frameLayoutPrice.setVisibility(View.GONE);
                    holder.binding.txtDiscountCharge.setText("\u20B9 " + itemData.getChargePerMinute() + " Per Minute");
                }
            }
        }
        else
        {
            if (itemData.getRemaningFreeSession() > 0 && itemData.getValidForFree())
            {
                holder.binding.imgFreeCallChat.setVisibility(View.VISIBLE);
                holder.binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                holder.binding.txtDiscountCharge.setVisibility(View.GONE);
                if (itemData.getOfferApplied()) {
                    if (itemData.getDiscountType().equals("Percentage")) {
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        holder.binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));

                    } else {
                        double amount = Double.parseDouble(itemData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(itemData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        holder.binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));
                    }
                } else {
                    holder.binding.txtCharge.setText("\u20B9 " + itemData.getChargePerMinute() + "/Min");
                }
            }
            else
            {
                {
                    holder.binding.imgFreeCallChat.setVisibility(View.GONE);
                    holder.binding.frameLayoutPrice.setVisibility(View.GONE);
                    holder.binding.txtDiscountCharge.setVisibility(View.VISIBLE);
                    if (itemData.getOfferApplied()) {
                        holder.binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                        if (itemData.getDiscountType().equals("Percentage")) {
                            holder.binding.txtCharge.setText("\u20b9 " + itemData.getChargePerMinute());
                            double amount = Double.parseDouble(itemData.getChargePerMinute());
                            double DiscountPrice = (amount / 100.0f) * Double.parseDouble(itemData.getDiscountAmount());
                            double DiscountFinalPrice = (amount - DiscountPrice);
                            holder.binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                        } else {
                            holder.binding.txtCharge.setText("\u20B9 " + itemData.getChargePerMinute());
                            double amount = Double.parseDouble(itemData.getChargePerMinute());
                            double discountedAmount = Double.parseDouble(itemData.getDiscountAmount());
                            double DiscountFinalPrice = (amount - discountedAmount);
                            holder.binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                        }

                    } else {
                        holder.binding.frameLayoutPrice.setVisibility(View.GONE);
                        holder.binding.txtDiscountCharge.setText("\u20B9 " + itemData.getChargePerMinute() + " Per Minute");
                    }
                }
            }
        }

        if (itemData.getProfileImageUrl() != null) {

            Picasso.get().load(itemData.getProfileImageUrl())
                    .resize(200, 200)
                    .into(holder.binding.imgProfile);
        }

        if (itemData.isOnlineForChat())
        {
            holder.binding.imgBtnChat.setVisibility(View.VISIBLE);
            if (itemData.getBusyOnCall()) {
                holder.binding.txtAstroStatus.setText(" Busy ");
                holder.binding.txtAstroStatus.setBackgroundResource(R.color.red);
                holder.binding.imgBtnChat.setEnabled(false);

            } else {
                holder.binding.txtAstroStatus.setText("Online");
                holder.binding.txtAstroStatus.setBackgroundResource(R.color.green);
                holder.binding.imgBtnChat.setEnabled(true);
            }

        } else {
            holder.binding.imgBtnChat.setVisibility(View.GONE);
            holder.binding.txtAstroStatus.setText("Offline");
            holder.binding.txtAstroStatus.setBackgroundResource(R.color.semi_gray);

        }

        if (!itemData.getWaitTime().toString().equals("0") &&  itemData.isOnlineForChat()) {
            holder.binding.txtWaitTime.setVisibility(View.VISIBLE);
            holder.binding.txtWait.setVisibility(View.VISIBLE);
            holder.binding.txtWaitTime.setText(itemData.getWaitTime() + "Min");
        } else {
            holder.binding.txtWaitTime.setVisibility(View.GONE);
            holder.binding.txtWait.setVisibility(View.GONE);
        }

        holder.binding.imgBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AllStaticFields.userData != null) {
                    if (AllStaticFields.sessionId == null) {
                        AllStaticFields.astrologerData = itemData;
                        context.startActivity(new Intent(context, UpdateRegistrationFormActivity.class).setAction(ASTROLOGERS_FOR_CHAT)
                                .putExtra("AstrologerId", itemData.getAstrologerId())
                                .putExtra("UserId", AllStaticFields.userData.getUserId())
                                .putExtra("RemainingFreeSession", AllStaticFields.astrologerData.getRemaningFreeSession().toString())
                                .putExtra("ValidForFree", itemData.getValidForFree())


                        );
                    } else {
                        Toast.makeText(context, "you are in call disconnect to continue", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent1 = new Intent(context, LoginSignupActivity.class);
                    context.startActivity(intent1);
                }
            }
        });

        holder.binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllStaticFields.astrologerData = itemData;
                context.startActivity(new Intent(context, AstrologerProfileActivity.class)
                        .putExtra("AstrologerId", itemData.getAstrologerId())
                        .putExtra("ValidForFree", itemData.getValidForFree())

                );
            }
        });

        holder.binding.llMyProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStaticFields.astrologerData = itemData;
                context.startActivity(new Intent(context, AstrologerProfileActivity.class)
                        .putExtra("AstrologerId", itemData.getAstrologerId())
                        .putExtra("ValidForFree", itemData.getValidForFree())


                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ItemListAstrologerForSelectionTestBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListAstrologerForSelectionTestBinding.bind(itemView);
        }
    }
}

