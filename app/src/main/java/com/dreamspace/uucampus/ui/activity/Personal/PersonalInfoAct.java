package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

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

    @Override
    protected View getLoadingTargetView() {
        return null;
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
                showYearDialog();
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
    }

    private void showYearDialog(){
        ArrayList<String> years = new ArrayList<>();
        for(int i = 2010;i < 2020;i++){
            years.add(i + "");
        }
        WheelViewDialog inSchoolYeardialog = new WheelViewDialog(this,years,getString(R.string.select_in_school_year));

        inSchoolYeardialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        inSchoolYeardialog.setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
