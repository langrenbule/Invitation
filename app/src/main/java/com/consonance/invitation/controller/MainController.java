package com.consonance.invitation.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.consonance.invitation.R;
import com.consonance.invitation.adapter.ViewPagerAdapter;
import com.consonance.invitation.chatting.ContactsFragment;
import com.consonance.invitation.chatting.ConversationListFragment;
import com.consonance.invitation.chatting.MainActivity;
import com.consonance.invitation.chatting.MeFragment;
import com.consonance.invitation.data.InApplication;
import com.consonance.invitation.utils.HandleResponseCode;
import com.consonance.invitation.widget.MainView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class MainController implements OnClickListener, OnPageChangeListener {

    private final static String TAG = "MainController";



    private ConversationListFragment mConvListFragment;
    private MeFragment mMeActivity;
    private MainView mMainView;
    private MainActivity mContext;
    private ProgressDialog mDialog;
    // 裁剪后图片的宽(X)和高(Y), 720 X 720的正方形。
    private static int OUTPUT_X = 720;
    private static int OUTPUT_Y = 720;
    private static MainController mainController;

    private MainController(){};

    public static MainController getInstance(){
        if (null==mainController){
            synchronized (MainController.class){
                if(null==mainController){
                    mainController = new MainController();
                }
            }
        }
        return mainController;
    }

    public MainController(MainView mMainView, MainActivity context) {
        this.mMainView = mMainView;
        this.mContext = context;
        setViewPager();
    }

    private void setViewPager() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        // init Fragment
        mConvListFragment = new ConversationListFragment();
        ContactsFragment contactsActivity = new ContactsFragment();
        mMeActivity = new MeFragment();
        fragments.add(mConvListFragment);
        fragments.add(contactsActivity);
        fragments.add(mMeActivity);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext.getSupportFragmentManger(),
                fragments);
        mMainView.setViewPagerAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.actionbar_msg_btn:
                mMainView.setCurrentItem(0);
                break;
            case R.id.actionbar_contact_btn:
                mMainView.setCurrentItem(1);
                break;
            case R.id.actionbar_me_btn:
                mMainView.setCurrentItem(2);
                break;
        }
    }

    public String getPhotoPath() {
        return mMeActivity.getPhotoPath();
    }

    /**
     * 裁剪图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", OUTPUT_X);
        intent.putExtra("outputY", OUTPUT_Y);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mContext.startActivityForResult(intent, InApplication.REQUEST_CODE_CROP_PICTURE);
    }



    public void uploadUserAvatar(final String path) {
        mDialog = new ProgressDialog(mContext);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(mContext.getString(R.string.updating_avatar_hint));
        mDialog.show();
        JMessageClient.updateUserAvatar(new File(path), new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                mDialog.dismiss();
                if (status == 0) {
                    Log.i(TAG, "Update avatar succeed path " + path);
                    loadUserAvatar(path);
                //如果头像上传失败，删除剪裁后的文件
                }else {
                    HandleResponseCode.onHandle(mContext, status, false);
                    File file = new File(path);
                    if (file.delete()) {
                        Log.d(TAG, "Upload failed, delete cropped file succeed");
                    }
                }
            }
        });
    }

    private void loadUserAvatar(String path) {
        if (path != null)
            mMeActivity.loadUserAvatar(path);
    }

    @Override
    public void onPageSelected(int index) {
        mMainView.setButtonColor(index);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public void sortConvList() {
        mConvListFragment.sortConvList();
    }

    public void refreshNickname(String newName) {
        mMeActivity.refreshNickname(newName);
    }
}
