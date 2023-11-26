package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

public class ViewProblems extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminDataAdapter adminDataadapter;

    ArrayList id=new ArrayList();
    ArrayList prob=new ArrayList();
    ArrayList url=new ArrayList();
    ArrayList iid=new ArrayList();
    ArrayList solution=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problems);

        recyclerView=findViewById(R.id.rec);
        getData();


    }

    private void getData() {
        final ProgressDialog pPd = new ProgressDialog(ViewProblems.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wizzie.tech/learn4growth/getData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(ViewProblems.this, "No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        id.add(jsonObject.getString("number").trim());
                                        prob.add(jsonObject.getString("problem").trim());
                                        url.add(jsonObject.getString("url").trim());
                                        iid.add(jsonObject.getString("id").trim());
                                        solution.add(jsonObject.getString("solution").trim());

                                    }

                                    adminDataadapter = new AdminDataAdapter(ViewProblems.this,id,prob,url,iid,solution);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(adminDataadapter);

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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}