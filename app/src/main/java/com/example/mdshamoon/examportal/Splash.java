package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mdshamoon.examportal.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                Intent i = new Intent(Splash.this,homeactivity.class);
                startActivity(i);
                finish();

            }

        };

        new Timer().schedule(timerTask,2000);

    }
}
