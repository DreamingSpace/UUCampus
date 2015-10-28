package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.ShopListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.CategoryItem;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.ShopItem;
import com.dreamspace.uucampus.model.api.SearchShopRes;
import com.dreamspace.uucampus.ui.MarketFragment;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    private CategoryItem categoryItem;
    private ArrayList<ShopItem> mShops = new ArrayList<>();
    private int shopPage = 1;
    private boolean fragmentDestroy = false;
    private boolean alreadyGotData = false;

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
        if(mShops.size() == 0){
            toggleShowEmpty(true,getString(R.string.no_such_shop),null);
            return;
        }
        ShopListAdapter adapter = new ShopListAdapter(mContext,mShops,ShopListAdapter.ViewHolder.class);
        loadMoreListView.setAdapter(adapter);
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return swipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        loadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readyGo(ShopShowGoodsAct.class);
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
                alreadyGotData = false;
                mShops.clear();
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
            if(!alreadyGotData){
                toggleNetworkError(true, getShopsClickListener);
            }
            loadMoreListView.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(mContext).searchShop("", "", categoryItem.getName(), shopPage, new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                if(searchShopRes != null && !fragmentDestroy){
                    mShops.addAll(searchShopRes.getResult());
                    ShopListAdapter adapter = new ShopListAdapter(mContext,mShops,ShopListAdapter.ViewHolder.class);
                    loadMoreListView.setAdapter(adapter);
                    //若还没取得过数据则取消加载界面
                    if(!alreadyGotData){
                        toggleRestore();
                    }
                    //已取得了数据
                    alreadyGotData = true;
                    loadMoreListView.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                //没有商店提醒用户没有相关店家
                if(errorRes.getCode() == 406){
                    if(shopPage == 1){
                        toggleShowEmpty(true,"没有相关商家",null);
                    }else{
                        //没有更多
                        showToast(getString(R.string.no_more));
                    }
                }else{
                    //非406的其他错误
                    if(shopPage == 1){
                        toggleShowEmpty(true, null, getShopsClickListener);
                    }else{
                        showInnerError(error);
                    }
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
//            System.out.println(categoryItem.getName() + "click");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDestroy = true;
    }
}
