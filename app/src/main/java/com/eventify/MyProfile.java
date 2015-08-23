package com.eventify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.gc.materialdesign.views.ButtonRectangle;


public class MyProfile extends AppCompatActivity{

    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.fragment_mhprofile);
t1 = (TextView) findViewById(R.id.name);
        t2 = (TextView) findViewById(R.id.email);




        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MyProfile.this);
        String name = s.getString("username", "Swati");
        String email = s.getString("email","swati1@gmail.com");

        t1.setText(name);
        t2.setText(email);
        setTitle("My Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}