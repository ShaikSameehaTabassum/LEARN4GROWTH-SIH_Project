package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {


    EditText review;
    RatingBar ratingBar;
    private static final String URL="http://wizzie.tech/learn4growth/feedback.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        review=findViewById(R.id.rev);
        ratingBar=findViewById(R.id.ratingBar);
        
    }

    public void fed(View view) {
        
        if(review.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Review", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog pdd = new ProgressDialog(Feedback.this);
            pdd.setMessage("Please Wait, While We Add");
            pdd.setCancelable(false);
            pdd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(Signup.this, ""+response, Toast.LENGTH_SHORT).show();
                            pdd.dismiss();
                            // System.out.println(response);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {
                                    Intent i = new Intent(getApplicationContext(),Login.class);
                                    startActivity(i);
                                    finish();
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

                    params.put("r", review.getText().toString().trim());
                    params.put("star", String.valueOf(ratingBar.getRating()));
                    params.put("id", MainActivity.Id);
                    params.put("nm", MainActivity.Nm);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        
    }
}