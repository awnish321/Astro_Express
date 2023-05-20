package com.astroexpress.user.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.astroexpress.user.R;
import com.astroexpress.user.model.SpinnerModel;

public class MySpinnerAdapter extends ArrayAdapter {

    private Context mContext;
    private List<SpinnerModel> spinnerModels;

    public MySpinnerAdapter(@NonNull Context context, List<SpinnerModel> spinnerModels) {
        super(context, 0, spinnerModels);
        mContext = context;
        this.spinnerModels = spinnerModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list_spinner_layout, parent, false);
        try {
            TextView txtName = (TextView) listItem.findViewById(R.id.txtName);
            txtName.setText(spinnerModels.get(position).getName());
        } catch (Exception e) {

        }
        return listItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list_spinner_layout, parent, false);

        TextView txtName = (TextView) listItem.findViewById(R.id.txtName);
        txtName.setText(spinnerModels.get(position).getName());

        return listItem;
    }
}
