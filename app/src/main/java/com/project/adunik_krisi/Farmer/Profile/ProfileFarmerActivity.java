package com.project.adunik_krisi.Farmer.Profile;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFarmerActivity extends AppCompatActivity {
    TextView txtNameFarmer, txtUserNameFarmer, txtGenderFarmer, txtAddressFarmer, txtMobileFarmer, txtEmailFarmer, txtLocationFarmer, txtPasswordFarmer;
    Button btnEditProfile;

    String UserCell;

    private ProgressDialog loading;

    SharedPreferences sharedPreferences;

    ImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_farmer);

        //Fetching mobile from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("প্রোফাইল");


        txtNameFarmer = findViewById(R.id.txtNameFarmer);
        txtUserNameFarmer = findViewById(R.id.txtUsernameFarmer);
        txtMobileFarmer = findViewById(R.id.txtMobileFarmer);
        txtEmailFarmer = findViewById(R.id.txtEmailFarmer);
        txtGenderFarmer = findViewById(R.id.txtGenderFarmer);
        txtLocationFarmer = findViewById(R.id.txtLocationFarmer);
        txtAddressFarmer = findViewById(R.id.txtAddressFarmer);


        profilePic = findViewById(R.id.profile_image);
        btnEditProfile = findViewById(R.id.btnEditProfileFarmer);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileFarmerActivity.this, EditProfileFarmerActivity.class);
                intent.putExtra("name", txtNameFarmer.getText());
                intent.putExtra("mobile", txtMobileFarmer.getText());
                intent.putExtra("email", txtEmailFarmer.getText());
                intent.putExtra("location", txtLocationFarmer.getText());
                intent.putExtra("address", txtAddressFarmer.getText());
                intent.putExtra("gender", txtGenderFarmer.getText());
                startActivity(intent);
            }
        });

        //call function
        getData();

    }


    private void getData() {

        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //showing progress dialog
        loading = new ProgressDialog(ProfileFarmerActivity.this);
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
                        Toasty.error(ProfileFarmerActivity.this, "ইন্টারনেট কানেকশন নেই!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileFarmerActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {


        Log.d("RESPONSE", response);

        String name = "";
        String gender = "";
        String mobile = "";
        String email = "";
        String location = "";
        String address = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);
            JSONObject ProfileData = result.getJSONObject(0);

            name = ProfileData.getString(Constant.KEY_NAME);
            gender = ProfileData.getString(Constant.KEY_GENDER);
            mobile = ProfileData.getString(Constant.KEY_MOBILE);
            email = ProfileData.getString(Constant.KEY_EMAIL);
            location = ProfileData.getString(Constant.KEY_LOCATION);
            address = ProfileData.getString(Constant.KEY_ADDRESS);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);

        txtNameFarmer.setText(name);
        txtUserNameFarmer.setText(name);
        txtGenderFarmer.setText(gender);

//        if (gender.equals("Female"))
//        {
//            profilePic.setImageResource(R.drawable.girl);
//        }


        txtMobileFarmer.setText(mobile);
        txtEmailFarmer.setText(email);
        txtLocationFarmer.setText(location);
        txtAddressFarmer.setText(address);

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
