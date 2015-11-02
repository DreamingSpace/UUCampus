package com.dreamspace.uucampus.ui.activity.Order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

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
        loadMoreLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                switch (position){
//                    case 0:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.UNPAID);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
//
//                    case 1:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.UNCONSUME);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
//
//                    case 2:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.UNCOMMENT);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
//
//                    case 3:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.IN_REFUND);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
//
//                    case 4:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.ALREADY_REFUND);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
//
//                    case 5:
//                        bundle.putInt(OrderDetailAct.ORDER_STATE,OrderDetailAct.ALREADY_COMMENT);
//                        readyGo(OrderDetailAct.class,bundle);
//                        break;
                }
            }
        });
    }
}
