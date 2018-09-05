package com.example.mdshamoon.examportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_login extends AppCompatActivity {
    Button fregister;
    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "http://thdcihet.in/loginn.php";
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private EditText rollno, password;
    private Button btnlogin;
    public static final String MyPREFERENCES = "Login" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        fregister=findViewById(R.id.btn_signup);
        fregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Student_login.this,Student_register.class);
                startActivity(intent);
            }
        });

        rollno=findViewById(R.id.rollno);
        password=findViewById(R.id.input_password);
        btnlogin=findViewById(R.id.btn_signin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(!isUserLogedOut())
        {
           rollno.setText(sharedPreferences.getString("rollno",""));

           password.setText(sharedPreferences.getString("password",""));
            loginUser(rollno.getText().toString(),password.getText().toString());

        }





        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("rollno", rollno.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();
                editor.commit();
                loginUser(rollno.getText().toString(),password.getText().toString());
            }
        });

    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        // Launch User activity
                        Intent intent = new Intent(
                                Student_login.this,
                                Student_home.class);
                        intent.putExtra("username", user);
                        intent.putExtra("roll", email);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog( );
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        Appsingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("rollno", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }
}

