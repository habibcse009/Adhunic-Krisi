package com.project.adunik_krisi;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactAdminActivity extends AppCompatActivity {
    Button btnOk, btnClear, btnCall, btnMessage, btnEmail, btnShare;
    TextView txtText;
    EditText etxInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Contact us");

        btnEmail = findViewById(R.id.btn_email);
        btnMessage = findViewById(R.id.btn_sms);
        //btnShare = findViewById(R.id.btn_share);
        btnCall = findViewById(R.id.btn_call);
        btnOk = findViewById(R.id.btn1);
        btnClear = findViewById(R.id.btn2);
        txtText = findViewById(R.id.textView);
        etxInput = findViewById(R.id.editText);

        //Initialize font
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "aqua.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(), "SolaimanLipi.ttf");
        btnEmail.setTypeface(tf2);
        btnMessage.setTypeface(tf2);
        btnCall.setTypeface(tf2);
        btnClear.setTypeface(tf2);
        btnOk.setTypeface(tf2);
        txtText.setTypeface(tf3);
        etxInput.setTypeface(tf3);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etxInput.getText().toString();
                if (text.isEmpty()) {
                    etxInput.setError("Please enter text!");
                    etxInput.requestFocus();
                } else {
                    txtText.setText(text);
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtText.setText("Text");
                etxInput.setText("");
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String phoneNumber = "01625925155";
                String phoneNumber = "01883668454";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactMessage = etxInput.getText().toString();
                //String phoneNumber = "01625925155";
                String phoneNumber = "01883668454";

                if (contactMessage.isEmpty()) {
                    etxInput.setError("Please enter messages!");
                    etxInput.requestFocus();
                } else {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    //sendIntent.setData(Uri.parse("smsto:"+phoneNumber));
                    sendIntent.putExtra("sms_body", contactMessage);
                    sendIntent.putExtra("address", phoneNumber);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);
                }

            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailMessage = etxInput.getText().toString();
                /*String emailAddress = "contact@bloodbank24.cf";

                if (emailMessage.isEmpty()) {
                    etxInput.setError("Please enter your opinion!");
                    etxInput.requestFocus();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    // intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                    intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contact from Teleblood App");
                    intent.putExtra(Intent.EXTRA_TEXT, emailMessage);

                    startActivity(Intent.createChooser(intent, "Send Email"));
                }*/

                if (emailMessage.isEmpty()) {
                    etxInput.setError("Please enter your opinion!");
                    etxInput.requestFocus();
                } else {
                    //Log.i("Send email", emailMessage);
                    String[] TO = {"contact@bloodbank24.cf"};
                    //String[] CC = {"habibcse009@gmail.com"};
                    String[] CC = {"admin_opps@gmail.com"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    //emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/html");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact from OPPS App");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        finish();
                        Log.i("Finished sending email.", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ContactAdminActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    /*protected void sendEmail(){
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }*/
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


