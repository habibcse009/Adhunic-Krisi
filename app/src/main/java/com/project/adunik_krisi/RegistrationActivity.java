package com.project.adunik_krisi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class RegistrationActivity extends AppCompatActivity {
    ShimmerTextView tv1;
    Shimmer shimmer1;
    private ProgressDialog loading;


    String name, accountType, mobile, email, address, gender, location, password;

    Button btnRegistration;
    EditText etxtName, etxtAccountType, etxtCell, etxtGender, etxtLocation, etxtEmail, etxtPassword, etxtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tv1 = (ShimmerTextView) findViewById(R.id.txtTitlesignup);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "AsapCondensed-Regular.ttf");
        //txtTitle.setTypeface(tf);
        tv1.setTypeface(tf);

        getSupportActionBar().setTitle("রেজিস্ট্রেশন প্যানেল");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        shimmer1 = new Shimmer();
        shimmer1.start(tv1);


        etxtName = findViewById(R.id.fullname);
        etxtAccountType = findViewById(R.id.ac_type);
        etxtCell = findViewById(R.id.cell);
        etxtLocation = findViewById(R.id.location);
        etxtAddress = findViewById(R.id.fulladdress);
        etxtEmail = findViewById(R.id.email);
        etxtPassword = findViewById(R.id.password);
        etxtGender = findViewById(R.id.gender);

        btnRegistration = findViewById(R.id.btn_signup_submit);


        //For choosing account type and open alert dialog
        etxtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"পুরুষ", "নারী"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                builder.setTitle("লিঙ্গ নির্ধারণ করুন");
                //builder.setIcon(R.drawable.ic_gender);


                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtGender.setText(genderList[position]);
                                break;

                            case 1:
                                etxtGender.setText(genderList[position]);
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


        //For choosing account type and open alert dialog
        etxtAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] typeList = {"ক্রেতা", "কৃষক"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                builder.setTitle("একাউন্টের ধরন নির্ধারণ করুন");
                //builder.setIcon(R.drawable.ic_gender);


                builder.setCancelable(false);
                builder.setItems(typeList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtAccountType.setText(typeList[position]);
                                break;

                            case 1:
                                etxtAccountType.setText(typeList[position]);
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


        //For choosing gender and open alert dialog
        etxtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] cityList = {"ঢাকা", "চট্টগ্রাম", "সিলেট", "রাজশাহী", "বরিশাল", "খুলনা", "রংপুর", "ময়মনসিং"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                builder.setTitle("বিভাগ নির্ধারণ করুন");
                //builder.setIcon(R.drawable.ic_location);


                builder.setCancelable(false);
                builder.setItems(cityList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:

                                etxtLocation.setText("ঢাকা");
                                break;

                            case 1:

                                etxtLocation.setText("চট্টগ্রাম");
                                break;

                            case 2:

                                etxtLocation.setText("সিলেট");
                                break;

                            case 3:

                                etxtLocation.setText("রাজশাহী");
                                break;

                            case 4:

                                etxtLocation.setText("বরিশাল");
                                break;

                            case 5:

                                etxtLocation.setText("খুলনা");
                                break;

                            case 6:

                                etxtLocation.setText("রংপুর");
                                break;

                            case 7:

                                etxtLocation.setText("ময়মনসিং");
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

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Getting values from edit texts
                name = etxtName.getText().toString().trim();
                accountType = etxtAccountType.getText().toString().trim();
                mobile = etxtCell.getText().toString().trim();
                email = etxtEmail.getText().toString().trim();
                location = etxtLocation.getText().toString().trim();
                address = etxtAddress.getText().toString().trim();
                password = etxtPassword.getText().toString().trim();
                gender = etxtGender.getText().toString().trim();


                //Checking  field/validation


                //Checking  field/validation
                if (accountType.isEmpty()) {
                    etxtAccountType.setError("একাউন্টের ধরন নির্ধারণ করুন!");
                    requestFocus(etxtAccountType);
                } else if (name.isEmpty()) {
                    etxtName.setError("সম্পূর্ন নাম লিখুন!");
                    requestFocus(etxtName);
                }


                //Checking username field/validation
                else if (mobile.length() != 11 || mobile.contains(" ") || mobile.charAt(0) != '0' || mobile.charAt(1) != '1') {
                    etxtCell.setError("সঠিক মোবাইল নাম্বার দিন!");
                    requestFocus(etxtCell);
                } else if (email.isEmpty()) {
                    etxtEmail.setError("সঠিক ইমেইল এড্রেস দিন!");
                    requestFocus(etxtEmail);
                }

                //Checking  field/validation

                //Checking username field/validation
                else if (location.isEmpty()) {
                    etxtLocation.setError("বিভাগ নির্ধারণ করুন!");
                    requestFocus(etxtLocation);
                } else if (address.isEmpty()) {
                    etxtAddress.setError("বিস্তারিত ঠিকানা লিখুন!");
                    requestFocus(etxtAddress);
                }


                //Checking password field/validation
                else if (password.length() < 6) {

                    etxtPassword.setError("পাসওয়ার্ড সর্বনিম্ন ৬ সংখ্যার হতে হবে!");
                    requestFocus(etxtPassword);

                } else if (gender.isEmpty()) {
                    etxtGender.setError("লিঙ্গ নির্ধারণ করুন!");
                    requestFocus(etxtGender);
                } else {


                    signup();


                }

            }
        });

    }


    private void signup() {


        //showing progress dialog
//
        loading = new ProgressDialog(RegistrationActivity.this);
        loading.setMessage("লোডিং হচ্ছে, অপেক্ষা করুন...");
        loading.show();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String myResponse = response.trim();


                        //for logcat
                        Log.d("RESPONSE", response);


                        //If we are getting success from server
                        if (myResponse.equals("success")) {


                            loading.dismiss();
                            //Starting profile activity
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            Toasty.success(RegistrationActivity.this, "আপনার একাউন্ট সঠিকভাবে চালু হয়েছে!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else if (myResponse.equalsIgnoreCase(Constant.USER_EXISTS)) {

                            Toasty.error(RegistrationActivity.this, "আগেই অন্যকারো একাউন্ট আছে!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toasty.error(RegistrationActivity.this, "ইন্টারনেট কানেকশনের সমস্যা!", Toast.LENGTH_LONG).show();
                        // loading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEY_TYPE, accountType);
                params.put(Constant.KEY_NAME, name);
                params.put(Constant.KEY_MOBILE, mobile);
                params.put(Constant.KEY_EMAIL, email);
                params.put(Constant.KEY_GENDER, gender);
                params.put(Constant.KEY_LOCATION, location);
                params.put(Constant.KEY_ADDRESS, address);
                params.put(Constant.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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





