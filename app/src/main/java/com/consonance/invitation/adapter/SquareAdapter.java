package com.consonance.invitation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.consonance.invitation.R;
import com.consonance.invitation.entity.OrderEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单系统适配器
 * Created by Deity on 2016/5/10.
 */
public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<OrderEntity> mOrderList = new ArrayList<>();
    private RecycleViewOnClickListener recycleViewOnClickListener;

    public SquareAdapter(Context context){
        this.context = context;
    }

    public void setData(List<OrderEntity> mOrderList){
        this.mOrderList = mOrderList;
    }

    public void addData(List<OrderEntity> mOrderList){
        this.mOrderList.addAll(mOrderList);
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener recycleViewOnClickListener) {
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public interface RecycleViewOnClickListener{
        void onItemClick(View view,OrderEntity data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        //将创建的View注册点击事件
        rootView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderEntity orderEntity = mOrderList.get(position);
        ImageLoader.getInstance().displayImage(orderEntity.getUser_display(),holder.i_user_display);
        holder.i_user_nickName.setText(orderEntity.getUser_nickName());
        holder.itemView.setTag(orderEntity);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context,"点击事件",Toast.LENGTH_LONG).show();
        if (null!=recycleViewOnClickListener){
            recycleViewOnClickListener.onItemClick(view, (OrderEntity) view.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView i_user_display;
        private TextView i_user_nickName;

        public ViewHolder(View view){
            super(view);
            i_user_display = (ImageView) view.findViewById(R.id.i_user_display);
            i_user_nickName = (TextView) view.findViewById(R.id.i_user_nickName);
        }
    }
}
