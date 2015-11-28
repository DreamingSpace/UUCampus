package com.dreamspace.uucampus.ui.fragment.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.Personal.FreeGoodCollectionListAdapter;
import com.dreamspace.uucampus.adapter.market.GoodsListAdapter;
import com.dreamspace.uucampus.adapter.market.ShopListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.GoodsItem;
import com.dreamspace.uucampus.model.IdleCollectionRes;
import com.dreamspace.uucampus.model.ShopItem;
import com.dreamspace.uucampus.model.api.AllGoodsCollectionRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.IdleCollectionItem;
import com.dreamspace.uucampus.model.api.ShopCollectionRes;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.widget.SwipeMenuLoadMoreListView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/15.
 * 此fragment为“我的收藏”界面的三个收藏的fragment，因此要对此fragment的功能根据type来进行判断
 */
public class CollectionFragment extends BaseFragment {
    @Bind(R.id.collection_smlv)
    SwipeMenuLoadMoreListView collectionLv;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private SwipeMenuCreator creator;

    private GoodsListAdapter goodsListAdapter;
    private ShopListAdapter shopListAdapter;
    private FreeGoodCollectionListAdapter idleListAdapter;
    private ProgressDialog progressDialog;
    private String type;
    private int goodsPage = 1;
    private int shopPage = 1;
    private int idlePage = 1;
    private boolean firstGetData = true;
    private int detailInPosition = -1;//进入的详情页面的list position

    public static final int GOOD_DETAIL = 1;
    public static final int SHOP_SHOW_GOODS = 2;
    public static final int IDLE_DETAIL = 3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取type，从而判断当前fragment为了展示那个界面效果
        type = getArguments() == null? "null":getArguments().getString(MyCollectionAct.TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    public void initViews(View view) {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
    }

    @Override
    public void initDatas() {
        if(firstGetData){
            if(type.equals(getString(R.string.goods))){
                getGoodsCollection();
            }else if(type.equals(getString(R.string.seller))){
                getShopCollection();
            }else if(type.equals(getString(R.string.free_goods))){
                getIdleCollection();
            }
        }else{
            if(type.equals(getString(R.string.goods))){
                collectionLv.setAdapter(goodsListAdapter);
            }else if(type.equals(getString(R.string.seller))){
                collectionLv.setAdapter(shopListAdapter);
            }else if(type.equals(getString(R.string.free_goods))){
                collectionLv.setAdapter(idleListAdapter);
            }
        }

        initListeners();
    }

    private void initListeners(){
        collectionLv.setOnLoadMoreListener(new SwipeMenuLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(type.equals(getString(R.string.goods))){
                    goodsPage++;
                    getGoodsCollection();
                }else if(type.equals(getString(R.string.seller))){
                    shopPage++;
                    getShopCollection();
                }else if(type.equals(getString(R.string.free_goods))){
                    idlePage++;
                    getIdleCollection();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(type.equals(getString(R.string.goods))){
                    goodsPage = 1;
                    firstGetData = true;
                    getGoodsCollection();
                }else if(type.equals(getString(R.string.seller))){
                    shopPage = 1;
                    firstGetData = true;
                    getShopCollection();
                }else if(type.equals(getString(R.string.free_goods))){
                    idlePage = 1;
                    firstGetData = true;
                    getIdleCollection();
                }
            }
        });

        //为list item添加swipe menu item
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
                openItem.setBackground(R.color.price_text_color);
                openItem.setWidth((int)(90*getResources().getDisplayMetrics().density));
                openItem.setTitle(getString(R.string.delete));
                openItem.setTitleSize((int)(8*getResources().getDisplayMetrics().density));
                openItem.setTitleColor(getResources().getColor(R.color.white));
                swipeMenu.addMenuItem(openItem);
            }
        };
        collectionLv.setMenuCreator(creator);

        //为menu item添加点击事件
        collectionLv.setOnMenuItemClickListener(new SwipeMenuLoadMoreListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                //i1为menu item的位置,i为listview的位置
                if (i1 == 0) {
                    initProgressDialog();
                    progressDialog.show();
                    if(type.equals(getString(R.string.goods))){
                        GoodsItem goodsItem = goodsListAdapter.getItem(i);
                        goodCollectionDelete(goodsItem.getGoods_id());
                    }else if(type.equals(getString(R.string.seller))){
                        ShopItem shopItem = shopListAdapter.getItem(i);
                        shopCollectionDelete(shopItem.getShop_id());
                    }else if(type.equals(getString(R.string.free_goods))){
                        IdleCollectionItem idleItem = idleListAdapter.getItem(i);
                        idleCollectionDelete(idleItem.getIdle_id());
                    }
                }
                return false;
            }
        });

        setListItemClickListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    //根据type对listview的itemclick事件进行设置
    private void setListItemClickListeners(){
        if(type.equals(getString(R.string.goods))){
            collectionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(goodsListAdapter != null){
                        //将对应的good_id传入商品详情界面
                        Bundle bundle = new Bundle();
                        bundle.putString(GoodDetailAct.GOOD_ID, goodsListAdapter.getItem(position).getGoods_id());
                        detailInPosition = position;
                        readyGoForResult(GoodDetailAct.class, GOOD_DETAIL, bundle);
                    }
                }
            });
        }else if(type.equals(getString(R.string.seller))){
            collectionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(shopListAdapter != null){
                        //将对应shop的id和name传入
                        Bundle bundle = new Bundle();
                        bundle.putString(ShopShowGoodsAct.SHOP_ID,shopListAdapter.getItem(position).getShop_id());
                        bundle.putString(ShopShowGoodsAct.SHOP_NAME,shopListAdapter.getItem(position).getName());
                        detailInPosition = position;
                        readyGoForResult(ShopShowGoodsAct.class, SHOP_SHOW_GOODS, bundle);
                    }
                }
            });
        }else if(type.equals(getString(R.string.free_goods))){
            collectionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(idleListAdapter != null){
                        Bundle bundle = new Bundle();
                        bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID,idleListAdapter.getItem(position).getIdle_id());
                        detailInPosition = position;
                        readyGoForResult(FreeGoodsDetailActivity.class,IDLE_DETAIL,bundle);
                    }
                }
            });
        }
    }

    //获取商品收藏
    private void getGoodsCollection(){
        if(firstGetData){
            toggleShowLoading(true,null);
        }

        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            if(firstGetData){
                toggleNetworkError(true,getGoodsCollectionClickListener);
            }
            collectionLv.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(getActivity()).getAllGoodsCollection(goodsPage, new Callback<AllGoodsCollectionRes>() {
            @Override
            public void success(AllGoodsCollectionRes allGoodsCollectionRes, Response response) {
                if (allGoodsCollectionRes != null) {
                    collectionLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                    if (allGoodsCollectionRes.getCollection().size() == 0 && goodsPage == 1) {
                        toggleShowEmpty(true, getString(R.string.no_such_good), null);
                        return;
                    }

                    //没有更多
                    if (allGoodsCollectionRes.getCollection().size() == 0 && goodsPage != 1) {
                        return;
                    }

                    if (firstGetData) {
                        goodsListAdapter = new GoodsListAdapter(getActivity(), allGoodsCollectionRes.getCollection(), GoodsListAdapter.ViewHolder.class);
                        collectionLv.setAdapter(goodsListAdapter);
                        firstGetData = false;
                        toggleRestore();
                    } else {
                        goodsListAdapter.addEntities(allGoodsCollectionRes.getCollection());
                        goodsListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                collectionLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
                if (firstGetData) {
                    toggleShowEmpty(true, null, getGoodsCollectionClickListener);
                } else {
                    showInnerError(error);
                }
            }
        });
    }

    //获取商铺收藏
    private void getShopCollection(){
        if(firstGetData){
            toggleShowLoading(true,null);
        }

        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            if(firstGetData){
                toggleNetworkError(true,getShopCollectionClickListener);
            }
            collectionLv.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(getActivity()).getPersonShopCollection(shopPage, new Callback<ShopCollectionRes>() {
            @Override
            public void success(ShopCollectionRes shopCollectionRes, Response response) {
                if (shopCollectionRes != null) {
                    collectionLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                    if (shopCollectionRes.getCollection().size() == 0 && shopPage == 1) {
                        toggleShowEmpty(true, getString(R.string.no_such_shop), null);
                        return;
                    }

                    if (shopCollectionRes.getCollection().size() == 0 && shopPage != 1) {
                        showToast("没有更多");
                        return;
                    }

                    if (firstGetData) {
                        shopListAdapter = new ShopListAdapter(getActivity(), shopCollectionRes.getCollection(), ShopListAdapter.ViewHolder.class);
                        collectionLv.setAdapter(shopListAdapter);
                        firstGetData = false;
                        toggleRestore();
                    } else {
                        shopListAdapter.addEntities(shopCollectionRes.getCollection());
                        shopListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                collectionLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
                if (firstGetData) {
                    toggleShowEmpty(true, null, getShopCollectionClickListener);
                } else {
                    showInnerError(error);
                }
            }
        });
    }

    //获取闲置收藏
    private void getIdleCollection(){
        if(firstGetData){
            toggleShowLoading(true,null);
        }

        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            if(firstGetData){
                toggleNetworkError(true,getShopCollectionClickListener);
            }
            collectionLv.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(getActivity()).getIdleCollection(idlePage, new Callback<IdleCollectionRes>() {
            @Override
            public void success(IdleCollectionRes idleCollectionRes, Response response) {
                if (idleCollectionRes != null) {
                    collectionLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                    if (idleCollectionRes.getCollection().size() == 0 && idlePage == 1) {
                        toggleShowEmpty(true, getString(R.string.no_such_good), null);
                        return;
                    }

                    if (idleCollectionRes.getCollection().size() == 0 && idlePage != 1) {
                        showToast("没有更多");
                        return;
                    }

                    if (firstGetData) {
                        idleListAdapter = new FreeGoodCollectionListAdapter(getActivity(), idleCollectionRes.getCollection(), FreeGoodCollectionListAdapter.ViewHolder.class);
                        collectionLv.setAdapter(idleListAdapter);
                        firstGetData = false;
                        toggleRestore();
                    } else {
                        idleListAdapter.addEntities(idleCollectionRes.getCollection());
                        idleListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                collectionLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
                if (firstGetData) {
                    toggleShowEmpty(true, null, getIdleCollectionClickListener);
                } else {
                    showInnerError(error);
                }
            }
        });
    }

    //商品收藏删除
    private void goodCollectionDelete(String goodId){
        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        ApiManager.getService(getActivity()).deleteGoodsCollection(goodId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null) {
                    progressDialog.dismiss();
                    showToast(getString(R.string.delete_success));
                    //刷新数据
                    goodsPage = 1;
                    firstGetData = true;
                    getGoodsCollection();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    //商铺收藏删除
    private void shopCollectionDelete(String shopId){
        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        ApiManager.getService(getActivity()).deleteShopCollection(shopId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null) {
                    progressDialog.dismiss();
                    showToast(getString(R.string.delete_success));
                    //刷新数据
                    shopPage = 1;
                    firstGetData = true;
                    getShopCollection();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    //闲置收藏删除
    private void idleCollectionDelete(String idleId){
        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        ApiManager.getService(getActivity()).idleCollectionDelete(idleId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null) {
                    progressDialog.dismiss();
                    showToast(getString(R.string.delete_success));
                    //刷新数据
                    idlePage = 1;
                    firstGetData = true;
                    getIdleCollection();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //若进入详情页面取消收藏后返回此页面则将相应项从列表中删除
        if(requestCode == GOOD_DETAIL && resultCode == getActivity().RESULT_OK){
            int state = data.getIntExtra(GoodDetailAct.GOOD_CURRENT_COLLCET_STATE,-1);
            if(state == 0){
                //取消收藏
                if(type.equals(getString(R.string.goods))){
                    goodsListAdapter.removeItem(detailInPosition);
                }
//                else if(type.equals(getString(R.string.seller))){
//                    shopListAdapter.removeItem(detailInPosition);
//                }else if(type.equals(getString(R.string.free_goods))){
//                    idleListAdapter.removeItem(detailInPosition);
//                }
            }
        }else if(requestCode == SHOP_SHOW_GOODS && resultCode == getActivity().RESULT_OK){
            int state = data.getIntExtra(ShopShowGoodsAct.SHOP_CURRENT_COLLECT_STATE,-1);
            if(state == 0){
                if(type.equals(getString(R.string.seller))){
                    shopListAdapter.removeItem(detailInPosition);
                }
//                if(type.equals(getString(R.string.goods))){
//                    goodsListAdapter.removeItem(detailInPosition);
//                }else if(type.equals(getString(R.string.seller))){
//                    shopListAdapter.removeItem(detailInPosition);
//                }else if(type.equals(getString(R.string.free_goods))){
//                    idleListAdapter.removeItem(detailInPosition);
//                }
            }
        }else if(requestCode == IDLE_DETAIL && resultCode == getActivity().RESULT_OK){
            int state = data.getIntExtra(FreeGoodsDetailActivity.IDLE_CURRENT_COLLECT_STATE,-1);
            if(state == 0){
                if(type.equals(getString(R.string.free_goods))){
                    idleListAdapter.removeItem(detailInPosition);
                }
            }
//            if(type.equals(getString(R.string.goods))){
//                goodsListAdapter.removeItem(detailInPosition);
//            }else if(type.equals(getString(R.string.seller))){
//                shopListAdapter.removeItem(detailInPosition);
//            }else if(type.equals(getString(R.string.free_goods))){
//                idleListAdapter.removeItem(detailInPosition);
//            }
        }
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setContent(getString(R.string.in_delete));
    }

    private View.OnClickListener getGoodsCollectionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getGoodsCollection();
        }
    };

    private View.OnClickListener getShopCollectionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getShopCollection();
        }
    };

    private View.OnClickListener getIdleCollectionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getIdleCollection();
        }
    };
}