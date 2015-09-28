package com.dreamspace.uucampus.ui.person;

import android.content.Intent;
import android.view.MenuItem;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

/**
 * Created by zsh on 2015/9/22.
 */
public class FeedbackActivity extends AbsActivity {
    @Override
    protected int getContentView() {
        return R.layout.person_activity_feedback;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
