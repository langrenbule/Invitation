package com.consonance.invitation.utils;

import java.io.File;

import android.os.Environment;


/**
 * 
 * <table><tr><td><b>ClassName</b></td><td>ProjectPath</td></tr>
 * <tr><td><b>Function</b></td><td> 获取各种项目所需的路径信息.</td></tr>
 * <tr><td><b>Reason</b></td><td>项目里所用到的文件夹/文件路径.</td></tr>
 * <tr><td><b>Date</b></td><td>2014年11月12日 下午4:20:11 <br></td></tr></table>
 *
 * @author zhangyonglin@rd.keytop.com.cn
 * @version 1.0.0
 */
public class ProjectPath {

	private static String SD_PATH;
	private static String SD_FILES_PATH;
	private static String SD_CACHE_PATH;

	/**
	 * 
	 * getSDPath:获取SD卡路径,如果无挂载SD卡或获取异常则返回null . <br>
	 *
	 * @return 本地的SD卡路径(根目录)
	 */
	public static String getSDPath() {
		if(SD_PATH == null){
			boolean sdCardExist = MyUtils.isSDCardEnable();
			// 判断sd卡是否存在
			if (sdCardExist) {
				SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
			} 
		}
		return SD_PATH;
	}

    /**
     * 
     * getRootDirectoryPath:获取系统存储路径 . <br>
     *
     * @return Environment.getRootDirectory().getAbsolutePath()
     */
    public static String getRootDirectoryPath()  
    {  
        return Environment.getRootDirectory().getAbsolutePath();  
    }  
	
}
