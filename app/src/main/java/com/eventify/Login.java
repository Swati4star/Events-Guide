package com.eventify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends AppCompatActivity {

    EditText username,email,pass,cpass;
    String Username,Email,Pass,Cpass;
    ProgressBar pb;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");


        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String se = s.getString("id", null);
        if(!(se==null))
        if(!se.equals(null) && !se.equals("null")){

            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);

            finish();

        }

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.id);
        pass = (EditText) findViewById(R.id.pass);
        cpass = (EditText) findViewById(R.id.cpass);


        b = (Button) findViewById(R.id.signup);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString();
                Pass = pass.getText().toString();

                if(Email.equals("")|| (Pass.equals(""))){

                }
                else
                    new Down().execute();

            }
        });

    }







    String text;

    public class Down extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            String URL;
            URL = "http://csinsit.org/prabhakar/aloogobhi/login.php?pass="+Pass+"&email="+Email;
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
        protected void onPostExecute(String s) {
            pb.setVisibility(View.GONE);
            JSONObject ob;
            String id=null;
            if(text.contains("\"id\"")){
                try {
                    ob = new JSONObject(text);
                    id = ob.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
                SharedPreferences se = PreferenceManager.getDefaultSharedPreferences(Login.this);
                SharedPreferences.Editor e = se.edit();
                e.putString("id",id);
                e.commit();

                Log.e("heere",text + "vfnkkvfbvykfvknjre" + id);

                finish();
            }
        }
    }


}
