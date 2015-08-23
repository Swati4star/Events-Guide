package com.eventify;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonRectangle;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment implements View.OnClickListener{



    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency, container, false);
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
