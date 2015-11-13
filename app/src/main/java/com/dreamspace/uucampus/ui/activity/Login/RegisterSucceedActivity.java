package com.dreamspace.uucampus.ui.activity.Login;

import android.view.View;
import android.widget.Button;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/30.
 */
public class RegisterSucceedActivity extends AbsActivity {

    @Bind(R.id.register_succeed_btn)
    Button registerSucceedBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_succeed;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        registerSucceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoThenKill(LoginActivity.class);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
