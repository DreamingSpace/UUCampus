package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsFragment;
import com.dreamspace.uucampus.ui.popupwindow.GoodsSortPopupWindow;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/19.
 */
public class FreeGoodsActivity extends AbsActivity {

    @Bind(R.id.free_goods_publish_btn)
    FloatingActionButton mPublishBtn;

    GoodsSortPopupWindow popupWindow;

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

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        popupWindow.setMostPopularOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(0);
            }
        });

        popupWindow.setHighestAppraiesOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(1);
            }
        });

        popupWindow.setRecentPublishOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(2);
            }
        });

        popupWindow.setCheapestOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(3);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_free_goods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.free_goods_action_sort) {
            if (popupWindow != null) {
                popupWindow.showAsDropDown(mToolBar);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            super.onBackPressed();
        }
    }
}