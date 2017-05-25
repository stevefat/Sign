package com.gisroad.sign.util;
/**
 * Created by stevefat on 17-5-23.
 */

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.gisroad.sign.BuildConfig;
import com.gisroad.sign.SignApp;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-23 下午5:15
 */
public class ToolsUtil {

    /**
     * 获取软件的版本code
     * @return
     */
    public static int getVersionCode() {
        try {

            PackageInfo pi = SignApp.getInstance().getPackageManager().getPackageInfo(SignApp.getInstance().getPackageName(), 0);
            int version = pi.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取软件的版本code
     * @return
     */
    public static String getVersionName() {
        try {

            PackageInfo pi = SignApp.getInstance().getPackageManager().getPackageInfo(SignApp.getInstance().getPackageName(), 0);
            String versionName = pi.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
