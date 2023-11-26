package com.example.smartlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminDataAdapter extends RecyclerView.Adapter<AdminDataAdapter.ViewHolder> {

   Context context;
    ArrayList id=new ArrayList();
    ArrayList prob=new ArrayList();
    ArrayList url=new ArrayList();
    ArrayList iid=new ArrayList();
    ArrayList solution=new ArrayList();


    private static final String URL="http://wizzie.tech/learn4growth/deleteproblem.php";

    public AdminDataAdapter(ViewProblems viewProblems, ArrayList id, ArrayList prob, ArrayList url, ArrayList iid, ArrayList solution) {
      this.context=viewProblems;
        this.id=id;
        this.prob=prob;
        this.url=url;
        this.iid=iid;
        this.solution=solution;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(id.get(position).toString().trim());
        holder.probl.setText(prob.get(position).toString().trim());
        holder.ur.setText(url.get(position).toString().trim());
        holder.sol.setText(solution.get(position).toString().trim());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirm Action..!!!");
                alertDialogBuilder.setIcon(R.drawable.ic_baseline_delete_outline_24);
                alertDialogBuilder.setMessage("Are you sure,You want to Action");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        final ProgressDialog pdd = new ProgressDialog(context);
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
                                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();

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

                                params.put("id", id.get(position).toString().trim());

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);


                    }
                });

                alertDialogBuilder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(context,EditProblem.class);
                        intent.putExtra("id",id.get(position).toString().trim());
                        intent.putExtra("prob",prob.get(position).toString().trim());
                        intent.putExtra("url",url.get(position).toString().trim());
                        intent.putExtra("iid",iid.get(position).toString().trim());
                        intent.putExtra("sol",solution.get(position).toString().trim());
                        context.startActivity(intent);

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }




    @Override
    public int getItemCount() {
        return prob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,probl,ur,sol;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.layout);

            id=itemView.findViewById(R.id.id);
            probl=itemView.findViewById(R.id.pb);
            ur=itemView.findViewById(R.id.ur);
            sol=itemView.findViewById(R.id.sol);

        }
    }
}
