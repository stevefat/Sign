package com.gisroad.sign.ui.activity;
/**
 * Created by stevefat on 17-5-22.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gisroad.sign.R;
import com.gisroad.sign.module.entity.CheckUpdate;
import com.gisroad.sign.util.CheckUpdateUtil;
import com.gisroad.sign.util.ToolsUtil;
import com.gisroad.sign.util.ShareSdkUtil;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-22 上午9:49
 */
public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sign_noti)
    Switch signNoti;
    @BindView(R.id.about_layout)
    RelativeLayout aboutLayout;
    @BindView(R.id.up_version)
    RelativeLayout upVersion;
    @BindView(R.id.version_name)
    TextView versionName;
    @BindView(R.id.versionstate)
    TextView versionState;

    CheckUpdateUtil checkUpdateUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        super.onCreate(savedInstanceState);

        toolbarTitle.setText("设置");

        //设置当前软件版本
        versionName.setText("v " + ToolsUtil.getVersionName());

    }

    @OnCheckedChanged(R.id.sign_noti)
    public void signNotiSwitch(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //选中了不通知
        } else {
            //这个是默认的，通知
        }
    }

    @OnClick(R.id.about_layout)
    public void aboutLayoutClick(View v) {
    }

    @OnClick(R.id.up_version)
    public void upCheckVersion(View v) {
        checkUp();
    }

    private void checkUp() {
        checkUpdateUtil = new CheckUpdateUtil(new CheckUpdateUtil.CheckResult() {
            @Override
            public void onsuccess() {
                Toast.makeText(SettingActivity.this, "您的版本已经是最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNewSuccess(CheckUpdate checkUpdate) {
                //提示用户更新
                versionState.setText("发现新版本： v " + checkUpdate.getVersionShort());

                checkUpdateUtil.downNewVersion(SettingActivity.this, checkUpdate);
            }
        });
        checkUpdateUtil.checkUpdate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareSdkUtil.showShare(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
