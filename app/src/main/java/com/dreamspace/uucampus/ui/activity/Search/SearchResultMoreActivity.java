package com.dreamspace.uucampus.ui.activity.Search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dreamspace.uucampus.widget.LoadMoreListView;

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
    @Bind(R.id.search_goods_more_linear)
    LinearLayout searchGoodsMoreLinear;
    @Bind(R.id.search_idle_more_linear)
    LinearLayout searchIdleMoreLinear;
    @Bind(R.id.search_shop_more_linear)
    LinearLayout searchShopMoreLinear;
    @Bind(R.id.search_more_failed_linear)
    LinearLayout searchMoreFailedLinear;
    @Bind(R.id.search_goods_more_list)
    LoadMoreListView searchGoodsMoreList;
    @Bind(R.id.search_idle_more_list)
    LoadMoreListView searchIdleMoreList;
    @Bind(R.id.search_shop_more_list)
    LoadMoreListView searchShopMoreList;
    @Bind(R.id.search_more_history_list)
    ListView searchMoreHistoryList;
    @Bind(R.id.search_more_history_divider)
    View searchMoreHistoryDivider;
    @Bind(R.id.search_more_history_delete_linear)
    LinearLayout searchMoreHistoryDeleteLinear;
    @Bind(R.id.search_more_history_linear)
    LinearLayout searchMoreHistoryLinear;

    private String keyWord;
    private static int category;
    private int goodPage = 1;
    private int idlePage = 1;
    private int shopPage = 1;
    private ProgressDialog pd;
    private SearchHistoryAdapter searchHistoryAdapter;
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
        pd.show();
        switch (category) {
            case 0:
                goodsItems = new ArrayList<>();
                searchGood();
                break;
            case 1:
                idleItems = new ArrayList<>();
                searchIdle();
                break;
            case 2:
                shopItems = new ArrayList<>();
                searchShop();
                break;
        }
    }

    @Override
    protected void initViews() {
        searchMoreHistoryLinear.setVisibility(View.GONE);
        searchGoodsMoreLinear.setVisibility(View.GONE);
        searchIdleMoreLinear.setVisibility(View.GONE);
        searchShopMoreLinear.setVisibility(View.GONE);
        searchMoreFailedLinear.setVisibility(View.GONE);

        initListeners();
    }

    private void search() {
        pd.show();
        switch (category) {
            case 0:
                goodsItems = new ArrayList<>();
                goodPage = 1;
                searchGoodsMoreLinear.setVisibility(View.GONE);
                searchMoreFailedLinear.setVisibility(View.GONE);
                searchGood();
                break;
            case 1:
                idleItems = new ArrayList<>();
                idlePage = 1;
                searchIdleMoreLinear.setVisibility(View.GONE);
                searchMoreFailedLinear.setVisibility(View.GONE);
                searchIdle();
                break;
            case 2:
                shopItems = new ArrayList<>();
                shopPage = 1;
                searchShopMoreLinear.setVisibility(View.GONE);
                searchMoreFailedLinear.setVisibility(View.GONE);
                searchShop();
                break;
        }
    }

    private void initListeners() {
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMoreHistoryLinear.setVisibility(View.GONE);
                keyWord = searchText.getText().toString();
                searchMoreFailedLinear.setVisibility(View.GONE);
                search();
            }
        });

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后显示历史记录，如果没有历史记录则不显示
                //隐藏其他列表
                if (SharePreference.searchHistory.size() != 0) {
                    searchMoreHistoryDivider.setVisibility(View.VISIBLE);
                    searchMoreHistoryDeleteLinear.setVisibility(View.VISIBLE);
                    searchHistory();
                }
            }
        });
        searchMoreHistoryDeleteLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后清除历史记录
                SharePreference.searchHistory = new ArrayList<String>();
                searchHistory();

                searchMoreHistoryDivider.setVisibility(View.GONE);
                searchMoreHistoryDeleteLinear.setVisibility(View.GONE);
            }
        });

        searchMoreHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchHistoryAdapter != null) {
                    String temp = searchHistoryAdapter.getItem(i);
                    searchText.setText(temp);
                    keyWord = temp;
                    search();
                }
            }
        });
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
                if (searchShopAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ShopShowGoodsAct.SHOP_ID, searchShopAdapter.getItem(i).getShop_id());
                    bundle.putString(ShopShowGoodsAct.SHOP_NAME, searchShopAdapter.getItem(i).getName());
                    readyGo(ShopShowGoodsAct.class, bundle);
                }
            }
        });

        searchIdleMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchIdleAdapter != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID, searchIdleAdapter.getItem(i).getIdle_id());
                    readyGo(FreeGoodsDetailActivity.class, bundle);
                }
            }
        });

        //上拉加载
        searchGoodsMoreList.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                switch (category) {
                    case 0:
                        searchGoodsMoreList.setLoading(true);
                        goodPage++;
                        searchGood();
                        break;
                    case 1:
                        searchIdleMoreList.setLoading(true);
                        idlePage++;
                        searchIdle();
                        break;
                    case 2:
                        searchShopMoreList.setLoading(true);
                        shopPage++;
                        searchShop();
                        break;
                }
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void searchHistory() {
        searchMoreHistoryLinear.setVisibility(View.VISIBLE);
        searchMoreHistoryDivider.setVisibility(View.VISIBLE);
        searchGoodsMoreLinear.setVisibility(View.GONE);
        searchShopMoreLinear.setVisibility(View.GONE);
        searchIdleMoreLinear.setVisibility(View.GONE);
        searchMoreFailedLinear.setVisibility(View.GONE);

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
        searchMoreHistoryList.setAdapter(searchHistoryAdapter);
    }

    private void searchGood() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                searchGoodsMoreList.setLoading(false);
                ApiManager.getService(this).searchGoods(keyWord, null, null, null, null, null, goodPage, "东南大学九龙湖校区", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        //历史记录隐藏,搜索关键词加入历史记录
                        searchMoreHistoryLinear.setVisibility(View.GONE);
                        SharePreference.searchHistory.add(keyWord);

                        goodsItems = searchGoodsRes.getResult();
                        if (goodsItems.size() != 0) {
                            searchGoodsMoreLinear.setVisibility(View.VISIBLE);
                            if (goodPage == 1) {
                                //第一次加载两页，尽量撑满屏幕
                                searchGoodsAdapter = new SearchGoodsAdapter(getApplicationContext(), goodsItems, SearchGoodsAdapter.ViewHolder.class);
                                goodPage++;
                                searchGood();
                            } else {
                                searchGoodsAdapter.addEntities(goodsItems);
                            }
                            searchGoodsMoreList.setAdapter(searchGoodsAdapter);
                        } else {
                            if (goodPage == 1) {
                                searchMoreFailedLinear.setVisibility(View.VISIBLE);
                            }
                        }
                        pd.dismiss();
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

    private void searchIdle() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                searchIdleMoreList.setLoading(false);
                ApiManager.getService(this).searchIdle(keyWord, null, null, idlePage, "东南大学九龙湖校区", new Callback<SearchIdleRes>() {
                    @Override
                    public void success(SearchIdleRes searchIdleRes, Response response) {
                        //历史记录隐藏,搜索关键词加入历史记录
                        searchMoreHistoryLinear.setVisibility(View.GONE);
                        SharePreference.searchHistory.add(keyWord);

                        idleItems = searchIdleRes.getResult();
                        if (idleItems.size() != 0) {
                            searchIdleMoreLinear.setVisibility(View.VISIBLE);
                            if (idlePage == 1) {
                                searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), idleItems, SearchIdleAdapter.ViewHolder.class);
                                idlePage++;
                                searchIdle();
                            } else {
                                searchIdleAdapter.addEntities(idleItems);
                            }
                            searchIdleMoreList.setAdapter(searchIdleAdapter);
                        } else {
                            if (idlePage == 1) {
                                searchMoreFailedLinear.setVisibility(View.VISIBLE);
                            }
                        }
                        pd.dismiss();
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

    private void searchShop() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                searchShopMoreList.setLoading(false);
                ApiManager.getService(this).searchShop(keyWord, null, null, shopPage, "东南大学九龙湖校区", new Callback<SearchShopRes>() {
                    @Override
                    public void success(SearchShopRes searchShopRes, Response response) {
                        //历史记录隐藏,搜索关键词加入历史记录
                        searchMoreHistoryLinear.setVisibility(View.GONE);
                        SharePreference.searchHistory.add(keyWord);

                        shopItems = searchShopRes.getResult();
                        if (shopItems.size() != 0) {
                            searchShopMoreLinear.setVisibility(View.VISIBLE);
                            if (shopPage == 1) {
                                searchShopAdapter = new SearchShopAdapter(getApplicationContext(), shopItems, SearchShopAdapter.ViewHolder.class);
                                shopPage++;
                                searchShop();
                            } else {
                                searchShopAdapter.addEntities(shopItems);
                            }
                            searchShopMoreList.setAdapter(searchShopAdapter);
                            pd.dismiss();
                        } else {
                            if (shopPage == 1) {
                                searchMoreFailedLinear.setVisibility(View.VISIBLE);
                            }
                        }
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
}
