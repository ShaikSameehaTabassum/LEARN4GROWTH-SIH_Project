package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Player extends AppCompatActivity {

    private static final String URL="http://wizzie.tech/learn4growth/getvideo.php";
    static String id;
    YouTubePlayerView youTubePlayerView;
    TextView textView,solution;
   static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        textView=findViewById(R.id.tv);
        solution=findViewById(R.id.sol);

        youTubePlayerView=findViewById(R.id.youtube);
        id=getIntent().getStringExtra("id");
        getData(id);

    }

    private void getData(String id) {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                // problem.add(jsonObject.getString("problem").toString().trim());
                                // url.add(jsonObject.getString("url").toString().trim());

                                solution.setText(jsonObject.getString("solution").trim());
                                textView.setText(jsonObject.getString("problem").trim());
                                url=jsonObject.getString("url").trim();

                                /*youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(YouTubePlayer youTubePlayer) {
                                        super.onReady(youTubePlayer);
                                        try {
                                            youTubePlayer.loadVideo(jsonObject.getString("url").trim(),0);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });*/

                            }

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
                params.put("m", id);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void under(View view) {
        Intent intent=new Intent(getApplicationContext(),ObjectiveTesting.class);
        intent.putExtra("id",id.trim());
        startActivity(intent);

    }

    public void wsc(View view) {
       // Toast.makeText(this, "Student Want Some More Clarity Watch Video !", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getApplicationContext(),PlayVideo.class);
        intent.putExtra("id",url.trim());
        intent.putExtra("id1",id.trim());
        startActivity(intent);
    }
}