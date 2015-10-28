package com.dreamspace.uucampus.ui.activity.Login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.LoginReq;
import com.dreamspace.uucampus.model.api.LoginRes;
import com.dreamspace.uucampus.model.api.ResetReq;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/10/13.
 */
public class LoginActivity extends AbsActivity {

    @Bind(R.id.Login_userName)
    EditText LoginUserName;
    @Bind(R.id.Login_pwd)
    EditText LoginPwd;
    @Bind(R.id.login_page_loginButton)
    Button loginPageLoginButton;
    @Bind(R.id.login_page_forget)
    TextView loginPageForget;
    @Bind(R.id.login_page_register)
    TextView loginPageRegister;
    @Bind(R.id.login_page_weichat_img)
    ImageView loginPageWeichatImg;
    @Bind(R.id.login_page_weibo_img)
    ImageView loginPageWeiboImg;

    private String phoneNum = null;
    private String password = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_login_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        LoginUserName.setText(PreferenceUtils.getString(LoginActivity.this,PreferenceUtils.Key.PHONE));
        LoginPwd.setText(PreferenceUtils.getString(LoginActivity.this,PreferenceUtils.Key.PASSWORD));
        initListener();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    //设置监听器
    private void initListener(){
        loginPageLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = LoginUserName.getText().toString();
                password = LoginPwd.getText().toString();
                login(phoneNum,password);
            }
        });
        loginPageForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(FindBackActivity.class);
            }
        });
        loginPageRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(RegisterActivity.class);
            }
        });
        loginPageWeichatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ShareSDK
            }
        });
        loginPageWeiboImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void login(String username, String password){
        if(!NetUtils.isNetworkConnected(LoginActivity.this)){
            showToast("网络没有链接");
            return;
        }
        final LoginReq loginReq = new LoginReq();
        loginReq.setPhone_num(username);
        loginReq.setPassword(password);
        ApiManager.getService(this).createAccessToken(loginReq, new Callback<LoginRes>() {
            @Override
            public void success(LoginRes loginRes, Response response) {
                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.ACCESS, loginRes.getAccess_token());
                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.PHONE, loginReq.getPhone_num());
                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.PASSWORD, loginReq.getPassword());
                readyGo(MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
//                showInnerError(error);
            }
        });
    }
}
