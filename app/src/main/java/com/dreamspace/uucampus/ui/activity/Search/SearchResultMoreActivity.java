package com.dreamspace.uucampus.ui.activity.Search;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.search.SearchGoodsAdapter;
import com.dreamspace.uucampus.adapter.search.SearchIdleAdapter;
import com.dreamspace.uucampus.adapter.search.SearchShopAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.GoodsItem;
import com.dreamspace.uucampus.model.IdleItem;
import com.dreamspace.uucampus.model.ShopItem;
import com.dreamspace.uucampus.model.api.SearchGoodsRes;
import com.dreamspace.uucampus.model.api.SearchIdleRes;
import com.dreamspace.uucampus.model.api.SearchShopRes;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/11/19.
 */
public class SearchResultMoreActivity extends AbsActivity {
    @Bind(R.id.search_text)
    EditText searchText;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.search_goods_more_list)
    ListView searchGoodsMoreList;
    @Bind(R.id.search_goods_more_linear)
    LinearLayout searchGoodsMoreLinear;
    @Bind(R.id.search_idle_more_list)
    ListView searchIdleMoreList;
    @Bind(R.id.search_idle_more_linear)
    LinearLayout searchIdleMoreLinear;
    @Bind(R.id.search_shop_more_list)
    ListView searchShopMoreList;
    @Bind(R.id.search_shop_more_linear)
    LinearLayout searchShopMoreLinear;
    @Bind(R.id.search_more_failed_linear)
    LinearLayout searchMoreFailedLinear;

    private String keyWord;
    private static int category;
    private ProgressDialog pd;
    private boolean haveResult;
    private SearchGoodsAdapter searchGoodsAdapter;
    private SearchIdleAdapter searchIdleAdapter;
    private SearchShopAdapter searchShopAdapter;
    //保存搜索结果
    private List<GoodsItem> goodsItems;
    private List<IdleItem> idleItems;
    private List<ShopItem> shopItems;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result_more;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        category = bundle.getInt("category");
        keyWord = bundle.getString("key");
        searchText.setText(keyWord);
        pd = new ProgressDialog(this);
        goodsItems = new ArrayList<>();
        idleItems = new ArrayList<>();
        shopItems = new ArrayList<>();
        switch (category) {
            case 0:
                searchGoodsMoreLinear.setVisibility(View.GONE);
                searchGood();
                break;
            case 1:
                searchIdleMoreLinear.setVisibility(View.GONE);
                searchIdle();
                break;
            case 2:
                searchShopMoreLinear.setVisibility(View.GONE);
                searchShop();
                break;
        }
    }

    @Override
    protected void initViews() {
        searchGoodsMoreLinear.setVisibility(View.GONE);
        searchIdleMoreLinear.setVisibility(View.GONE);
        searchShopMoreLinear.setVisibility(View.GONE);
        searchMoreFailedLinear.setVisibility(View.GONE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyWord = searchText.getText().toString();
                searchMoreFailedLinear.setVisibility(View.GONE);
                pd.show();
                switch (category) {
                    case 0:
                        searchGoodsMoreLinear.setVisibility(View.GONE);
                        searchGood();
                        break;
                    case 1:
                        searchIdleMoreLinear.setVisibility(View.GONE);
                        searchIdle();
                        break;
                    case 2:
                        searchShopMoreLinear.setVisibility(View.GONE);
                        searchShop();
                        break;
                }
            }
        });

        initListeners();
    }

    private void initListeners(){
        searchGoodsMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchGoodsAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GoodDetailAct.GOOD_ID, searchGoodsAdapter.getItem(i).getGoods_id());
                    readyGo(GoodDetailAct.class, bundle);
                }
            }
        });

        searchShopMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(searchShopAdapter != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(ShopShowGoodsAct.SHOP_ID,searchShopAdapter.getItem(i).getShop_id());
                    bundle.putString(ShopShowGoodsAct.SHOP_NAME,searchShopAdapter.getItem(i).getName());
                    readyGo(ShopShowGoodsAct.class,bundle);
                }
            }
        });

        searchIdleMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(searchIdleAdapter != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID,searchIdleAdapter.getItem(i).getIdle_id());
                    readyGo(FreeGoodsDetailActivity.class,bundle);
                }
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void searchGood() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                ApiManager.getService(this).searchGoods(keyWord, null, null, null, null, null, 1, "东南大学九龙湖校区", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        goodsItems = searchGoodsRes.getResult();
                        if (goodsItems.size() != 0) {
                            searchGoodsMoreLinear.setVisibility(View.VISIBLE);
                            searchGoodsAdapter = new SearchGoodsAdapter(getApplicationContext(), goodsItems, SearchGoodsAdapter.ViewHolder.class);
                            searchGoodsMoreList.setAdapter(searchGoodsAdapter);
                            fixListViewHeight(searchGoodsMoreList);
                        } else {
                            searchMoreFailedLinear.setVisibility(View.VISIBLE);
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }else{
                showNetWorkError();
                pd.dismiss();
            }
        } else {
            showToast("搜索关键字不能为空");
            pd.dismiss();
        }
    }

    private void searchIdle() {
        if(!CommonUtils.isEmpty(keyWord)){
            if(NetUtils.isNetworkConnected(this)){
                ApiManager.getService(this).searchIdle(keyWord, null, null, 1, "东南大学九龙湖校区", new Callback<SearchIdleRes>() {
                    @Override
                    public void success(SearchIdleRes searchIdleRes, Response response) {
                        idleItems = searchIdleRes.getResult();
                        if (idleItems.size() != 0) {
                            searchIdleMoreLinear.setVisibility(View.VISIBLE);
                            searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), idleItems, SearchIdleAdapter.ViewHolder.class);
                            searchIdleMoreList.setAdapter(searchIdleAdapter);
                            fixListViewHeight(searchIdleMoreList);
                        } else {
                            searchMoreFailedLinear.setVisibility(View.VISIBLE);
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }else{
                showNetWorkError();
                pd.dismiss();
            }
        }else{
            showToast("搜索关键字不能为空");
            pd.dismiss();
        }
    }

    private void searchShop() {
        if(!CommonUtils.isEmpty(keyWord)){
            if(NetUtils.isNetworkConnected(this)){
                ApiManager.getService(this).searchShop(keyWord, null, null, 1, "东南大学九龙湖校区", new Callback<SearchShopRes>() {
                    @Override
                    public void success(SearchShopRes searchShopRes, Response response) {
                        shopItems = searchShopRes.getResult();
                        if(shopItems.size()!=0){
                            searchShopMoreLinear.setVisibility(View.VISIBLE);
                            searchShopAdapter = new SearchShopAdapter(getApplicationContext(),shopItems,SearchShopAdapter.ViewHolder.class);
                            searchShopMoreList.setAdapter(searchShopAdapter);
                            fixListViewHeight(searchShopMoreList);
                            pd.dismiss();
                        }else{
                            searchMoreFailedLinear.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }else{
                showNetWorkError();
                pd.dismiss();
            }
        }else{
            showToast("搜索关键字不能为空");
            pd.dismiss();
        }
    }

    //ScrollVIew嵌套ListView时，会无法正确的计算ListView的大小
    //需要手动计算列表的高度
    private void fixListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(i, null, listView);
            //计算子项View的宽高
            listViewItem.measure(0, 0);
            totalHeight += listViewItem.getMeasuredHeight();
            //并不知道为什么要减掉4，一个一个试的
            //这样计算出来的列表高度刚好
            totalHeight -= 4;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
