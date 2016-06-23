package com.consonance.invitation.utils;

import com.consonance.invitation.data.Params;
import com.consonance.invitation.event.UIEvent;

import cn.jpush.im.android.eventbus.EventBus;


/**
 * Created by Deity on 2016/6/23.
 */
public class EventBusHelper {

    public static void sendUIEvent(Params.UIEventType cmdType,Object message){
        UIEvent event = new UIEvent();
        event.setData(cmdType,message);
        EventBus.getDefault().post(event);
    }
}
