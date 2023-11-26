package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjectiveTesting extends AppCompatActivity {

    RadioGroup rg1,rg2;
    String id;

   static String [] answered;

    ArrayList ques=new ArrayList();
    ArrayList op1=new ArrayList();
    ArrayList op2=new ArrayList();
    ArrayList op3=new ArrayList();
    ArrayList op4=new ArrayList();
    ArrayList ans=new ArrayList();

    RecyclerView recyclerView;
    TestAdapter testAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);

        rg1=findViewById(R.id.radioGroup);
        rg2=findViewById(R.id.radioGroup1);

        recyclerView=findViewById(R.id.rec);
      id=getIntent().getStringExtra("id");
      getData(id);
    }

    private void getData(String id) {

        ques.clear();
        op1.clear();
        op2.clear();
        op3.clear();
        op4.clear();
        ans.clear();

        final ProgressDialog pPd = new ProgressDialog(ObjectiveTesting.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wizzie.tech/learn4growth/gettest.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(ObjectiveTesting.this, "No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                                        ques.add(jsonObject.getString("question").trim());
                                        op1.add(jsonObject.getString("option_1").trim());
                                        op2.add(jsonObject.getString("option_2").trim());
                                        op3.add(jsonObject.getString("option_3").trim());
                                        op4.add(jsonObject.getString("option_4").trim());
                                        ans.add(jsonObject.getString("answer").trim());

                                    }

                                    answered=new String[ques.size()];
                                    for(int i=0;i<answered.length;i++){
                                        answered[i]="2";
                                    }

                                    testAdapter = new TestAdapter(ObjectiveTesting.this,ques,op1,op2,op3,op4,ans,answered);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(testAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void sub(View view) {

        ArrayList ttt=new ArrayList(Arrays.asList(answered));
        int marks=0;
        for(int i=0;i<ttt.size();i++){
            marks=marks+Integer.parseInt(ttt.get(i).toString().trim());
        }
        if(ttt.contains("2")){
            Toast.makeText(this, "Please Select All", Toast.LENGTH_SHORT).show();
        }
        else {

            if(marks==2){
                Toast.makeText(this, "Great Job", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Scan Again", Toast.LENGTH_SHORT).show();

            }

        }

    }



}