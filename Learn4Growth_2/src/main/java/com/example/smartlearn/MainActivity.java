package com.example.smartlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;



    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String Id = "id";
    public static final String Nm = "nm";
    public static final String Em = "em"    ;
    static  String m,d,name,email;
    Button link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        link = (Button) findViewById(R.id.link);


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                golink("https://drive.google.com/drive/folders/1u2ymj5K_OG0-188BwjFJyXuVY15gdVG6");


            }

        });


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Phone)) {

            m=sharedpreferences.getString(Phone, "");
            d=sharedpreferences.getString(Id, "");
            name=sharedpreferences.getString(Nm, "");
            email=sharedpreferences.getString(Em, "");


        }else {
            Intent i = getIntent();
            m = i.getStringExtra("mob");
            d = i.getStringExtra("id");
            name = i.getStringExtra("nm");
            email = i.getStringExtra("em");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void log(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Logout..!!!");
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_power_settings_new_24);
        alertDialogBuilder.setMessage("Are you sure,You want to Logout");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(), "Logout Success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void scan(View view) {
        startActivity(new Intent(getApplicationContext(),Test.class));
    }

    public void feed(View view) {

        startActivity(new Intent(getApplicationContext(),Feedback.class));
    }

    public void note(View view) {

        startActivity(new Intent(this,NoteMakingActivity.class));
    }
    private void openActivity2() {
        Intent intent = new Intent(this, DIGITAL_NOTES.class);
        startActivity(intent);
    }

    private void golink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}