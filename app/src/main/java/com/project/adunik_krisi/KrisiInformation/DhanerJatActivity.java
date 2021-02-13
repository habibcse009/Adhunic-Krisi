package com.project.adunik_krisi.KrisiInformation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;

import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.project.adunik_krisi.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;

public class DhanerJatActivity extends AppCompatActivity {
    ShimmerTextView tv1;
    Shimmer shimmer1;
    AdaptiveTableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhaner_jat);
        getSupportActionBar().setTitle("উন্নতজাতের ধানসমূহ ");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        tv1 = (ShimmerTextView) findViewById(R.id.txtTitleDhanerJatInfo);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "AsapCondensed-Regular.ttf");
        //txtTitle.setTypeface(tf);
        tv1.setTypeface(tf);
        shimmer1 = new Shimmer();
        shimmer1.start(tv1);



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
