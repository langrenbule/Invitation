package com.consonance.invitation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.consonance.invitation.R;

import java.util.List;

/**
 * 用户的明细内容
 * Created by Deity on 2016/7/4.
 */
public class UserDetailAdapter extends PagerAdapter {
    private List<String> viewList;
    private Context context;
    private LayoutInflater inflater;

    public UserDetailAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<String> viewList){
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (null==viewList||viewList.isEmpty()){
            return 0;
        }
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        NetworkImageView imageView = new NetworkImageView(context);
//        //设置图片填充格式
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.drawable.i_normal_bg);
//        getImageLoader().get(viewList.get(position),ImageLoader.getImageListener(imageView,R.drawable.i_normal_bg,R.drawable.i_normal_bg));
//        container.addView(imageView);
//        View itemView = inflater.inflate(R.layout.item_user_detail_image,null);
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_user_detail_image);
        ImageView imageView = new ImageView(context);
        ViewPager.LayoutParams params =  new ViewPager.LayoutParams();
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(viewList.get(position),imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


    public ImageLoader getImageLoader(){
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        return imageLoader;
    }
}
