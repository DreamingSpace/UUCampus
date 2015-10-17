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
    @Bind(R.id.goods_tab_bottom_iv)
    ImageView goodsBottom;
    @Bind(R.id.seller_tab_bottom_iv)
    ImageView sellerBottom;

    public static final String TYPE = "type";

    private CollectionFragment goodsFragment;
    private CollectionFragment sellerFragment;
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

    private void initListeners(){
        goodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 0){
                    goodsRl.setSelected(true);
                    sellerRl.setSelected(false);
                    goodsBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    sellerBottom.setBackgroundColor(getResources().getColor(R.color.white));
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
                    goodsBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    sellerBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    currentIndex = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sellerFragment)
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
        sellerBundle.putString(TYPE,getResources().getString(R.string.seller));
        sellerFragment.setArguments(sellerBundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,goodsFragment)
                .commit();
    }
}
