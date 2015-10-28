package com.dreamspace.uucampus.ui.activity.Login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterActivity extends AbsActivity implements View.OnClickListener{
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

    private String phoneNum = null;
    private String verifyCode = null;
    private String password = null;
    private String passwordConfirm = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_page;
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

    private void initListener(){
        registerGetCode.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_get_code:

                break;
            case R.id.register_button:

                break;
        }
    }
}
