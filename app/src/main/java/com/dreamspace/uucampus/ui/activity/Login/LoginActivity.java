package com.dreamspace.uucampus.ui.activity.Login;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.LoginReq;
import com.dreamspace.uucampus.model.api.LoginRes;
import com.dreamspace.uucampus.model.api.UserInfoRes;
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

    ProgressDialog progressDialog;

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
        initListener();
    }

    //设置监听器
    private void initListener(){
        loginPageLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = LoginUserName.getText().toString();
                String password = LoginPwd.getText().toString();
                if(isValid(phoneNum,password)){
                    LoginReq req = new LoginReq();
                    req.setPhone_num(phoneNum);
                    req.setPassword(password);
                    readyGo(MainActivity.class);
                    //login(req);
                }
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

    //登录操作
    private void login(LoginReq loginReq){
        progressDialog = ProgressDialog.show(this,"","正在登录",true,false);
        if(NetUtils.isNetworkConnected(this)){
            ApiManager.getService(this.getApplicationContext()).createAccessToken(loginReq,new Callback<LoginRes>(){
                @Override
                public void success(LoginRes loginRes, Response response) {
                    PreferenceUtils.putString(LoginActivity.this.getApplicationContext(),
                            PreferenceUtils.Key.ACCESS,loginRes.getAccess_token());
                    ApiManager.clear();
                    getUserInfo();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    showInnerError(error);
                }
            });
        }else{
            progressDialog.dismiss();
            showNetWorkError();
        }
    }

    //获取用户信息
    private void getUserInfo() {
        ApiManager.getService(getApplicationContext()).getUserInfo(new Callback<UserInfoRes>() {

            @Override
            public void success(UserInfoRes userInfoRes, Response response) {
                if(userInfoRes != null){
                    Log.i("INFO", userInfoRes.toString());
                    saveUserInfo(userInfoRes);
                    progressDialog.dismiss();
                    readyGo(MainActivity.class);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    //保存用户信息到本地
    private void saveUserInfo(UserInfoRes userInfoRes){
        //这部分还需要修改~
        PreferenceUtils.putString(getApplicationContext(),PreferenceUtils.Key.AVATAR,userInfoRes.getImage());
        PreferenceUtils.putString(getApplicationContext(),PreferenceUtils.Key.ACCOUNT,userInfoRes.getName());
        //PreferenceUtils.putString(getApplicationContext(),PreferenceUtils.Key.SEX,userInfoRes.getSex());
        PreferenceUtils.putString(getApplicationContext(), PreferenceUtils.Key.PHONE, LoginUserName.getText().toString());
    }

    //输入有效性判断
    private boolean isValid(String phoneNum,String pwd){

        if(CommonUtils.isEmpty(phoneNum)){
            showToast("请输入您的手机号码");
            LoginUserName.requestFocus();
            return false;
        }
        if(phoneNum.length()!=11){
            showToast("请检查您的输入格式");
            LoginUserName.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(pwd)){
            showToast("请输入您的密码");
            return false;
        }
        return true;
    }
}
