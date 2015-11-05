package com.dreamspace.uucampus.ui.activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.LoginReq;
import com.dreamspace.uucampus.model.api.LoginRes;
import com.dreamspace.uucampus.model.api.ResetReq;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/10/13.
 */
public class LoginActivity extends AbsActivity {
    //
//    @Bind(R.id.Login_userName)
//    EditText LoginUserName;
//    @Bind(R.id.Login_pwd)
//    EditText LoginPwd;
//    @Bind(R.id.login_page_loginButton)
//    Button loginPageLoginButton;
//    @Bind(R.id.login_page_forget)
//    TextView loginPageForget;
//    @Bind(R.id.login_page_register)
//    TextView loginPageRegister;
//    @Bind(R.id.login_page_weichat_img)
//    ImageView loginPageWeichatImg;
//    @Bind(R.id.login_page_weibo_img)
//    ImageView loginPageWeiboImg;
//
//    private String phoneNum = null;
//    private String password = null;
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_login_page;
//    }
//
//    @Override
//    protected void prepareDatas() {
//        ButterKnife.bind(this);
//    }
//
//    @Override
//    protected void initViews() {
//        LoginUserName.setText(PreferenceUtils.getString(LoginActivity.this,PreferenceUtils.Key.PHONE));
//        LoginPwd.setText(PreferenceUtils.getString(LoginActivity.this,PreferenceUtils.Key.PASSWORD));
//        initListener();
//    }
//
//    @Override
//    protected View getLoadingTargetView() {
//        return null;
//    }
//
//    //设置监听器
//    private void initListener(){
//        loginPageLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                phoneNum = LoginUserName.getText().toString();
//                password = LoginPwd.getText().toString();
//                login(phoneNum,password);
//            }
//        });
//        loginPageForget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readyGo(FindBackActivity.class);
//            }
//        });
//        loginPageRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readyGo(RegisterActivity.class);
//            }
//        });
//        loginPageWeichatImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //ShareSDK
//            }
//        });
//        loginPageWeiboImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
//
//    private void login(String username, String password){
//        if(!NetUtils.isNetworkConnected(LoginActivity.this)){
//            showToast("网络没有链接");
//            return;
//        }
//        final LoginReq loginReq = new LoginReq();
//        loginReq.setPhone_num(username);
//        loginReq.setPassword(password);
//        ApiManager.getService(this).createAccessToken(loginReq, new Callback<LoginRes>() {
//            @Override
//            public void success(LoginRes loginRes, Response response) {
//                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.ACCESS, loginRes.getAccess_token());
//                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.PHONE, loginReq.getPhone_num());
//                PreferenceUtils.putString(LoginActivity.this, PreferenceUtils.Key.PASSWORD, loginReq.getPassword());
//                readyGo(MainActivity.class);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
////                showInnerError(error);
//            }
//        });
//    }
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
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("登录");
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this, ShareData.WechatAppId,
                ShareData.WechatAppSecret);
        wxHandler.addToSocialSDK();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    //设置监听器
    private void initListener() {
        loginPageLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = LoginUserName.getText().toString();
                String password = LoginPwd.getText().toString();
                if (isValid(phoneNum, password)) {
                    LoginReq req = new LoginReq();
                    req.setPhone_num(phoneNum);
                    req.setPassword(password);
                    login(req);
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

        //微博登录
        loginPageWeiboImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("开始授权");
                //设置新浪SSO handler
                mController.getConfig().setSsoHandler(new SinaSsoHandler());
                //授权接口
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                        if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                            showToast("授权成功~");
                        } else {
                            showToast("授权失败！");
                        }
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });

                showToast("获取用户数据----");
                //获取access_token及用户资料
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        showToast("获取平台数据开始~~~~");
                    }

                    @Override
                    public void onComplete(int i, Map<String, Object> map) {
                        if (i == 200 && map != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = map.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + map.get(key).toString() + "\r\n");
                            }
                            Log.d("TestData", sb.toString());
                        } else {
                            Log.d("TestData", "发生错误：" + i);
                        }
                    }
                });
            }
        });
        loginPageWeichatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showToast("授权开始");
                    }

                    @Override
                    public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                        showToast("授权完成");
                        mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                            @Override
                            public void onStart() {
                                showToast("获取平台数据开始~~~");
                            }

                            @Override
                            public void onComplete(int i, Map<String, Object> map) {
                                showToast("授权完成");
                                if (i == 200 && map != null) {
                                    StringBuilder sb = new StringBuilder();
                                    Set<String> keys = map.keySet();
                                    for (String key : keys) {
                                        sb.append(key + "=" + map.get(key).toString() + "\r\n");
                                    }
                                    Log.d("TestData", sb.toString());
                                } else {
                                    Log.d("TestData", "发生错误：" + i);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        showToast("授权取消");
                    }
                });
            }
        });
    }

    //登录操作
    private void login(LoginReq loginReq) {
        progressDialog = ProgressDialog.show(this, "", "正在登录", true, false);
        if (NetUtils.isNetworkConnected(this)) {
            ApiManager.getService(this.getApplicationContext()).createAccessToken(loginReq, new Callback<LoginRes>() {
                @Override
                public void success(LoginRes loginRes, Response response) {
                    PreferenceUtils.putString(LoginActivity.this.getApplicationContext(),
                            PreferenceUtils.Key.ACCESS, loginRes.getAccess_token());
                    ApiManager.clear();
                    getUserInfo();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    showInnerError(error);
                }
            });
        } else {
            progressDialog.dismiss();
            showNetWorkError();
        }
    }

    //获取用户信息
    private void getUserInfo() {
        ApiManager.getService(getApplicationContext()).getUserInfo(new Callback<UserInfoRes>() {

            @Override
            public void success(UserInfoRes userInfoRes, Response response) {
                if (userInfoRes != null) {
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
    private void saveUserInfo(UserInfoRes userInfoRes) {
        //这部分还需要修改~
        PreferenceUtils.putString(getApplicationContext(), PreferenceUtils.Key.AVATAR, userInfoRes.getImage());
        PreferenceUtils.putString(getApplicationContext(), PreferenceUtils.Key.ACCOUNT, userInfoRes.getName());
        //PreferenceUtils.putString(getApplicationContext(),PreferenceUtils.Key.SEX,userInfoRes.getSex());
        PreferenceUtils.putString(getApplicationContext(), PreferenceUtils.Key.PHONE, LoginUserName.getText().toString());
    }

    //输入有效性判断
    private boolean isValid(String phoneNum, String pwd) {

        if (CommonUtils.isEmpty(phoneNum)) {
            showToast("请输入您的手机号码");
            LoginUserName.requestFocus();
            return false;
        }
        if (phoneNum.length() != 11) {
            showToast("请检查您的输入格式");
            LoginUserName.requestFocus();
            return false;
        }
        if (CommonUtils.isEmpty(pwd)) {
            showToast("请输入您的密码");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}