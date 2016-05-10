package com.consonance.invitation.test;

import com.consonance.invitation.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deity on 2016/5/10.
 */
public class MonitorData {
    public static final String display_url_1="http://g.hiphotos.baidu.com/image/h%3D200/sign=875ef8ffb63533faeab6942e98d2fdca/0eb30f2442a7d93356139582aa4bd11372f00183.jpg";
    public final String display_url_2="http://img2.imgtn.bdimg.com/it/u=2143948337,3970654076&fm=21&gp=0.jpg";
    public final String display_url_3="http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=e613f6f04e90f60304e594430c229f2d/7e3e6709c93d70cf5756d0ccfcdcd100bba12b46.jpg";
    public final String display_url_4="http://img2.imgtn.bdimg.com/it/u=4223356602,671645851&fm=21&gp=0.jpg";
    public final String display_url_5="http://img4.imgtn.bdimg.com/it/u=2759668553,3123152347&fm=21&gp=0.jpg";
    public final String display_url_6="http://pic.6188.com/upload_6188s/flashAll/s800/20120628/1340846176MSWTQ3.jpg";

    public static List<OrderEntity> getOrderEntityList(){
        List<OrderEntity> list = new ArrayList<>();
        for (int i=0;i<6;i++){
            OrderEntity entity = new OrderEntity();
            entity.setUser_display(display_url_1);
            entity.setUser_nickName("天神Deity");
            list.add(entity);
        }
        return list;
    }
}
