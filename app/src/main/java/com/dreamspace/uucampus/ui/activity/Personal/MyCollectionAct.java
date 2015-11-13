package com.dreamspace.uucampus.ui.activity.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Personal.CollectionFragment;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/15.
 */
public class MyCollectionAct extends AbsActivity {
    @Bind(R.id.goods_rl)
    RelativeLayout goodsRl;
    @Bind(R.id.seller_rl)
    RelativeLayout sellerRl;
    @Bind(R.id.idle_rl)
    RelativeLayout idleRl;
    @Bind(R.id.goods_tab_bottom_iv)
    ImageView goodsBottom;
    @Bind(R.id.seller_tab_bottom_iv)
    ImageView sellerBottom;
    @Bind(R.id.idle_tab_bottom_iv)
    ImageView idleBottom;

    public static final String TYPE = "type";

    private CollectionFragment goodsFragment;
    private CollectionFragment sellerFragment;
    private CollectionFragment idleFragment;
    //当前所在的fragment
    private int currentIndex = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void prepareDatas() {
        initFragments();
        goodsRl.setSelected(true);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.my_collection));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        goodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 0){
                    goodsRl.setSelected(true);
                    sellerRl.setSelected(false);
                    idleRl.setSelected(false);
                    goodsBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    sellerBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    idleBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    currentIndex = 0;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,goodsFragment)
                            .commit();
                }
            }
        });

        sellerRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 1){
                    goodsRl.setSelected(false);
                    sellerRl.setSelected(true);
                    idleRl.setSelected(false);
                    goodsBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    sellerBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    idleBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    currentIndex = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sellerFragment)
                            .commit();
                }
            }
        });

        idleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 2){
                    goodsRl.setSelected(false);
                    sellerRl.setSelected(false);
                    idleRl.setSelected(true);
                    goodsBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    sellerBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    idleBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    currentIndex = 2;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,idleFragment)
                            .commit();
                }
            }
        });
    }

    //初始化fragment
    private void initFragments(){
        goodsFragment = new CollectionFragment();
        Bundle goodsBundle = new Bundle();
        goodsBundle.putString(TYPE, getResources().getString(R.string.goods));
        goodsFragment.setArguments(goodsBundle);

        sellerFragment = new CollectionFragment();
        Bundle sellerBundle = new Bundle();
        sellerBundle.putString(TYPE, getResources().getString(R.string.seller));
        sellerFragment.setArguments(sellerBundle);

        idleFragment = new CollectionFragment();
        Bundle idleBundle = new Bundle();
        idleBundle.putString(TYPE,getString(R.string.free_goods));
        idleFragment.setArguments(idleBundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,goodsFragment)
                .commit();
    }
}
