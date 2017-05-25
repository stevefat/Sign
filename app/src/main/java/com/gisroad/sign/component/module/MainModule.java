package com.gisroad.sign.component.module;
/**
 * Created by stevefat on 17-5-18.
 */

import com.gisroad.sign.contract.MainContract;
import com.gisroad.sign.module.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-18 下午8:05
 */
@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    MainContract.View provideView() {
        return this.view;
    }

    @Provides
    MainContract.Model provideModel(MainModel mainModel) {
        return mainModel;
    }

}
