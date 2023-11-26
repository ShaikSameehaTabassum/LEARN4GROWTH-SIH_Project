package com.example.smartlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddQuestions extends AppCompatActivity {


    EditText que,opt1,opt2,opt3,opt4,ans,probid;
    private static final String  URL="http://wizzie.tech/learn4growth/addmoc.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        que=findViewById(R.id.que);
        opt1=findViewById(R.id.op1);
        opt2=findViewById(R.id.op2);
        opt3=findViewById(R.id.op3);
        opt4=findViewById(R.id.op4);
        ans=findViewById(R.id.ans);
        probid=findViewById(R.id.pid);

    }

    public void add(View view) {

    if(que.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Question", Toast.LENGTH_SHORT).show();
    }
       else if(opt1.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Option 1", Toast.LENGTH_SHORT).show();
        }
    else if(opt2.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Option 2", Toast.LENGTH_SHORT).show();
    }
    else if(opt3.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Option 3", Toast.LENGTH_SHORT).show();
    }
    else if(opt4.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Option 4", Toast.LENGTH_SHORT).show();
    }
    else if(ans.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Answer", Toast.LENGTH_SHORT).show();
    }
    else if(probid.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "Enter Problem Id", Toast.LENGTH_SHORT).show();
    }
    else {
        final ProgressDialog pdd = new ProgressDialog(AddQuestions.this);
        pdd.setMessage("Please Wait, While We Add");
        pdd.setCancelable(false);
        pdd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(CreateQr.this, ""+response, Toast.LENGTH_SHORT).show();

                        pdd.dismiss();
                        // System.out.println(response);
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {

                                Toast.makeText(AddQuestions.this, "Added Success", Toast.LENGTH_SHORT).show();
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

                params.put("q", que.getText().toString().trim());
                params.put("o1", opt1.getText().toString().trim());
                params.put("o2", opt2.getText().toString().trim());
                params.put("o3", opt3.getText().toString().trim());
                params.put("o4", opt4.getText().toString().trim());
                params.put("an", ans.getText().toString().trim());
                params.put("pid",probid.getText().toString().trim());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    }
}