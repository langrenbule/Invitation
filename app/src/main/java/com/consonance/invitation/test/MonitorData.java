package com.consonance.invitation.test;

import com.consonance.invitation.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deity on 2016/5/10.
 */
public class MonitorData {
    public static final String[] display_url=new String[]{
            "http://a.hiphotos.baidu.com/zhidao/pic/item/024f78f0f736afc33199a401b119ebc4b7451238.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfa95c7a1cab78f8c5494ee7b6e.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/87/8a/ae/878aaeca3226240fe7991b7dbc46c5da.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/2e/7d/31/2e7d312d24158afd9cf770f70c489c1c.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/9d/b3/6c/9db36cd350564cc3bf09b863120682b6.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/26/99/4c/26994c145b73f09ecdb759abc15ded99.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/93/c9/61/93c961df564c1cfdaa8cda221d37a426.jpg"
    };

    public static List<OrderEntity> getOrderEntityList(){
        List<OrderEntity> list = new ArrayList<>();
        for (int i=0;i<6;i++){
            OrderEntity entity = new OrderEntity();
            entity.setUser_display(display_url[i]);
            entity.setUser_nickName("天神Deity");
            list.add(entity);
        }
        return list;
    }
}
