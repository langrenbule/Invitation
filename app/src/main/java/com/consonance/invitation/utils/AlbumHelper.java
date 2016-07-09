package com.consonance.invitation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;

import com.consonance.invitation.entity.ImageBucket;
import com.consonance.invitation.entity.ImageItem;

/**
 * 专辑帮助类
 * 
 * @author Administrator
 * 
 */
public class AlbumHelper {
	private Context context;
	private ContentResolver mContentResolver;
	/**
	 * 是否创建了图片集
	 */
	boolean hasBuildImagesBucketList = false;

	// 缩略图列表
	HashMap<String, String> thumbnailList = new HashMap<>();
	HashMap<String, ImageBucket> bucketList = new HashMap<>();

	private static AlbumHelper instance;

	private AlbumHelper() {
	}

	/**单例模式*/
	public static AlbumHelper getHelper() {
		if (instance == null) {
			instance = new AlbumHelper();
		}
		return instance;
	}

	/**
	 * 初始化
	 * 
	 * @param context 上下文
	 */
	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
			mContentResolver = context.getContentResolver();
		}
	}

	/**
	 * 得到缩略图
	 */
	private void getThumbnail() {
		String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID,Thumbnails.DATA };/**关心ID/IMAGE_ID/DATA数据*/
		Cursor cursor = mContentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection,null, null, null);
		getThumbnailColumnData(cursor);
	}

	/**
	 * 从数据库中得到缩略图
	 * 
	 * @param cursor
	 */
	private void getThumbnailColumnData(Cursor cursor) {
		if (cursor.moveToFirst()) {
			int image_id;
			String image_path;
			int image_idColumn = cursor.getColumnIndex(Thumbnails.IMAGE_ID);
			int dataColumn = cursor.getColumnIndex(Thumbnails.DATA);

			do {
				image_id = cursor.getInt(image_idColumn);
				image_path = cursor.getString(dataColumn);
				thumbnailList.put(String.valueOf(image_id), image_path);
			} while (cursor.moveToNext());
		}
	}

	/**
	 * 得到图片集
	 */
	void buildImagesBucketList() {
		long startTime = System.currentTimeMillis();
		getThumbnail();// 构造缩略图索引
		String columns[] = new String[] { Media._ID, Media.BUCKET_ID,Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,Media.SIZE, Media.BUCKET_DISPLAY_NAME };// 构造相册索引
		Cursor cur = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,null);// 得到一个游标
		if (cur.moveToFirst()) {
			// 获取指定列的索引
			int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
			int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
			int photoNameIndex = cur.getColumnIndexOrThrow(Media.DISPLAY_NAME);
			int photoTitleIndex = cur.getColumnIndexOrThrow(Media.TITLE);
			int photoSizeIndex = cur.getColumnIndexOrThrow(Media.SIZE);
			int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
			int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
			int picasaIdIndex = cur.getColumnIndexOrThrow(Media.PICASA_ID);
			// 获取图片总数
			int totalNum = cur.getCount();

			do {
				String _id = cur.getString(photoIDIndex);
				String name = cur.getString(photoNameIndex);
				String path = cur.getString(photoPathIndex);
				String title = cur.getString(photoTitleIndex);
				String size = cur.getString(photoSizeIndex);
				String bucketName = cur.getString(bucketDisplayNameIndex);
				String bucketId = cur.getString(bucketIdIndex);
				String picasaId = cur.getString(picasaIdIndex);

				ImageBucket bucket = bucketList.get(bucketId);
				if (bucket == null) {
					bucket = new ImageBucket();
					bucketList.put(bucketId, bucket);
					bucket.imageList = new ArrayList<>();
					bucket.bucketName = bucketName;
				}
				bucket.count++;
				ImageItem imageItem = new ImageItem();
				imageItem.imageId = _id;
				imageItem.imagePath = path;
				imageItem.thumbnailPath = thumbnailList.get(_id);
				bucket.imageList.add(imageItem);

			} while (cur.moveToNext());
		}
		hasBuildImagesBucketList = true;//已初始化
		long endTime = System.currentTimeMillis();
		Log.d(AlbumHelper.class.getSimpleName(), "耗时:" + (endTime - startTime) + "ms");
	}

	/**
	 * 得到图片集/相册集合
	 * @param refresh
	 * @return
	 */
	public List<ImageBucket> getImagesBucketList(boolean refresh) {
		if (refresh ||!hasBuildImagesBucketList) {//只要相册集还没创建都会执行
			buildImagesBucketList();
		}
		List<ImageBucket> tmpList = new ArrayList<>();
		Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, ImageBucket> entry = itr.next();
			tmpList.add(entry.getValue());
		}
		return tmpList;
	}
}
