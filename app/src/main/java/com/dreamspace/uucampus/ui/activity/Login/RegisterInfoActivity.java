package com.dreamspace.uucampus.ui.activity.Login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterInfoActivity extends AbsActivity {

    @Bind(R.id.register_personinfo_username)
    EditText userNameTextView;
    @Bind(R.id.register_personinfo_school)
    TextView schoolTextView;
    @Bind(R.id.personinfo_button)
    Button personInfoButton;
    @Bind(R.id.register_personinfo_enrolledtime)
    TextView enrolledTime;
    @Bind(R.id.register_info_icon)
    RelativeLayout registerInfoIcon;

    //还不知道Location是怎么回事~
    private String userName;
    private String school;
    private String img;
    private String enroll_year;
    private String location;
    public static final int AVATER = 1;

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
                userInfo();
                readyGo(MainActivity.class);
            }
        });
        registerInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoForResult(SelectPhotoActivity.class,AVATER);
            }
        });
        enrolledTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearDialog();
            }
        });
    }

    //显示年份选择对话框
    private void showYearDialog() {
        ArrayList<String> years = new ArrayList<>();
        for (int i = 2010; i < 2020; i++) {
            years.add(i + "");
        }
        final WheelViewDialog inSchoolYeardialog = new WheelViewDialog(this, years, getString(R.string.select_in_school_year));

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

    //提交个人信息
    private void userInfo(){
        userName = userNameTextView.getText().toString();
        school = schoolTextView.getText().toString();
        enroll_year = enrolledTime.getText().toString();
        //写与后台的交互，后台还有点问题暂时不写

    }
}
