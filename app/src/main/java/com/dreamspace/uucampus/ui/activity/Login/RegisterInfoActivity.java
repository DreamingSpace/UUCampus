package com.dreamspace.uucampus.ui.activity.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterInfoActivity extends AbsActivity {

    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.register_personinfo_username)
    EditText userNameTextView;
    @Bind(R.id.register_personinfo_school)
    TextView schoolTextView;
    @Bind(R.id.personinfo_button)
    Button personInfoButton;
    @Bind(R.id.register_personinfo_enrolledtime)
    TextView enrolledTime;

    private String userName;
    private String school;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_personinfo_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        personInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(MainActivity.class);
            }
        });
        enrolledTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearDialog();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //显示年份选择对话框
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
                enrolledTime.setText(inSchoolYeardialog.getSelected());
                inSchoolYeardialog.dismiss();
            }
        });
    }
}
