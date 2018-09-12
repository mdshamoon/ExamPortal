package com.example.mdshamoon.examportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.RecoverySystem;
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

public class faculty_register extends AppCompatActivity {

    ProgressDialog progressDialog;
    private static final String URL_FOR_REGISTRATION = "https://spidersdsc.com/facultyresponse.php";
    private static final String TAG = "RegisterActivity";
    private EditText firstname,lastname,userid,password,cpassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);
        progressDialog = new ProgressDialog(this);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        userid=findViewById(R.id.userid);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.confirm_password);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }

    public void Registration() {

        RegisterUser(firstname.getText().toString(), lastname.getText().toString()
                , password.getText().toString(), userid.getText().toString());

    }


    private void RegisterUser(final String firstname, final String lastname, final String Password,final String userid) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user + ", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                faculty_register.this,
                                faculty_login.class);
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("password", Password);
                params.put("userid", userid);

                return params;
            }
        };
        // Adding request to request queue
        Appsingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public void validate()
    {
        boolean valid=true;

        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String user=userid.getText().toString();
        String pass = password.getText().toString();
        String confirm=cpassword.getText().toString();
        if (fname.isEmpty()) {
            firstname.setError("enter a valid first name");
            valid = false;
        } else {
            firstname.setError(null);
        }
        if (lname.isEmpty()) {
            lastname.setError("enter a valid last name");
            valid = false;
        } else {
            lastname.setError(null);
        }
        if (pass.isEmpty()) {
            password.setError("enter a valid password");
            valid = false;
        } else {
            password.setError(null);
        }
        if (confirm.isEmpty()|| !confirm.equals(pass)) {
            cpassword.setError("password does not match");
            valid = false;
        } else {
            cpassword.setError(null);
        }
        if (user.isEmpty()) {
            userid.setError("enter a valid userid");
            valid = false;
        } else {
            userid.setError(null);
        }

        if (valid == true) {
            Registration();
        }
    }
}
