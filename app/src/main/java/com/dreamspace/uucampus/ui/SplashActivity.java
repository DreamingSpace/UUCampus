package com.dreamspace.uucampus.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.igexin.sdk.PushManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/11/28.
 */
public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化个推SDK
        PushManager.getInstance().initialize(this.getApplicationContext());
        int enter;
        if(!PreferenceUtils.hasKey(this, PreferenceUtils.Key.FIRST_USE) || PreferenceUtils.getBoolean(this,PreferenceUtils.Key.FIRST_USE)){
            enter = 0;
            PreferenceUtils.putBoolean(this, PreferenceUtils.Key.FIRST_USE, false);
            PreferenceUtils.putString(this,PreferenceUtils.Key.LOCATION,getString(R.string.seu));//添加默认校区
        }else{
            enter = 1;
        }

        //目的在于更新后再次启动app会首先进入引导页
        try {
            int currentVersionCode = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
            if(currentVersionCode > PreferenceUtils.getInt(this,PreferenceUtils.Key.VERSION_CODE)){
                enter = 0;
                PreferenceUtils.putInt(this,PreferenceUtils.Key.VERSION_CODE,currentVersionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final int enter1 = enter;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = enter1 == 0 ? new Intent(SplashActivity.this, AppFirstInActivity.class) :
                        new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                finish();
            }
        }, 2000);

        //在启动页获取用户信息，目的在于“自动登录能获取最新的头像”
        ApiManager.getService(this).getUserInfo(new Callback<UserInfoRes>() {
            @Override
            public void success(UserInfoRes userInfoRes, Response response) {
                if(userInfoRes != null){
                    PreferenceUtils.putString(SplashActivity.this,PreferenceUtils.Key.AVATAR,userInfoRes.getImage());
                    PreferenceUtils.putString(SplashActivity.this,PreferenceUtils.Key.NAME,userInfoRes.getName());
                    PreferenceUtils.putString(SplashActivity.this,PreferenceUtils.Key.ENROLL_YEAR,userInfoRes.getEnroll_year());
                    PreferenceUtils.putString(SplashActivity.this,PreferenceUtils.Key.PHONE,userInfoRes.getPhone_num());
                    PreferenceUtils.putString(SplashActivity.this,PreferenceUtils.Key.LOCATION,userInfoRes.getLocation());
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
