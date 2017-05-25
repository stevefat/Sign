package com.gisroad.sign;
/**
 * Created by stevefat on 17-5-10.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gisroad.sign.service.DownSignService;

import org.litepal.LitePal;

import javax.inject.Singleton;

import butterknife.ButterKnife;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-10 下午2:44
 */
public class SignApp extends Application {

    private static SignApp instance;


    @Singleton
    public static SignApp getInstance() {
        return instance;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
        //分型
//        ShareSDK.initSDK(this);
        //注册一些信息
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
                if (activity.findViewById(R.id.toolbar) != null) {
                    Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                            activity.getActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                    }
                }
                if (activity.findViewById(R.id.toolbar_title) != null) { //找到 Toolbar 的标题栏并设置标题名
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }

                ButterKnife.bind(activity);

                Intent intent = new Intent(activity, DownSignService.class);
                startService(intent);


            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Intent intent = new Intent(activity, DownSignService.class);
                stopService(intent);
            }
        });
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }


}
