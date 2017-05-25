package com.gisroad.sign.base;
/**
 * Created by stevefat on 17-5-11.
 */

import android.content.Intent;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午2:45
 */
public interface IView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();


    void error();
}
