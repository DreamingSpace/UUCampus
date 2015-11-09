package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/15.
 */
public class SettingAct extends AbsActivity {
    @Bind(R.id.check_update_rl)
    RelativeLayout updateRl;
    @Bind(R.id.logout_btn)
    Button logoutBtn;
    @Bind(R.id.feedback_rl)
    RelativeLayout feedBackRl;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        updateRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        feedBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(FeedbackAct.class);
            }
        });
    }
}
