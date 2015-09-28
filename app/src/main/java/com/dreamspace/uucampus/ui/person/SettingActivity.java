package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/22.
 */
public class SettingActivity extends AbsActivity {
    private Context mContext;
    @Bind(R.id.relative1)
    RelativeLayout relative1;
    @Bind(R.id.relative2)
    RelativeLayout relative2;
    @Bind(R.id.logout_button)
    Button logout_button;
    @Override
    protected int getContentView() {
        return R.layout.person_activity_setting;
    }

    @Override
    protected void prepareDatas() {
        mContext = this;
    }

    @Override
    protected void initViews() {
        relative1.setOnClickListener(new MyOnClickListener(0));
        relative2.setOnClickListener(new MyOnClickListener(1));
        logout_button.setOnClickListener(new MyOnClickListener(2));
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            if(index == 0) {
                Toast.makeText(mContext,"当前版本是最新的哦~",Toast.LENGTH_LONG).show();
            }
            if(index == 1) {
                Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //readyGo(FeedbackActivity.class);
            }
            if(index == 2) {

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
