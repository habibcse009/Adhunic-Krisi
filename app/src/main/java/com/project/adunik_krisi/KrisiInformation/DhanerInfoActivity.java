package com.project.adunik_krisi.KrisiInformation;

import android.os.Bundle;
import android.view.MenuItem;

import com.project.adunik_krisi.R;

import androidx.appcompat.app.AppCompatActivity;

public class DhanerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhaner_info);

        getSupportActionBar().setTitle("ধানের বিবরণ");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

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
