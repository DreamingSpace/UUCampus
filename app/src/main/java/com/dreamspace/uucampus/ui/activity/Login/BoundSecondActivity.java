package com.dreamspace.uucampus.ui.activity.Login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.api.UUService;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.WeiXinBindReq;
import com.dreamspace.uucampus.model.api.WeiXinBindRes;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/11/9.
 * 微信授权后绑定已有用户
 */
public class BoundSecondActivity extends AbsActivity {

    @Bind(R.id.bound_second_userName)
    EditText boundSecondUserName;
    @Bind(R.id.bound_second_password)
    EditText boundSecondPassword;
    @Bind(R.id.bound_second_button)
    Button boundSecondButton;

    private String phoneNum;
    private String password;

    private UUService mService;


    @Override
    protected int getContentView() {
        return R.layout.activity_bound_second;

    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
        mService = ApiManager.getService(getApplicationContext());
    }

    @Override
    protected void initViews() {
        boundSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = boundSecondUserName.getText().toString();
                password = boundSecondPassword.getText().toString();
                weChatBind();
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void weChatBind(){
        if(isValid()){
            if(NetUtils.isNetworkConnected(this)){
                final WeiXinBindReq weiXinBindReq = new WeiXinBindReq();
                weiXinBindReq.setAccess_token(ShareData.weChatUser.getAccess_token());
                weiXinBindReq.setOpen_id(ShareData.weChatUser.getOpenid());
                weiXinBindReq.setPhone_num(phoneNum);
                weiXinBindReq.setPassword(password);

                mService.weiXinBind(weiXinBindReq, new Callback<WeiXinBindRes>() {
                    @Override
                    public void success(WeiXinBindRes weiXinBindRes, Response response) {
                        Log.d("TestData",weiXinBindReq.toString());
                        PreferenceUtils.putString(BoundSecondActivity.this.getApplicationContext(),
                                PreferenceUtils.Key.ACCESS, weiXinBindRes.getAccess_token());
                        showToast("微信绑定成功~~");
                        readyGoThenKill(MainActivity.class);
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

    private boolean isValid(){
        if (CommonUtils.isEmpty(phoneNum)) {
            showToast("请先输入您的手机号");
            return false;
        }
        if (phoneNum.length() != 11) {
            showToast("请检查您的手机号是否正确");
            boundSecondUserName.requestFocus();
            return false;
        }
        if(CommonUtils.isEmpty(password)){
            showToast("请输入您的密码");
            boundSecondPassword.requestFocus();
            return false;
        }
        return true;
    }

}
