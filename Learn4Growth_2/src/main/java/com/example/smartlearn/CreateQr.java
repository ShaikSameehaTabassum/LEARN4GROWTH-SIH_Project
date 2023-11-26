package com.example.smartlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class CreateQr extends AppCompatActivity {

    EditText id,problem,url,sol;
    private static final String URL="http://wizzie.tech/learn4growth/problemdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);

        id=findViewById(R.id.pn);
        problem=findViewById(R.id.pb);
        url=findViewById(R.id.url);
        sol=findViewById(R.id.sl);


    }

    public void data(View view) {
        if(id.getText().toString().trim().isEmpty()){
            id.setError("Enter Id");
        }
        else if(problem.getText().toString().trim().isEmpty()) {
            problem.setError("Enter Problem");
        }
        else if(sol.getText().toString().trim().isEmpty()) {
            sol.setError("Enter Solution");
        }
        else if(url.getText().toString().trim().isEmpty()) {
            url.setError("Enter Url");
        }
        else{
            final ProgressDialog pdd = new ProgressDialog(CreateQr.this);
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


                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateQr.this);
                                    final AlertDialog dialog = builder.create();
                                    LayoutInflater inflater = getLayoutInflater();
                                    View dialogLayout = inflater.inflate(R.layout.dialog_layout, null);
                                    dialog.setView(dialogLayout);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                                    ImageView image = (ImageView) dialogLayout.findViewById(R.id.goProDialogImage);

                                    MultiFormatWriter writer = new MultiFormatWriter();
                                    try {

                                        BitMatrix bitMatrix = writer.encode(""+id.getText().toString().trim(), BarcodeFormat.QR_CODE, 512, 512);
                                        int width = bitMatrix.getWidth();
                                        int height = bitMatrix.getHeight();
                                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

                                        for (int x = 0; x < width; x++) {
                                            for (int y = 0; y < height; y++) {
                                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                                            }
                                        }

                                        image.setImageBitmap(bmp);

                                    } catch (WriterException e) {
                                        e.printStackTrace();
                                    }
                                    dialog.show();

                                    id.getText().clear();
                                    problem.getText().clear();
                                    url.getText().clear();


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

                    params.put("id", id.getText().toString().trim());
                    params.put("pb", problem.getText().toString().trim());
                    params.put("ur", url.getText().toString().trim());
                    params.put("sl", sol.getText().toString().trim());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }
}