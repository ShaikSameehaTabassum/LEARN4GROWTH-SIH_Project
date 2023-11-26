package com.example.smartlearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DIGITAL_NOTES extends AppCompatActivity implements View.OnClickListener {
    Button link1;
    Button link2;
    Button link3;
    Button link4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digital);
        link1=findViewById(R.id.link1);
        link2=findViewById(R.id.link2);
        link3=findViewById(R.id.link3);
        link4=findViewById(R.id.link4);

        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
        link3.setOnClickListener(this);
        link4.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.link1:
                golink("https://drive.google.com/drive/folders/134vZtMpDwdTNYKH00bwoBLLOXjM5x9W2");
            case R.id.link2:
                golink("https://drive.google.com/drive/folders/1fsxWtpjbVtwlDm0AdOqxhvhRsDlsCL18");
            case R.id.link3:
                golink("https://drive.google.com/drive/folders/1wTo8QHR_h1dx2X1S6W4xclPPPrM7Uz-w");
            case R.id.link4:
                golink("https://drive.google.com/drive/folders/1huRTwGKf3CCYgGD8Ksol_SwlD__MoH-Q?usp=sharing");


        }
    }

    private void golink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}