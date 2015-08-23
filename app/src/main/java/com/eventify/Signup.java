package com.eventify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import func.TouchImageView;

public class Signup extends AppCompatActivity {

    EditText username,email,pass,cpass;
    String Username,Email,Pass,Cpass;
    ProgressBar pb;
    Button b,continu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SignUp");

        Intent i2 = getIntent();
        String a = i2.getStringExtra("signout");
        if(!(a==null))
        if(a.equals("1")){
            SharedPreferences se = PreferenceManager.getDefaultSharedPreferences(Signup.this);
            SharedPreferences.Editor e = se.edit();
            e.putString("id",null);
            e.commit();

        }

        continu = (Button) findViewById(R.id.continu);
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(Signup.this);
        String se = s.getString("id", null);
        if(!(se==null))
        if(!se.equals("null")){

            Intent i = new Intent(Signup.this,MainActivity.class);
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
                Username = username.getText().toString();
                Email = email.getText().toString();
                Pass = pass.getText().toString();
                Cpass = cpass.getText().toString();

                if(Username.equals("")||Email.equals("")|| (Pass.equals(""))||Cpass.equals("")){

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
            URL = "http://csinsit.org/prabhakar/aloogobhi/signup.php?name="+Username+"&pass="+Pass+"&email="+Email;
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
                Intent i = new Intent(Signup.this,MainActivity.class);
                startActivity(i);
                SharedPreferences se = PreferenceManager.getDefaultSharedPreferences(Signup.this);
                SharedPreferences.Editor e = se.edit();
                e.putString("id",id);
                e.putString("username",Username);
                e.putString("name",Email);
                e.commit();

                Log.e("heere",text + "vfnkkvfbvykfvknjre" + id);

                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            Intent i = new Intent(Signup.this,Login.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
