package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String URL_FOR_LOGIN = "https://spidersdsc.com/news.php";
    TextView t1,t2,t3,t4,user,user1,rol,s1,s2,s3,s4,s5,s6;
    LinearLayout l1,text1;
    ViewGroup parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        t1=findViewById(R.id.notice1);
        t2=findViewById(R.id.notice2);
        t3=findViewById(R.id.notice3);
        t4=findViewById(R.id.notice4);
        l1=findViewById(R.id.linear1);
        s1=findViewById(R.id.subject1);
        s2=findViewById(R.id.subject2);
        s3=findViewById(R.id.subject3);
        s4=findViewById(R.id.subject4);
        s5=findViewById(R.id.subject5);
        s6=findViewById(R.id.subject6);
        text1=findViewById(R.id.text12);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("ExamPortal");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        String name=getIntent().getExtras().getString("username");
        String roll=getIntent().getExtras().getString("roll");
        user=findViewById(R.id.name);
        rol=headerView.findViewById(R.id.rol);

        user1=headerView.findViewById(R.id.name1);

        user1.setText(name);
        rol.setText(roll);
        parent= (ViewGroup) l1.getParent();




        String cancel_req_tag = "fetch";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONArray arr=new JSONArray(response);



                        String news = arr.getString(0);
                        String news1 = arr.getString(1);
                        String news2 = arr.getString(2);
                        String news3 = arr.getString(3);
                        t1.setText(news);
                        t2.setText(news1);
                        t3.setText(news2);
                        t4.setText(news3);

                        // Launch User activity



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }

        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        }



        ;
        // Adding request to request queue
        Appsingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sessional) {

            parent.removeView(l1);
            text1.setVisibility(View.VISIBLE);





            // Handle the camera action
        } else if (id == R.id.attendance) {

        } else if (id == R.id.debarred) {

        }

        else if (id == R.id.profile) {

        } else if (id == R.id.share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
