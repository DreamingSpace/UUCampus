package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.ShopListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.CategoryItem;
import com.dreamspace.uucampus.model.ShopItem;
import com.dreamspace.uucampus.model.api.SearchShopRes;
import com.dreamspace.uucampus.ui.MarketFragment;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/9/21.
 */
public class ShowShopsFragment extends BaseLazyFragment {
    @Bind(R.id.swiperefresh_id)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.load_more_lv)
    LoadMoreListView loadMoreListView;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private CategoryItem categoryItem;
    private int shopPage = 1;
    private boolean fragmentDestroy = false;
    private boolean firstGetData = true;
    private ShopListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryItem = getArguments().getParcelable(MarketFragment.CATEGORY);
    }

    @Override
    protected void onFirstUserVisible() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
        toggleShowLoading(true, null);
        getShops();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    @Override
    protected void initViewsAndEvents() {
        loadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter != null){
                    //启动商家页面，并传入对应商家的shopid,shopname
                    ShopItem shopInfo = adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(ShopShowGoodsAct.SHOP_ID,shopInfo.getShop_id());
                    bundle.putString(ShopShowGoodsAct.SHOP_NAME,shopInfo.getName());
                    readyGo(ShopShowGoodsAct.class, bundle);
                }
            }
        });

        loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreListView.setLoading(true);
                shopPage++;
                getShops();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shopPage = 1;
                firstGetData = true;
                getShops();
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_show_items;
    }

    private void getShops(){
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            //若还没有取得数据则显示网络错误界面
            if(firstGetData){
                toggleNetworkError(true, getShopsClickListener);
            }
            loadMoreListView.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(mContext).searchShop("", "", categoryItem.getName(), shopPage, "东南大学九龙湖校区",new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                if(searchShopRes != null && !fragmentDestroy){
                    loadMoreListView.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                    //没有数据
                    if(shopPage == 1 && searchShopRes.getResult().size() == 0){
                        toggleShowEmpty(true,getString(R.string.no_such_shop),null);
                        return;
                    }

                    //没有更多
                    if(shopPage != 1 && searchShopRes.getResult().size() == 0){
                        showToast(getString(R.string.no_more));
                        return;
                    }

                    //若还没取得过数据则取消加载界面
                    if(firstGetData){
                        adapter = new ShopListAdapter(mContext,searchShopRes.getResult(),ShopListAdapter.ViewHolder.class);
                        loadMoreListView.setAdapter(adapter);
                        toggleRestore();
                        //已取得了数据
                        firstGetData = false;
                    }else{
                        adapter.addEntities(searchShopRes.getResult());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(shopPage == 1){
                    toggleShowEmpty(true, null, getShopsClickListener);
                }else{
                    showInnerError(error);
                }
                loadMoreListView.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private View.OnClickListener getShopsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getShops();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDestroy = true;
    }
}
