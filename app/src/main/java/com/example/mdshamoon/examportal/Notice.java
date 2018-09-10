package com.example.mdshamoon.examportal;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notice extends Fragment {

    TextView t1,t2,t3,t4;
    private static final String URL_FOR_LOGIN = "https://spidersdsc.com/news.php";
    public Notice() {

        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_notice, container, false);

        t1=view.findViewById(R.id.notice1);
        t2=view.findViewById(R.id.notice2);
        t3=view.findViewById(R.id.notice3);
        t4=view.findViewById(R.id.notice4);


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

                Toast.makeText(getActivity(),
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
        Appsingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);

        return view;


    }

}
