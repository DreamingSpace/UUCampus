package com.dreamspace.uucampus.ui.activity.Market;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

/**
 * Created by Lx on 2015/10/10.
 */
public class ShopDetailAct extends AbsActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle("东大旅游吧");
    }
}
