package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

/**
 * Created by Lx on 2015/10/15.
 */
public class FeedbackAct extends AbsActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.feedback));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){

    }
}
