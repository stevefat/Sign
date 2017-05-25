package com.gisroad.sign.util;
/**
 * Created by stevefat on 17-5-24.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gisroad.sign.R;
import com.gisroad.sign.SignApp;
import com.gisroad.sign.module.api.Api;
import com.gisroad.sign.module.api.ApiClient;
import com.gisroad.sign.module.entity.CheckUpdate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-24 上午9:05
 */
public class CheckUpdateUtil {

    CheckResult checkResult;
    AlertDialog dialog;

    public CheckUpdateUtil(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public void checkUpdate() {
        Flowable fb = ApiClient.getInstance().getSignService().chekcUpdate(Api.CHECK_UPDATE);
        fb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckUpdate>() {
                    CheckUpdate checkUpdate;

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(CheckUpdate checkUpdate) {
                        this.checkUpdate = checkUpdate;
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //检测是否可以使用
                        int newVersion = Integer.valueOf(checkUpdate.getVersion()); //服务器返回的数据
                        int currentVersion = ToolsUtil.getVersionCode();
                        if (newVersion >currentVersion) {
                            //提示有更新了，
                            checkResult.onNewSuccess(checkUpdate);
                        } else {
                            //没有更新，当前版本是最新版本
                            checkResult.onsuccess();
                        }
                    }
                });

    }

    /**
     * 下载最新版本的安装包
     */
    public void downNewVersion(final Context context, final CheckUpdate checkUpdate) {
//        Toast.makeText(SignApp.getInstance(), "开始更新数据", Toast.LENGTH_SHORT).show();
        //弹出提示是否下载用户选择   更新内容什么的
        dialog = new AlertDialog.Builder(context).create();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View v = LayoutInflater.from(context).inflate(R.layout.activity_user_detail, null);
        dialog.setView(v);

        dialog.show();


        TextView closeUp = (TextView) v.findViewById(R.id.close_up);
        TextView versionNew = (TextView) v.findViewById(R.id.version_new);
        TextView upMsg = (TextView) v.findViewById(R.id.up_msg);
        TextView btn = (TextView) v.findViewById(R.id.up_btn);

        versionNew.setText("发现新版本：V " + checkUpdate.getVersionShort());
        upMsg.setText(checkUpdate.getChangelog());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //开启服务下载数据
                down(context, checkUpdate.getInstall_url());
            }
        });
        closeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    //发送到通知栏下载数据
    private void down(Context context, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);  //wifi下可以下载
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE); //下载中显示
        request.setTitle("软件更新中");
        request.setDescription("考勤查询正在下载");

        File saveFile = new File(context.getExternalCacheDir(), "sign.apk");
        request.setDestinationUri(Uri.fromFile(saveFile));


    }


    public interface CheckResult {
        void onsuccess();

        void onNewSuccess(CheckUpdate checkUpdate);
    }
}
