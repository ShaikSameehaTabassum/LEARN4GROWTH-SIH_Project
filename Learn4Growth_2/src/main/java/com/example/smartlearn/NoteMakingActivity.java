package com.example.smartlearn;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NoteMakingActivity extends AppCompatActivity {

    RecyclerView uuu;
    NoteAdapter uchalansAdapter;
    ArrayList list1=new ArrayList();
    ArrayList list2=new ArrayList();

    EditText date;
    DatePickerDialog datePickerDialog;

    Button crd,but_u,but_c;
    LinearLayout l1,l2;

    EditText amountEt, noteEt, nameEt, upiIdEt;

    EditText card,cvv,amt;
    private static final String URL="http://wizzie.tech/learn4growth/note.php";
    private static final String URL1="http://wizzie.tech/learn4growth/getnotes.php";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        date = findViewById(R.id.textInputEditTextDob);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(NoteMakingActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {

                        date.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }

                    @SuppressLint("SetTextI18n")
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        fun();
        listView=findViewById(R.id.lst);

        //upi=findViewById(R.id.b1);
        crd=findViewById(R.id.b2);
        l1=findViewById(R.id.llt);
        l2=findViewById(R.id.llt1);

        but_u=findViewById(R.id.u);
        but_c=findViewById(R.id.pay);

        amountEt = findViewById(R.id.idEdtAmount);
        noteEt = findViewById(R.id.idEdtDescription);
        nameEt = findViewById(R.id.idEdtName);
        upiIdEt = findViewById(R.id.idEdtUpi);

        card=findViewById(R.id.crd);
        cvv=findViewById(R.id.cvv);
        amt=findViewById(R.id.amt);

        crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.GONE);
                if(l2.getVisibility()== View.GONE){
                    l2.setVisibility(View.VISIBLE);
                }else {
                    l1.setVisibility(View.GONE);
                }
            }
        });

        but_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(card.getText().toString().trim().isEmpty()){
                    card.setError("Enter Note");
                }
                else if(date.getText().toString().trim().isEmpty()){
                    date.setError("Enter Date");
                }

                else {
                    final ProgressDialog pdd = new ProgressDialog(NoteMakingActivity.this);
                    pdd.setMessage("Please Wait, While We Add");
                    pdd.setCancelable(false);
                    pdd.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Toast.makeText(NoteMakingActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                                    pdd.dismiss();
                                    // System.out.println(response);
                                    JSONObject object = null;
                                    try {
                                        object = new JSONObject(response);
                                        if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {
                                            Toast.makeText(NoteMakingActivity.this, "Note Added Success", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override

                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("note", card.getText().toString().trim());
                            params.put("date", date.getText().toString().trim());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }

            }
        });

    }

    private void fun() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                list1.add(jsonObject.getString("note"));
                                list2.add(jsonObject.getString("date"));

                            }
                            uchalansAdapter=new NoteAdapter(NoteMakingActivity.this,list1,list2);
                            uuu=(RecyclerView)findViewById(R.id.uu);
                            uuu.setLayoutManager(new LinearLayoutManager(NoteMakingActivity.this));
                            uuu.setAdapter(uchalansAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("id",Login.id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}