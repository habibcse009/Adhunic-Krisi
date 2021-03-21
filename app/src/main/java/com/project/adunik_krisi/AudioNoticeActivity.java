package com.project.adunik_krisi;

import android.graphics.Typeface;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;

public class AudioNoticeActivity extends AppCompatActivity {
    ShimmerTextView tv1;
    Shimmer shimmer1;
    Button btnPlay1, btnPause1, btnPlay2, btnPause2, btnPlay3, btnPause3, btnPlay4, btnPause4, btnPlay5, btnPause5,
            btnPlay6, btnPause6, btnStop;

    MediaPlayer player1, player2, player3, player4, player5, player6;
    //initial playing set as false
    boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_notice);

        tv1 = (ShimmerTextView) findViewById(R.id.txtTitleKrisiInfo);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "AsapCondensed-Regular.ttf");
        //txtTitle.setTypeface(tf);
        tv1.setTypeface(tf);

        getSupportActionBar().setTitle("কৃষি সহায়ক অডিও নোটিশ সমূহ");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        shimmer1 = new Shimmer();
        shimmer1.start(tv1);

        btnPlay1 = (Button) findViewById(R.id.btnPlay1);
        btnPlay2 = (Button) findViewById(R.id.btnPlay2);
        btnPlay3 = (Button) findViewById(R.id.btnPlay3);
        btnPlay4 = (Button) findViewById(R.id.btnPlay4);
        btnPlay5 = (Button) findViewById(R.id.btnPlay5);
        btnPlay6 = (Button) findViewById(R.id.btnPlay6);

        btnPause1 = (Button) findViewById(R.id.btnPause1);
        btnPause2 = (Button) findViewById(R.id.btnPause2);
        btnPause3 = (Button) findViewById(R.id.btnPause3);
        btnPause4 = (Button) findViewById(R.id.btnPause4);
        btnPause5 = (Button) findViewById(R.id.btnPause5);
        btnPause6 = (Button) findViewById(R.id.btnPause6);

        // btnStop =(Button)findViewById(R.id.btnStop);

        player1 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_one);
        player2 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_two);
        player3 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_three);
        player4 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_four);
        player5 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_five);
        player6 = MediaPlayer.create(AudioNoticeActivity.this, R.raw.audio_six);

        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*player2.stop();
                player3.stop();
                player4.stop();
                player5.stop();
                player6.stop();
                */
              //  playing = false;

                if (!playing) {
                    player1.start();
                    playing = true;
                }

            }
        });

        btnPause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player1.pause();
                    playing = false;
                }

            }
        });
//two
        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*player1.stop();
                player3.stop();
                player4.stop();
                player5.stop();
                player6.stop();
                */
            //    playing = false;

                if (!playing) {
                    player2.start();
                    playing = true;
                }

            }
        });

        btnPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player2.pause();
                    playing = false;
                }

            }
        });

        //three
        btnPlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  player2.stop();
                player1.stop();
                player4.stop();
                player5.stop();
                player6.stop();
             */
              //  playing = false;

                if (!playing) {
                    player3.start();
                    playing = true;
                }

            }
        });

        btnPause3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player3.pause();
                    playing = false;
                }

            }
        });
        //four
        btnPlay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*player2.pause();
                player3.pause();
                player1.pause();
                player5.pause();
                player6.pause();
                */
              //  playing = false;

                if (!playing) {
                    player4.start();
                    playing = true;
                }

            }
        });

        btnPause4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player4.pause();
                    playing = false;
                }

            }
        });

        //five
        btnPlay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* player2.pause();
                player3.pause();
                player4.pause();
                player1.pause();
                player6.pause();
               */
               // playing = false;

                if (!playing) {
                    player5.start();
                    playing = true;
                }

            }
        });

        btnPause5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player5.pause();
                    playing = false;
                }

            }
        });

        //six
        btnPlay6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   player2.pause();
                player3.pause();
                player4.pause();
                player5.pause();
                player1.pause();
             */
              //  playing = false;

                if (!playing) {
                    player6.start();
                    playing = true;
                }

            }
        });

        btnPause6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    player6.pause();
                    playing = false;
                }

            }
        });
    }
}