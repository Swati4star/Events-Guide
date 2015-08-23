package com.eventify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ImageView;

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


public class MainActivity2 extends AppCompatActivity {

    String latitude;
    String longitude;
    ImageView iv;
    ProgressBar pb1,pb2,pb3,pb4;
    TwoWayView lvRest,lvShop, lvTour, lvHotels;
    ArrayList<Restaurant> restaurants1,restaurants2,restaurants3,restaurants4;
    CustomList2 adapter;
    String id,event_link;

    TextView name,desc,start_time,venue,categories;
    TextView rest,hotel,shop,att;

    ButtonRectangle get_direction, visit_event;

    Double OriginLat,OriginLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "Oregon LDO Bold.ttf");
        Typeface custom_font3 = Typeface.createFromAsset(getAssets(), "Bariol_Light.ttf");

        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        id = i.getStringExtra("eid");
        latitude = i.getStringExtra("lat");
        longitude = i.getStringExtra("lon");

        name = (TextView) findViewById(R.id.event_name);
        desc = (TextView) findViewById(R.id.desc);
        start_time = (TextView) findViewById(R.id.start_time);
        venue = (TextView) findViewById(R.id.venue);
        categories = (TextView) findViewById(R.id.categories);

        iv = (ImageView) findViewById(R.id.fav);
        rest = (TextView) findViewById(R.id.rest_header);
        shop = (TextView) findViewById(R.id.shop_header);
        att = (TextView) findViewById(R.id.tour_header);
        hotel = (TextView) findViewById(R.id.hotel_header);

        desc.setTypeface(custom_font3);
        rest.setTypeface(custom_font2);
        shop.setTypeface(custom_font2);
        att.setTypeface(custom_font2);
        hotel.setTypeface(custom_font2);
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        pb2 = (ProgressBar) findViewById(R.id.pb2);
        pb3 = (ProgressBar) findViewById(R.id.pb3);
        pb4 = (ProgressBar) findViewById(R.id.pb4);

        get_direction = (ButtonRectangle) findViewById(R.id.get_directions);
        visit_event = (ButtonRectangle) findViewById(R.id.view_event);

        get_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s,%s", OriginLat, OriginLong, latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        visit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(event_link));
                startActivity(intent);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageResource(R.drawable.heart_filled);


            }

        });


        setTitle(i.getStringExtra("name"));

        name.setText(i.getStringExtra("name"));
        start_time.setText(i.getStringExtra("time"));

        desc.setText(i.getStringExtra("des"));


        lvRest = (TwoWayView) findViewById(R.id.lvRestaurants);
        lvShop = (TwoWayView) findViewById(R.id.lvShopping);
        lvTour = (TwoWayView) findViewById(R.id.lvTourists);
        lvHotels = (TwoWayView) findViewById(R.id.lvHotels);


        restaurants1 = new ArrayList<>();
        restaurants2 = new ArrayList<>();
        restaurants3 = new ArrayList<>();
        restaurants4 = new ArrayList<>();
       // lvRest.setAdapter(adapter);
        lvRest.setItemMargin(10);

        //lvShop.setAdapter(adapter);
        lvShop.setItemMargin(10);

        //lvTour.setAdapter(adapter);
        lvTour.setItemMargin(10);

        //lvHotels.setAdapter(adapter);
        lvHotels.setItemMargin(10);
        // adapter.notifyDataSetChanged();

        new DownloadWebPageTask2().execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OriginLat=0.0;
        OriginLong=0.0;
        GPSTracker tracker = new GPSTracker(MainActivity2.this);
        if (tracker.canGetLocation() == false) {
            tracker.showSettingsAlert();
        } else {
            OriginLat = tracker.getLatitude();
            OriginLong = tracker.getLongitude();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String text;
   private class DownloadWebPageTask2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String URL;
            String us;
            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
            us = s.getString("id",null);
            URL = "http://csinsit.org/prabhakar/aloogobhi/get-event-info.php?eventid="+id +"&userid="+us;
            HttpClient Client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                text = Client.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

      @Override
        protected void onPostExecute(String result) {
            int j = 0;
            JSONObject ob,ob2;
            JSONArray arr;
          try {
              ob = new JSONObject(text);
              desc.setText(ob.getString("long_description"));
              event_link = ob.getString("link");
              venue.setText(ob.getString("venue"));
              if(ob==null)
                  return;
              arr = ob.getJSONArray("category");
              for(int i=0; i<arr.length();i++ ) {
                  String a = arr.getString(i);
                  categories.append(a + " ");
              }



              restaurants1.clear();
              if(ob.has("restraunt")){
                  arr = ob.getJSONArray("restraunt");
                  for(int i=0; i<arr.length();i++){
                      ob2 = arr.getJSONObject(i);
                      String name,add,con,lat,lon,web;
                      name = ob2.getString("name");
                      add = ob2.getString("address");
                      con = ob2.getString("contact");
                      lat = ob2.getString("latitude");
                      lon = ob2.getString("longitude");
                      web = ob2.getString("website");

                      Restaurant r = new Restaurant(name,add,null,con,web,Double.parseDouble(lat),Double.parseDouble(lon));
                      restaurants1.add(r);
                  }
                  pb1.setVisibility(View.GONE);

                  adapter = new CustomList2(MainActivity2.this, restaurants1);
                  lvRest.setAdapter(adapter);
              }else{

                  pb1.setVisibility(View.GONE);
                  rest.setVisibility(View.GONE);
                  Log.e("no data", " " +id);
                  lvRest.setVisibility(View.GONE);
              }



              restaurants2.clear();
              if(ob.has("shopping")){
                  arr = ob.getJSONArray("shopping");
                  Log.e("bjdkngfd",ob + " ");
                  for(int i=0; i<arr.length();i++){
                      ob2 = arr.getJSONObject(i);
                      String name,add,con,lat,lon,web;
                      name = ob2.getString("name");
                      add = ob2.getString("address");
                      con = ob2.getString("contact");
                      lat = ob2.getString("latitude");
                      lon = ob2.getString("longitude");
                      web = ob2.getString("website");

                      Restaurant r = new Restaurant(name,add,null,con,web,Double.parseDouble(lat),Double.parseDouble(lon));
                      restaurants2.add(r);
                  }
                  CustomList_shop adapter = new CustomList_shop(MainActivity2.this, restaurants2);
                  lvShop.setAdapter(adapter);
                  pb2.setVisibility(View.GONE);

              }else{
                  pb2.setVisibility(View.GONE);
                  shop.setVisibility(View.GONE);
                  Log.e("" +
                          "no data", " " +id);
                  lvRest.setVisibility(View.GONE);
              }

              restaurants3.clear();

              if(ob.has("art_gallery")){
                  arr = ob.getJSONArray("art_gallery");
                  for(int i=0; i<arr.length();i++){
                      ob2 = arr.getJSONObject(i);
                      String name,add,con,lat,lon,web;
                      name = ob2.getString("name");
                      add = ob2.getString("address");
                      con = ob2.getString("contact");
                      lat = ob2.getString("latitude");
                      lon = ob2.getString("longitude");
                      web = ob2.getString("website");

                      Restaurant r = new Restaurant(name,add,null,con,web,Double.parseDouble(lat),Double.parseDouble(lon));
                      restaurants3.add(r);
                         }
                  pb3.setVisibility(View.GONE);

                  CustomList_att adapter = new CustomList_att(MainActivity2.this, restaurants3);
                  lvTour.setAdapter(adapter);
              }else{
                  pb3.setVisibility(View.GONE);
                  att.setVisibility(View.GONE);
                  Log.e("no data", " " +id);
                  lvRest.setVisibility(View.GONE);
              }
              restaurants4.clear();
              if(ob.has("hotels")){
                  arr = ob.getJSONArray("hotels");
                  for(int i=0; i<arr.length();i++){
                      ob2 = arr.getJSONObject(i);
                      String name,add,con,lat,lon,web;
                      name = ob2.getString("name");
                      add = ob2.getString("address");
                      con = ob2.getString("contact");
                      lat = ob2.getString("latitude");
                      lon = ob2.getString("longitude");
                      web = ob2.getString("website");

                      Restaurant r = new Restaurant(name,add,null,con,web,Double.parseDouble(lat),Double.parseDouble(lon));
                      restaurants4.add(r);

                  }

                  pb4.setVisibility(View.GONE);
                  CustomList_hot adapter = new CustomList_hot(MainActivity2.this, restaurants4);
                  lvHotels.setAdapter(adapter);
              }else{
                  pb4.setVisibility(View.GONE);
                  hotel.setVisibility(View.GONE);
                  Log.e("no data", " " +id);
                  lvRest.setVisibility(View.GONE);
              }

          } catch (JSONException e) {
              e.printStackTrace();
          }
        }
    }

    private class DownloadWebPageTask3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String URL;
            String us;
            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
            us = s.getString("id","0");
            URL = "http://csinsit.org/prabhakar/aloogobhi/increase-priority.php?userid="+us+"&eventid="+id+"&score="+2;
            HttpClient Client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                text = Client.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }



}