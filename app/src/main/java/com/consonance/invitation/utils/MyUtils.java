package com.consonance.invitation.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 
 * <table><tr><td><b>ClassName</b></td><td>MyUtils</td></tr>
 * <tr><td><b>Function</b></td><td>小工具集合 </td></tr>
 * <tr><td><b>Reason</b></td><td>在日常开发中所需要的小工具方法,欢迎添加,call me!!!</td></tr>
 * <tr><td><b>Date</b></td><td>2014年11月12日 下午4:12:37 <br></td></tr></table>
 *
 * @author zhangyonglin@rd.keytop.com.cn
 * @version 1.0.0
 */
public class MyUtils {

	private MyUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 
	 * getProcessorNum:获取本系统CPU个数. <br>
	 * 
	 * @see Runtime#availableProcessors()
	 * @return CPU个数
	 */
	public static int getProcessorNum() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 
	 * isMobileNo:验证一个字符串是否为合法的手机号码. <br>
	 * <font color="red"><b>注意</b></font>:三大运营商的号段未验证的很细致<br>
	 * 
	 * @param mobileNo
	 *            只能是11位的国内手机号码,不支持加国际码的国内手机号验证
	 * @return true为合法,false为不合法
	 */
	public static boolean isMobileNo(String mobileNo) {
		// ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
		// ^(13[0-9]{9}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}$
		Pattern p = Pattern.compile("^1[34578]\\d{9}$");
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	/**
	 * 
	 * isTelephoneNo:验证一个字符串是否为合法的电话号码. <br>
	 * 
	 * @param telephoneNo
	 *            电话号码字符串:支持3-4位区号，7-8位直播号码，1－4位分机号
	 * @return true为合法,false为不合法
	 */
	public static boolean isTelephoneNo(String telephoneNo) {
		Pattern p = Pattern
				.compile("^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$");
		Matcher m = p.matcher(telephoneNo);
		return m.matches();
	}

	/**
	 * 
	 * isIP:验证一个字符串是否为合法的IPv4地址. <br>
	 * 
	 * @param ip
	 *            需要数字之间只能用"."隔开,如:192.168.0.46
	 * @return true为合法,false为不合法
	 */
	public static boolean isIP(String ip) {
		Pattern p = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
		Matcher m = p.matcher(ip);
		return m.matches();
	}


	/**
	 * 
	 * isConnected:判断网络是否连接. <br>
	 *
	 * @param context
	 * @return 是否连接
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * isWifi:判断是否是wifi连接. <br>
	 *
	 * @param context
	 * @return 是否是wifi连接
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 
	 * openSetting:打开系统设置界面. <br>
	 *
	 * @param activity
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings","com.android.settings.Settings$WirelessSettingsActivity");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
	
	/**
	 * 
	 * openKeybord:打开软键盘. <br>
	 *
	 * @param editText 输入框
	 * @param context
	 */
	public static void openKeybord(EditText editText, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 
	 * closeKeybord:关闭软键盘. <br>
	 *
	 * @param editText 输入框
	 * @param context
	 */
	public static void closeKeybord(EditText editText, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 
	 * getScreenWidth:获得屏幕宽度. <br>
	 *
	 * @param context
	 * @return 单位px
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 
	 * getScreenHeight:获得屏幕高度. <br>
	 *
	 * @param context
	 * @return 单位px
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}



	/**
	 * 
	 * snapShotWithStatusBar:获取当前屏幕截图，包含状态栏. <br>
	 *
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 
	 * snapShotWithoutStatusBar:获取当前屏幕截图，不包含状态栏. <br>
	 *
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 
	 * isSDCardEnable:判断SDCard是否可用. <br>
	 *
	 * @return 是否可用
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 
	 * getSDCardAllSize:获取SD卡的剩余(可用)容量. <br>
	 * <pre>
	 * 需要注册权限:&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;
	 * </pre>
	 * <ul>
	 * <li>在1.0.4版本,将方法名从getSDCardAllSize修改为getSDCardAvailableSize,被zhangyl修改</li>
	 * <li>在1.0.4版本,增加更详细的注释,被zhangyl修改.</li>
	 * </ul>
	 * @return 单位byte
	 */
	public static long getSDCardAvailableSize()  
    {  
        if (MyUtils.isSDCardEnable())  
        {  
            StatFs stat = new StatFs(ProjectPath.getSDPath());  
            //获取block的SIZE
            long blockSize = stat.getBlockSize(); 
            //空闲(可用)的Block的数量
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize; 
        }  
        return 0L;  
    } 
	
	/**
	 * 
	 * dp2px:dp转px. <br>
	 * @param context
	 * @param dpVal
	 * @return px
	 */
	public static int dp2px(Context context, float dpVal) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics())+0.5f);
	}

	/**
	 * 
	 * sp2px:sp转px. <br>
	 *
	 * @param context
	 * @param spVal
	 * @return px
	 */
	public static int sp2px(Context context, float spVal) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics())+0.5f);
	}

	/**
	 * 
	 * px2dp:px转dp. <br>
	 *
	 * @param context
	 * @param pxVal
	 * @return dp
	 */
	public static float px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * 
	 * px2sp:px转sp. <br>
	 *
	 * @param context
	 * @param pxVal
	 * @return sp
	 */
	public static float px2sp(Context context, float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}

	 /**
     * isEmpty4String:判断输入的字符串参数是否为空.<br>
     * 比{@link TextUtils#isEmpty(CharSequence)}校验的更细致,<br>
     * 当然也比较耗时,如果此操作频繁,则需要调用者权衡利弊.<br>
     * 判断的逻辑:<pre>
     * return null==input || 0==input.length() || 0==input.replaceAll("\\s", "").length();
     * </pre>
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty4String(String input) {
        return null==input || 0==input.length() || 0==input.replaceAll("\\s", "").length();
    }
	

    
    /**
     * 
     * timeSecondToHour:时间转换工具方法:秒数转换成小时. <br>
     * 如:3654秒转换的结果是"1.02"小时.<br>
     * <ul>
     * <li>在1.0.5版本中,此方法由zhangyl创建</li>
     * </ul>
     *
     * @param seconds 秒数
     * @return 格式为浮点数的字符串形式,保留小数点后两位,并且四舍五入
     */
	public static String timeSecondToHour(int seconds){
		return String.format(Locale.getDefault(),"%.2f", seconds/3600.00D);
	}
	

    
	/**
	 * simplifyMoney:去掉金额多余的.和0以及前后空格,使金额保持最短且有效<br>
	 * 如:将**.10转换为**.1和**.00转换为**;
	 * @param money2str 单精度,双精度,整型都可以,但不支持有","的金额操作
	 * @return 如果形参为空,则直接返回形参;
	 */
	public static String simplifyMoney(String money2str) {
		if(TextUtils.isEmpty(money2str)){
			return money2str;
		}
		if (money2str.indexOf(".") > -1) {// 没有等于0的情况
			money2str = money2str
					.replaceAll("(^\\s+)|(\\s+$)", "")//取掉前后空格
					.replaceAll("(^0+)|(0+$)|([.]$)", "")//取掉前0后0后.
					.replaceAll("[.]$", "")//取掉最后一位.
					.replaceAll("^[.]", "0.");//将第一位的.xx更改为0.xx
		} else {
			money2str = money2str.replaceAll("^0+", "");//取掉前0
		}
		return money2str.equals("") ? "0" : money2str;
	}

	
	/**
	 * 
	 * isAppInstalled:(判断是否安装某个应用). <br>
	 * <ul>
	 * <li>由谭博创建</li>
	 * </ul>
	 * @param context
	 * @param packagename
	 * @return
	 */
	public static boolean isAppInstalled(Context context,String packagename)
	{
	PackageInfo packageInfo;        
	try {
	            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
	         }catch (NameNotFoundException e) {
	            packageInfo = null;
	            e.printStackTrace();
	         }
	         if(packageInfo ==null){
	            //System.out.println("没有安装");
	            return false;
	         }else{
	            //System.out.println("已经安装");
	            return true;
	        }
	}



}
