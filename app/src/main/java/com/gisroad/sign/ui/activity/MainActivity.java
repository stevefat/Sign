package com.gisroad.sign.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gisroad.sign.R;
import com.gisroad.sign.component.DaggerMainComponent;
import com.gisroad.sign.component.module.MainModule;
import com.gisroad.sign.contract.MainContract;
import com.gisroad.sign.module.entity.DepartEntity;
import com.gisroad.sign.module.entity.DepartUserEntity;
import com.gisroad.sign.module.entity.Users;
import com.gisroad.sign.presenter.MainPresenter;
import com.gisroad.sign.ui.adapter.PeopleListAdapter;
import com.gisroad.sign.ui.adapter.RecycleAdapter;
import com.gisroad.sign.ui.view.EmptyLayout;
import com.gisroad.sign.util.SPUtils;
import com.gisroad.sign.util.ShareSdkUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.text)
//    TextView text;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.expanded)
    ExpandableListView expanableListView;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;


    PeopleListAdapter adapter;
    RecycleAdapter recycleAdapter;

    List<DepartEntity> departBeanList;
    List<List<DepartUserEntity>> departUserList;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        initView();
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        //所有人员的数据
        presenter.getPeople();

    }

    private void initView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        emptyLayout.bindView(swipeRefreshLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycle.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        String userUrl = (String) SPUtils.get(this, "userUrl", "");
        String userName = (String) SPUtils.get(this, "userName", "");

        if (!TextUtils.isEmpty(userName)) {
            toolbarTitle.setText(userName);
        } else {
            toolbarTitle.setText("");
        }

        if (!userUrl.isEmpty()) {
            presenter.getUserInfo(userUrl);
        } else {
            emptyLayout.noData();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void error() {
        emptyLayout.noWifi();

    }


    @Override
    public void showPeople(Object obj1, Object obj2) {
        departBeanList = (List<DepartEntity>) obj1;
        departUserList = (List<List<DepartUserEntity>>) obj2;
        //左侧列表的数据构建
        adapter = new PeopleListAdapter(this, departBeanList, departUserList);
        expanableListView.setAdapter(adapter);
        expanableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onBackPressed();
                DepartUserEntity userEntity = (DepartUserEntity) adapter.getChild(groupPosition, childPosition);
                presenter.getUserInfo(userEntity.getUrl());

                toolbarTitle.setText(userEntity.getName());

                SPUtils.put(MainActivity.this, "userUrl", userEntity.getUrl());
                SPUtils.put(MainActivity.this, "userName", userEntity.getName());
                return false;
            }
        });
    }

    @Override
    public void getShowResult(Object obj) {
        swipeRefreshLayout.setRefreshing(false);

        //显示获取到的数据
        List<Users> usersList = (List<Users>) obj;
        Collections.reverse(usersList); //倒序一下


        if (usersList.size() > 0) {
            emptyLayout.success();
//            toolbarTitle.setText(usersList.get(0).getName());
            recycleAdapter = new RecycleAdapter(this, usersList);
            recycle.setAdapter(recycleAdapter);
            recycle.scrollTo(0, 0);  //默认滚动到顶部
        } else {
            emptyLayout.noData();
        }


    }

    /**********菜单的一些操作**********/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_share:
                ShareSdkUtil.showShare(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        String userUrl = (String) SPUtils.get(this, "userUrl", "");

        if (!userUrl.isEmpty()) {
            presenter.getUserInfo(userUrl);
        } else {
            emptyLayout.noData();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
