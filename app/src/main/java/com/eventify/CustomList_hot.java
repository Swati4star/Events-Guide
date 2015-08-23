package com.eventify;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Pranav on 22-08-2015.
 */
public class CustomList_hot extends ArrayAdapter<Restaurant> {
    private final ArrayList<Restaurant> restaurants;
    private final Activity context;

    public CustomList_hot(Activity context, ArrayList<Restaurant> res) {
        super(context, R.layout.activity_main, res);
        this.context = context;
        this.restaurants = res;
    }

    private class ViewHolder {
        TextView name, address;
        ButtonRectangle call, website;
        double latitude, longitude;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.hotel_layout, null);
            holder = new ViewHolder();
            holder.address = (TextView) view.findViewById(R.id.item_address);
            holder.name = (TextView) view.findViewById(R.id.item_name);
            holder.website = (ButtonRectangle) view.findViewById(R.id.website);
            holder.call = (ButtonRectangle) view.findViewById(R.id.call);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }

        holder.address.setText(restaurants.get(position).address);
        holder.name.setText(restaurants.get(position).name);

        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(Label+%s)", restaurants.get(position).latitude, restaurants.get(position).longitude, restaurants.get(position).latitude, restaurants.get(position).longitude, restaurants.get(position).name);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });

        holder.website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(restaurants.get(position).URL));
                context.startActivity(viewIntent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + restaurants.get(position).number));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
