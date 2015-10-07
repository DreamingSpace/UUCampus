package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.API.ApiManager;
import com.dreamspace.uucampus.API.UUService;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.RegisterReq;
import com.dreamspace.uucampus.model.api.RegisterRes;
import com.dreamspace.uucampus.model.api.SendVerifyReq;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.util.logging.Handler;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zsh on 2015/9/29.
 */
public class UserRegisterActivity extends AbsActivity {
    @Bind(R.id.phonenum)
    EditText phonenum;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.commit)
    Button commit;

    private String phonenum_txt;
    private String code_txt;
    private String password_txt;
    private String access_token;
    private Handler mHandler;
    private UUService mService;
    private Context mCOntext;
    @Override
    protected int getContentView() {
        return R.layout.person_register;
    }

    @Override
    protected void prepareDatas() {
        mCOntext = this;
        mService = ApiManager.getService(mCOntext);
        phonenum_txt = phonenum.getText().toString();
        code_txt = code.getText().toString();
        password_txt = password.getText().toString();
    }

    @Override
    protected void initViews() {
        send.setOnClickListener(new MyOnClickListener(0));
        code.setOnClickListener(new MyOnClickListener(1));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v){
            if (index == 0) {
                sendCode();
            }
            if (index == 1) {
                regist();
            }
        }
    }
    void sendCode(){
        if (NetUtils.isNetworkConnected(mCOntext)) {
            SendVerifyReq req = new SendVerifyReq();
            req.setPhone_num(phonenum_txt);
            mService.sendVerifyCode(req, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    send.setEnabled(false);
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    //Log.i("INFO", res.toString());
                }
            });
        } else {
            showNetWorkError();
        }
    }
    void regist() {
        if (NetUtils.isNetworkConnected(mCOntext)) {
            RegisterReq req = new RegisterReq();
            req.setPhone_num(phonenum_txt);
            req.setCode(code_txt);
            req.setPassword(password_txt);
            mService.register(req, new Callback<RegisterRes>() {
                @Override
                public void success(RegisterRes registerRes, Response response) {
                    if (response.getStatus() == 200) {
                        access_token = registerRes.getAccess_token();
                        System.out.println(access_token);
                        readyGo(MainActivity.class);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    //Log.i("INFO", res.toString());
                }
            });
        }else {
            showNetWorkError();
        }
    }
}
