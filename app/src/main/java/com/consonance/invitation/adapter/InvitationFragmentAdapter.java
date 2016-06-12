package com.consonance.invitation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Deity on 2016/6/12.
 */
public class InvitationFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public InvitationFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> mFragmentList){
        this.mFragmentList=mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (null==mFragmentList||mFragmentList.isEmpty()) return 0;
        return mFragmentList.size();
    }
}
