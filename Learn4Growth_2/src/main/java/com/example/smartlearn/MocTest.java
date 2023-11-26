package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

public class MocTest extends AppCompatActivity {


    private static final String URL="http://wizzie.tech/learn4growth/gettest.php";

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
        setContentView(R.layout.activity_moc_test);

        recyclerView=findViewById(R.id.rec);

        getData();

    }

    private void getData() {
        final ProgressDialog pPd = new ProgressDialog(MocTest.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(MocTest.this, "No Data Found", Toast.LENGTH_LONG).show();
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
                                    /*testAdapter = new TestAdapter(MocTest.this,ques,op1,op2,op3,op4,ans);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(testAdapter);*/



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