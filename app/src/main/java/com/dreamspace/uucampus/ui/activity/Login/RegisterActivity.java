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
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.RegisterReq;
import com.dreamspace.uucampus.model.api.RegisterRes;
import com.dreamspace.uucampus.model.api.SendVerifyReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterActivity extends AbsActivity implements View.OnClickListener,Handler.Callback{
    @Bind(R.id.register_userName)
    EditText registerUserName;
    @Bind(R.id.register_verify_code)
    EditText registerVerifyCode;
    @Bind(R.id.register_get_code)
    TextView registerGetCode;
    @Bind(R.id.register_pwd)
    EditText registerPwd;
    @Bind(R.id.register_pwd_confirm)
    EditText registerPwdConfirm;
    @Bind(R.id.register_button)
    Button registerButton;

    private String phoneNum;
    private String code;
    private String password;
    private String passwordConfirm;
    private UUService mService;
    private int timer = 60;
    private Handler mHandler;
    private static final int BEGIN_TIMER = 23333;
    private String text = "发送验证码";

    @Override
    protected int getContentView() {
        return R.layout.activity_register_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
        mHandler = new Handler(this);
        mService = ApiManager.getService(getApplicationContext());
    }

    @Override
    protected void initViews() {
        initListener();
    }

    private void initListener(){
        registerGetCode.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_get_code:
                sendVerifyCode();
                break;
            case R.id.register_button:
                register();
                break;
        }
    }

    //获取手机验证码
    private void sendVerifyCode(){
        if(isPhoneValid()){
            if (NetUtils.isNetworkConnected(this)) {
                SendVerifyReq req = new SendVerifyReq();
                req.setPhone_num(phoneNum);
                mService.sendVerifyCode(req, new Callback<Response>() {
                    @Override
                    public void success(Response o, Response response) {
                        showToast("验证码发送成功~");
                        registerGetCode.setEnabled(false);
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

    //注册
    private void register(){
        if(isPhoneValid()&&isRestValid()){
            if(NetUtils.isNetworkConnected(this)){
            final RegisterReq registerReq = new RegisterReq();
            registerReq.setPhone_num(phoneNum);
            registerReq.setCode(code);
            registerReq.setPassword(password);
            mService.register(registerReq, new Callback<RegisterRes>() {
                @Override
                public void success(RegisterRes registerRes, Response response) {
                    if(response.getStatus()==200){
                        //保存user_id,access_token,timelimit
                        PreferenceUtils.putString(RegisterActivity.this.getApplicationContext(),
                                PreferenceUtils.Key.ACCESS, registerRes.getAccess_token());
                        mHandler.removeMessages(BEGIN_TIMER);
                        readyGoThenKill(RegisterInfoActivity.class);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    showInnerError(error);
                }
            });
        }}else{
        showNetWorkError();
    }
    }

    //检查手机号码是否输入正确
    private boolean isPhoneValid(){
        phoneNum = registerUserName.getText().toString();
        if (CommonUtils.isEmpty(phoneNum)) {
            showToast("请先输入您的手机号");
            return false;
        }
        if (phoneNum.length() != 11) {
            showToast("请检查您的手机号是否正确");
            registerUserName.requestFocus();
            return false;
        }
        return true;
    }

    //检查验证码密码是否输入正确
    private boolean isRestValid(){
        code = registerVerifyCode.getText().toString();
        password = registerPwd.getText().toString();
        passwordConfirm = registerPwdConfirm.getText().toString();
        if (CommonUtils.isEmpty(code)) {
            showToast("请先输入您输入的验证码");
            registerVerifyCode.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(password)){
            showToast("请输入您设置的密码");
            registerPwd.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(passwordConfirm)){
            showToast("请再次输入您设置的密码");
            registerPwdConfirm.requestFocus();
            return false;
        }
        if(!password.equals(passwordConfirm)){
            showToast("两次输入的密码不一致，请重新输入");
            registerPwdConfirm.setText("");
            registerPwdConfirm.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    //获取验证码的间隔限制
    public boolean handleMessage(Message message) {
        if(message.what == BEGIN_TIMER){
            if(timer==0){
                if(registerGetCode!=null){
                    registerGetCode.setText(text);
                    registerGetCode.setEnabled(true);
                    timer=60;
                }
            }else{
                if(registerGetCode!=null){
                    registerGetCode.setText(timer + "秒后重新发送");
                    timer--;
                    mHandler.sendEmptyMessageDelayed(BEGIN_TIMER, 1000);
                }
            }
        }
        return true;
    }
}
