package com.eventify;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.util.MaterialActionBarDrawerToggle;


public class MainActivity extends MaterialNavigationDrawer {

    String evid;

    @Override
    public void init(Bundle bundle) {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String id = s.getString("id",null);

        // create sections
        if(id==null)
            this.addSection(newSection("All Events", R.drawable.ic_event_available_black_24dp, new fragment_colleges()));
        else{
            this.addSection(newSection("All Events", R.drawable.ic_event_available_black_24dp, new Fragment_Dashboard()));
            Intent i2 = new Intent(MainActivity.this,Signup.class);
            i2.putExtra("signout", "1");
            this.addBottomSection(newSection("Sign Out",i2));
            Intent i3 = new Intent(MainActivity.this,MyProfile.class);

            this.addSection(newSection("My Profile", R.drawable.ic_mood_black_24dp, i3));
        }


        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(
                                "http://www.delhitourism.gov.in/delhitourism/index.jsp"));

        this.addSection(newSection("Delhi Tourism", R.drawable.ic_map_black_24dp, viewIntent));

        Intent viewIntent2 =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(
                                "https://play.google.com/store/apps/details?id=com.sraoss.dmrc"));

        this.addSection(newSection("DMRC Android App", R.drawable.ic_directions_transit_black_24dp, viewIntent2));

        Intent i = new Intent(MainActivity.this,Emergency.class);
        this.addSubheader("Support");
        this.addSection(newSection("Emergency Contacts", R.drawable.ic_warning_black_24dp,i));



      /*  this.addSection(newSection("Search an Event", R.drawable.ic_search_black_24dp, new Fragment_searcheventslist()));
        this.addSection(newSection("Today's Events", R.drawable.ic_loupe_black_24dp, new Fragment_todayseventslist()));
        this.addSection(newSection("Add a new Event", R.drawable.ic_event_available_black_24dp, new fragment_add_event()));
        this.addSection(newSection("My favourites", R.drawable.ic_star_black_24dp, new Fragment_fav()));
        this.addSection(newSection("All colleges", R.drawable.ic_pin_drop_black_24dp, new fragment_colleges()));
        this.addSection(newSection("Add your College", R.drawable.ic_school_black_24dp, new fragment_add_college()));//.setSectionColor(Color.parseColor("#03a9f4")));


        this.addSection(newSection("View Past Events", R.drawable.ic_access_time_black_24dp, new Fragment_pasteventslist()));//.setSectionColor(Color.parseColor("#03a9f4")));



        this.addBottomSection(newSection("Say a word", R.drawable.ic_feedback_black_24dp, new fragment_feedback()));

*/
       /* Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"collegegapp@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
*/

        //  this.addBottomSection(newSection("Contact us", R.drawable.ic_email_black_24dp, intent));

        // this.addBottomSection(newSection("Settings", R.drawable.ic_build_black_24dp, new Intent(this, SettingsActivity.class)));

        this.disableLearningPattern();



        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, "Check out this awesome android app to know about all the " +
                "upcoming college events in our city\nCollegeGapp : https://goo.gl/S0cUIh ");
     //   this.addBottomSection(newSection("Share with F.R.I.E.N.D.S", R.drawable.ic_share_black_24dp, Intent.createChooser(intent2, "Share via")));

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    String text;
   /* private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {};

        @Override
        protected String doInBackground(String... urls) {

            Log.e("Yo", "Started");
            String  URL =
                    "http://collegegapp.ml/android/get-event.php?eventid="+evid;
            Log.e("this",URL + "   ");
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
            Log.e("YO", "Done");
            JSONObject c;
            JSONArray arr;
            if(text!=null)
                try {
                    c= new JSONObject(text);
                    Context c2 = getBaseContext();
                    Intent i = new Intent(c2 , EventDes.class);
                    i.putExtra("name", c.getString("event_name"));
                    i.putExtra("id",evid);
                    i.putExtra("time", c.getString("start_time"));
                    i.putExtra("time2",c.getString("end_time"));
                    i.putExtra("coll",c.getString("college_name"));
                    i.putExtra("image",c.getString("image"));
                    i.putExtra("des",c.getString("description"));
                    i.putExtra("cate",c.getString("category"));
                    i.putExtra("reg",c.getString("registration"));
                    i.putExtra("link",c.getString("link"));
                    i.putExtra("lat",c.getString("latitude"));
                    i.putExtra("lon",c.getString("longitude"));
                    i.putExtra("online",c.getString("online"));
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    Log.e("error2",e.getMessage() + " ");
                }
            Log.e("Yo", text + "vsdvews");
        }
    }*/

}
