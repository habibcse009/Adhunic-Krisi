package com.project.adunik_krisi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class ViewAdminNoticesActivity extends AppCompatActivity {
    ListView CustomList;
    Button btnSearch;
    EditText etxtSearch;
    //    private ImageView imgNoData;
    private ProgressDialog loading;
    int MAX_SIZE = 999;
    public String cusCell[] = new String[MAX_SIZE];
    public String userID[] = new String[MAX_SIZE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_notices);

        CustomList = findViewById(R.id.listView_ViewNotice);
        //  btnSearch = findViewById(R.id.btnSearch_user);
        etxtSearch = findViewById(R.id.etxt_search_ViewNotice);

        //  imgNoData = findViewById(R.id.img_no_data);

        //   imgNoData.setVisibility(View.INVISIBLE);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("নোটিশের তালিকা সমূহ");


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


    private void getData(String s) {

        String getSearchText = s;
        //showing progress dialog
        loading = new ProgressDialog(this);
        loading.setMessage("অপেক্ষা করুন, লোডিং হচ্ছে...");
        loading.show();

        if (!s.isEmpty()) {
            getSearchText = s;
        }


        String url = Constant.NOTICE_LIST_URL + "?text=" + getSearchText;

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
                        Toasty.error(ViewAdminNoticesActivity.this, "ইন্টারনেট কানেকশন নেই!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void searchData(String text) {


        String URL = Constant.NOTICE_LIST_URL + "?text=" + text;

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


                        Toast.makeText(ViewAdminNoticesActivity.this, "ইন্টারনেট কানেকশন নেই!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewAdminNoticesActivity.this);
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
                Toasty.error(ViewAdminNoticesActivity.this, "কোন নোটিশের তথ্য পাওয়া যায় নী!", Toast.LENGTH_SHORT).show();

            } else {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String id = jo.getString(Constant.KEY_ID);
                    String title = jo.getString(Constant.KEY_TITLE);
                    String description = jo.getString(Constant.KEY_DESCRIPTION);
                    userID[i] = id;

                    HashMap<String, String> user_msg = new HashMap<>();
                    user_msg.put(Constant.KEY_TITLE, title);
                    user_msg.put(Constant.KEY_DESCRIPTION, "নোটিশের বিবরণ : " + description);


                    list.add(user_msg);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAdminNoticesActivity.this, list, R.layout.notice_list_item,
                new String[]{Constant.KEY_TITLE, Constant.KEY_DESCRIPTION},
                new int[]{R.id.NoticeList_title, R.id.NoticeList_descrition});
        CustomList.setAdapter(adapter);

       /* CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {


                new AlertDialog.Builder(ViewAdminNoticesActivity.this)
                        //.setMessage("Want to Call this Shop Now?")
                        .setMessage("এই ক্রেতার ক্ষেত্রে নিচের যেকোন অপশন নির্বাচন করুন...")
                        .setCancelable(false)
                        .setPositiveButton("কল করুন", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + cusCell[position]));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("একাউন্ট ডিলিট করুন", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //write here
                                new androidx.appcompat.app.AlertDialog.Builder(ViewAdminNoticesActivity.this)
                                        .setMessage("আপনি কি একাউন্ট ডিলিট করতে নিশ্চিত?")
                                        .setCancelable(false)
                                        .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {


                                                // Perform Your Task Here--When Yes Is Pressed.
                                                deleteUser(userID[position]);
                                                dialog.cancel();


                                            }
                                        })


                                        .setNegativeButton("না", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Perform Your Task Here--When No is pressed
                                                dialog.cancel();
                                            }
                                        }).show();


                                //end

                            }
                        })

                        .show();
            }
        });
*/
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


