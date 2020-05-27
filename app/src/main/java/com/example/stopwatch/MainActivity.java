package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    ImageButton buttonStart,buttonStop;

    private boolean isResume;
    Handler handler;

    long tMilliSec,tStart,tBuff,tUpdate = 0L;
    int sec,min,milliSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);

        buttonStart = findViewById(R.id.btn_play);
        buttonStop = findViewById(R.id.btn_stop);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    buttonStop.setVisibility(View.GONE);
                    buttonStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause));
                } else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    buttonStop.setVisibility(View.VISIBLE);
                    buttonStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play));
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    buttonStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play));
                    tBuff = 0L;
                    tMilliSec = 0L;
                    tStart = 0L;
                    tUpdate = 0L;
                    min = 0;
                    sec = 0;
                    milliSec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }


  public  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec % 60;
            milliSec = (int) (tUpdate%100);

            chronometer.setText(String.format("%02d",min) +":"+String.format("%02d",sec)+":"+String.format("%02d",milliSec));
            handler.postDelayed(this,60);
        }
    };

    }