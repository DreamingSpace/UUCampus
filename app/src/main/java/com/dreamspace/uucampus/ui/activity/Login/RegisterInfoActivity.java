package com.dreamspace.uucampus.ui.activity.Login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterInfoActivity extends AbsActivity {

    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.rl_icon)
    RelativeLayout rlIcon;
    @Bind(R.id.register_personinfo_username)
    EditText registerPersoninfoUsername;
    @Bind(R.id.register_personinfo_school)
    TextView registerPersoninfoSchool;
    @Bind(R.id.register_personinfo_spinner_enrolledtime)
    Spinner registerPersoninfoSpinnerEnrolledtime;
    @Bind(R.id.register_personinfo_schooltimeLayout)
    RelativeLayout registerPersoninfoSchooltimeLayout;
    @Bind(R.id.personinfo_button)
    Button personinfoButton;

    private String userName = null;
    private String school = null;

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
        personinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(MainActivity.class);
            }
        });
    }
}
