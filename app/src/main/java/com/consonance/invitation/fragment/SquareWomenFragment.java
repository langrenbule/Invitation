package com.consonance.invitation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.consonance.invitation.R;
import com.consonance.invitation.adapter.OrderAdapter;
import com.consonance.invitation.test.MonitorData;

/**
 * Created by Deity on 2016/6/12.
 *  解决fragment+viewpager第二次进入的时候没有数据的问题
 * http://blog.csdn.net/zmldlut/article/details/17689011
 * https://www.zhihu.com/question/27313300
 *
 * 解决http://my.oschina.net/buobao/blog/610496
 */
public class SquareWomenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = SquareWomenFragment.class.getSimpleName();
    public RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_square_women,container,false);
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_lv);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new OrderAdapter(getActivity());
        mAdapter.setData(MonitorData.getOrderEntityList());
        /**线性布局*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

    }
}
