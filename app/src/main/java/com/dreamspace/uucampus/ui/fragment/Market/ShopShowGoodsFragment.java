package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodsListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.SearchGoodsRes;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
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
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private String shopId;//当前商铺的shopid
    private String group;//当前group的名称
    private int goodPage = 1;//当前good的page
    private boolean fragmentDestory = false;
    private boolean alreadyGetData = false;
    private boolean firstGetGoods = true;//判断是不是第一次获取数据，第一次获取数据需要显示“加载中”界面
    private GoodsListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取从activity传递过来的shopid和group，根据这两个属性获取自己要获取的内容
        shopId = getArguments() == null? "null":getArguments().getString(ShopShowGoodsAct.SHOP_ID);
        group = getArguments() == null? "null":getArguments().getString(ShopShowGoodsAct.GROUP);
    }

    @Override
    protected void onFirstUserVisible() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
        if(!alreadyGetData){
            getGoods();
        }else{
            if(adapter != null){
                loadMoreListView.setAdapter(adapter);
            }else{
                toggleShowEmpty(true,getString(R.string.no_such_shop),getGoodsClickListener);
            }
        }
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
                Bundle bundle = new Bundle();
                bundle.putString(GoodDetailAct.GOOD_ID, adapter.getItem(position).getGoods_id());
                readyGo(GoodDetailAct.class, bundle);
            }
        });

        loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreListView.setLoading(true);
                goodPage++;
                getGoods();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstGetGoods = true;
                goodPage = 1;
                getGoods();
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_show_items;
    }

    //获取商品
    private void getGoods(){
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

        ApiManager.getService(mContext).searchGoods(null, null, null, null, group, shopId, goodPage
                , PreferenceUtils.getString(getActivity(),PreferenceUtils.Key.LOCATION), new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        if(searchGoodsRes != null && !fragmentDestory){
                            alreadyGetData = true;
                            loadMoreListView.setLoading(false);
                            swipeRefreshLayout.setRefreshing(false);
                            if(goodPage == 1 && searchGoodsRes.getResult().size() == 0){
                                toggleShowEmpty(true,getString(R.string.no_such_good),null);
                                return;
                            }

                            if(goodPage != 1 && searchGoodsRes.getResult().size() == 0){
                                //没有更多
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
                        if(!fragmentDestory){
                            alreadyGetData = false;
                            if(goodPage == 1){
                                toggleShowEmpty(true, null, getGoodsClickListener);
                            }else{
                                showInnerError(error);
                            }
                            loadMoreListView.setLoading(false);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    private View.OnClickListener getGoodsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getGoods();
        }
    };

    @Override
    public void onDestroy() {
        fragmentDestory = true;
        super.onDestroy();
    }
}
