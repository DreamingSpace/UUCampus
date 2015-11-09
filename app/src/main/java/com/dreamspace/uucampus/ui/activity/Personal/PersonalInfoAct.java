package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lx on 2015/10/17.
 */
public class PersonalInfoAct extends AbsActivity {
    @Bind(R.id.avater_rl)
    RelativeLayout avaterRl;
    @Bind(R.id.nickname_rl)
    RelativeLayout nicknameRl;
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
    @Bind(R.id.personal_info_avater)
    CircleImageView avaterCiv;
    @Bind(R.id.nickname_tv)
    TextView nickNameTv;
    @Bind(R.id.bundling_phone_tv)
    TextView phoneTv;
    @Bind(R.id.in_school_year_tv)
    TextView yearTv;

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
        final WheelViewDialog inSchoolYeardialog = new WheelViewDialog(this,years,getString(R.string.select_in_school_year));

        inSchoolYeardialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inSchoolYeardialog.dismiss();
            }
        });

        inSchoolYeardialog.setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearTv.setText(inSchoolYeardialog.getSelected());
                inSchoolYeardialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AVATER && resultCode == RESULT_OK){
            String path = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
            Glide.with(PersonalInfoAct.this)
                    .load(path)
                    .error(R.drawable.default_error)
                    .centerCrop()
                    .into(avaterCiv);
//          CommonUtils.showImageWithGlide(this,avaterCiv,path);
        }
    }
}
