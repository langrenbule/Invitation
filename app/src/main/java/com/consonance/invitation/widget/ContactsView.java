package com.consonance.invitation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.consonance.invitation.R;


public class ContactsView extends LinearLayout{

	private TextView mTitle;
    private Context mContext;
	
	public ContactsView(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public void initModule(){
        mTitle = (TextView) findViewById(R.id.title_bar_title);
        mTitle.setText(mContext.getString(R.string.actionbar_contact));
	}

}
