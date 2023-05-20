package com.astroexpress.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ItemListUserSavedAddressBinding;
import com.astroexpress.user.model.request.OrderRequestModel;
import com.astroexpress.user.model.responsemodel.GetAllSavedResponseModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.ui.activity.BillingActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadSavedAddressAdapter extends RecyclerView.Adapter<LoadSavedAddressAdapter.ImageViewHolder> {

    private Context context;
    private List<GetAllSavedResponseModel.Result> resultList;

    private String remedyId, remedyPrice;
    Boolean isOwnedRemedy;

    public LoadSavedAddressAdapter(Context context, List<GetAllSavedResponseModel.Result> resultList, String remedyId, String remedyPrice, Boolean isOwnedRemedy) {
        this.context = context;
        this.resultList = resultList;
        this.remedyId = remedyId;
        this.remedyPrice = remedyPrice;
        this.isOwnedRemedy = isOwnedRemedy;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_user_saved_address, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        GetAllSavedResponseModel.Result itemData = resultList.get(position);

        holder.binding.txtUserName.setText(itemData.getPersonName());
        holder.binding.txtAddress1.setText(itemData.getAddress1());
        holder.binding.txtAddress2.setText(itemData.getAddress2());
        holder.binding.txtLandMark.setText(itemData.getLandmark());
        holder.binding.txtCityName.setText(itemData.getArea());
        holder.binding.txtPin.setText(itemData.getPincode());

        holder.binding.llSavedAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                    context.startActivity(new Intent(context, UpdateRegistrationFormActivity.class)
//                            .putExtra("remedyId", remedyId)
//                            .putExtra("AddressId",itemData.getUserAddressId())
//                            .putExtra("remedyPrice",remedyPrice)
//                    );

               if (isOwnedRemedy){
                   String orderType ="OwnedRemedy";
               }else {
                   String orderType ="CreatedByAstrologerRemedy";
               }

                OrderRequestModel orderRequestModel = new OrderRequestModel(
                        AllStaticFields.userData.getUserId(),
                        AppConstants.REMEDY_BOOKING,
                        remedyPrice,
                        false,
                        null,
                        false,
                        null,
                        remedyId,
                        itemData.getUserAddressId());

                RetrofitAPIService.getApiClient().generateOrder(orderRequestModel).enqueue(new Callback<OrderResponseModel>() {
                    @Override
                    public void onResponse(Call<OrderResponseModel> call, Response<OrderResponseModel> response) {
                        try {

                            if (response.isSuccessful() && response.body() != null) {

                                if (response.body().getCode().equals("200")) {
                                    AllStaticFields.orderData = response.body().getResult();
                                    context.startActivity(new Intent(context, BillingActivity.class)
                                            .setAction(AppConstants.REMEDY_BOOKING)
                                    );

                                } else {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            AllStaticMethods.saveException(e);
                        }

                    }

                    @Override
                    public void onFailure(Call<OrderResponseModel> call, Throwable t) {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ItemListUserSavedAddressBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListUserSavedAddressBinding.bind(itemView);
        }
    }
}

