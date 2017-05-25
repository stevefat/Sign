package com.gisroad.sign.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.gisroad.sign.R;
import com.gisroad.sign.contract.WelcomeContract;
import com.gisroad.sign.module.entity.DepartEntity;
import com.gisroad.sign.util.PeopleUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import butterknife.BindView;

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.WelcomeView {
    @BindView(R.id.dialog_layout)
    LinearLayout dialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        showLoading();

        getPeople();
        EventBus.getDefault().register(this);

    }


    @Override
    public void showLoading() {
        dialogLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        dialogLayout.setVisibility(View.GONE);
    }

    @Override
    public void error() {

    }

    @Override
    public void getPeople() {
        int size = DataSupport.findAll(DepartEntity.class).size();
        if (size <= 0) {
            PeopleUtils peopleUtils = new PeopleUtils(this);
            peopleUtils.getPeople();

        } else {
            toActivity();
        }
    }

    @Override
    public void toActivity() {
        hideLoading();
        //跳转
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String result) {
        if (result.equals("success")) {
            toActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
