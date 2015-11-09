package com.dreamspace.uucampus.ui.activity.Login;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.api.UUService;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.SendVerifyReq;
import com.dreamspace.uucampus.model.api.WeiXinLoginReq;
import com.dreamspace.uucampus.model.api.WeiXinLoginRes;
import com.dreamspace.uucampus.model.api.WeiXinRegisterReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/10/13.
 * 微信授权后创建新用户
 */
public class BoundActvity extends AbsActivity implements Handler.Callback {

    @Bind(R.id.bound_userName)
    EditText boundUserName;
    @Bind(R.id.bound_verify_code)
    EditText boundVerifyCode;
    @Bind(R.id.bound_get_code)
    TextView boundGetCode;
    @Bind(R.id.bound_button)
    Button boundButton;
    @Bind(R.id.bound_password)
    EditText boundPassword;
    @Bind(R.id.bound_pwd_confirm)
    EditText boundPwdConfirm;

    private String phoneNum = null;
    private String code = null;
    private String password = null;
    private String passwordConfirm = null;

    private UUService mService;
    private int timer = 60;
    private Handler mHandler;
    private static final int BEGIN_TIMER = 23333;
    private String text = "发送验证码";

    @Override
    protected int getContentView() {
        return R.layout.activity_bound_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
        mHandler = new Handler(this);
        mService = ApiManager.getService(getApplicationContext());
    }

    @Override
    protected void initViews() {
        boundGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerifyCode();
            }
        });
        boundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = boundUserName.getText().toString();
                code = boundVerifyCode.getText().toString();
                password = boundPassword.getText().toString();
                passwordConfirm = boundPwdConfirm.getText().toString();
                weChatRegister();
            }
        });
    }

    //获取手机验证码
    private void sendVerifyCode() {
        if (isPhoneValid()) {
            if (NetUtils.isNetworkConnected(this)) {
                SendVerifyReq req = new SendVerifyReq();
                req.setPhone_num(phoneNum);
                mService.sendVerifyCode(req, new Callback<Response>() {
                    @Override
                    public void success(Response o, Response response) {
                        boundUserName.setEnabled(false);
                        mHandler.sendEmptyMessage(BEGIN_TIMER);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                    }
                });
            } else {
                showNetWorkError();
            }
        }
    }

    //检查手机号码是否输入正确
    private boolean isPhoneValid() {
        phoneNum = boundUserName.getText().toString();
        if (CommonUtils.isEmpty(phoneNum)) {
            showToast("请先输入您的手机号");
            return false;
        }
        if (phoneNum.length() != 11) {
            showToast("请检查您的手机号是否正确");
            boundUserName.requestFocus();
            return false;
        }
        return true;
    }

    //检查验证码密码是否输入正确
    private boolean isRestValid(){
        if (CommonUtils.isEmpty(code)) {
            showToast("请先输入您输入的验证码");
            boundVerifyCode.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(password)){
            showToast("请输入您设置的密码");
            boundPassword.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(passwordConfirm)){
            showToast("请再次输入您设置的密码");
            boundPwdConfirm.requestFocus();
            return false;
        }
        if(!password.equals(passwordConfirm)){
            showToast("两次输入的密码不一致，请重新输入");
            boundPwdConfirm.setText("");
            boundPwdConfirm.requestFocus();
            return false;
        }
        return true;
    }

    //微信注册新用户
    private void weChatRegister() {
        if(isPhoneValid()&&isRestValid()){
            if(NetUtils.isNetworkConnected(this)){
            WeiXinRegisterReq weiXinRegisterReq = new WeiXinRegisterReq();
            weiXinRegisterReq.setPhone_num(phoneNum);
            weiXinRegisterReq.setAccess_token(ShareData.weChatUser.getAccess_token());
            weiXinRegisterReq.setCode(code);
            weiXinRegisterReq.setOpen_id(ShareData.weChatUser.getOpenid());
            weiXinRegisterReq.setPassword(password);

            mService.weiXinRegister(weiXinRegisterReq, new Callback<Response>() {

                @Override
                public void success(Response o,Response response) {
                    weChatLogin();
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                }
            });
        }else{
                showNetWorkError();
            }
        }
    }

    //微信登录获取access_token
    private void weChatLogin() {
        final WeiXinLoginReq weiXinLoginReq = new WeiXinLoginReq();
        weiXinLoginReq.setOpen_id(ShareData.weChatUser.getOpenid());
        weiXinLoginReq.setAccess_token(ShareData.weChatUser.getAccess_token());
        mService.weiXinLogin(weiXinLoginReq, new Callback<WeiXinLoginRes>() {
            @Override
            public void success(WeiXinLoginRes weiXinLoginRes, Response response) {
                PreferenceUtils.putString(BoundActvity.this.getApplicationContext(),
                        PreferenceUtils.Key.ACCESS, weiXinLoginRes.getAccess_token());
                mHandler.removeMessages(BEGIN_TIMER);
                readyGoThenKill(RegisterInfoActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });

    }

    @Override
    //获取验证码的间隔限制
    public boolean handleMessage(Message message) {
        if (message.what == BEGIN_TIMER) {
            if (timer == 0) {
                if (boundGetCode != null) {
                    boundGetCode.setText(text);
                    boundGetCode.setEnabled(true);
                    timer = 60;
                }
            } else {
                if (boundGetCode != null) {
                    boundGetCode.setText(timer + "秒后重新发送");
                    timer--;
                    mHandler.sendEmptyMessageDelayed(BEGIN_TIMER, 1000);
                }
            }
        }
        return true;
    }

}
