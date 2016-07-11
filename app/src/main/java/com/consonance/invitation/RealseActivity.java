package com.consonance.invitation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.consonance.invitation.adapter.ImgGridAdapter;
import com.consonance.invitation.data.Params;
import com.consonance.invitation.widget.AddPublicPicPop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deity on 2016/6/25.
 */
public class RealseActivity extends AppCompatActivity {
    private GridView noScrollgridview;
    private ImgGridAdapter imgGridAdapter;
    private static int maxNumber = 6;
    private List<String> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        initActionBar();

        noScrollgridview = (GridView) this.findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        imgGridAdapter = new ImgGridAdapter(RealseActivity.this);
        noScrollgridview.setAdapter(imgGridAdapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==Params.UPLOAD_IMG_LIST.size()){
                    new AddPublicPicPop(RealseActivity.this, noScrollgridview);
                }else {
                    Intent intent = new Intent(RealseActivity.this,PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }

            }
        });
    }


    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("信息发布");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.jmui_back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealseActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgGridAdapter.notifyDataSetChanged();
    }

    private static final int TAKE_PICTURE = 0x000000;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (imgGridAdapter.getCount()<maxNumber) {
                    Params.UPLOAD_IMG_LIST.add(AddPublicPicPop.getPath());
//                    imgGridAdapter.addData(AddPublicPicPop.getPath());
                    imgGridAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
