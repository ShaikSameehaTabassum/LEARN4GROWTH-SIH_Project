package com.example.smartlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

    }

    public void start(View view) {

        startActivity(new Intent(getApplicationContext(),CreateQr.class));
    }

    public void AddData(View view) {
        startActivity(new Intent(getApplicationContext(),ViewProblems.class));
    }

    public void logout(View view) {

        startActivity(new Intent(getApplicationContext(),Login.class));

    }

    public void feedback(View view) {
        startActivity(new Intent(getApplicationContext(),ViewFeedback.class));
    }

    public void Questions(View view) {

        startActivity(new Intent(getApplicationContext(),AddQuestions.class));
    }
}