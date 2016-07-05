package com.consonance.invitation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.consonance.invitation.R;
import com.consonance.invitation.UserDetailActivity;
import com.consonance.invitation.adapter.SquareAdapter;
import com.consonance.invitation.entity.OrderEntity;
import com.consonance.invitation.test.MonitorData;
import com.deity.customview.widget.RefreshView;

/**
 * Created by Deity on 2016/6/12.
 */
public class SquareMenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public RefreshView mRecyclerView;
    private SquareAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square_men,container,false);
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_lv);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new SquareAdapter(getActivity());
        mAdapter.setData(MonitorData.getOrderEntityList());
        /**线性布局*/
        mRecyclerView = (RefreshView) view.findViewById(R.id.order_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAutoLoadMoreEnable(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setRecycleViewOnClickListener(new SquareAdapter.RecycleViewOnClickListener() {
            @Override
            public void onItemClick(View view, OrderEntity data) {
                Toast.makeText(getActivity(),"Test",Toast.LENGTH_LONG).show();
                Intent startUserDetail = new Intent(getActivity(), UserDetailActivity.class);
                startActivity(startUserDetail);
            }
        });
        mRecyclerView.setLoadMoreListener(new RefreshView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(false);
                        mAdapter.addData(MonitorData.getOrderEntityList());
                        mRecyclerView.notifyMoreFinish(true);
                    }
                }, 1000);
            }
        });
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onRefresh() {

    }
}
