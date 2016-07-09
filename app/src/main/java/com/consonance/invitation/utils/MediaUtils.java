package com.consonance.invitation.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 多媒体工具类
 * Created by Deity on 2016/7/7.
 */
public class MediaUtils {

    /**
     * 照片选取处理,返回照片的地址
     * @param context 上下文
     * @param selectedImg 选取的照片上下文
     * @return 返回照片的地址，如果没有返回空
     */
    public static String getImageFile(Context context, Uri selectedImg) {
        String resultFilePath = null;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImg, filePathColumn, null, null, null);
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        boolean isSuccess = cursor.moveToFirst();
        if (isSuccess){
            resultFilePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return resultFilePath;
    }

}
