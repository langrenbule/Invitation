package com.consonance.invitation.chatting;

import android.content.Intent;
import android.os.Bundle;

import com.consonance.invitation.R;
import com.consonance.invitation.controller.CreateGroupController;
import com.consonance.invitation.data.InApplication;
import com.consonance.invitation.widget.CreateGroupView;

/**
创建群聊
 */
public class CreateGroupActivity extends BaseActivity{
	
	private CreateGroupView mCreateGroupView;
	private CreateGroupController mCreateGroupController;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		mCreateGroupView = (CreateGroupView) findViewById(R.id.create_group_view);
		mCreateGroupView.initModule();
		mCreateGroupController = new CreateGroupController(mCreateGroupView, this);
		mCreateGroupView.setListeners(mCreateGroupController);
	}


	public void startChatActivity(long groupId, String groupName) {
		Intent intent = new Intent();
		//设置跳转标志
		intent.putExtra("fromGroup", true);
		intent.putExtra(InApplication.GROUP_ID, groupId);
		intent.putExtra(InApplication.GROUP_NAME, groupName);
		intent.putExtra(InApplication.MEMBERS_COUNT, 1);
		intent.setClass(this, ChatActivity.class);
		startActivity(intent);
		finish();
	}

}
