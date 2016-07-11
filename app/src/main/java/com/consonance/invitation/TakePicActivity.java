package com.consonance.invitation;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.consonance.invitation.adapter.ImageBucketAdapter;
import com.consonance.invitation.data.Params;
import com.consonance.invitation.entity.ImageBucket;
import com.consonance.invitation.utils.AlbumHelper;

/**
 * 相册缩略图显示
 */
public class TakePicActivity extends AppCompatActivity {
    List<ImageBucket> imageBuckets;
    GridView gridView;
    ImageBucketAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static Bitmap bimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket);
        initActionBar();
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        initData();
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
                TakePicActivity.this.finish();
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        imageBuckets = helper.getImagesBucketList(false);
        bimap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_addpic_unfocused);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageBucketAdapter(TakePicActivity.this, imageBuckets);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                /**
                 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
                 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
                 */
                Intent intent = new Intent(TakePicActivity.this,ImageGridActivity.class);
                intent.putExtra(Params.EXTRA_IMAGE_LIST,(Serializable) imageBuckets.get(position).imageList);
                startActivity(intent);
                finish();
            }

        });
    }
}
