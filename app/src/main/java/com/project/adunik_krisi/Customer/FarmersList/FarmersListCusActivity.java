package com.project.adunik_krisi.Customer.FarmersList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.adunik_krisi.Admin.AdminMainActivity;
import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class FarmersListCusActivity extends AppCompatActivity {
    ListView CustomList;
    Button btnSearch;
    EditText etxtSearch;
    private ImageView imgNoData;
    private ProgressDialog loading;
    int MAX_SIZE = 999;
    public String farmerCell[] = new String[MAX_SIZE];
    public String userID[] = new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_list_cus);

        CustomList = findViewById(R.id.listView_FarmerCUS);
        //  btnSearch = findViewById(R.id.btnSearch_user);
        etxtSearch = findViewById(R.id.etxt_search_FarmerCUS);

        //  imgNoData = findViewById(R.id.img_no_data);

        //   imgNoData.setVisibility(View.INVISIBLE);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("কৃষকের তালিকা সমূহ");


        //call function
        getData("");
        etxtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                searchData(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });


    }



    /*    btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = etxtSearch.getText().toString().trim();

                if (searchText.isEmpty()) {
                    Toasty.error(FarmersListCusActivity.this, "Please input customer name!", Toast.LENGTH_SHORT).show();
                } else {
                    getData(searchText);
                }
            }
        });
    }*/


    private void getData(String s) {

        String getSearchText = s;
        //showing progress dialog
        loading = new ProgressDialog(this);
        loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
        loading.show();

        if (!s.isEmpty()) {
            getSearchText = s;
        }


        String url = Constant.FARMER_LIST_URL + "?text=" + getSearchText;

        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(FarmersListCusActivity.this, "ইন্টারনেট কানেকশন নেই!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void searchData(String text) {


        String URL = Constant.FARMER_LIST_URL + "?text=" + text;

        //  Log.d("url", URL);

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(FarmersListCusActivity.this, "ইন্টারনেট কানেকশন নেই!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(FarmersListCusActivity.this);
        requestQueue.add(stringRequest);

    }


    private void showJSON(String response) {


        Log.d("Response 2", response);

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);

            if (result.length() == 0) {
                Toasty.error(FarmersListCusActivity.this, "কোন কৃষকের তথ্য পাওয়া যায় নী!", Toast.LENGTH_SHORT).show();
                //    imgNoData.setImageResource(R.drawable.nodatafound);
                //    imgNoData.setVisibility(View.VISIBLE);

            } else {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String id = jo.getString(Constant.KEY_ID);
                    String name = jo.getString(Constant.KEY_NAME);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String location = jo.getString(Constant.KEY_LOCATION);
                    String email = jo.getString(Constant.KEY_EMAIL);
                    String address = jo.getString(Constant.KEY_ADDRESS);
                    String mobile = jo.getString(Constant.KEY_MOBILE);

                    farmerCell[i] = mobile;
                    userID[i] = id;

                    HashMap<String, String> user_msg = new HashMap<>();
                    user_msg.put(Constant.KEY_NAME, name);
                    user_msg.put(Constant.KEY_GENDER, "লিঙ্গ : " + gender);
                    user_msg.put(Constant.KEY_LOCATION, "বিভাগ : " + location);
                    user_msg.put(Constant.KEY_ADDRESS, "ঠিকানা : " + address);
                    user_msg.put(Constant.KEY_EMAIL, "ইমেইল : " + email);
                    user_msg.put(Constant.KEY_MOBILE, "মোবাইল নাম্বার : " + mobile);


                    list.add(user_msg);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                FarmersListCusActivity.this, list, R.layout.farmer_list_item,
                new String[]{Constant.KEY_NAME, Constant.KEY_GENDER, Constant.KEY_MOBILE, Constant.KEY_LOCATION, Constant.KEY_EMAIL, Constant.KEY_ADDRESS,},
                new int[]{R.id.user_name, R.id.user_gender, R.id.user_mobile, R.id.user_email, R.id.user_division, R.id.user_address,});
        CustomList.setAdapter(adapter);

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {


                new AlertDialog.Builder(FarmersListCusActivity.this)
                        //.setMessage("Want to Call this Shop Now?")
                        .setMessage("এই কৃষকের ক্ষেত্রে নিচের যেকোন অপশন নির্বাচন করুন...")
                        .setCancelable(false)
                        .setPositiveButton("কল করুন", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + farmerCell[position]));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("তার সকল পণ্য দেখুন", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(FarmersListCusActivity.this, FarmerProductsActivity.class);
                                //intent.setData(Uri.parse("cell" + shopCell[position]));
                                String farmer_cell = farmerCell[position];
                                intent.putExtra("getcell", farmer_cell);

                                //intent.putExtra("type", "Shop");
                                startActivity(intent);


                            }
                        })

                        .show();
            }
        });

    }

    //update contact method
    public void deleteUser(final String id) {


        loading = new ProgressDialog(this);
        // loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("একাউন্ট ডিলিট");
        loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
        loading.show();

        String URL = Constant.DELETE_FARMER_URL;


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //for track response in logcat
                        Log.d("RESPONSE", response);
                        // Log.d("RESPONSE", userCell);

                        String getResponse = response.trim();

                        //If we are getting success from server
                        if (getResponse.equals("success")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(FarmersListCusActivity.this, AdminMainActivity.class);
                            Toasty.success(FarmersListCusActivity.this, " একাউন্ট ডিলিট সম্পন্ন!", Toast.LENGTH_SHORT).show();


                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (getResponse.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity

                            //  Intent intent = new Intent(UserActivity.this, FarmerOrderActivity.class);
                            Toast.makeText(FarmersListCusActivity.this, "একাউন্ট ডিলিট ব্যার্থ!", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);

                        } else {

                            loading.dismiss();
                            Toast.makeText(FarmersListCusActivity.this, "নেটওয়ার্কের সমস্যা", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(FarmersListCusActivity.this, "ইন্টারনেট কানেকশন নেই অথবা \nএখানে কোন একটা সমস্যা আছে !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEY_ID, id);
                params.put(Constant.KEY_STATUS, "1"); //1 for approved


                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(FarmersListCusActivity.this);
        requestQueue.add(stringRequest);


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
