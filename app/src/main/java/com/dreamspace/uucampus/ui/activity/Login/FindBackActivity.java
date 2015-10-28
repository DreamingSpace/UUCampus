package com.dreamspace.uucampus.ui.activity.Login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/13.
 */
public class FindBackActivity extends AbsActivity implements View.OnClickListener {

    @Bind(R.id.findback_userName)
    EditText findbackUserName;
    @Bind(R.id.findback_verify_code)
    EditText findbackVerifyCode;
    @Bind(R.id.findback_get_code)
    TextView findbackGetCode;
    @Bind(R.id.findback_pwd)
    EditText findbackPwd;
    @Bind(R.id.findback_pwd_confirm)
    EditText findbackPwdConfirm;
    @Bind(R.id.findback_button)
    Button findbackButton;

    private String phoneNum = null;
    private String verifyCode = null;
    private String password = null;
    private String passwordConfirm = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_findback_pwd;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        initListener();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.findback_get_code:
                //添加获取验证码的代码，后台服务
                break;
            case R.id.findback_button:
                readyGo(MainActivity.class);
                break;
        }
    }

    private void initListener(){
        findbackGetCode.setOnClickListener(this);
        findbackButton.setOnClickListener(this);
    }
}
