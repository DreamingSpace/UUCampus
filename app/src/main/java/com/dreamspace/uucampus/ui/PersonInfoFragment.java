package com.dreamspace.uucampus.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.market.ShopHostActivity;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */
public class PersonInfoFragment extends BaseLazyFragment {
    @Bind(R.id.my_free_goods_ll)
    LinearLayout freeGoodsLl;
    @Bind(R.id.my_collection_ll)
    LinearLayout collectionLl;
    @Bind(R.id.my_shop_ll)
    LinearLayout shopLl;
    @Bind(R.id.setting_ll)
    LinearLayout settingLl;
    @Bind(R.id.about_ll)
    LinearLayout aboutLl;

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
        shopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopHostActivity.class);
            }
        });

        collectionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MyCollectionAct.class);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_second;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}