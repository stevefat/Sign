package com.gisroad.sign.module;
/**
 * Created by stevefat on 17-5-12.
 */

import com.gisroad.sign.contract.MainContract;
import com.gisroad.sign.module.api.ApiClient;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-12 下午2:00
 */
public class MainModel implements MainContract.Model {

    @Inject
    public MainModel() {
    }

    @Override
    public Flowable<ResponseBody> getUsers(String uid) {
        return ApiClient.getInstance().getSignService().getUserItem(uid);
    }

    @Override
    public void onDestroy() {

    }
}
