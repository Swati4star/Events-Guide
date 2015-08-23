package com.eventify;

/**
 * Created by Swati garg on 22-06-2015.
 */

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import func.Utils;

public class fragment_colleges extends Fragment{
   SwipeRefreshLayout swipeLayout;
    ListView lv;
    String cat;
    static List<String> list = new ArrayList<String>();
    static List<String> list1 = new ArrayList<String>();
    static List<String> list2 = new ArrayList<String>();
    static List<String> list5 = new ArrayList<String>();
    static List<String> list6 = new ArrayList<String>();
    static List<String> list7 = new ArrayList<String>();
    static List<String> list8 = new ArrayList<String>();
    static List<String> list9 = new ArrayList<String>();

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colleges, container, false);
        if(activity!=null) {
            InputMethodManager input = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(activity.getCurrentFocus()!=null)
                input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }





        lv = (ListView) rootView.findViewById(R.id.list);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String time1 = s.getString("college", "1");


        if(Utils.isNetworkAvailable(activity))
            new DownloadWebPageTask().execute();
        else
        Toast.makeText(activity,"Check Your internet connection",Toast.LENGTH_SHORT).show();
   /*
        if(Utils.isNetworkAvailable(activity)) {
            new DownloadWebPageTask().execute();
        }else {
           /* SnackbarManager.show(
                    Snackbar.with(activity.getApplicationContext())
                            .text("Check Your internet connection")
                            .duration(Snackbar.SnackbarDuration.LENGTH_SHORT), activity);
        }
*/



        return rootView;
    }

    String text;
    int iszer=0;
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            iszer=0;
            list.clear();
            list1.clear();
            list2.clear();
            list5.clear();
            list6.clear();
            list7.clear();
            list8.clear();
            list9.clear();
        };

        @Override
        protected String doInBackground(String... urls) {


            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            String aloo = s.getString("id","0");
            String  URL = "http://csinsit.org/prabhakar/aloogobhi/get-events.php?userid="+aloo;

            Log.e("this",URL + " ");
            HttpClient Client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                text = Client.execute(httpget, responseHandler);
                Log.e("EWFU","doing");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("here",e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("YO", "Done");
            Log.e("yrs",text+" ");
            JSONObject ob;
            JSONArray arr;
            if(text!=null)
            try {
                ob = new JSONObject(text);
                arr = ob.getJSONArray("events");

                if(arr.length()==0){
                    iszer=1;
                       }
                Log.e("yo", " " + arr.length());
                for(int i = 0; i < arr.length(); i++){
                    try {
                        list.add(arr.getJSONObject(i).getString("id"));
                        list1.add(arr.getJSONObject(i).getString("name"));
                        list2.add(arr.getJSONObject(i).getString("start_time"));
                        list5.add(arr.getJSONObject(i).getString("latitude"));
                        list6.add(arr.getJSONObject(i).getString("longitude"));
                        list7.add(arr.getJSONObject(i).getString("description"));
                        list8.add(arr.getJSONObject(i).getString("venue"));
                        list9.add(arr.getJSONObject(i).getString("image"));

                        Log.e("yolo",arr.getJSONObject(i).getString("image"));
                       /* DBhelpColl mDbHelper = new DBhelpColl(activity);
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();


                        ContentValues values = new ContentValues();
                        values.put(TableEntryCollege.COLUMN_NAME_ID,arr.getJSONObject(i).getString("id") );
                        values.put(TableEntryCollege.COLUMN_NAME,arr.getJSONObject(i).getString("name"));
                        values.put(TableEntryCollege.COLUMN_NAME_DESC, arr.getJSONObject(i).getString("description"));
                        values.put(TableEntryCollege.COLUMN_NAME_LINK, arr.getJSONObject(i).getString("website"));
                        values.put(TableEntryCollege.COLUMN_NAME_LAT, arr.getJSONObject(i).getString("latitude"));
                        values.put(TableEntryCollege.COLUMN_NAME_LON, arr.getJSONObject(i).getString("longitude"));
                        Cursor c = db.rawQuery("SELECT * FROM "+ TableEntryCollege.TABLE_NAME+
                                " WHERE "+ TableEntryCollege.COLUMN_NAME_ID + " IS "+arr.getJSONObject(i).getString("id") ,null);
                        if(!c.moveToFirst())
                        db.insert(
                                TableEntryCollege.TABLE_NAME,
                                TableEntryCollege.COLUMN_NAME_LINK,
                                values);
                        else
                            db.update(TableEntryCollege.TABLE_NAME, values,
                                    TableEntryCollege.COLUMN_NAME_ID +" = " +  arr.getJSONObject(i).getString("id"), null);*/

                    } catch (JSONException e) {
                        Log.e("Error",e.getMessage());
                    }
                }

                show();

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void show(){

        CustomList adapter = new CustomList(activity, list,list1,list2,list5,list6,list7,list8,list9);
        lv.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        show();
    }




}
