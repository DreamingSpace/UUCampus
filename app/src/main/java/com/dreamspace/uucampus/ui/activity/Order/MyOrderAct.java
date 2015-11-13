package com.dreamspace.uucampus.ui.activity.Order;

import android.content.Intent;
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

    private static final int GO_PAY = 1;//从列表项进入支付界面
    private static final int GO_COMMENT = 2;//从列表项进入评论界面
    private static final int GO_ORDER_DETAIL = 3;

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
                bundle.putString(OrderDetailAct.ORDER_ID, myOrderListAdapter.getItem(position).get_id());
                readyGoForResult(OrderDetailAct.class, GO_ORDER_DETAIL, bundle);
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
                if (getOrderListRes != null && !actDestroy) {
                    loadMoreLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);

                    if (orderPage == 1 && getOrderListRes.getOrders().size() == 0) {
                        toggleShowEmpty(true, getString(R.string.no_orders), null);
                        return;
                    }

                    if (orderPage != 1 && getOrderListRes.getOrders().size() == 0) {
                        showToast(getString(R.string.no_more));
                        return;
                    }

                    if (firstGetData) {
                        myOrderListAdapter = new MyOrderListAdapter(MyOrderAct.this, getOrderListRes.getOrders(), MyOrderListAdapter.ViewHolder.class);
                        loadMoreLv.setAdapter(myOrderListAdapter);
                        myOrderListAdapter.setOnPayClickListener(new MyOrderListAdapter.OnPayClickListener() {
                            @Override
                            public void onPayClick(String order_name, String order_id, float total_price, float rest_to_pay) {
                                //进入支付界面
                                inPayAct(order_name, order_id, total_price, rest_to_pay);
                            }
                        });

                        myOrderListAdapter.setOnCommentClickListener(new MyOrderListAdapter.OnCommentClickListener() {
                            @Override
                            public void onCommentClick(String order_id, String good_id) {
                                //进入评论界面
                                inCommentAct(order_id, good_id);
                            }
                        });
                        toggleRestore();
                        firstGetData = false;
                    } else {
                        myOrderListAdapter.addEntities(getOrderListRes.getOrders());
                        myOrderListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (orderPage == 1) {
                    toggleShowEmpty(true, null, getOrderListClickListener);
                } else {
                    showInnerError(error);
                }
                loadMoreLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void inCommentAct(String order_id,String good_id){
        Bundle bundle = new Bundle();
        bundle.putString(CommentAct.ORDER_ID, order_id);
        bundle.putString(CommentAct.GOOD_ID, good_id);
        readyGoForResult(CommentAct.class,GO_COMMENT,bundle);
    }

    private void inPayAct(String order_name,String order_id,float total_price,float rest_to_pay){
        Bundle bundle = new Bundle();
        bundle.putString(OrderPayAct.ORDER_NAME,order_name);
        bundle.putString(OrderPayAct.ORDER_ID,order_id);
        bundle.putFloat(OrderPayAct.ORDER_TOTAL_PRICE, total_price);
        bundle.putFloat(OrderPayAct.REST_TO_PAY,rest_to_pay);
        readyGoForResult(OrderPayAct.class, GO_PAY, bundle);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GO_PAY && resultCode == RESULT_OK){
            //刷新数据
            orderPage = 1;
            firstGetData = true;
            getOrderList();
        }else if(requestCode == GO_COMMENT && resultCode == RESULT_OK){
            orderPage = 1;
            firstGetData = true;
            getOrderList();
        }else if(requestCode == GO_ORDER_DETAIL && resultCode == RESULT_OK){
            //订单状态改变
            orderPage = 1;
            firstGetData = true;
            getOrderList();
        }
    }
}
