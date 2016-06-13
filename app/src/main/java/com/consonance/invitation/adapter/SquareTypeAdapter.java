package com.consonance.invitation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Deity on 2016/6/12.
 */
public class SquareTypeAdapter extends FragmentStatePagerAdapter {

    private final static String[] MY_TITLES ={"离我最近","花样少女","俊美型男"};
    private List<Fragment> mFragmentList;

    public SquareTypeAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> mFragmentList){
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return MY_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MY_TITLES[position%MY_TITLES.length];
    }
}
