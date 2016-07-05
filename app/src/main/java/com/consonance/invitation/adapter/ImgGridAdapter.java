package com.consonance.invitation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.consonance.invitation.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 发表图片说说
 * Created by Deity on 2016/6/29.
 */
public class ImgGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> imgList = new ArrayList<>();

    public ImgGridAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgList.add("");
    }

    public void setData(List<String> imgList){
        this.imgList.addAll(imgList);
    }

    public void addData(String imageUrl){
        this.imgList.add(imageUrl);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null==convertView){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_public_image,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_public_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.bt_add_pic);// 先设置默认图片
        if (position>0){
            String imgUrl = "file://"+imgList.get(position);
            ImageLoader.getInstance().displayImage(imgUrl,viewHolder.imageView);
        }
        return convertView;
    }


    public class ViewHolder {
        public ImageView imageView;
    }
}
