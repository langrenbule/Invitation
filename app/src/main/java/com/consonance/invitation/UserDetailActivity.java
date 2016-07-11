package com.consonance.invitation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.consonance.invitation.adapter.UserDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户内容显示
 * Created by Deity on 2016/7/2.
 */
public class UserDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView tv_img_sum;
    private TextView tv_img_index;
    private List<View> pageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        initActionBar();
        initView();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_bucket_toolbar);
        toolbar.setTitle("信息发布");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.jmui_back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailActivity.this.finish();
            }
        });

    }

    public List<String> testData(){
        List<String> dataList = new ArrayList<>();
        dataList.add("http://img2.imgtn.bdimg.com/it/u=3260548700,3238345600&fm=21&gp=0.jpg");
        dataList.add("http://img4.imgtn.bdimg.com/it/u=697890588,3646918970&fm=21&gp=0.jpg");
        dataList.add("http://tx.haiqq.com/qqtouxiang/uploads/2013-06-30/211435581.jpg");
        dataList.add("http://ww2.sinaimg.cn/crop.0.0.1242.1242.1024/a79cbdbcjw8evme52qf8sj20yi0yitba.jpg");
        dataList.add("http://img4q.duitang.com/uploads/item/201408/10/20140810131526_t3hfi.jpeg");
        return dataList;
    }

    public void initView(){
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        tv_img_index = (TextView) this.findViewById(R.id.tv_img_index);
        tv_img_sum = (TextView) this.findViewById(R.id.tv_img_sum);
        UserDetailAdapter adapter = new UserDetailAdapter(UserDetailActivity.this);
        adapter.setData(testData());
        tv_img_sum.setText(String.valueOf(testData().size()));
        tv_img_index.setText("1");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_img_index.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
