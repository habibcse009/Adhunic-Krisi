package com.project.adunik_krisi.Customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.Customer.FarmersList.FarmersListCusActivity;
import com.project.adunik_krisi.Customer.Orders.OrderListCusActivity;
import com.project.adunik_krisi.Customer.Profile.ProfileCusActivity;
import com.project.adunik_krisi.KrisiInformation.KrisiInfoMainActivity;
import com.project.adunik_krisi.LoginActivity;
import com.project.adunik_krisi.R;
import com.project.adunik_krisi.ViewAdminNoticesActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class CusMainActivity extends AppCompatActivity {
    CardView cardProfileCus, cardAllFarmersCus, cardAllProductsCus, cardOrdersCus, cardLogoutCus, cardKrisiInfoCus, cardAdminNoticeCus;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameCus, txtHelloCus;
    Shimmer shimmerCus;

    String UserCell;

    private ProgressDialog loading;
    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_main);
        getSupportActionBar().setTitle("প্রধান পাতা (ক্রেতা প্যানেল)");


        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileCus = findViewById(R.id.card_profileCus);
        cardAllProductsCus = findViewById(R.id.card_allProductsCus);
        cardLogoutCus = findViewById(R.id.card_logoutCus);
        cardOrdersCus = findViewById(R.id.card_allOrderCus);
        cardAllFarmersCus = findViewById(R.id.card_FarmerlistCus);
        cardKrisiInfoCus = findViewById(R.id.card_krisi_infoCus);
        cardAdminNoticeCus = findViewById(R.id.card_AdminNoticeCus);
        txtWelcomeNameCus = findViewById(R.id.txtwelcomeNameCus);
        txtHelloCus = findViewById(R.id.txtwelcomeCus);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        //txtTitle.setTypeface(tf);
        txtWelcomeNameCus.setTypeface(tf);
        txtHelloCus.setTypeface(tf);

        shimmerCus = new Shimmer();
        shimmerCus.start(txtWelcomeNameCus);
        shimmerCus.start(txtHelloCus);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CusMainActivity.this, ProfileCusActivity.class);
                startActivity(intent);

            }
        });

        cardAllFarmersCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CusMainActivity.this, FarmersListCusActivity.class);
                startActivity(intent);


            }
        });


        cardAdminNoticeCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CusMainActivity.this, ViewAdminNoticesActivity.class);
                startActivity(intent);


            }
        });


        cardOrdersCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CusMainActivity.this, OrderListCusActivity.class);
                startActivity(intent);


            }
        });
        cardKrisiInfoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CusMainActivity.this, KrisiInfoMainActivity.class);
                startActivity(intent);


            }
        });
        cardAllProductsCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(CusMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);
*/

            }
        });


        cardLogoutCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(CusMainActivity.this, "ক্রেতা প্যানেল থেকে সঠিকভাবে লগ আউট হয়েছে!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CusMainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        //call function
        getData();
    }

    private void getData() {

        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //showing progress dialog
        loading = new ProgressDialog(CusMainActivity.this);
        loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
        loading.show();


        String url = Constant.PROFILECUS_URL + UserCell;  // url for connecting php file

        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Volley Error", "Error:" + error);
                        Toasty.error(CusMainActivity.this, "ইন্টারনেট কানেকশনের সমস্যা!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(CusMainActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {


        Log.d("RESPONSE", response);

        String name = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);
            JSONObject ProfileData = result.getJSONObject(0);

            name = ProfileData.getString(Constant.KEY_NAME);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtWelcomeNameCus.setText(name + "...!");

    }


    //double backpress to exit
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //finish();
            finishAffinity();

        } else {
            Toasty.info(this, "অ্যাপ থেকে বের হয়ে যেতে আরেকবার ব্যাকে ক্লিক করুন!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}