package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/17.
 */
public class PersonalInfoAct extends AbsActivity {
    @Bind(R.id.avater_rl)
    RelativeLayout avaterRl;
    @Bind(R.id.nickname_rl)
    RelativeLayout nicknameRl;
    @Bind(R.id.sex_rl)
    RelativeLayout sexRl;
    @Bind(R.id.bundling_phone_rl)
    RelativeLayout phoneRl;
    @Bind(R.id.school_rl)
    RelativeLayout schoolRl;
    @Bind(R.id.year_rl)
    RelativeLayout yearRl;
    @Bind(R.id.weibo_rl)
    RelativeLayout weiboRl;
    @Bind(R.id.wechat_rl)
    RelativeLayout wechatRl;
    @Bind(R.id.logout_btn)
    Button logoutBtn;

    public static final int AVATER = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.personal_info));
        initListeners();
    }

    private void initListeners(){
        avaterRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(SelectPhotoActivity.class,AVATER);
            }
        });

        nicknameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sexRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        phoneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        schoolRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        yearRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        weiboRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
