package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodsListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.SearchGoodsRes;
import com.dreamspace.uucampus.ui.activity.Market.FastInAct;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/11/2.
 * 进入店铺页面用来展示商品的fragment
 */
public class ShopShowGoodsFragment extends BaseLazyFragment{
    @Bind(R.id.swiperefresh_id)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.load_more_lv)
    LoadMoreListView loadMoreListView;

    private String label;
    private String category;
    private int goodPage = 1;//当前good的page
    private boolean fragmentDestory = false;
    private boolean firstGetGoods = true;//判断是不是第一次获取数据，第一次获取数据需要显示“加载中”界面
    private GoodsListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取从activity传递过来的category和label，根据这两个属性获取自己要获取的内容
        label = getArguments() == null? "null":getArguments().getString(FastInAct.LABEL);
        category = getArguments() == null? "null":getArguments().getString(FastInAct.CATEGORY);
    }

    @Override
    protected void onFirstUserVisible() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
        getGoods(null);
    }

    @Override
    protected void onUserVisible() {

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
                Bundle bundle = new Bundle();
                bundle.putString(GoodDetailAct.GOOD_ID,adapter.getItem(position).getGoods_id());
                readyGo(GoodDetailAct.class,bundle);
            }
        });

        loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreListView.setLoading(true);
                goodPage++;
                getGoods(null);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstGetGoods = true;
                goodPage = 1;
                getGoods(null);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_show_items;
    }

    //order需要根据用户的选择传递进来
    private void getGoods(String order){
        if(firstGetGoods){
            toggleShowLoading(true,null);
        }
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            if(firstGetGoods){
                toggleNetworkError(true,getGoodsClickListener);
            }
            loadMoreListView.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(mContext).searchGoods(null, order, category, label, null, null, goodPage,
                new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        loadMoreListView.setLoading(false);
                        swipeRefreshLayout.setRefreshing(false);
                        if(searchGoodsRes != null && !fragmentDestory){
                            if(goodPage == 1 && searchGoodsRes.getResult().size() == 0){
                                toggleShowEmpty(true,getString(R.string.no_such_good),null);
                                return;
                            }

                            if(goodPage != 1 && searchGoodsRes.getResult().size() == 0){
                                showToast(getString(R.string.no_more));
                                return;
                            }

                            if(firstGetGoods){
                                adapter = new GoodsListAdapter(mContext,searchGoodsRes.getResult(),GoodsListAdapter.ViewHolder.class);
                                loadMoreListView.setAdapter(adapter);
                                toggleRestore();
                            }else{
                                adapter.addEntities(searchGoodsRes.getResult());
                                adapter.notifyDataSetChanged();
                            }
                            firstGetGoods = false;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(goodPage == 1){
                            toggleShowEmpty(true, null, getGoodsClickListener);
                        }else{
                            showInnerError(error);
                        }
                        loadMoreListView.setLoading(false);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    //当更换排序方式时，activity对调用此方法
    public void orderChange(String order){

    }

    private View.OnClickListener getGoodsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getGoods(null);
        }
    };

    @Override
    public void onDestroy() {
        fragmentDestory = true;
        super.onDestroy();
    }
}