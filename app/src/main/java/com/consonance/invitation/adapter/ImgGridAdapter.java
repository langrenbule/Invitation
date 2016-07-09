package com.consonance.invitation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.consonance.invitation.R;
import com.consonance.invitation.data.Params;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 发表图片说说,图片必须至少有1个以上
 * Created by Deity on 2016/6/29.
 */
public class ImgGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
//    private List<String> imgList = new ArrayList<>();

    public ImgGridAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imgList.add("");
    }

//    public void setData(List<String> imgList){
//        this.imgList.addAll(imgList);
//    }

//    public void addData(String imageUrl){
//        this.imgList.add(imageUrl);
//    }

    @Override
    public int getCount() {
        return Params.UPLOAD_IMG_LIST.size()+1;//其中的+1是在没有选择的情况的的一张默认图片
    }

    @Override
    public Object getItem(int position) {
//        return Params.UPLOAD_IMG_LIST.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
//        return position;
        return 0;
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
        if (position==Params.UPLOAD_IMG_LIST.size()){
            viewHolder.imageView.setImageResource(R.drawable.icon_addpic_unfocused);
            if (position == 9) {
                viewHolder.imageView.setVisibility(View.GONE);
            }
        }else {
            String imgUrl = "file://"+Params.UPLOAD_IMG_LIST.get(position);
            ImageLoader.getInstance().displayImage(imgUrl,viewHolder.imageView);
        }
        return convertView;
    }


    public class ViewHolder {
        public ImageView imageView;
    }
}
