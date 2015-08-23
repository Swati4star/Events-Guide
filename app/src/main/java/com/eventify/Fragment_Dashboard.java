package com.eventify;

/**
 * Created by Swati garg on 21-06-2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_Dashboard extends Fragment {


    public static ViewPager pager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View rootView = inflater.inflate(R.layout.activity_tabs, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 1) {

                }
            }
        });

        PagerTabStrip titleStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_tab_strip);
        titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        /** Getting fragment manager */
        FragmentManager fm = getActivity().getSupportFragmentManager();
        /** Instantiating FragmentPagerAdapter */
       Dashboard_PageAdapter pageAdapter= new Dashboard_PageAdapter(fm);
        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pageAdapter);
        return rootView;
    }

}
