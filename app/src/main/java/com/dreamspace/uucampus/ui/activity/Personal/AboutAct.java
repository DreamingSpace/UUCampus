package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

/**
 * Created by Lx on 2015/11/9.
 */
public class AboutAct extends AbsActivity{
    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.about));
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
