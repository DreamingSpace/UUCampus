package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    @Bind(R.id.free_goods_ll)
    LinearLayout freeGoodsLl;
    @Bind(R.id.travel_ll)
    LinearLayout travelLl;
    @Bind(R.id.driver_school_ll)
    LinearLayout driverSchoolLl;
    @Bind(R.id.class_uniform_ll)
    LinearLayout classUniformLl;
    @Bind(R.id.studying_abroad_ll)
    LinearLayout studyAbroadLl;
    @Bind(R.id.personal_shop_ll)
    LinearLayout personalShopLl;
    @Bind(R.id.study_abroad_rl)
    RelativeLayout studyAbroadRl;
    @Bind(R.id.free_goods_rl)
    RelativeLayout freeGoodsRl;
    @Bind(R.id.travel_rl)
    RelativeLayout travelRl;
    @Bind(R.id.driver_school_rl)
    RelativeLayout driverSchoolRl;
    @Bind(R.id.personal_shop_rl)
    RelativeLayout personalShopRl;

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
        travelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.travel));
                readyGo(FastInAct.class,bundle);
            }
        });

        classUniformLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.class_uniform));
                readyGo(FastInAct.class,bundle);
            }
        });

        driverSchoolLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class,bundle);
            }
        });

        studyAbroadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class,bundle);
            }
        });

        personalShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class,bundle);
            }
        });

        freeGoodsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //快捷方式进入闲置页面
               readyGo(FreeGoodsActivity.class);
            }
        });

        travelRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.travel));
                readyGo(FastInAct.class,bundle);
            }
        });

        driverSchoolRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class,bundle);
            }
        });

        studyAbroadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class,bundle);
            }
        });

        personalShopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CLASSIFICATION_TYPE,getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class,bundle);
            }
        });

        freeGoodsRl.setOnClickListener(new View.OnClickListener() {
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