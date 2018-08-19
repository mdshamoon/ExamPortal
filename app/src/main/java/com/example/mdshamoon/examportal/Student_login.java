package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Student_login extends AppCompatActivity {
    Button fregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        fregister=findViewById(R.id.btn_signup);
        fregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Student_login.this,faculty_register.class);
                startActivity(intent);
            }
        });
    }
}
