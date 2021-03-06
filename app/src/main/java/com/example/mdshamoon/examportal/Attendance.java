package com.example.mdshamoon.examportal;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Attendance extends Fragment {
    TextView s1,s2,s3,s4,s5,s6;
    ProgressDialog progressDialog;
    String roll;
    Button find;
    private static final String URL_FOR_FETCH = "https://spidersdsc.com/attendance.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_attendance, container, false);


        s1=view.findViewById(R.id.subject1);
        s2=view.findViewById(R.id.subject2);
        s3=view.findViewById(R.id.subject3);
        s4=view.findViewById(R.id.subject4);
        s5=view.findViewById(R.id.subject5);
        s6=view.findViewById(R.id.subject6);
        find=view.findViewById(R.id.search);
        roll = getActivity().getIntent().getExtras().getString("roll");
        progressDialog=new ProgressDialog(getActivity());
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMarks();
            }
        });
        return view;
    }

    public void fetchMarks(){
        String cancel_req_tag1 = "fetch";
        progressDialog.setMessage("fetching");
        progressDialog.show();
        StringRequest strReq1 = new StringRequest(Request.Method.POST,
                URL_FOR_FETCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray arr=jsonObject.getJSONArray("subject");
                    JSONArray arr1=jsonObject.getJSONArray("attendance");
                    JSONArray arr2=jsonObject.getJSONArray("total");


                    String sub1 = arr.getString(0)+"\n"+arr1.getString(0)+"/"+arr2.getString(0);
                    String sub2 = arr.getString(1)+"\n"+arr1.getString(1)+"/"+arr2.getString(1);
                    String sub3 = arr.getString(2)+"\n"+arr1.getString(2)+"/"+arr2.getString(2);
                    String sub4 = arr.getString(3)+"\n"+arr1.getString(3)+"/"+arr2.getString(3);
                    String sub5 = arr.getString(4)+"\n"+arr1.getString(4)+"/"+arr2.getString(4);
                    String sub6 = arr.getString(5)+"\n"+arr1.getString(5)+"/"+arr2.getString(5);
                    s1.setText(sub1);
                    s2.setText(sub2);
                    s3.setText(sub3);
                    s4.setText(sub4);
                    s5.setText(sub5);
                    s6.setText(sub6);

                    // Launch User activity



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }

        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("rollno",roll);
                return params;
            }
        }



                ;
        // Adding request to request queue
        Appsingleton.getInstance(getActivity()).addToRequestQueue(strReq1,cancel_req_tag1);
    }


}
