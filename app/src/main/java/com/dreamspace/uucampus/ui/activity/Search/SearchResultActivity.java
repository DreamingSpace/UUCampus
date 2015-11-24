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
import com.dreamspace.uucampus.adapter.search.SearchHistoryAdapter;
import com.dreamspace.uucampus.adapter.search.SearchIdleAdapter;
import com.dreamspace.uucampus.adapter.search.SearchShopAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.SharePreference;
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
 * Created by money on 2015/11/11.
 */
public class SearchResultActivity extends AbsActivity {

    @Bind(R.id.search_text)
    EditText searchText;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.search_goods_list)
    ListView searchGoodsList;
    @Bind(R.id.search_goods_more)
    LinearLayout searchGoodsMore;
    @Bind(R.id.search_goods_linear)
    LinearLayout searchGoodsLinear;
    @Bind(R.id.search_idle_more)
    LinearLayout searchIdleMore;
    @Bind(R.id.search_idle_linear)
    LinearLayout searchIdleLinear;
    @Bind(R.id.search_shop_more)
    LinearLayout searchShopMore;
    @Bind(R.id.search_shop_linear)
    LinearLayout searchShopLinear;
    @Bind(R.id.search_failed_linear)
    LinearLayout searchFailedLinear;
    @Bind(R.id.search_idle_list)
    ListView searchIdleList;
    @Bind(R.id.search_shop_list)
    ListView searchShopList;
    @Bind(R.id.search_history_list)
    ListView searchHistoryList;
    @Bind(R.id.search_history_delete_linear)
    LinearLayout searchHistoryDeleteLinear;
    @Bind(R.id.search_history_linear)
    LinearLayout searchHistoryLinear;
    @Bind(R.id.search_history_divider)
    View searchHistoryDivider;
    @Bind(R.id.search_goods_divider)
    View searchGoodsDivider;
    @Bind(R.id.search_idle_divider)
    View searchIdleDivider;
    @Bind(R.id.search_shop_divider)
    View searchShopDivider;

    private ProgressDialog pd;
    private boolean haveResult;
    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchGoodsAdapter searchGoodsAdapter;
    private SearchIdleAdapter searchIdleAdapter;
    private SearchShopAdapter searchShopAdapter;
    private String keyWord;
    //保存搜索结果
    private List<GoodsItem> goodsItems;
    private List<IdleItem> idleItems;
    private List<ShopItem> shopItems;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        goodsItems = new ArrayList<>();
        idleItems = new ArrayList<>();
        shopItems = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        searchHistoryLinear.setVisibility(View.GONE);
        searchGoodsLinear.setVisibility(View.GONE);
        searchIdleLinear.setVisibility(View.GONE);
        searchShopLinear.setVisibility(View.GONE);
        searchFailedLinear.setVisibility(View.GONE);
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners() {
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后显示历史记录,如果没有历史记录则不显示
                //隐藏其他列表
                if (SharePreference.searchHistory.size() != 0) {
                    searchHistoryDeleteLinear.setVisibility(View.VISIBLE);
                    searchHistoryDivider.setVisibility(View.VISIBLE);
                    searchHistory();
                }
            }
        });
        searchHistoryDeleteLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后清除历史记录
                SharePreference.searchHistory = new ArrayList<String>();
                searchHistory();
                searchHistoryDeleteLinear.setVisibility(View.GONE);
                searchHistoryDivider.setVisibility(View.GONE);
            }
        });
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                //判断是否有匹配结果
                haveResult = false;
                keyWord = searchText.getText().toString();
                searchFailedLinear.setVisibility(View.GONE);
                searchGoods();
            }
        });
        searchGoodsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("category", 0);
                bundle.putString("key", keyWord);
                readyGo(SearchResultMoreActivity.class, bundle);
            }
        });
        searchIdleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("category", 1);
                bundle.putString("key", keyWord);
                readyGo(SearchResultMoreActivity.class, bundle);
            }
        });
        searchShopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("category", 2);
                bundle.putString("key", keyWord);
                readyGo(SearchResultMoreActivity.class, bundle);
            }
        });

        searchGoodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchGoodsAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GoodDetailAct.GOOD_ID, searchGoodsAdapter.getItem(i).getGoods_id());
                    readyGo(GoodDetailAct.class, bundle);
                }
            }
        });

        searchShopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchShopAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ShopShowGoodsAct.SHOP_ID, searchShopAdapter.getItem(i).getShop_id());
                    bundle.putString(ShopShowGoodsAct.SHOP_NAME, searchShopAdapter.getItem(i).getName());
                    readyGo(ShopShowGoodsAct.class, bundle);
                }
            }
        });

        searchIdleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchIdleAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID, searchIdleAdapter.getItem(i).getIdle_id());
                    readyGo(FreeGoodsDetailActivity.class, bundle);
                }
            }
        });

        searchHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchHistoryAdapter != null) {
                    String temp = searchHistoryAdapter.getItem(i);
                    searchText.setText(temp);
                    keyWord = temp;
                    pd.show();
                    searchFailedLinear.setVisibility(View.GONE);
                    searchGoods();
                }
            }
        });
    }

    //显示历史记录
    private void searchHistory() {
        searchHistoryLinear.setVisibility(View.VISIBLE);
        searchHistoryDivider.setVisibility(View.VISIBLE);
        searchGoodsLinear.setVisibility(View.GONE);
        searchIdleLinear.setVisibility(View.GONE);
        searchShopLinear.setVisibility(View.GONE);
        searchFailedLinear.setVisibility(View.GONE);

        //只显示十条历史记录
        List<String> histories = new ArrayList<>();
        List<String> temp = SharePreference.searchHistory;
        int n = temp.size();
        if (n > 10) {
            for (int i = 0; i < 10; i++) {
                histories.add(temp.get(n - i - 1));
            }
        } else {
            for (int i = 0; i < n; i++) {
                histories.add(temp.get(n - i - 1));
            }
        }

        searchHistoryAdapter = new SearchHistoryAdapter(getApplicationContext(), histories, SearchHistoryAdapter.ViewHolder.class);
        searchHistoryList.setAdapter(searchHistoryAdapter);
    }

    //搜索商品
    private void searchGoods() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                ApiManager.getService(this).searchGoods(keyWord, null, null, null, null, null, 1, "东南大学九龙湖校区", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        //历史记录隐藏,搜索关键词加入历史记录
                        searchHistoryLinear.setVisibility(View.GONE);
                        SharePreference.searchHistory.add(keyWord);

                        goodsItems = searchGoodsRes.getResult();
                        searchGoodsLinear.setVisibility(View.GONE);
                        int size = goodsItems.size();
                        if (size != 0) {
                            searchGoodsLinear.setVisibility(View.VISIBLE);
                            searchGoodsDivider.setVisibility(View.GONE);
                            searchGoodsMore.setVisibility(View.GONE);
                            haveResult = true;
                        }
                        List<GoodsItem> temp = new ArrayList<GoodsItem>();
                        if (size > 2) {
                            temp.add(goodsItems.get(0));
                            temp.add(goodsItems.get(1));
                            searchGoodsDivider.setVisibility(View.VISIBLE);
                            searchGoodsMore.setVisibility(View.VISIBLE);
                        } else {
                            temp = goodsItems;
                        }
                        searchGoodsAdapter = new SearchGoodsAdapter(getApplicationContext(), temp, SearchGoodsAdapter.ViewHolder.class);
                        searchGoodsList.setAdapter(searchGoodsAdapter);
                        fixListViewHeight(searchGoodsList);
                        searchIdle();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            } else {
                showNetWorkError();
                pd.dismiss();
            }
        } else {
            showToast("搜索关键字不能为空");
            pd.dismiss();
        }
    }

    //搜索闲置
    private void searchIdle() {
        ApiManager.getService(this).searchIdle(keyWord, null, null, 1, "东南大学九龙湖校区", new Callback<SearchIdleRes>() {
            @Override
            public void success(SearchIdleRes searchIdleRes, Response response) {
                idleItems = searchIdleRes.getResult();
                searchIdleLinear.setVisibility(View.GONE);
                int size = idleItems.size();
                if (size != 0) {
                    searchIdleLinear.setVisibility(View.VISIBLE);
                    searchIdleDivider.setVisibility(View.GONE);
                    searchIdleMore.setVisibility(View.GONE);
                    haveResult = true;
                }
                List<IdleItem> temp = new ArrayList<IdleItem>();
                if (size > 2) {
                    temp.add(idleItems.get(0));
                    temp.add(idleItems.get(1));
                    searchIdleDivider.setVisibility(View.VISIBLE);
                    searchIdleMore.setVisibility(View.VISIBLE);
                } else {
                    temp = idleItems;
                }
                searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), temp, SearchIdleAdapter.ViewHolder.class);
                searchIdleList.setAdapter(searchIdleAdapter);
                fixListViewHeight(searchIdleList);
                searchShop();
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                pd.dismiss();
            }
        });
    }

    //搜索店铺
    private void searchShop() {
        ApiManager.getService(this).searchShop(keyWord, null, null, 1, "东南大学九龙湖校区", new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                shopItems = searchShopRes.getResult();
                searchShopLinear.setVisibility(View.GONE);
                int size = shopItems.size();
                if (size != 0) {
                    searchShopLinear.setVisibility(View.VISIBLE);
                    searchShopDivider.setVisibility(View.GONE);
                    searchShopMore.setVisibility(View.GONE);
                    haveResult = true;
                }
                List<ShopItem> temp = new ArrayList<ShopItem>();
                if (size > 2) {
                    temp.add(shopItems.get(0));
                    temp.add(shopItems.get(1));
                    searchShopDivider.setVisibility(View.VISIBLE);
                    searchShopMore.setVisibility(View.VISIBLE);
                } else {
                    temp = shopItems;
                }
                searchShopAdapter = new SearchShopAdapter(getApplicationContext(), temp, SearchShopAdapter.ViewHolder.class);
                searchShopList.setAdapter(searchShopAdapter);
                fixListViewHeight(searchShopList);
                pd.dismiss();

                //显示没有搜索结果的界面
                if (!haveResult) {
                    searchFailedLinear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                pd.dismiss();
            }
        });
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
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
