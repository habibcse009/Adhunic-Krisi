package com.project.adunik_krisi.Farmer;

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
import com.project.adunik_krisi.Farmer.Profile.ProfileFarmerActivity;
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

public class FarmerMainActivity extends AppCompatActivity {
    CardView cardProfileFarmer, cardAddProductsFarmer, cardMyProductsFarmer, cardOrdersFarmer, cardLogoutFarmer, cardKrisiInfoFarmer, cardAdminNoticeFarmer;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameFarmer, txtHelloFarmer;
    Shimmer shimmerFarmer;

    String UserCell;

    private ProgressDialog loading;
    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_main);
        getSupportActionBar().setTitle("প্রধান পাতা (কৃষক প্যানেল)");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileFarmer = findViewById(R.id.card_profileFarmer);
        cardMyProductsFarmer = findViewById(R.id.card_allProductsFarmer);
        cardLogoutFarmer = findViewById(R.id.card_logoutFarmer);
        cardOrdersFarmer = findViewById(R.id.card_allOrderFarmer);
        cardAddProductsFarmer = findViewById(R.id.card_CreateNewProductsFarmer);
        cardKrisiInfoFarmer = findViewById(R.id.card_krisi_infoFarmer);
        cardAdminNoticeFarmer = findViewById(R.id.card_AdminNoticeFarmer);
        txtWelcomeNameFarmer = findViewById(R.id.txtwelcomeNameFarmer);
        txtHelloFarmer = findViewById(R.id.txtwelcomeFarmer);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        //txtTitle.setTypeface(tf);
        txtWelcomeNameFarmer.setTypeface(tf);
        txtHelloFarmer.setTypeface(tf);

        shimmerFarmer = new Shimmer();
        shimmerFarmer.start(txtWelcomeNameFarmer);
        shimmerFarmer.start(txtHelloFarmer);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerMainActivity.this, ProfileFarmerActivity.class);
                startActivity(intent);

            }
        });

        cardAddProductsFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Intent intent = new Intent(FarmerMainActivity.this, FarmersListFarmerActivity.class);
                // startActivity(intent);


            }
        });


        cardAdminNoticeFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FarmerMainActivity.this, ViewAdminNoticesActivity.class);
                startActivity(intent);


            }
        });


        cardOrdersFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    Intent intent = new Intent(FarmerMainActivity.this, OrderListFarmerActivity.class);
                //  startActivity(intent);


            }
        });
        cardKrisiInfoFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FarmerMainActivity.this, KrisiInfoMainActivity.class);
                startActivity(intent);


            }
        });
        cardMyProductsFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(FarmerMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Farmer");
                startActivity(intent);
*/

            }
        });


        cardLogoutFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(FarmerMainActivity.this, "কৃষক প্যানেল থেকে সঠিকভাবে লগ আউট হয়েছে!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FarmerMainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        //call function
        getData();
    }

    private void getData() {

        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //showing progress dialog
        loading = new ProgressDialog(FarmerMainActivity.this);
        loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
        loading.show();


        String url = Constant.PROFILE_FARMER_URL + UserCell;  // url for connecting php file

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
                        Toasty.error(FarmerMainActivity.this, "ইন্টারনেট কানেকশনের সমস্যা!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerMainActivity.this);
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

        txtWelcomeNameFarmer.setText(name + "...!");

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