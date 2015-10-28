package com.dreamspace.uucampus.ui.activity.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Personal.MyFreeGoodsFragment;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/17.
 */
public class MyFreeGoodsAct extends AbsActivity{
    @Bind(R.id.on_sale_rl)
    RelativeLayout saleRl;
    @Bind(R.id.pull_off_rl)
    RelativeLayout pullOffRl;
    @Bind(R.id.sale_tab_bottom_iv)
    ImageView saleBottom;
    @Bind(R.id.pull_off_tab_bottom_iv)
    ImageView pullOffBottom;

    public static final String TYPE = "type";

    private MyFreeGoodsFragment onSaleFragment;
    private MyFreeGoodsFragment pullOffFragment;

    private int currentIndex = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_free_goods;
    }

    @Override
    protected void prepareDatas() {
        initFragments();
        saleRl.setSelected(true);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.my_free_goods));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        saleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 0){
                    saleRl.setSelected(true);
                    pullOffRl.setSelected(false);
                    saleBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    pullOffBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    currentIndex = 0;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,onSaleFragment)
                            .commit();
                }
            }
        });

        pullOffRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != 1){
                    saleRl.setSelected(false);
                    pullOffRl.setSelected(true);
                    saleBottom.setBackgroundColor(getResources().getColor(R.color.white));
                    pullOffBottom.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                    currentIndex = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,pullOffFragment)
                            .commit();
                }
            }
        });
    }

    private void initFragments(){
        onSaleFragment = new MyFreeGoodsFragment();
        Bundle saleBundle = new Bundle();
        saleBundle.putString(TYPE, getResources().getString(R.string.on_sale));
        onSaleFragment.setArguments(saleBundle);

        pullOffFragment = new MyFreeGoodsFragment();
        Bundle pullOffBundle = new Bundle();
        pullOffBundle.putString(TYPE,getResources().getString(R.string.already_pull_off));
        pullOffFragment.setArguments(pullOffBundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,onSaleFragment)
                .commit();
    }
}
