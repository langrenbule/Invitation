package com.consonance.invitation.fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.consonance.invitation.R;
import com.consonance.invitation.UserDetailActivity;
import com.consonance.invitation.adapter.HeaderViewRecyclerAdapter;
import com.consonance.invitation.adapter.SquareAdapter;
import com.consonance.invitation.entity.OrderEntity;
import com.consonance.invitation.test.MonitorData;
import com.consonance.invitation.widget.EndlessRecyclerOnScrollListener;
import com.deity.customview.widget.RefreshView;

/**
 * Created by Deity on 2016/6/12.
 * recycleview的使用
 * https://www.easydone.cn/2015/10/26/
 */
public class SquareWomenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = SquareWomenFragment.class.getSimpleName();
    public RefreshView mRecyclerView;
    private SquareAdapter mAdapter;
    private HeaderViewRecyclerAdapter adapter;
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
        mAdapter = new SquareAdapter(getActivity());
        mAdapter.setData(MonitorData.getOrderEntityList());
        adapter = new HeaderViewRecyclerAdapter(mAdapter);

        /**线性布局*/
        mRecyclerView = (RefreshView) view.findViewById(R.id.order_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        mAdapter.setRecycleViewOnClickListener(new SquareAdapter.RecycleViewOnClickListener() {
            @Override
            public void onItemClick(View view, OrderEntity data) {
                Toast.makeText(getActivity(),"Test",Toast.LENGTH_LONG).show();
                Intent startUserDetail = new Intent(getActivity(), UserDetailActivity.class);
                startActivity(startUserDetail);
            }
        });
        mRecyclerView.setAutoLoadMoreEnable(true);
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
    }

    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(false);
        mAdapter.addData(MonitorData.getOrderEntityList());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Refresh Finished!", Toast.LENGTH_SHORT).show();
    }

//    private void createLoadMoreView() {
//        View loadMoreView = LayoutInflater
//                .from(getActivity())
//                .inflate(R.layout.view_load_more, recyclerView, false);
//        adapter.addFooterView(loadMoreView);
//    }

    private void loadMoreData() {
        mSwipeLayout.setRefreshing(false);
        mAdapter.addData(MonitorData.getOrderEntityList());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "loadMoreData Finished!", Toast.LENGTH_SHORT).show();
    }


}
