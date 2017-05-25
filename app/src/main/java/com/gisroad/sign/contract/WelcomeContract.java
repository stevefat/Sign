package com.gisroad.sign.contract;
/**
 * Created by stevefat on 17-5-11.
 */

import com.gisroad.sign.base.IPresenter;
import com.gisroad.sign.base.IView;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午2:47
 */
public interface WelcomeContract {

    interface WelcomeView extends IView {

        void getPeople();

        void toActivity();
    }

    interface Presenter extends IPresenter {
        void saxHtml(String html);
    }

}
