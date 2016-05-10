package com.consonance.invitation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.consonance.invitation.R;
import com.consonance.invitation.entity.OrderEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单系统适配器
 * Created by Deity on 2016/5/10.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<OrderEntity> mOrderList = new ArrayList<>();

    public OrderAdapter(Context context){
        this.context = context;
    }

    public void setData(List<OrderEntity> mOrderList){
        this.mOrderList = mOrderList;
    }

    public void addData(List<OrderEntity> mOrderList){
        this.mOrderList.addAll(mOrderList);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderEntity orderEntity = mOrderList.get(position);
        ImageLoader.getInstance().displayImage(orderEntity.getUser_display(),holder.i_user_display);
        holder.i_user_nickName.setText(orderEntity.getUser_nickName());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView i_user_display;
        private Button i_user_nickName;

        public ViewHolder(View view){
            super(view);
            i_user_display = (ImageView) view.findViewById(R.id.i_user_display);
            i_user_nickName = (Button) view.findViewById(R.id.i_user_nickName);
        }
    }
}
