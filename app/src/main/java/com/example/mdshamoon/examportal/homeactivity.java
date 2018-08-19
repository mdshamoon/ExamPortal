package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeactivity extends AppCompatActivity {
    Button flogin,fregister,slogin,sregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        getSupportActionBar().hide();
        flogin=findViewById(R.id.LOGIN);
        fregister=findViewById(R.id.register);
        slogin=findViewById(R.id.LOGIN1);
        sregister=findViewById(R.id.register1);
        flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homeactivity.this,faculty_login.class);
                startActivity(intent);
            }
        });
    }
}
