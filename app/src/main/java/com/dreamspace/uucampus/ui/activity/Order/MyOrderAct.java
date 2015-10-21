package com.dreamspace.uucampus.ui.activity.Order;

import android.support.v4.widget.SwipeRefreshLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.Order.MyOrderListAdapter;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/20.
 */
public class MyOrderAct extends AbsActivity{
    @Bind(R.id.swp)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.load_more_lv)
    LoadMoreListView loadMoreLv;

    private MyOrderListAdapter myOrderListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void prepareDatas() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        myOrderListAdapter = new MyOrderListAdapter(this,list,MyOrderListAdapter.ViewHolder.class);
        loadMoreLv.setAdapter(myOrderListAdapter);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.my_orders));
        initListeners();
    }

    private void initListeners(){

    }
}
