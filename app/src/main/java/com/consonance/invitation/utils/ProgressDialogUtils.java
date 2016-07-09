package com.consonance.invitation.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.consonance.invitation.R;

/**
 * 进度条工具类
 * Created by Deity on 2016/7/9.
 */
public class ProgressDialogUtils {
    private static ProgressDialog mDialog;

    public static void showDialog(Context context,String message){
        if (null==mDialog){
            mDialog = new ProgressDialog(context);
        }
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        mDialog.show();
    }

    public static void dissmissDialog(){
        if (null!=mDialog&&mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
