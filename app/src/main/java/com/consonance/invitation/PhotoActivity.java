package com.consonance.invitation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.consonance.invitation.data.Params;

public class PhotoActivity extends Activity {

	private ArrayList<String> listViews = new ArrayList<>();;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private int currentIndex;

//	public List<Bitmap> bmp = new ArrayList<>();
//	public List<String> drr = new ArrayList<>();
//	public List<String> del = new ArrayList<>();
//	public int max;

	RelativeLayout photo_relativeLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);

		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (null!=listViews&&!listViews.isEmpty()){
					String imageUrl = listViews.get(currentIndex);
					listViews.remove(imageUrl);
					Params.UPLOAD_IMG_LIST.remove(imageUrl);
				}
				if (listViews.isEmpty()){
					finish();
				}
				adapter.setListViews(listViews);
				adapter.notifyDataSetChanged();
			}
		});
		Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
		photo_bt_enter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.addOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < Params.UPLOAD_IMG_LIST.size(); i++) {
			initListViews(Params.UPLOAD_IMG_LIST.get(i));
		}

		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		Intent intent = getIntent();
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private void initListViews(String imageUrl) {
		listViews.add(imageUrl);// 添加view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// 页面选择响应函数
			currentIndex = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

		}

		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

		}
	};

	class MyPageAdapter extends PagerAdapter {

		private List<String> listViews;// content

		private int size;// 页数

		public MyPageAdapter(List<String> listViews) {// 构造函数
															// 初始化viewpager的时候给的一个页面
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<String> listViews) {// 自己写的一个方法用来添加数据
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(PhotoActivity.this);
			imageView.setBackgroundColor(0xff000000);
			ViewPager.LayoutParams params =  new ViewPager.LayoutParams();
			imageView.setLayoutParams(params);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("file://"+listViews.get(position),imageView);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
