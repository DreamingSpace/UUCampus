package com.dreamspace.uucampus.ui.activity.Search;

import android.app.ProgressDialog;
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

    private ProgressDialog pd;
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
                showPd();
                //判断是否有匹配结果
                haveResult = false;
                keyWord = searchText.getText().toString();
                searchFailedLinear.setVisibility(View.GONE);
                searchGoods(false);
            }
        });
        searchGoodsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchGoods(true);
            }
        });
        searchIdleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIdle(true);
            }
        });
        searchShopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchShop(true);
            }
        });

    }

    //搜索商品
    private void searchGoods(final boolean ifAll) {
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                ApiManager.getService(this).searchGoods(keyWord, null, null,null,null,null,1,"东南大学九龙湖校区", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        searchGoodsMore.setVisibility(View.GONE);
                        searchGoodsLinear.setVisibility(View.GONE);
                        int size = searchGoodsRes.getResult().size();
                        if (size != 0) {
                            searchGoodsLinear.setVisibility(View.VISIBLE);
                            haveResult = true;
                        }
                        List<GoodsItem> temp = new ArrayList<GoodsItem>();
                        if (!ifAll) {
                            if (size > 2) {
                                temp.add(searchGoodsRes.getResult().get(0));
                                temp.add(searchGoodsRes.getResult().get(1));
                                searchGoodsMore.setVisibility(View.VISIBLE);
                            } else {
                                temp = searchGoodsRes.getResult();
                            }
                        } else {
                            temp = searchGoodsRes.getResult();
                        }
                        searchGoodsAdapter = new SearchGoodsAdapter(getApplicationContext(), temp, SearchGoodsAdapter.ViewHolder.class);
                        searchGoodsList.setAdapter(searchGoodsAdapter);
                        fixListViewHeight(searchGoodsList);
                        dismissPd();
                        if(!ifAll){
                            searchIdle(ifAll);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        dismissPd();
                    }
                });
            } else {
                showNetWorkError();
                dismissPd();
            }
        } else {
            showToast("搜索关键字不能为空");
            dismissPd();
        }
    }

    //搜索闲置
    private void searchIdle(final boolean ifAll) {
        ApiManager.getService(this).searchIdle(keyWord, "东南大学九龙湖校区", "1", new Callback<SearchIdleRes>() {
            @Override
            public void success(SearchIdleRes searchIdleRes, Response response) {
                searchIdleLinear.setVisibility(View.GONE);
                searchIdleMore.setVisibility(View.GONE);
                int size = searchIdleRes.getResult().size();
                if (size != 0) {
                    searchIdleLinear.setVisibility(View.VISIBLE);
                    haveResult = true;
                }
                List<IdleItem> temp = new ArrayList<IdleItem>();
                if (!ifAll) {
                    if (size > 2) {
                        temp.add(searchIdleRes.getResult().get(0));
                        temp.add(searchIdleRes.getResult().get(1));
                        searchIdleMore.setVisibility(View.VISIBLE);
                    } else {
                        temp = searchIdleRes.getResult();
                    }
                } else {
                    temp = searchIdleRes.getResult();
                }
                searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), temp, SearchIdleAdapter.ViewHolder.class);
                searchIdleList.setAdapter(searchIdleAdapter);
                fixListViewHeight(searchIdleList);
                dismissPd();
                if(!ifAll){
                    searchShop(ifAll);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                dismissPd();
            }
        });
    }

    //搜索店铺
    private void searchShop(final boolean ifAll) {
        ApiManager.getService(this).searchShop(keyWord, "东南大学九龙湖校区", "1", new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                searchShopLinear.setVisibility(View.GONE);
                searchShopMore.setVisibility(View.GONE);
                int size = searchShopRes.getResult().size();
                if(size!=0){
                    searchShopLinear.setVisibility(View.VISIBLE);
                    haveResult = true;
                }
                List<ShopItem> temp = new ArrayList<ShopItem>();
                if(!ifAll){
                    if(size>2){
                        temp.add(searchShopRes.getResult().get(0));
                        temp.add(searchShopRes.getResult().get(1));
                        searchShopMore.setVisibility(View.VISIBLE);
                    }else{
                        temp = searchShopRes.getResult();
                    }
                }else{
                    temp = searchShopRes.getResult();
                }
                searchShopAdapter = new SearchShopAdapter(getApplicationContext(),temp,SearchShopAdapter.ViewHolder.class);
                searchShopList.setAdapter(searchShopAdapter);
                fixListViewHeight(searchShopList);
                dismissPd();

                if(!haveResult){
                    searchFailedLinear.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                dismissPd();
            }
        });
    }

    private void showPd() {
        if (pd == null) {
            pd = ProgressDialog.show(this, "", "正在获取数据，请稍候", true, false);
        } else {
            pd.show();
        }
    }

    private void dismissPd() {
        if (pd != null) {
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
