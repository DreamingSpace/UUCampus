package com.dreamspace.uucampus.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.market.ShopHostActivity;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */
public class PersonInfoFragment extends BaseLazyFragment {
    @Bind(R.id.shop_management_btn)
    Button btn;

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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopHostActivity.class);
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