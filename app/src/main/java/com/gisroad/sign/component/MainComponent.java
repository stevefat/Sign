package com.gisroad.sign.component;
/**
 * Created by stevefat on 17-5-12.
 */


import com.gisroad.sign.component.module.MainModule;
import com.gisroad.sign.ui.activity.MainActivity;

import dagger.Component;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-12 下午2:00
 */
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
