package com.consonance.invitation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.consonance.invitation.R;
import com.consonance.invitation.adapter.SquareTypeAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.util.Arrays;

/**
 * 广场
 * Created by Deity on 2016/6/12.
 */
public class SquareFragment extends Fragment {
    public TabPageIndicator mIndicator;
    public ViewPager mContentPage;
    private Fragment[] mFragments = new Fragment[]{new SquareNeighborFragment(),new SquareMenFragment(),new SquareWomenFragment()};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square,container,false);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mContentPage = (ViewPager) view.findViewById(R.id.pager);
        SquareTypeAdapter typeAdapter = new SquareTypeAdapter(getActivity().getSupportFragmentManager());
        typeAdapter.setData(Arrays.asList(mFragments));
        mContentPage.setAdapter(typeAdapter);
        mIndicator.setViewPager(mContentPage);
        return view;
    }
}
