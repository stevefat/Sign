package com.gisroad.sign.service;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gisroad.sign.SignApp;

import java.io.File;

/**
 * Created by stevefat on 17-5-22.
 */

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-22 上午9:44
 */
public class DownSignService extends Service {
    private DownReceiver downReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        downReceiver = new DownReceiver();
        registerReceiver(downReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downReceiver);
    }


    class DownReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //下载完成了
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                String file = context.getExternalCacheDir() + "/sign.apk";

                openPackage(file);

            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                //点击了通知栏
                Toast.makeText(context, "点击了通知栏", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 安装包路径
     */
    private boolean openPackage(final String target) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(target);
        if (file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + target), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            return true;
        }
        return false;
    }


}
