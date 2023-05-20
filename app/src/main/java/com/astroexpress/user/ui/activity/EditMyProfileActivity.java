package com.astroexpress.user.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.astroexpress.user.adapter.MySpinnerAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityEditMyProfileBinding;
import com.astroexpress.user.model.SpinnerModel;
import com.astroexpress.user.model.request.EditProfileRequestModel;
import com.astroexpress.user.model.responsemodel.CityModel;
import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.astroexpress.user.utility.SharedPreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyProfileActivity extends AppCompatActivity {

    ActivityEditMyProfileBinding binding;
    MySpinnerAdapter genderSpinnerAdapter;
    List<SpinnerModel> genderList = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private String uid;
    DatabaseReference myRef;
    String formattedTime = null, formattedDate = null;
    String time1 = null, date1 = null;
    NetworkChange networkChange = new NetworkChange();

    List<CityModel> cityModelList;
    List<String> placeName;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityEditMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = EditMyProfileActivity.this;

        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.layoutToolbar.toolbar.setTitle("Edit Profile");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        populateGenderSpinner();

        if (AllStaticFields.userData != null) {

            binding.editUserFirstName.setText(AllStaticFields.userData.getFirstName() == null ? "" : AllStaticFields.userData.getFirstName());
            binding.editUserLastName.setText(AllStaticFields.userData.getLastName() == null ? "" : AllStaticFields.userData.getLastName());
            for (int i = 0; i < genderList.size(); i++) {
                if (genderList.get(i).getId().equals(AllStaticFields.userData.getGender())) {
                    binding.editUserGender.setSelection(i);
                }
            }
            binding.txtDob.setText(AllStaticFields.userData.getDateOfBirth() == null ? "" : dateFormatter(AllStaticFields.userData.getDateOfBirth()));
            binding.txtUserTimeOfBirth.setText(AllStaticFields.userData.getTimeOfBirth() == null ? "" : timeFormatter(AllStaticFields.userData.getTimeOfBirth()));
            binding.editUserOccupation.setText(AllStaticFields.userData.getOccupation() == null ? "" : AllStaticFields.userData.getOccupation());
            binding.editUserPlaceOfBirth.setText(AllStaticFields.userData.getPlaceOfBirth() == null ? "" : AllStaticFields.userData.getPlaceOfBirth());
            binding.txtUserMobile.setText(AllStaticFields.userData.getMobile() == null ? "" : AllStaticFields.userData.getMobile());
            binding.editUserCurrentCity.setText(AllStaticFields.userData.getCurrentCityName() == null ? "" : AllStaticFields.userData.getCurrentCityName());
            binding.editUserProblemArea.setText(AllStaticFields.userData.getProblemArea().equals("") ? "" : AllStaticFields.userData.getProblemArea());

        }

        binding.txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                String cal = (year + "-" + monthOfYear + "-" + dayOfMonth);
                                binding.txtDob.setText(dateFormatter(cal));
                                formattedDate = cal;
                                datePickerDialog.dismiss();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });

        binding.txtUserTimeOfBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalender.set(Calendar.MINUTE, minute);

                            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");

                            String strTime = hourOfDay + ":" + minute;
                            Date time = null;
                            try {
                                time = sdf24.parse(strTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            formattedTime = (strTime);
                            binding.txtUserTimeOfBirth.setText(sdf12.format(time));
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, false);
                timePickerDialog.setTitle("Choose hour:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (formattedTime != null) {
                    time1 = formattedTime;
                } else {
                    if (AllStaticFields.userData.getTimeOfBirth() == null) {
                        time1 = " ";
                    } else {
                        time1 = AllStaticFields.userData.getTimeOfBirth();
                    }
                }
                if (formattedDate != null) {
                    date1 = formattedDate;
                } else {
                    if (AllStaticFields.userData.getDateOfBirth() == null) {
                        date1 = " ";
                    } else {
                        date1 = AllStaticFields.userData.getDateOfBirth();
                    }
                }

                EditProfileRequestModel editProfileRequestModel = new EditProfileRequestModel
                        (
                                AllStaticFields.userData.getUserId(),
                                binding.editUserProblemArea.getText().toString(),
                                "0",
                                "0",
                                "0",
                                binding.editUserFirstName.getText().toString(),
                                binding.editUserLastName.getText().toString(),
                                genderList.get(binding.editUserGender.getSelectedItemPosition()).getId(),
                                date1,
                                null,
                                time1,
                                null,
                                binding.editUserOccupation.getText().toString(),
                                null,
                                binding.editUserPlaceOfBirth.getText().toString(),
                                binding.txtUserMobile.getText().toString(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                binding.editUserCurrentCity.getText().toString(),
                                null

                        );

                callEditProfileAPI(editProfileRequestModel);
            }
        });

        cityModelList = new ArrayList<>();

        placeName=new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(AllStaticMethods.loadJSONFromAsset(this, "city_list.json"));
            JSONArray jsonArray = obj.getJSONArray("indian_city");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String state = jsonObject.getString("state");
                String lat = jsonObject.getString("lat");
                String lon = jsonObject.getString("lon");

                cityModelList.add(new CityModel(name, state, lat, lon));
                placeName.add(name + ", " + state);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, placeName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editUserPlaceOfBirth.setThreshold(1);
        binding.editUserPlaceOfBirth.setAdapter(adapter);
        binding.editUserPlaceOfBirth.setOnItemClickListener((adapterView, view, position, id) -> {
            int wordEnd = adapter.getItem(position).indexOf(",");
            place = adapter.getItem(position);
            String subString;
            if (wordEnd != -1) {
                subString = adapter.getItem(position).substring(0, wordEnd);
                for (CityModel model : cityModelList) {
                    if (model.getName().contains(subString)) {
//                        latitude = model.getLat();
//                        longitude = model.getLon();
                    }
                }

            }

        });
    }

    private void populateGenderSpinner() {

        genderList.add(new SpinnerModel("0", "Select Gender", null));
        genderList.add(new SpinnerModel("1", "Male", null));
        genderList.add(new SpinnerModel("2", "Female", null));
        genderList.add(new SpinnerModel("3", "Others", null));

        genderSpinnerAdapter = new MySpinnerAdapter(context, genderList);
        binding.editUserGender.setAdapter(genderSpinnerAdapter);
        binding.editUserGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void callEditProfileAPI(EditProfileRequestModel editProfileRequestModel) {
        RetrofitAPIService.getApiClient().updateUser(editProfileRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            SharedPreferenceManager.setUserData(context, response.body().getResult());
                            AllStaticFields.userData = response.body().getResult();
                            setResult(RESULT_OK);
                            Toast.makeText(context, "User detail successfully updated", Toast.LENGTH_SHORT).show();
                            finish();

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
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

    public static String dateFormatter(String unFormattedDate) {
        String datetime = null;
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(unFormattedDate);
            datetime = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return datetime;

    }

    public static String timeFormatter(String unFormattedTime) {

        String time = null;
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm");

        try {
            Date convertedDate = inputFormat.parse(unFormattedTime);
            time = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return time;
    }

}