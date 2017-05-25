package com.gisroad.sign.presenter;
/**
 * Created by stevefat on 17-5-12.
 */

import android.util.Log;

import com.gisroad.sign.base.BasePresenter;
import com.gisroad.sign.contract.MainContract;
import com.gisroad.sign.module.entity.DepartEntity;
import com.gisroad.sign.module.entity.DepartUserEntity;
import com.gisroad.sign.module.entity.Users;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-12 下午1:59
 */
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    List<DepartEntity> departBeanList;
    List<List<DepartUserEntity>> departUserList;
    List<Users> usersList;


    @Inject
    public MainPresenter(MainContract.Model mModel, MainContract.View mRootView) {
        super(mModel, mRootView);
    }

    public void getPeople() {
        departUserList = new ArrayList<>();
        departBeanList = new ArrayList<>();
        departBeanList = DataSupport.findAll(DepartEntity.class);
        for (int i = 0; i < departBeanList.size(); i++) {
            DepartEntity entity = departBeanList.get(i);
            List<DepartUserEntity> userEntitys = DataSupport.where("did=?", entity.getId() + "").find(DepartUserEntity.class);
            departUserList.add(userEntitys);
        }
        mRootView.showPeople(departBeanList, departUserList);
    }


    /**
     * 获取用户当月的签到数据
     *
     * @param uid
     */
    public void getUserInfo(String uid) {

        mModel.getUsers(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            saxSignHtml(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            mRootView.error();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        if (t instanceof UnknownHostException || t instanceof ConnectException||t instanceof SocketTimeoutException) {
                            mRootView.error();
                        }
                    }

                    @Override
                    public void onComplete() {
                        mRootView.getShowResult(usersList);
                    }
                });

    }

    /**
     * 解析返回的签到数据
     *
     * @param html
     */
    private void saxSignHtml(String html) {
        usersList = new ArrayList<>();
        //开始解析这些数据
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("tr");
        for (int i = 0; i < elements.size(); i++) {
            Elements tds = elements.get(i).getElementsByTag("td");
            //过滤掉头部的th 标签
            if (tds.size() > 0) {
                Users user = new Users();
                user.setName(tds.get(0).text());
                user.setDate_time(tds.get(1).text());
                user.setMorning_to(tds.get(2).text());
                user.setMorning_retreat(tds.get(3).text());
                user.setAfternoon_to(tds.get(4).text());
                user.setAfternoon_retreat(tds.get(5).text());
                user.setNight_retreat(tds.get(6).text());
                user.setLeave(tds.get(7).text());
                usersList.add(user);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
