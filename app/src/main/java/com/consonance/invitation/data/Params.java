package com.consonance.invitation.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deity on 2016/6/23.
 */
public class Params {
    public static final String EXTRA_IMAGE_LIST = "IMAGE_BUCKETS";
    /**等待上传的图片*/
    public static List<String> UPLOAD_IMG_LIST=new ArrayList<>();
    public enum UIEventType{
        MSG_SHOW_DIALOG,//显示进度条
        MSG_SHOW_MESSAGE,//显示信息
        MSG_DISMISS_DIALOG,//隐藏进度条
        MSG_LOAD_AVATAR,//加载用户头像
        MSG_LOAD_AVATAR_FAIL,//加载用户头像失败
        MSG_SORT_MESSAGE;//聊天信息排序
    }
}
