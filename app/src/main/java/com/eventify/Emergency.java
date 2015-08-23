package com.eventify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class Emergency extends AppCompatActivity implements View.OnClickListener{
    ButtonRectangle police,fire,ambulance, blood_bank,bomb,railways;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.fragment_emergency);

        setTitle("Emergency Contacts");
        police = (ButtonRectangle)  findViewById(R.id.police);
        fire = (ButtonRectangle)  findViewById(R.id.fire);
        ambulance = (ButtonRectangle)  findViewById(R.id.ambulance);
        blood_bank = (ButtonRectangle)  findViewById(R.id.blood_bank);
        bomb = (ButtonRectangle)  findViewById(R.id.bomb);
        railways = (ButtonRectangle)  findViewById(R.id.railways);

        police.setOnClickListener(this);
        fire.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        blood_bank.setOnClickListener(this);
        bomb.setOnClickListener(this);
        railways.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        switch (v.getId()){
            case R.id.police:
                intent.setData(Uri.parse("tel:100"));
                break;
            case R.id.fire:
                intent.setData(Uri.parse("tel:101"));
                break;
            case R.id.ambulance:
                intent.setData(Uri.parse("tel:102"));
                break;
            case R.id.blood_bank:
                intent.setData(Uri.parse("tel:25752924"));
                break;
            case R.id.bomb:
                intent.setData(Uri.parse("tel:22512201"));
                break;
            case R.id.railways:
                intent.setData(Uri.parse("tel:23366177"));
        }
        startActivity(intent);
    }
}