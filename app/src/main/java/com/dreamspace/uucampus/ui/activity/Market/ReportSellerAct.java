package com.dreamspace.uucampus.ui.activity.Market;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/22.
 */
public class ReportSellerAct extends AbsActivity{
    @Bind(R.id.report_seller_et)
    EditText reportEt;
    @Bind(R.id.finish_report_btn)
    Button submitBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_report_seller;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.report_shop1));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){

    }
}
