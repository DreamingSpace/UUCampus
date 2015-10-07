package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/22.
 */
public class FreeGoodsPublishSuccessActivity extends AbsActivity {

   @Bind(R.id.free_goods_publish_look_detail_btn)
    Button mLookDetailBtn;
    @Bind(R.id.free_goods_publish_back_home_btn)
    Button mBackBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_success;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        mLookDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                readyGo(FreeGoodsDetailActivity.class,bundle);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MainActivity.class);
                finish();
            }
        });
    }
}
