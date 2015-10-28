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
public class BoundActvity extends AbsActivity {

    @Bind(R.id.bound_userName)
    EditText boundUserName;
    @Bind(R.id.bound_verify_code)
    EditText boundVerifyCode;
    @Bind(R.id.bound_get_code)
    TextView boundGetCode;
    @Bind(R.id.bound_button)
    Button boundButton;

    private String userName = null;
    private String verifyCode = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_bound_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        boundGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加获取验证码的代码，后台服务
            }
        });
        boundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(MainActivity.class);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
