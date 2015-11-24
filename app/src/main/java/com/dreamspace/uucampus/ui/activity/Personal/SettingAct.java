package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.DeleteAccessTokenRes;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/15.
 */
public class SettingAct extends AbsActivity {
    @Bind(R.id.check_update_rl)
    RelativeLayout updateRl;
    @Bind(R.id.logout_btn)
    Button logoutBtn;
    @Bind(R.id.feedback_rl)
    RelativeLayout feedBackRl;

    private boolean actDestroy = false;
    private static final int LOGIN = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));

        if (!PreferenceUtils.hasKey(this, PreferenceUtils.Key.LOGIN)
                || !PreferenceUtils.getBoolean(this, PreferenceUtils.Key.LOGIN)) {
            //未登录
            initNoLoginViewsAndEvent();
        } else {
            initLoginViewsAndEvent();
        }

        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners() {
        updateRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void logout() {
        if (!NetUtils.isNetworkConnected(this)) {
            logoutOnNetErrorEnvir();
            return;
        }

        ApiManager.getService(this).deleteAccessToken(new Callback<DeleteAccessTokenRes>() {
            @Override
            public void success(DeleteAccessTokenRes deleteAccessTokenRes, Response response) {
                if (deleteAccessTokenRes != null && !actDestroy) {
                    //登出，修改preference
                    PreferenceUtils.putString(SettingAct.this, PreferenceUtils.Key.ACCESS, "");
                    PreferenceUtils.putBoolean(SettingAct.this, PreferenceUtils.Key.LOGIN, false);
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                logoutOnNetErrorEnvir();
            }
        });
    }

    //无网环境或者请求错误的情况下登出
    private void logoutOnNetErrorEnvir() {
        PreferenceUtils.putString(this, PreferenceUtils.Key.ACCESS, "");
        PreferenceUtils.putBoolean(SettingAct.this, PreferenceUtils.Key.LOGIN, false);
        setResult(RESULT_OK);
        finish();
    }

    //未登录
    private void initNoLoginViewsAndEvent() {
        feedBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoForResult(LoginActivity.class, LOGIN);
            }
        });

        logoutBtn.setVisibility(View.INVISIBLE);
    }

    //登录后
    private void initLoginViewsAndEvent() {
        logoutBtn.setVisibility(View.VISIBLE);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        feedBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(FeedbackAct.class);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN && resultCode == RESULT_OK) {
            initLoginViewsAndEvent();
        }
    }

    @Override
    protected void onDestroy() {
        actDestroy = true;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
