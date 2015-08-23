package com.eventify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Swati Garg on 08-06-2015.
 */
public class Dashboard_PageAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 2;

        public Dashboard_PageAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            if (arg0==0)
            {
               fragment_colleges umf = new fragment_colleges();

                return umf;
            }
            else
            {
                fragment_colleges2 umf = new fragment_colleges2();
                return umf;
            }

        }




        /** Returns the number of pages */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
                return "Recommended";
            else
                return "All";

        }

    }
