package com.project.adunik_krisi.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.adunik_krisi.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().setTitle("প্রধান পাতা (এডমিন প্যানেল)");

    }
}