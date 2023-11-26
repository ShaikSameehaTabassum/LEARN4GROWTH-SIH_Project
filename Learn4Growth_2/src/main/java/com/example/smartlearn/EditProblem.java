package com.example.smartlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

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

public class EditProblem extends AppCompatActivity {


    EditText id,prob,url,sol;
    String iis;
    private static final String EDIT="http://wizzie.tech/learn4growth/editproblem.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        id=findViewById(R.id.id);
        prob=findViewById(R.id.prob);
        url=findViewById(R.id.url);
        sol=findViewById(R.id.sol);

        id.setText(getIntent().getStringExtra("id"));
        prob.setText(getIntent().getStringExtra("prob"));
        url.setText(getIntent().getStringExtra("url"));
        sol.setText(getIntent().getStringExtra("sol"));
        iis=getIntent().getStringExtra("iid");


    }

    public void data(View view) {
        if(id.getText().toString().trim().isEmpty()){
            id.setError("Enter Id");
        }
        else if(prob.getText().toString().trim().isEmpty()) {
            prob.setError("Enter Problem");
        }
        else if(sol.getText().toString().trim().isEmpty()) {
            sol.setError("Enter Solution");
        }
        else if(url.getText().toString().trim().isEmpty()) {
            url.setError("Enter Url");
        }
        else {
            final ProgressDialog pdd = new ProgressDialog(EditProblem.this);
            pdd.setMessage("Please Wait, While We Add");
            pdd.setCancelable(false);
            pdd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT,
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


                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProblem.this);
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
                                    prob.getText().clear();
                                    url.getText().clear();
                                    url.getText().clear();
                                    sol.getText().clear();


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
                    params.put("pb", prob.getText().toString().trim());
                    params.put("ur", url.getText().toString().trim());
                    params.put("sl", sol.getText().toString().trim());
                    params.put("iid", iis.trim());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }
}