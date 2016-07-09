package com.consonance.invitation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.consonance.invitation.adapter.ImageGridAdapter;
import com.consonance.invitation.data.Params;
import com.consonance.invitation.entity.ImageItem;
import com.consonance.invitation.utils.Bimp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  图片选取
 */
public class ImageGridActivity extends Activity {
    private List<ImageItem> imageItems;
    private GridView gridView;
    private ImageGridAdapter adapter;
    @BindView(R.id.bt)
    public Button bt_ok;

    @OnClick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        ButterKnife.bind(this);

        imageItems = (List<ImageItem>) getIntent().getSerializableExtra(Params.EXTRA_IMAGE_LIST);

        initView();
        bt_ok = (Button) findViewById(R.id.bt);//【是否完成】 的 按钮
        bt_ok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
                    Intent intent = new Intent(ImageGridActivity.this,RealseActivity.class);
                    startActivity(intent);
                    Bimp.act_bool = false;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.drr.size() < 9) {
                        Bimp.drr.add(list.get(i));
                    }
                }
                finish();
            }

        });
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, imageItems);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt_ok.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                adapter.notifyDataSetChanged();
            }

        });

    }
}
