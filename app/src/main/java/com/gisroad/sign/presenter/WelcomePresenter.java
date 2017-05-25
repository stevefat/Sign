package com.gisroad.sign.presenter;
/**
 * Created by stevefat on 17-5-11.
 */

import com.gisroad.sign.contract.WelcomeContract;
import com.gisroad.sign.module.WelcomeModel;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午2:47
 */
public class WelcomePresenter implements WelcomeContract.Presenter {
    WelcomeModel module;
    WelcomeContract.WelcomeView welcomeView;

    public WelcomePresenter(WelcomeContract.WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
        module = new WelcomeModel();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void saxHtml(String html) {
        module.saxHtml(html);
    }
}
