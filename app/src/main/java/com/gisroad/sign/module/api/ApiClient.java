package com.gisroad.sign.module.api;
/**
 * Created by stevefat on 17-5-18.
 */

import com.gisroad.sign.module.api.service.SignService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-18 下午7:42
 */
public class ApiClient {

    //服务提供者
    private static ApiClient instance;
    //网络接口服务提供
    private SignService signService;

    Retrofit retrofit;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }


    public ApiClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取提供服务的接口
     *
     * @return
     */
    public SignService getSignService() {
        return retrofit.create(SignService.class);
    }
}
