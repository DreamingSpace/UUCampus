package com.dreamspace.uucampus.ui.activity.Login;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.api.UUService;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.ResetReq;
import com.dreamspace.uucampus.model.api.SendVerifyReq;
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
public class FindBackActivity extends AbsActivity implements View.OnClickListener,Handler.Callback {

    @Bind(R.id.findback_userName)
    EditText findBackUserName;
    @Bind(R.id.findback_verify_code)
    EditText findBackVerifyCode;
    @Bind(R.id.findback_get_code)
    TextView findBackGetCode;
    @Bind(R.id.findback_pwd)
    EditText findBackPwd;
    @Bind(R.id.findback_pwd_confirm)
    EditText findBackPwdConfirm;
    @Bind(R.id.findback_button)
    Button findBackButton;

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
        return R.layout.activity_findback_pwd;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.findback_get_code:
                sendVerifyCode();
                break;
            case R.id.findback_button:
                reset();
                break;
        }
    }

    private void initListener(){
        findBackGetCode.setOnClickListener(this);
        findBackButton.setOnClickListener(this);
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
                        findBackGetCode.setEnabled(false);
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

    //重置密码
    private void reset(){
        if(isPhoneValid()&&isRestValid()){
            final ResetReq resetReq = new ResetReq();
            resetReq.setPhone_num(phoneNum);
            resetReq.setCode(code);
            resetReq.setPassword(password);
            mService.resetPassword(resetReq, new Callback<Response>() {

                @Override
                public void success(Response response, Response response2) {
                    if (response.getStatus() == 200) {
                        readyGo(MainActivity.class);
                        mHandler.removeMessages(BEGIN_TIMER);
                        finish();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    Log.i("INFO", errorRes.toString());
                }
            });
        }
    }

    //检查手机号码是否输入正确
    private boolean isPhoneValid(){
        phoneNum = findBackUserName.getText().toString();
        if (CommonUtils.isEmpty(phoneNum)) {
            showToast("请先输入您的手机号");
            return false;
        }
        if (phoneNum.length() != 11) {
            showToast("请检查您的手机号是否正确");
            findBackUserName.requestFocus();
            return false;
        }
        return true;
    }

    //检查验证码密码是否输入正确
    private boolean isRestValid(){
        code = findBackVerifyCode.getText().toString();
        password = findBackPwd.getText().toString();
        passwordConfirm = findBackPwdConfirm.getText().toString();
        if (CommonUtils.isEmpty(code)) {
            showToast("请先输入您输入的验证码");
            findBackVerifyCode.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(password)){
            showToast("请输入您设置的密码");
            findBackPwd.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(passwordConfirm)){
            showToast("请再次输入您设置的密码");
            findBackPwdConfirm.requestFocus();
            return false;
        }
        if(!password.equals(passwordConfirm)){
            showToast("两次输入的密码不一致，请重新输入");
            findBackPwdConfirm.setText("");
            findBackPwdConfirm.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean handleMessage(Message message) {
        if(message.what == BEGIN_TIMER){
            if(timer==0){
                if(findBackGetCode!=null){
                    findBackGetCode.setText(text);
                    findBackGetCode.setEnabled(true);
                    timer=60;
                }
            }else{
                if(findBackGetCode!=null){
                    findBackGetCode.setText(timer + "秒后重新发送");
                    timer--;
                    mHandler.sendEmptyMessageDelayed(BEGIN_TIMER, 1000);
                }
            }
        }
        return true;
    }
}
