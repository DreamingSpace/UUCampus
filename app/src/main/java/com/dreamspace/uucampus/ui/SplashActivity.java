package com.dreamspace.uucampus.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;

/**
 * Created by Lx on 2015/11/28.
 */
public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int enter;
        if(!PreferenceUtils.hasKey(this, PreferenceUtils.Key.FIRST_USE) || PreferenceUtils.getBoolean(this,PreferenceUtils.Key.FIRST_USE)){
            enter = 0;
            PreferenceUtils.putBoolean(this,PreferenceUtils.Key.FIRST_USE,false);
            PreferenceUtils.putString(this,PreferenceUtils.Key.LOCATION,"东南大学九龙湖校区");
        }else{
            enter = 1;
        }

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
    }
}
