package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsFragment;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/19.
 */
public class FreeGoodsActivity extends AbsActivity {

    @Bind(R.id.free_goods_publish_btn)
    FloatingActionButton mPublishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods;
    }

    @Override
    protected void prepareDatas() {
        FreeGoodsFragment fragment = new FreeGoodsFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.free_goods_tab_container, fragment).
                commit();
    }

    @Override
    protected void initViews() {
        mPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(FreeGoodsPublishFirstActivity.class);
            }
        });
    }


}
