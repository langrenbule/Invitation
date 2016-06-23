package com.consonance.invitation.event;

import com.consonance.invitation.data.Params;

/**
 * Created by Deity on 2016/6/23.
 */
public class UIEvent {
    private Params.UIEventType cmdType;
    private Object message;

    public Params.UIEventType getCmdType() {
        return cmdType;
    }

    public void setCmdType(Params.UIEventType cmdType) {
        this.cmdType = cmdType;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setData(Params.UIEventType cmdType, Object message){
        this.cmdType = cmdType;
        this.message = message;
    }
}
