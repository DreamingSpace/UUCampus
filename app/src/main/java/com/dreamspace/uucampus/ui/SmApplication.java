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
            //非第一次登陆
            Log.i("INFO", "not first");
        } else {
            //第一次打开软件时 设置已经打开过，增加初始化分类
            Log.i("INFO", "first");
            PreferenceUtils.putBoolean(getApplicationContext(),PreferenceUtils.Key.FIRST_USE, true);
            List<String> items = new ArrayList<>();
            items.add("精选");
            items.add("数码电子");
            items.add("书籍杂志");
            items.add("生活用品");
            items.add("衣帽服饰");
            items.add("出行车辆");
        }
    }
}
