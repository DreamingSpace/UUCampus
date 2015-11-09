package com.dreamspace.uucampus.ui.activity.Order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.Order.MyOrderListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.GetOrderListRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.ArrayList;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/20.
 */
public class MyOrderAct extends AbsActivity{
    @Bind(R.id.swp)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.load_more_lv)
    LoadMoreListView loadMoreLv;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private MyOrderListAdapter myOrderListAdapter;
    private int orderPage = 1;
    private boolean actDestroy = false;
    private boolean firstGetData = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void prepareDatas() {
        getOrderList();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.my_orders));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    private void initListeners(){
        loadMoreLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailAct.ORDER_ID,myOrderListAdapter.getItem(position).get_id());
                readyGo(OrderDetailAct.class, bundle);
                System.out.println("item click" + position);
            }
        });

        loadMoreLv.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                orderPage++;
                getOrderList();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPage = 1;
                firstGetData = true;
                getOrderList();
            }
        });
    }

    private void getOrderList(){
        if(firstGetData){
            toggleShowLoading(true,null);
        }

        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            if(firstGetData){
                toggleNetworkError(true,getOrderListClickListener);
            }
            loadMoreLv.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(this).getOrderList(orderPage, new Callback<GetOrderListRes>() {
            @Override
            public void success(GetOrderListRes getOrderListRes, Response response) {
                if(getOrderListRes != null && !actDestroy){
                    loadMoreLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);

                    if(orderPage == 1 && getOrderListRes.getOrders().size() == 0){
                        toggleShowEmpty(true,getString(R.string.no_orders),null);
                        return;
                    }

                    if(orderPage != 1 && getOrderListRes.getOrders().size() == 0){
                        showToast(getString(R.string.no_more));
                        return;
                    }

                    if(firstGetData){
                        myOrderListAdapter = new MyOrderListAdapter(MyOrderAct.this,getOrderListRes.getOrders(),MyOrderListAdapter.ViewHolder.class);
                        loadMoreLv.setAdapter(myOrderListAdapter);
                        toggleRestore();
                        firstGetData = false;
                    }else{
                        myOrderListAdapter.addEntities(getOrderListRes.getOrders());
                        myOrderListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(orderPage == 1){
                    toggleShowEmpty(true,null,getOrderListClickListener);
                }else{
                    showInnerError(error);
                }
                loadMoreLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private View.OnClickListener getOrderListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getOrderList();
        }
    };

    @Override
    protected void onDestroy() {
        actDestroy = true;
        super.onDestroy();
    }
}
