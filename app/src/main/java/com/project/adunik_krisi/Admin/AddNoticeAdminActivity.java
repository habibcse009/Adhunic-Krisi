package com.project.adunik_krisi.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class AddNoticeAdminActivity extends AppCompatActivity {

    EditText etxtTitle, etxtDescription;
    TextView txtSubmit;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice_admin);

        etxtTitle = findViewById(R.id.txt_editNoticeTitleAdmn);
        etxtDescription = findViewById(R.id.txt_editNoticeDetailsAdmn);
        txtSubmit = findViewById(R.id.btn_NoticeSubmitAdmn);


        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("এডমিন নোটিশ প্রদান");

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeAdminActivity.this);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setMessage("নোটিশ প্রকাশে নিশ্চিত?")
                        .setCancelable(false)
                        .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                publish_news(); //call publish_news function
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


    //Save contact method
    public void publish_news() {

        final String title = etxtTitle.getText().toString();
        final String description = etxtDescription.getText().toString();


        if (title.isEmpty()) {

            etxtTitle.setError("এখানে নোটিশের নাম লিখুন!");
            etxtTitle.requestFocus();
        } else if (description.isEmpty()) {
            etxtDescription.setError("এখানে নোটিশের বিবরণ লিখুন");
            etxtDescription.requestFocus();

        } else {
            loading = new ProgressDialog(this);
            loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
            loading.show();

            String URL = Constant.PUBLISH_NOTICE_URL;


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

                                Intent intent = new Intent(AddNoticeAdminActivity.this, AdminMainActivity.class);
                                Toasty.success(AddNoticeAdminActivity.this, "সঠিকভাবে হালনাগাদ হয়েছে!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                //Intent intent = new Intent(AddNoticeAdminActivity.this, HomeActivity.class);
                                Toasty.success(AddNoticeAdminActivity.this, " হালনাগাদ ব্যার্থ !", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toast.makeText(AddNoticeAdminActivity.this, "নেটওয়ার্কের সমস্যা", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toast.makeText(AddNoticeAdminActivity.this, "ইন্টারনেট কানেকশন নেই অথবা \nএখানে কোন একটা সমস্যা আছে !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request


                    params.put(Constant.KEY_TITLE, title);
                    params.put(Constant.KEY_DESCRIPTION, description);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(AddNoticeAdminActivity.this);
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
