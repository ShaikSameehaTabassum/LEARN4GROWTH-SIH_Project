package com.example.smartlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONException;

public class PlayVideo extends AppCompatActivity {

    String url;
    String id;

    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        url=getIntent().getStringExtra("id");
        id=getIntent().getStringExtra("id1");

        youTubePlayerView=findViewById(R.id.youtube);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(url,0);
            }
        });

    }

    public void under(View view) {
        Intent intent=new Intent(getApplicationContext(),ObjectiveTesting.class);
        intent.putExtra("id",id.trim());
        startActivity(intent);

    }

    public void wsc(View view) {

        startActivity(new Intent(getApplicationContext(),Test.class));

    }
}