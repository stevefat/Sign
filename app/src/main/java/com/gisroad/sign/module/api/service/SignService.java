package com.gisroad.sign.module.api.service;
/**
 * Created by stevefat on 17-5-11.
 */

import com.gisroad.sign.module.entity.CheckUpdate;
import com.gisroad.sign.util.CheckUpdateUtil;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午5:19
 */
public interface SignService {

    //KQlist.aspx?Userid=0100033
    @GET("KQlist.aspx")
    Flowable<ResponseBody> getUserItem(@Query("Userid") String userid);


    @GET
    Flowable<CheckUpdate> chekcUpdate(@Url String url);


}
