package com.dreamspace.uucampus.ui;

import android.view.View;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */

public class HomeFragment extends BaseLazyFragment {

    @Bind(R.id.free_goods_linear_layout)
    LinearLayout mFreeGoods;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {

        mFreeGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //快捷方式进入闲置页面
               readyGo(FreeGoodsActivity.class);
            }
        });


    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_first;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}