package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dreamspace.uucampus.API.ApiManager;
import com.dreamspace.uucampus.API.UUService;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.CheckUpdateRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zsh on 2015/9/22.
 */
public class SettingActivity extends AbsActivity {
    @Bind(R.id.relative1)
    RelativeLayout relative1;
    @Bind(R.id.relative2)
    RelativeLayout relative2;
    @Bind(R.id.logout_button)
    Button logout_button;

    private Context mContext;
    private UUService mService;
    private String currentVersion;
    @Override
    protected int getContentView() {
        return R.layout.person_activity_setting;
    }

    @Override
    protected void prepareDatas() {
        mContext = this;
        mService = ApiManager.getService(mContext);
    }

    @Override
    protected void initViews() {
        relative1.setOnClickListener(new MyOnClickListener(0));
        relative2.setOnClickListener(new MyOnClickListener(1));
        logout_button.setOnClickListener(new MyOnClickListener(2));
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            if(index == 0) {
                //Toast.makeText(mContext,"当前版本是最新的哦~",Toast.LENGTH_LONG).show();
                checkUpdate();
            }
            if(index == 1) {
                Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //readyGo(FeedbackActivity.class);
            }
            if(index == 2) {

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void checkUpdate(){
        if (NetUtils.isNetworkConnected(mContext)){
            final CheckUpdateRes res = new CheckUpdateRes();
            //getApplicationContext()
            mService.checkUpdate(currentVersion, new Callback<CheckUpdateRes>() {
                @Override
                public void success(CheckUpdateRes checkUpdateRes, Response response) {
                    if (res.getVersion()==(checkUpdateRes.getVersion())){
                        Toast.makeText(mContext,"当前版本是最新的哦~",Toast.LENGTH_LONG).show();
                    }else{
                        Uri uri = Uri.parse(checkUpdateRes.getDownlink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    //Log.i("INFO", res.toString());
                }
            });

        }else{
            showNetWorkError();
        }
    }
    private void getCurrentVersion(){
        PackageManager packageManager=getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(), 0);
            currentVersion=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
