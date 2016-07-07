package com.consonance.invitation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.consonance.invitation.adapter.InvitationFragmentAdapter;
import com.consonance.invitation.adapter.SquareAdapter;
import com.consonance.invitation.chatting.ConversationListFragment;
import com.consonance.invitation.chatting.CropImageActivity;
import com.consonance.invitation.chatting.FixProfileActivity;
import com.consonance.invitation.chatting.LoginActivity;
import com.consonance.invitation.chatting.MeFragment;
import com.consonance.invitation.chatting.ReloginActivity;
import com.consonance.invitation.controller.MainController;
import com.consonance.invitation.data.InApplication;
import com.consonance.invitation.data.Params;
import com.consonance.invitation.event.UIEvent;
import com.consonance.invitation.fragment.OrderFragment;
import com.consonance.invitation.fragment.ReleaseFragment;
import com.consonance.invitation.fragment.SquareFragment;
import com.consonance.invitation.utils.EventBusHelper;
import com.consonance.invitation.utils.FileHelper;
import com.consonance.invitation.utils.SharePreferenceManager;
import com.deity.customview.widget.NavigationBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{//implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, ViewPager.OnPageChangeListener
//    @BindView(R.id.order_list)
    private static final int REFRESH_COMPLETE = 0X110;
    public RecyclerView mRecyclerView;
    private SquareAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
//    private MainController mMainController;
    private Uri mUri;
    private Fragment[] mFragmentArray;
    private ConversationListFragment conversationListFragment;
    private SquareFragment squareFragment;
    private ReleaseFragment releaseFragment;
    private OrderFragment orderFragment;
//    private SetFragment setFragment;
    private MeFragment meFragment;

    //
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private List<NavigationBar> mNavigationBarList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        initRecyclerView();
        initView();
        initParams();
        pagerAdapter();
        initEvent();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        //第一次登录需要设置昵称
        boolean flag = SharePreferenceManager.getCachedFixProfileFlag();
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
            Intent intent = new Intent();
            if (null != SharePreferenceManager.getCachedUsername()) {
                intent.putExtra("userName", SharePreferenceManager.getCachedUsername());
                intent.putExtra("avatarFilePath", SharePreferenceManager.getCachedAvatarPath());
                intent.setClass(this, ReloginActivity.class);
            } else {
                intent.setClass(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            InApplication.setPicturePath(myInfo.getAppKey());
            if (TextUtils.isEmpty(myInfo.getNickname()) && flag) {
                Intent intent = new Intent();
                intent.setClass(this, FixProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }
        conversationListFragment.sortConvList();
        super.onResume();
    }

    public void onEventBackgroundThread(UIEvent event){
        switch (event.getCmdType()){
            case MSG_SORT_MESSAGE:
                conversationListFragment.sortConvList();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == InApplication.REQUEST_CODE_TAKE_PHOTO) {
            String path = meFragment.getPhotoPath();
            if (path != null) {
                File file = new File(path);
                if (file.isFile()) {
                    mUri = Uri.fromFile(file);
                    //拍照后直接进行裁剪
//                mMainController.cropRawPhoto(mUri);
                    Intent intent = new Intent();
                    intent.putExtra("filePath", mUri.getPath());
                    intent.setClass(this, CropImageActivity.class);
                    startActivityForResult(intent, InApplication.REQUEST_CODE_CROP_PICTURE);
                }
            }
        } else if (requestCode == InApplication.REQUEST_CODE_SELECT_PICTURE) {
            if (data != null) {
                Uri selectedImg = data.getData();
                if (selectedImg != null) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = this.getContentResolver().query(selectedImg, filePathColumn, null, null, null);
                    if (null == cursor) {
                        String path = selectedImg.getPath();
                        File file = new File(path);
                        if (file.isFile()) {
                            copyAndCrop(file);
                            return;
                        } else {
                            Toast.makeText(this, this.getString(R.string.picture_not_found),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (!cursor.moveToFirst()) {
                        Toast.makeText(this, this.getString(R.string.picture_not_found),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    if (path != null) {
                        File file = new File(path);
                        if (!file.isFile()) {
                            Toast.makeText(this, this.getString(R.string.picture_not_found),
                                    Toast.LENGTH_SHORT).show();
                            cursor.close();
                        } else {
                            //如果是选择本地图片进行头像设置，复制到临时文件，并进行裁剪
                            copyAndCrop(file);
                            cursor.close();
                        }
                    }
                }
            }
        } else if (requestCode == InApplication.REQUEST_CODE_CROP_PICTURE) {
//            mMainController.uploadUserAvatar(mUri.getPath());
            String path = data.getStringExtra("filePath");
            if (path != null) {
                MainController.getInstance().uploadUserAvatar(path);
            }
        } else if (resultCode == InApplication.RESULT_CODE_ME_INFO) {
            String newName = data.getStringExtra("newName");
            if (!TextUtils.isEmpty(newName)) {
                MainController.getInstance().refreshNickname(newName);
            }
        }
    }

    /**
     * 复制后裁剪文件
     * @param file 要复制的文件
     */
    private void copyAndCrop(final File file) {
        FileHelper.getInstance().copyAndCrop(file, this, new FileHelper.CopyFileCallback() {
            @Override
            public void copyCallback(Uri uri) {
                mUri = uri;
//                mMainController.cropRawPhoto(mUri);
                Intent intent = new Intent();
                intent.putExtra("filePath", mUri.getPath());
                intent.setClass(MainActivity.this, CropImageActivity.class);
                startActivityForResult(intent, InApplication.REQUEST_CODE_CROP_PICTURE);
            }
        });
    }

    private void initParams(){
        conversationListFragment = new ConversationListFragment();
        squareFragment = new SquareFragment();
        releaseFragment = new ReleaseFragment();
        orderFragment = new OrderFragment();
//        setFragment = new SetFragment();
        meFragment = new MeFragment();
        mFragmentArray = new Fragment[]{squareFragment,conversationListFragment,releaseFragment,orderFragment,meFragment};
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_first));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_second));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_third));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_fourth));
        mNavigationBarList.add((NavigationBar)findViewById(R.id.tab_set));

        mNavigationBarList.get(0).setAlpha(1.0f);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void pagerAdapter() {
//        mFragmentArray = new Fragment[]{new SquareFragment(),new ConversationListFragment(),new ReleaseFragment(),new OrderFragment(),new SetFragment()};
        mFragmentList = Arrays.asList(mFragmentArray);
        InvitationFragmentAdapter adapter = new InvitationFragmentAdapter(getSupportFragmentManager());
        adapter.setData(mFragmentList);
        mViewPager.setAdapter(adapter);
    }

    private void initEvent() {
        for(int i = 0; i < mNavigationBarList.size(); i++){
            mNavigationBarList.get(i).setOnClickListener(this);
            mNavigationBarList.get(i).setTag(i);
        }
        mViewPager.addOnPageChangeListener(this);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what){
                case REFRESH_COMPLETE:
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        };
    };

    @Override
    public void onClick(View v) {
        int number = (Integer) v.getTag();
        changeAlpha(number);
        mViewPager.setCurrentItem(number, false);
    }

    public void changeAlpha(int number){
        for (NavigationBar btn:mNavigationBarList){
            btn.setAlpha(0f);
        }
        mNavigationBarList.get(number).setAlpha(1.0f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffsetPixels != 0){
            mNavigationBarList.get(position).setAlpha(1 - positionOffset);
            mNavigationBarList.get(position+1).setAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
