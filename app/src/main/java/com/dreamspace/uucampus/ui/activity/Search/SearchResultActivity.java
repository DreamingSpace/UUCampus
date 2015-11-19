package com.dreamspace.uucampus.ui.activity.Search;

import android.view.View;
import android.view.ViewGroup;
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
import com.dreamspace.uucampus.ui.base.AbsActivity;

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

    private com.dreamspace.uucampus.ui.dialog.ProgressDialog pd;
    private boolean haveResult;
    private SearchGoodsAdapter searchGoodsAdapter;
    private SearchIdleAdapter searchIdleAdapter;
    private SearchShopAdapter searchShopAdapter;
    private String keyWord;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        searchGoodsLinear.setVisibility(View.GONE);
        searchIdleLinear.setVisibility(View.GONE);
        searchShopLinear.setVisibility(View.GONE);
        searchFailedLinear.setVisibility(View.GONE);
        pd = new com.dreamspace.uucampus.ui.dialog.ProgressDialog(this);
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners() {
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

            }
        });
        searchIdleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        searchShopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    //搜索商品
    private void searchGoods() {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                ApiManager.getService(this).searchGoods(keyWord, null, null, null, null, null, 1, "东南大学九龙湖校区", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        searchGoodsLinear.setVisibility(View.GONE);
                        int size = searchGoodsRes.getResult().size();
                        if (size != 0) {
                            searchGoodsLinear.setVisibility(View.VISIBLE);
                            haveResult = true;
                        }
                        List<GoodsItem> temp = new ArrayList<GoodsItem>();
                        if (size > 2) {
                            temp.add(searchGoodsRes.getResult().get(0));
                            temp.add(searchGoodsRes.getResult().get(1));
                            searchGoodsMore.setVisibility(View.VISIBLE);
                        } else {
                            temp = searchGoodsRes.getResult();
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
                searchIdleLinear.setVisibility(View.GONE);
                int size = searchIdleRes.getResult().size();
                if (size != 0) {
                    searchIdleLinear.setVisibility(View.VISIBLE);
                    haveResult = true;
                }
                List<IdleItem> temp = new ArrayList<IdleItem>();
                if (size > 2) {
                    temp.add(searchIdleRes.getResult().get(0));
                    temp.add(searchIdleRes.getResult().get(1));
                    searchIdleMore.setVisibility(View.VISIBLE);
                } else {
                    temp = searchIdleRes.getResult();
                }
                searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), temp, SearchIdleAdapter.ViewHolder.class);
                searchIdleList.setAdapter(searchIdleAdapter);
                fixListViewHeight(searchIdleList);
                searchGoods();
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                pd.dismiss();
            }
        });
    }

    //搜索店铺
    private void searchShop(final boolean ifAll) {
        ApiManager.getService(this).searchShop(keyWord, null, null, 1, "东南大学九龙湖校区", new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                searchShopLinear.setVisibility(View.GONE);
                int size = searchShopRes.getResult().size();
                if (size != 0) {
                    searchShopLinear.setVisibility(View.VISIBLE);
                    haveResult = true;
                }
                List<ShopItem> temp = new ArrayList<ShopItem>();
                if (size > 2) {
                    temp.add(searchShopRes.getResult().get(0));
                    temp.add(searchShopRes.getResult().get(1));
                    searchShopMore.setVisibility(View.VISIBLE);
                } else {
                    temp = searchShopRes.getResult();
                }
                searchShopAdapter = new SearchShopAdapter(getApplicationContext(), temp, SearchShopAdapter.ViewHolder.class);
                searchShopList.setAdapter(searchShopAdapter);
                fixListViewHeight(searchShopList);
                pd.dismiss();

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
