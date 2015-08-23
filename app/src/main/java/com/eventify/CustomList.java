package com.eventify;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import func.DBhelp;
import func.TableEntry;

public class CustomList extends ArrayAdapter<String>{
	private final Activity context;
	private int lastPosition = -1;
	private final List<String> ids,name,time,lat,lon,des,Ven,ima;
	private String[] cat = {"Technical","Music","Drama","Dance","Gaming","Management","Art","Literature","Miscellaneous"};
	public CustomList(Activity context, List<String> id, List<String> n, List<String> t,  List<String> l
			, List<String> lo, List<String> de, List<String> ve, List<String> im
	){
		super(context, R.layout.message_layout, id);
		this.context = context;
		ids=id;
		name=n;
		time=t;
		lat=l;
		lon=lo;
		des=de;
		Ven=ve;
		ima = im;
		custom_font = Typeface.createFromAsset(context.getAssets(), "Lato-Light.ttf");
		custom_font2 = Typeface.createFromAsset(context.getAssets(), "Oregon LDO Bold.ttf");
		custom_font3 = Typeface.createFromAsset(context.getAssets(), "Oregon LDO Extended.ttf");


	}
	TextView more,Name,dis,Time,ven;
	ImageView iv;
	Typeface custom_font,custom_font2,custom_font3;
	
	@Override
	public View getView(final int position, View view2, ViewGroup parent) {
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.message_layout, null);



		Name = (TextView) view.findViewById(R.id.name);
		dis = (TextView) view.findViewById(R.id.dis);
		Time = (TextView) view.findViewById(R.id.time);
		ven = (TextView) view.findViewById(R.id.venue);


		iv = (ImageView) view.findViewById(R.id.im);

		Name.setTypeface(custom_font2);
		dis.setTypeface(custom_font3);
		Time.setTypeface(custom_font);
		ven.setTypeface(custom_font);


		if(ima.get(position)!=null) {
			Picasso.with(context).load(ima.get(position)).into(iv);
			Log.e("vhj", ima.get(position) + "");
			iv.setImageAlpha(30);
		}


		Name.setText(name.get(position));
		Time.setText(time.get(position));
		if(position<Ven.size())
		ven.setText(Ven.get(position));

		Double OriginLat=0.0,OriginLong=0.0;
		GPSTracker tracker = new GPSTracker(context);
		if (tracker.canGetLocation() == false) {
			tracker.showSettingsAlert();
		} else {
			OriginLat = tracker.getLatitude();
			OriginLong = tracker.getLongitude();
		}


		dis.setText(distance(OriginLat, OriginLong, Double.parseDouble(lat.get(position)), Double.parseDouble(lon.get(position)), 'N')
				+ " Km");





		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(context, MainActivity2.class);
				i.putExtra("name", name.get(position));
				i.putExtra("time", time.get(position));
				i.putExtra("eid", ids.get(position));
				i.putExtra("lat", lat.get(position));
				i.putExtra("lon", lon.get(position));
				i.putExtra("des", des.get(position));
				context.startActivity(i);

			}
		});


		AnimationSet set = new AnimationSet(true);
		TranslateAnimation slide = new TranslateAnimation(-100, 0, -100, 0);
		slide.setInterpolator(new DecelerateInterpolator(5.0f));
		slide.setDuration(300);
		Animation fade = new AlphaAnimation(0, 1.0f);
		fade.setInterpolator(new DecelerateInterpolator(5.0f));
		fade.setDuration(300);
		set.addAnimation(slide);
		set.addAnimation(fade);
		view.startAnimation(set);
		return view;
	}


	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}


	private String distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		dist = round(dist, 2);
		return (Double.toString(dist));

	}
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}