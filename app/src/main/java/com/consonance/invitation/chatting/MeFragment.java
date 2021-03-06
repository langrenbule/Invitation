package com.consonance.invitation.chatting;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.consonance.invitation.R;
import com.consonance.invitation.controller.MeController;
import com.consonance.invitation.data.InApplication;
import com.consonance.invitation.utils.BitmapLoader;
import com.consonance.invitation.utils.DialogCreator;
import com.consonance.invitation.utils.FileHelper;
import com.consonance.invitation.utils.HandleResponseCode;
import com.consonance.invitation.utils.SharePreferenceManager;
import com.consonance.invitation.widget.MeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class MeFragment extends BaseFragment {

    private static final String TAG = MeFragment.class.getSimpleName();

    private View mRootView;
    private MeView mMeView;
    private MeController mMeController;
    private Context mContext;
    private String mPath;
//    private boolean mIsShowAvatar = false;//发现切回来后，照片又不显示了
    private boolean mIsGetAvatar = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        ViewGroup parent = (ViewGroup) mRootView.getParent();
//        if (parent != null) {
//            parent.removeAllViewsInLayout();
//        }
//        container.removeAllViews();
        mRootView = inflater.inflate(R.layout.fragment_me,(ViewGroup) getActivity().findViewById(R.id.root_main), false);
        mMeView = (MeView) mRootView.findViewById(R.id.me_view);
        mMeView.initModule(mDensity, mWidth);
        mMeController = new MeController(mMeView, this, mWidth);
        mMeView.setListeners(mMeController);
        return mRootView;
    }

    @Override
    public void onResume() {
//        if (!mIsShowAvatar) {
            UserInfo myInfo = JMessageClient.getMyInfo();
            if (myInfo != null) {
                if (!TextUtils.isEmpty(myInfo.getAvatar())) {
                    myInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int status, String desc, Bitmap bitmap) {
                            if (status == 0) {
                                mMeView.showPhoto(bitmap);
//                                mIsShowAvatar = true;
                            } else {
                                HandleResponseCode.onHandle(mContext, status, false);
                            }
                        }
                    });
                }
//                mMeView.showNickName(myInfo.getNickname());
            //用户由于某种原因导致登出,跳转到重新登录界面
            } else {
                Intent intent = new Intent();
                intent.setClass(mContext, ReloginActivity.class);
                startActivity(intent);
            }
//        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //退出登录
    public void Logout() {
        // TODO Auto-generated method stub
        final Intent intent = new Intent();
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            intent.putExtra("userName", info.getUserName());
            File file = info.getAvatarFile();
            if (file != null && file.isFile()) {
                intent.putExtra("avatarFilePath", file.getAbsolutePath());
            } else {
                String path = FileHelper.getUserAvatarPath(info.getUserName());
                file = new File(path);
                if (file.exists()) {
                    intent.putExtra("avatarFilePath", file.getAbsolutePath());
                }
            }
            SharePreferenceManager.setCachedUsername(info.getUserName());
            SharePreferenceManager.setCachedAvatarPath(file.getAbsolutePath());
            JMessageClient.logout();
            intent.setClass(mContext, ReloginActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "user info is null!");
        }
    }

    public void StartSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    public void startMeInfoActivity() {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), MeInfoActivity.class);
        startActivityForResult(intent, InApplication.REQUEST_CODE_ME_INFO);
    }

    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    //照相
    public void takePhoto() {
        if (FileHelper.isSdCardExist()) {
            mPath = FileHelper.createAvatarPath(JMessageClient.getMyInfo().getUserName());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
            try {
                getActivity().startActivityForResult(intent, InApplication.REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                Toast.makeText(this.getActivity(), mContext.getString(R.string.camera_not_prepared), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), mContext.getString(R.string.jmui_sdcard_not_exist_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPhotoPath() {
        return mPath;
    }

    //选择本地图片
    public void selectImageFromLocal() {
        if (FileHelper.isSdCardExist()) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            getActivity().startActivityForResult(intent, InApplication.REQUEST_CODE_SELECT_PICTURE);
        } else {
            Toast.makeText(this.getActivity(), mContext.getString(R.string.jmui_sdcard_not_exist_toast),Toast.LENGTH_SHORT).show();
        }

    }

    public void loadUserAvatar(String path) {
        if (null != mMeView) {
            mIsGetAvatar = true;
            mMeView.showPhoto(path);
        }
    }

    //预览头像
    public void startBrowserAvatar() {
        final UserInfo myInfo = JMessageClient.getMyInfo();
        //如果本地保存了图片，直接加载，否则下载
        if (mIsGetAvatar) {
            String path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            File file = new File(path);
            if (file.exists()) {
                Intent intent = new Intent();
                intent.putExtra("browserAvatar", true);
                intent.putExtra("avatarPath", path);
                intent.setClass(mContext, BrowserViewPagerActivity.class);
                startActivity(intent);
            } else if (!TextUtils.isEmpty(myInfo.getAvatar())) {
                getBigAvatar(myInfo);
            }
        } else if (!TextUtils.isEmpty(myInfo.getAvatar())) {
            getBigAvatar(myInfo);
        }
    }

    private void getBigAvatar(final UserInfo myInfo) {
        final Dialog dialog = DialogCreator.createLoadingDialog(mContext,mContext.getString(R.string.jmui_loading));
        dialog.show();
        myInfo.getBigAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int status, String desc, Bitmap bitmap) {
                if (status == 0) {
                    mIsGetAvatar = true;
                    String path = BitmapLoader.saveBitmapToLocal(bitmap, mContext, myInfo.getUserName());
                    Intent intent = new Intent();
                    intent.putExtra("browserAvatar", true);
                    intent.putExtra("avatarPath", path);
                    intent.setClass(mContext, BrowserViewPagerActivity.class);
                    startActivity(intent);
                } else {
                    HandleResponseCode.onHandle(mContext, status, false);
                }
                dialog.dismiss();
            }
        });
    }

    public void refreshNickname(String newName) {
        mMeView.showNickName(newName);
    }
}
