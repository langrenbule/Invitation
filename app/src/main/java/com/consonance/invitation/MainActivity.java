package com.consonance.invitation;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.consonance.invitation.adapter.OrderAdapter;
import com.consonance.invitation.test.MonitorData;
import com.deity.customview.widget.NavigationBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, ViewPager.OnPageChangeListener{
//    @BindView(R.id.order_list)
    private static final int REFRESH_COMPLETE = 0X110;
    public RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    //
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private List<NavigationBar> mNavigationBarList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();

        initView();
//        pagerAdapter();
        initEvent();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_first));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_second));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_third));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_fourth));

        mNavigationBarList.get(0).setAlpha(1.0f);

    }

//    private void pagerAdapter() {
//        mFragmentList = new ArrayList<android.support.v4.app.Fragment>();
//        for (int i=0;i<4;i++){
//            mFragmentList.add(new Fragment(i));
//        }
//        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),mFragmentList);
//        mViewPager.setAdapter(adapter);
//    }

    private void initEvent() {
        for(int i = 0; i < mNavigationBarList.size(); i++){
            mNavigationBarList.get(i).setOnClickListener(this);
            mNavigationBarList.get(i).setTag(i);
        }
        mViewPager.addOnPageChangeListener(this);
    }

    public void initRecyclerView(){
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_lv);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new OrderAdapter(MainActivity.this);
        mAdapter.setData(MonitorData.getOrderEntityList());
        /**线性布局*/
        mRecyclerView = (RecyclerView) this.findViewById(R.id.order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what){
                case REFRESH_COMPLETE:
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        };
    };

    @Override
    public void onRefresh() {
        //请求网络,这边我模拟耗时
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

    }

    @Override
    public void onClick(View v) {
        int number = (Integer) v.getTag();
        changeAlpha(number);
        mViewPager.setCurrentItem(number, false);
    }

    public void changeAlpha(int number){
        for (NavigationBar btn:mNavigationBarList){
            btn.setAlpha(0f);
        }
        mNavigationBarList.get(number).setAlpha(1.0f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffsetPixels != 0){
            mNavigationBarList.get(position).setAlpha(1 - positionOffset);
            mNavigationBarList.get(position+1).setAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
