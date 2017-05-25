package com.gisroad.sign.contract;
/**
 * Created by stevefat on 17-5-12.
 */

import com.gisroad.sign.base.IModel;
import com.gisroad.sign.base.IView;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-12 下午1:59
 */
public interface MainContract {
    interface View extends IView {

        //构建显示的数据
        void showPeople(Object obj1, Object obj2);

        //显示查询到的数据
        void getShowResult(Object obj);

    }

    interface Model extends IModel {
        Flowable<ResponseBody> getUsers(String uid);
    }
}
