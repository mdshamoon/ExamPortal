package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class homeactivity extends AppCompatActivity {
    Button student,faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        getSupportActionBar().hide();



       student=findViewById(R.id.student);
       faculty=findViewById(R.id.faculty);
      student.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_left));
        faculty.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_right));
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homeactivity.this,faculty_login.class);
                startActivity(intent);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homeactivity.this,Student_login.class);
                startActivity(intent);
            }
        });
    }
}
