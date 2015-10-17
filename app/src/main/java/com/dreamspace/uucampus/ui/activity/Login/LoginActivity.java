package com.dreamspace.uucampus.ui.activity.Login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.model.api.ResetReq;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private String phoneNum = null;
    private String password = null;

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
                phoneNum = LoginUserName.getText().toString();
                password = LoginPwd.getText().toString();
                login();
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

    private void login(){
        //添加账户认证代码，后台交互
        readyGo(MainActivity.class);
    }
}
