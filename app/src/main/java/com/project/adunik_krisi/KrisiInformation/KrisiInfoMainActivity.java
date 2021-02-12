package com.project.adunik_krisi.KrisiInformation;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.project.adunik_krisi.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;

public class KrisiInfoMainActivity extends AppCompatActivity {
    RelativeLayout DhanerInfo, DhanerSession, DhanerProkriyakoron, DhanerJat, DhanerArea, DhanerUsefullLink;
    ShimmerTextView tv1;
    Shimmer shimmer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krisi_info_main);
        tv1 = (ShimmerTextView) findViewById(R.id.txtTitleKrisiInfo);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "AsapCondensed-Regular.ttf");
        //txtTitle.setTypeface(tf);
        tv1.setTypeface(tf);

        getSupportActionBar().setTitle("কৃষি সহায়ক তথ্য");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        shimmer1 = new Shimmer();
        shimmer1.start(tv1);


        DhanerInfo = findViewById(R.id.krisi_info_1);
        DhanerSession = findViewById(R.id.krisi_info_2);
        DhanerProkriyakoron = findViewById(R.id.krisi_info_3);
        DhanerJat = findViewById(R.id.krisi_info_4);
        DhanerArea = findViewById(R.id.krisi_info_5);
        DhanerUsefullLink = findViewById(R.id.krisi_info_6);

        DhanerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KrisiInfoMainActivity.this, DhanerInfoActivity.class);
                startActivity(intent);


            }
        });
        DhanerSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KrisiInfoMainActivity.this, DhanerSessionActivity.class);
                startActivity(intent);


            }
        });
        DhanerProkriyakoron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KrisiInfoMainActivity.this, DhanerProkriyakoronActivity.class);
                startActivity(intent);


            }
        });
        DhanerJat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KrisiInfoMainActivity.this, DhanerJatActivity.class);
                startActivity(intent);


            }
        });
        DhanerArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.brri.gov.bd/site/page/e2bdcb7f-90cc-4b50-974c-d76de49bd826/-"));
                startActivity(browserIntent);

            }
        });
        DhanerUsefullLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bangladesh.gov.bd/site/view/eservices/%E0%A6%95%E0%A7%83%E0%A6%B7%E0%A6%BF"));
                startActivity(browserIntent);

            }
        });

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
