package com.dreamspace.uucampus.ui.activity.Login;

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
import com.dreamspace.uucampus.model.WeChatUser;
import com.dreamspace.uucampus.model.api.LoginReq;
import com.dreamspace.uucampus.model.api.LoginRes;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
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
    //添加友盟第三方登录
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
        LoginUserName.setText(PreferenceUtils.getString(LoginActivity.this, PreferenceUtils.Key.PHONE));
//        LoginPwd.setText(PreferenceUtils.getString(LoginActivity.this, PreferenceUtils.Key.PASSWORD));

        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView titleTv = (TextView) mToolBar.findViewById(R.id.custom_title_tv);
        titleTv.setText(getString(R.string.login));

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,ShareData.WechatAppId,
                ShareData.WechatAppSecret);
        wxHandler.addToSocialSDK();

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
                String phoneNum = LoginUserName.getText().toString();
                String password = LoginPwd.getText().toString();
                if(isValid(phoneNum,password)){
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
                //设置新浪SSO handler
                mController.getConfig().setSsoHandler(new SinaSsoHandler());
                //授权接口
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d("TestData", "start");
                    }

                    @Override
                    public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                        Log.d("TestData","compete");
                        if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                            showToast("授权成功~");
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
                        } else {
                            showToast("授权失败！");
                        }
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA share_media) {
                        Log.d("TestData","error");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.d("TestData","cancel");
                    }
                });
            }
        });

        //微信授权登录
        loginPageWeichatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showToast("授权开始");
                        progressDialog.setContent("正在授权");
                        progressDialog.show();
                    }

                    @Override
                    public void onComplete(final Bundle bundle, SHARE_MEDIA share_media) {
                        progressDialog.dismiss();
                        //获取相关授权信息
                        mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                            @Override
                            public void onStart() {
                                showToast("获取平台数据开始~~~");
                            }

                            @Override
                            public void onComplete(int i, Map<String, Object> map) {
                                showToast("授权完成");
                                if( i == 200 && map!= null){
                                    StringBuilder sb = new StringBuilder();
                                    Set<String> keys = map.keySet();
                                    WeChatUser weChatUser = new WeChatUser();
                                    for(String key : keys){
                                        sb.append(key + "=" + map.get(key).toString() + "\r\n");
                                    }
                                    weChatUser.setCity(map.get("city").toString());
                                    weChatUser.setCountry(map.get("country").toString());
                                    weChatUser.setHeadimgurl(map.get("headimgurl").toString());
                                    weChatUser.setLanguage(map.get("language").toString());
                                    weChatUser.setNickname(map.get("nickname").toString());
                                    weChatUser.setProvince(map.get("province").toString());
                                    weChatUser.setSex(map.get("sex").toString());
                                    weChatUser.setOpenid(map.get("openid").toString());
                                    weChatUser.setUnionid(map.get("unionid").toString());
                                    weChatUser.setAccess_token(bundle.get("access_token").toString());
                                    ShareData.weChatUser = weChatUser;

                                    //授权成功获取用户信息之后跳转到微信绑定界面
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putParcelable(WechatActivity.WECHAT_USER,weChatUser);
                                    readyGo(WechatActivity.class,bundle1);

                                }else{
                                    Log.d("TestData","发生错误："+i);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA share_media) {
                        showToast("授权错误");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        showToast("授权取消");
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    //登录操作
    private void login(final LoginReq loginReq){
        progressDialog.setContent(getString(R.string.in_login));
        progressDialog.show();
        if(NetUtils.isNetworkConnected(this)){
            ApiManager.getService(this.getApplicationContext()).createAccessToken(loginReq,new Callback<LoginRes>(){
                @Override
                public void success(LoginRes loginRes, Response response) {
                    progressDialog.dismiss();
                    PreferenceUtils.putString(LoginActivity.this,
                            PreferenceUtils.Key.ACCESS, loginRes.getAccess_token());
                    //设置为已登录
                    PreferenceUtils.putBoolean(LoginActivity.this, PreferenceUtils.Key.LOGIN, true);
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
                    saveUserInfo(userInfoRes);
                    progressDialog.dismiss();
                    showToast("登录成功");
                    //当用户游客身份用APP时选择登录，登录成功后要给进入登录界面的activity返回一个登录成功状态，好让那个activity结束
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    //保存用户信息到本地
    private void saveUserInfo(UserInfoRes userInfoRes){
        PreferenceUtils.putString(this,PreferenceUtils.Key.AVATAR,userInfoRes.getImage());
        PreferenceUtils.putString(this,PreferenceUtils.Key.NAME,userInfoRes.getName());
        PreferenceUtils.putString(this,PreferenceUtils.Key.ENROLL_YEAR,userInfoRes.getEnroll_year());
        PreferenceUtils.putString(this,PreferenceUtils.Key.PHONE,userInfoRes.getPhone_num());
        PreferenceUtils.putString(this,PreferenceUtils.Key.LOCATION,userInfoRes.getLocation());
    }

    //输入有效性判断
    private boolean isValid(String phoneNum,String pwd){

        if(CommonUtils.isEmpty(phoneNum)){
            showToast("请输入您的手机号码");
            LoginUserName.requestFocus();
            return false;
        }
        if(phoneNum.length()!=11){
            showToast("请输入正确的手机号码");
            LoginUserName.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(pwd)){
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
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
