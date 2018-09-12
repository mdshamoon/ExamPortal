package com.example.mdshamoon.examportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Faculty_home extends AppCompatActivity {
Spinner year,lecture,subj;
ListView myList;
Button getChoice;
    ArrayAdapter<String> adapter2;
    String selected="";
    private static final String TAG = "List";


    private static final String URL_FOR_LOGIN = "https://spidersdsc.com/list.php";
    private static final String URL_FOR_LOGI = "https://spidersdsc.com/update.php";

    ArrayList<String> list=new ArrayList<String>();
String year_content[]={"year","1","2","3","4"};
String lecture_content[]={"branch","cse","civil","ece"};
String subj_content[]={"subjects","DA","advance java","networks","graphics","ppl","simulation"};
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
  getChoice=findViewById(R.id.take);
        year=findViewById(R.id.year);
        lecture=findViewById(R.id.lecture);
        subj=findViewById(R.id.subj);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,year_content);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lecture_content);
        ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subj_content);
        button=findViewById(R.id.attend);
        year.setAdapter(adapter);
        lecture.setAdapter(adapter1);
        subj.setAdapter(adapter3);
        myList = (ListView)findViewById(R.id.list);

        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter2 = new ArrayAdapter<String>(Faculty_home.this, android.R.layout.simple_list_item_multiple_choice, list);


        button.setOnClickListener(new Button.OnClickListener(){



            @Override

            public void onClick(View v) {

adapter2.clear();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        URL_FOR_LOGIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray jObj = new JSONArray(response);

                            for(int i=0;i<jObj.length();i++)
                            {
                               list.add(jObj.getString(i)) ;
                            }

                            myList.setAdapter(adapter2);





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

                    }
                });
                // Adding request to request queue
                Appsingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"cancel");



//
//


            }
//
//                Toast.makeText(Faculty_home.this,
//
//                        selected,
//
//                        Toast.LENGTH_LONG).show();

            });

        getChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selected="";
                final int cntChoice = myList.getCount();

                SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)) {

                        selected += myList.getItemAtPosition(i).toString()+" ";

                        }

                }


                StringRequest strReq1 = new StringRequest(Request.Method.POST,
                        URL_FOR_LOGI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Boolean error=jsonObject.getBoolean("error");
                            if(!error)
                            {
                                Toast.makeText(getApplicationContext(),"Succesful submission",Toast.LENGTH_LONG).show();
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

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to login url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("names", selected);
                        params.put("subject",subj.getSelectedItem().toString());

                        return params;
                    }};

                // Adding request to request queue
                Appsingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq1,"cancel");



            }
        });





    }
}
