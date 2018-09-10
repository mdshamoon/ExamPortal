package com.example.mdshamoon.examportal;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new Notice();
            case 1:
                return new Sessional_marks();
            case 2:
                return new Attendance();
            case 3:
                return new Debarred_list();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "notice";
            case 1:
                return "sessional marks";
            case 2:
                return "attendance";
            case 3:
                return "Debarred List";
        }

        return super.getPageTitle(position);
    }
}
