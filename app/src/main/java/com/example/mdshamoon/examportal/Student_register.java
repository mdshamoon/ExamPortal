package com.example.mdshamoon.examportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://thdcihet.in/response.php";
    ProgressDialog progressDialog;
    String branchcontent[]={"choose your branch","C.S.E","E.C.E","Mechanical","Electrical","Civil"};
    String batchcontent[]={"choose your batch","2015","2016","2017","2018","2019","2020","2021","2022"};
    private EditText FirstName, Lastname, Password, ConfirmPassword, RollNo;
    Spinner Branch,Batch;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        FirstName = findViewById(R.id.firstname);
        Lastname = findViewById(R.id.lastname);
        Password = findViewById(R.id.input_password);
        ConfirmPassword = findViewById(R.id.confirm_password);
        Branch = findViewById(R.id.branch);
        RollNo = findViewById(R.id.rollno);
        Batch = findViewById(R.id.batch);
        Register = findViewById(R.id.register);
        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,branchcontent);
        ArrayAdapter<String> badapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,batchcontent);
        Branch.setAdapter(adapter);
        Batch.setAdapter(badapter);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            validate();

            }
        });
    }

    public void Registration() {
        RegisterUser(FirstName.getText().toString(), Lastname.getText().toString(), Branch.getSelectedItem().toString()
                , RollNo.getText().toString(), Password.getText().toString(), ConfirmPassword.getText().toString(), Batch.getSelectedItem().toString());

    }

    private void RegisterUser(final String firstname, final String lastname, final String Branch,
                              final String rollno, final String Password, final String Confirmpass, final String batch) {
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
                                Student_register.this,
                                Student_login.class);
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
                params.put("confirmpassword", Confirmpass);
                params.put("batch", batch);
                params.put("branch", Branch);
                params.put("rollno", rollno);

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

    public void validate() {
        boolean valid = true;


        // get fields information

        String fname = FirstName.getText().toString();
        String lname = Lastname.getText().toString();
        String password = Password.getText().toString();
        String cpassword = ConfirmPassword.getText().toString();
        String branch = Branch.getSelectedItem().toString();
        String rollno = RollNo.getText().toString();
        String batch = Batch.getSelectedItem().toString();
        if (fname.isEmpty()) {
            FirstName.setError("enter a valid first name");
            valid = false;
        } else {
            FirstName.setError(null);
        }
        if (lname.isEmpty()) {
            Lastname.setError("enter a valid last name");
            valid = false;
        } else {
            Lastname.setError(null);
        }
        if (password.isEmpty()) {
            Password.setError("enter a valid password");
            valid = false;
        } else {
            Password.setError(null);
        }

        if (cpassword.isEmpty() || !cpassword.equals(password)) {
            ConfirmPassword.setError("password does not match");
            valid = false;
        } else {
            ConfirmPassword.setError(null);
        }
        if (branch.isEmpty()) {
            valid = false;
        }
        if (rollno.isEmpty() || rollno.length()!=12) {
            RollNo.setError("enter a valid roll number");
            valid = false;
        } else {
            RollNo.setError(null);
        }
        if (batch.isEmpty()) {
            valid = false;
        }
        if (valid == true) {
            Registration();
        }
    }
}
