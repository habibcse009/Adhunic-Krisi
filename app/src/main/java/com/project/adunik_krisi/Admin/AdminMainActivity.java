package com.project.adunik_krisi.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.adunik_krisi.Admin.Profile.ProfileAdminActivity;
import com.project.adunik_krisi.Admin.UsersList.CustomersListAdminActivity;
import com.project.adunik_krisi.Admin.UsersList.FarmersListAdminActivity;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.KrisiInformation.KrisiInfoMainActivity;
import com.project.adunik_krisi.LoginActivity;
import com.project.adunik_krisi.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class AdminMainActivity extends AppCompatActivity {

    CardView cardProfileAdmin, cardFarmerListAdmin, cardCustomerListAdmin, cardAllProductsAdmin, cardAddNoticeAdmin, cardKrisiInfoAdmin, cardLogoutAdmin;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameAdmin, txtHelloAdmin;
    Shimmer shimmerAdmin;

    String UserCell;

    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().setTitle("প্রধান পাতা (এডমিন প্যানেল)");

        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileAdmin = findViewById(R.id.card_profileAdmn);
        cardFarmerListAdmin = findViewById(R.id.card_FarmerlistAdmn);
        cardCustomerListAdmin = findViewById(R.id.card_allCusAdmn);
        cardAllProductsAdmin = findViewById(R.id.card_allProductsAdmn);
        cardAddNoticeAdmin = findViewById(R.id.card_createNotice);
        cardKrisiInfoAdmin = findViewById(R.id.card_krisi_info);
//        cardAllProductsAdmin.setVisibility(View.INVISIBLE);
        cardLogoutAdmin = findViewById(R.id.card_logoutAdmn);
        txtWelcomeNameAdmin = findViewById(R.id.txtwelcomeNameAdmn);
        txtHelloAdmin = findViewById(R.id.txtwelcomeAdmn);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        //txtTitle.setTypeface(tf);
        txtWelcomeNameAdmin.setTypeface(tf);
        txtHelloAdmin.setTypeface(tf);

        shimmerAdmin = new Shimmer();
        shimmerAdmin.start(txtWelcomeNameAdmin);
        shimmerAdmin.start(txtHelloAdmin);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ProfileAdminActivity.class);
                startActivity(intent);

            }
        });

        cardFarmerListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, FarmersListAdminActivity.class);
                startActivity(intent);

            }
        });


        cardCustomerListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, CustomersListAdminActivity.class);
                startActivity(intent);

            }
        });


        cardAllProductsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(AdminMainActivity.this, OrderListAdminActivity.class);
                startActivity(intent);*/
                Toasty.info(AdminMainActivity.this, "Products Panel Clicked!", Toast.LENGTH_SHORT).show();

            }
        });

        cardAddNoticeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AddNoticeAdminActivity.class);
                startActivity(intent);

            }
        });

        cardKrisiInfoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, KrisiInfoMainActivity.class);
                startActivity(intent);

            }
        });

        cardLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(AdminMainActivity.this, "এডমিন প্যানেল থেকে সঠিকভাবে লগ আউট হয়েছে!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


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