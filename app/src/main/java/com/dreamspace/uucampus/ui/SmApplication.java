package com.dreamspace.uucampus.ui;

import android.app.Application;
import android.util.Log;

import com.dreamspace.uucampus.common.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class SmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (PreferenceUtils.hasKey(getApplicationContext(), PreferenceUtils.Key.FIRST_USE)) {
        } else {
        }
    }
}
