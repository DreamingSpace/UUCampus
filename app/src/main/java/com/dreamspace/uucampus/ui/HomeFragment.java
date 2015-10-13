package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.market.FastInAct;

import butterknife.Bind;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */

public class HomeFragment extends BaseLazyFragment {
    @Bind(R.id.button1)
    Button travel;
    @Bind(R.id.button2)
    Button driver;
    @Bind(R.id.button3)
    Button uniform;
    @Bind(R.id.button4)
    Button abroad;
    @Bind(R.id.button5)
    Button shop;
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
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.travel));
                readyGo(FastInAct.class,bundle);
            }
        });

        uniform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.class_uniform));
                readyGo(FastInAct.class,bundle);
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class,bundle);
            }
        });

        abroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class,bundle);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class,bundle);
            }
        });

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