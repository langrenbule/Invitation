package com.consonance.invitation.jpushapi;

import android.util.Log;

import com.consonance.invitation.data.Params;
import com.consonance.invitation.event.UIEvent;
import com.consonance.invitation.utils.EventBusHelper;
import com.consonance.invitation.utils.HandleResponseCode;
import com.consonance.invitation.utils.ProgressDialogUtils;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Deity on 2016/7/9.
 */
public class JPushService {
    private static final String TAG=JPushService.class.getSimpleName();


    public static void uploadUserAvatar(final String path) {
        JMessageClient.updateUserAvatar(new File(path), new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                ProgressDialogUtils.dissmissDialog();
                if (status == 0) {
                    Log.i(TAG, "Update avatar succeed path " + path);
                    EventBusHelper.sendUIEvent(Params.UIEventType.MSG_LOAD_AVATAR,path);
//                    loadUserAvatar(path);
                } else {//如果头像上传失败，删除剪裁后的文件
                    EventBusHelper.sendUIEvent(Params.UIEventType.MSG_LOAD_AVATAR_FAIL,status);
//                    HandleResponseCode.onHandle(mContext, status, false);
                    File file = new File(path);
                    if (file.delete()) {
                        Log.d(TAG, "Upload failed, delete cropped file succeed");
                    }
                }
            }
        });
    }
}
