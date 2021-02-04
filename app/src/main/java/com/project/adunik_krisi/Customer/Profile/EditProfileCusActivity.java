package com.project.adunik_krisi.Customer.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.R;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class EditProfileCusActivity extends AppCompatActivity {
    EditText extUsernameCus, extMobileCus, extEmailCus, extLocationCus, extAddressCus, extGenderCus, extPasswordCus;
    ImageView imgProfile;

    ProgressDialog loading;
    TextView txtNameCus;
    Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_cus);

        extUsernameCus = findViewById(R.id.txt_editusernameCus);
        extMobileCus = findViewById(R.id.txt_editmobileCus);
        extEmailCus = findViewById(R.id.txt_editemailCus);
        extGenderCus = findViewById(R.id.txt_editgenderCus);
        extLocationCus = findViewById(R.id.txt_editlocationCus);
        extAddressCus = findViewById(R.id.txt_editaddressCus);
        extPasswordCus = findViewById(R.id.txt_editpasswordCus);
        txtNameCus = findViewById(R.id.txteditNameCus);
        btnUpdateProfile = findViewById(R.id.btn_updateCus);
        imgProfile = findViewById(R.id.profile_image);


        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("প্রোফাইল হালনাগাদ");


        String getName = getIntent().getExtras().getString("name");
        String getMobile = getIntent().getExtras().getString("mobile");
        String getEmail = getIntent().getExtras().getString("email");
        String getGender = getIntent().getExtras().getString("gender");
        String getLocation = getIntent().getExtras().getString("location");
        String getAddress = getIntent().getExtras().getString("address");


        extUsernameCus.setText(getName);
        txtNameCus.setText(getName);
        extMobileCus.setText(getMobile);
        extMobileCus.setEnabled(false);
        extEmailCus.setText(getEmail);


        extLocationCus.setText(getLocation);
        extAddressCus.setText(getAddress);
        extGenderCus.setText(getGender);
        //extPasswordCus.setText("*******");


//        if(getGender.equals("Female"))
//        {
//            imgProfile.setImageResource(R.drawable.girl);
//        }

        extLocationCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] cityList = {"ঢাকা", "চট্টগ্রাম", "সিলেট", "রাজশাহী", "বরিশাল", "খুলনা", "রংপুর", "ময়মনসিং"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileCusActivity.this);
                builder.setTitle("বিভাগ নির্ধারণ করুন");
                //builder.setIcon(R.drawable.ic_location);


                builder.setCancelable(false);
                builder.setItems(cityList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:

                                extLocationCus.setText("ঢাকা");
                                break;

                            case 1:

                                extLocationCus.setText("চট্টগ্রাম");
                                break;

                            case 2:

                                extLocationCus.setText("সিলেট");
                                break;

                            case 3:

                                extLocationCus.setText("রাজশাহী");
                                break;

                            case 4:

                                extLocationCus.setText("বরিশাল");
                                break;

                            case 5:

                                extLocationCus.setText("খুলনা");
                                break;

                            case 6:

                                extLocationCus.setText("রংপুর");
                                break;

                            case 7:

                                extLocationCus.setText("ময়মনসিং");
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog locationTypeDialog = builder.create();

                locationTypeDialog.show();
            }
        });


        extGenderCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] genderList = {"পুরুষ", "নারী"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileCusActivity.this);
                builder.setTitle("লিঙ্গ নির্ধারণ করুন");


                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                extGenderCus.setText(genderList[position]);
                                break;

                            case 1:
                                extGenderCus.setText(genderList[position]);
                                break;


                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileCusActivity.this);
                builder.setMessage("প্রোফাইল হালনাগাদ করতে চান?")
                        .setCancelable(false)
                        .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                UpdateProfile();
                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("না", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Your Task Here--When No is pressed
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }


    //update contact method
    public void UpdateProfile() {

        final String name = extUsernameCus.getText().toString();
        final String mobile = extMobileCus.getText().toString();
        final String email = extEmailCus.getText().toString();
        final String location = extLocationCus.getText().toString();
        final String address = extAddressCus.getText().toString();
        final String gender = extGenderCus.getText().toString();
        final String password = extPasswordCus.getText().toString();


        if (name.isEmpty()) {
            extUsernameCus.setError("নামের ঘর খালি হতে পারে না!");
            extUsernameCus.requestFocus();
        } else if (mobile.length() != 11) {
            extMobileCus.setError("মোবাইল নাম্বার ভুল!");
            extMobileCus.requestFocus();

        } else if (email.isEmpty()) {
            extEmailCus.setError("ইমেইলের ঘর খালি হতে পারে না!");
            extEmailCus.requestFocus();
        } else if (address.isEmpty()) {
            extAddressCus.setError("ঠিকানার ঘর খালি হতে পারে না!");
            extAddressCus.requestFocus();
        } else if (password.length() < 4) {
            extPasswordCus.setError("পাসওয়ার্ড অনেক ছোট কিংবা ভুল !");
            extPasswordCus.requestFocus();
        } else {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            // loading.setTitle("Update");
            loading.setMessage("অপেক্ষা করুন, হালনাগাদ হচ্ছে...");
            loading.show();

            String URL = Constant.PROFILECUS_UPDATE_URL;


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            //for track response in logcat
                            Log.d("RESPONSE", response);
                            // Log.d("RESPONSE", userCell);


                            //If we are getting success from server
                            if (response.equals("success")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(EditProfileCusActivity.this, ProfileCusActivity.class);
                                Toasty.success(EditProfileCusActivity.this, "প্রোফাইল সঠিকভাবে হালনাগাদ হয়েছে!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(EditProfileCusActivity.this, ProfileCusActivity.class);
                                Toasty.error(EditProfileCusActivity.this, "প্রোফাইল হালনাগাদ ব্যার্থ!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toasty.error(EditProfileCusActivity.this, "নেটওয়ার্কের সমস্যা", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(EditProfileCusActivity.this, "ইন্টারনেট কানেকশন নেই অথবা \nএখানে কোন একটা সমস্যা আছে !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    // params.put(Constant.KEY_ID, getID);
                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_MOBILE, mobile);
                    params.put(Constant.KEY_EMAIL, email);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_LOCATION, location);
                    params.put(Constant.KEY_ADDRESS, address);
                    params.put(Constant.KEY_PASSWORD, password);


                    //Log.d("ID", getID);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(EditProfileCusActivity.this);
            requestQueue.add(stringRequest);
        }

    }


    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}