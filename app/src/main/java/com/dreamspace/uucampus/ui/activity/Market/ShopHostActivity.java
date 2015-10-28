package com.dreamspace.uucampus.ui.activity.Market;

import android.view.View;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lx on 2015/9/23.
 */
public class ShopHostActivity extends AbsActivity{
    @Bind(R.id.my_shop_image_iv)
    CircleImageView shopImageIv;
    @Bind(R.id.my_shop_ll)
    LinearLayout myShopLl;
    @Bind(R.id.custom_service_ll)
    LinearLayout customServiceLl;

    @Override
    protected int getContentView() {
        return R.layout.activity_shop_host_page;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.shop_host));
        myShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopManagmentAct.class);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
