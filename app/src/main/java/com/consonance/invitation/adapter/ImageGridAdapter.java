package com.consonance.invitation.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.consonance.invitation.BitmapCache;
import com.consonance.invitation.R;
import com.consonance.invitation.data.Params;
import com.consonance.invitation.entity.ImageItem;
import com.consonance.invitation.utils.Bimp;
import com.consonance.invitation.utils.EventBusHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相册图片显示
 */
public class ImageGridAdapter extends BaseAdapter {

    private TextCallback textcallback = null;
    final String TAG = getClass().getSimpleName();
    private Activity activity;
    List<ImageItem> dataList;
    public Map<String, String> map = new HashMap<>();
    private int selectTotal = 0;

    public interface TextCallback {
        void onListen(int count);
    }

    public void setTextCallback(TextCallback listener) {
        textcallback = listener;
    }

    public ImageGridAdapter(Activity activity, List<ImageItem> list) {
        this.activity = activity;
        dataList = list;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        private ImageView iv;
        private ImageView selected;
        private TextView text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_img_grid, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
            holder.text = (TextView) convertView.findViewById(R.id.item_image_grid_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ImageItem imageItem = dataList.get(position);

        holder.iv.setTag(imageItem.imagePath);
        ImageLoader.getInstance().displayImage("file://"+imageItem.imagePath,holder.iv);
        if (imageItem.isSelected) {
            holder.selected.setImageResource(R.drawable.icon_data_select);
            holder.text.setBackgroundResource(R.drawable.bg_focus_line);
        } else {
            holder.selected.setImageResource(R.drawable.icon_account);
            holder.text.setBackgroundColor(0x00000000);
        }
        holder.iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String path = imageItem.imagePath;//dataList.get(position).imagePath;
                if ((Params.UPLOAD_IMG_LIST.size()+selectTotal)<9) {
                    imageItem.isSelected = !imageItem.isSelected;
                    if (imageItem.isSelected) {
                        holder.selected.setImageResource(R.drawable.icon_data_select);
                        holder.text.setBackgroundResource(R.drawable.bg_focus_line);
                        selectTotal++;
                        if (textcallback != null)
                            textcallback.onListen(selectTotal);
                        map.put(path, path);

                    } else if (!imageItem.isSelected) {
                        holder.selected.setImageResource(-1);
                        holder.text.setBackgroundColor(0x00000000);
                        selectTotal--;
                        if (textcallback != null)
                            textcallback.onListen(selectTotal);
                        map.remove(path);
                    }
                } else if ((Params.UPLOAD_IMG_LIST.size()+selectTotal)>= 9) {
                    if (imageItem.isSelected) {
                        imageItem.isSelected = !imageItem.isSelected;
                        holder.selected.setImageResource(-1);
                        selectTotal--;
                        map.remove(path);
                    } else {
                        EventBusHelper.sendUIEvent(Params.UIEventType.MSG_SHOW_MESSAGE,"最多选择9张图片");
                    }
                }
            }

        });

        return convertView;
    }
}
