package com.dreamspace.uucampus.ui;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
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
    @Bind(R.id.search_good_name_tv1)
    TextView searchGoodNameTv1;
    @Bind(R.id.search_good_shop_name_tv1)
    TextView searchGoodShopNameTv1;
    @Bind(R.id.search_good_price_tv1)
    TextView searchGoodPriceTv1;
    @Bind(R.id.search_good_name_tv2)
    TextView searchGoodNameTv2;
    @Bind(R.id.search_good_shop_name_tv2)
    TextView searchGoodShopNameTv2;
    @Bind(R.id.search_good_price_tv2)
    TextView searchGoodPriceTv2;
    @Bind(R.id.search_goods_more)
    LinearLayout searchGoodsMore;
    @Bind(R.id.search_goods_linear)
    LinearLayout searchGoodsLinear;
    @Bind(R.id.search_idle_name_tv1)
    TextView searchIdleNameTv1;
    @Bind(R.id.search_idle_shop_name_tv1)
    TextView searchIdleShopNameTv1;
    @Bind(R.id.search_idle_price_tv1)
    TextView searchIdlePriceTv1;
    @Bind(R.id.search_idle_name_tv2)
    TextView searchIdleNameTv2;
    @Bind(R.id.search_idle_shop_name_tv2)
    TextView searchIdleShopNameTv2;
    @Bind(R.id.search_idle_price_tv2)
    TextView searchIdlePriceTv2;
    @Bind(R.id.search_idle_more)
    LinearLayout searchIdleMore;
    @Bind(R.id.search_idle_linear)
    LinearLayout searchIdleLinear;
    @Bind(R.id.search_shop_name_tv1)
    TextView searchShopNameTv1;
    @Bind(R.id.search_shop_main_tv1)
    TextView searchShopMainTv1;
    @Bind(R.id.search_shop_name_tv2)
    TextView searchShopNameTv2;
    @Bind(R.id.search_shop_main_tv2)
    TextView searchShopMainTv2;
    @Bind(R.id.search_shop_more)
    LinearLayout searchShopMore;
    @Bind(R.id.search_shop_linear)
    LinearLayout searchShopLinear;
    @Bind(R.id.search_goods_relative)
    RelativeLayout searchGoodsRelative;
    @Bind(R.id.search_idle_relative)
    RelativeLayout searchIdleRelative;
    @Bind(R.id.search_shop_relative)
    RelativeLayout searchShopRelative;
    @Bind(R.id.search_failed_linear)
    LinearLayout searchFailedLinear;
    private ProgressDialog pd;


    private boolean haveResult;

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
        initListeners();
    }

    private void initListeners() {
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPd();
                //判断是否有匹配结果
                haveResult = false;
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
        final String keyWord = searchText.getText().toString();
        if (!CommonUtils.isEmpty(keyWord)) {
            if (NetUtils.isNetworkConnected(this)) {
                ApiManager.getService(this).searchGoods(keyWord, "东南大学九龙湖校区", "1", new Callback<SearchGoodsRes>() {
                    @Override
                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
                        int size = searchGoodsRes.getResult().size();
                        if (size != 0) {
                            haveResult = true;
                            searchGoodsLinear.setVisibility(View.VISIBLE);
                            GoodsItem temp = searchGoodsRes.getResult().get(0);
                            searchGoodNameTv1.setText(temp.getName());
                            searchGoodShopNameTv1.setText(temp.getShop_name());
                            searchGoodPriceTv1.setText(String.valueOf(temp.getPrice()));
                            if (size > 1) {
                                searchGoodsRelative.setVisibility(View.VISIBLE);
                                searchGoodsMore.setVisibility(View.VISIBLE);
                                temp = searchGoodsRes.getResult().get(1);
                                searchGoodNameTv2.setText(temp.getName());
                                searchGoodShopNameTv2.setText(temp.getShop_name());
                                searchGoodPriceTv2.setText(String.valueOf(temp.getPrice()));
                            } else {
                                searchGoodsRelative.setVisibility(View.GONE);
                                searchGoodsMore.setVisibility(View.GONE);
                            }
                        } else {
                            searchGoodsLinear.setVisibility(View.GONE);
                        }
                        searchIdle(keyWord);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                    }
                });
            } else {
                showNetWorkError();
            }
        } else {
            showToast("搜索关键字不能为空");
            dismissPd();
        }
    }

    //搜索闲置
    private void searchIdle(final String keyword) {
        ApiManager.getService(this).searchIdle(keyword, "东南大学九龙湖校区", "1", new Callback<SearchIdleRes>() {
            @Override
            public void success(SearchIdleRes searchIdleRes, Response response) {
                int size = searchIdleRes.getResult().size();
                if (size != 0) {
                    haveResult = true;
                    searchIdleLinear.setVisibility(View.VISIBLE);
                    IdleItem temp = searchIdleRes.getResult().get(0);
                    searchIdleNameTv1.setText(temp.getName());
                    searchIdleShopNameTv1.setText(temp.getUser_name());
                    searchIdlePriceTv1.setText(String.valueOf(temp.getPrice()));
                    if (size > 1) {
                        searchIdleRelative.setVisibility(View.VISIBLE);
                        searchIdleMore.setVisibility(View.VISIBLE);
                        temp = searchIdleRes.getResult().get(1);
                        searchIdleNameTv2.setText(temp.getName());
                        searchIdleShopNameTv2.setText(temp.getUser_name());
                        searchIdlePriceTv2.setText(String.valueOf(temp.getPrice()));
                    } else {
                        searchIdleRelative.setVisibility(View.GONE);
                        searchIdleMore.setVisibility(View.GONE);
                    }
                } else {
                    searchIdleLinear.setVisibility(View.GONE);
                }
                searchShop(keyword);
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    //搜索店铺
    private void searchShop(String keyWord) {
        ApiManager.getService(this).searchShop(keyWord, "东南大学九龙湖校区", "1", new Callback<SearchShopRes>() {
            @Override
            public void success(SearchShopRes searchShopRes, Response response) {
                int size = searchShopRes.getResult().size();
                if (size != 0) {
                    haveResult = true;
                    searchShopLinear.setVisibility(View.VISIBLE);
                    ShopItem temp = searchShopRes.getResult().get(0);
                    searchShopNameTv1.setText(temp.getName());
                    searchShopMainTv1.setText(temp.getMain());
                    if (size > 1) {
                        searchShopRelative.setVisibility(View.VISIBLE);
                        searchShopMore.setVisibility(View.VISIBLE);
                        searchShopNameTv2.setText(temp.getName());
                        searchShopMainTv2.setText(temp.getMain());
                    } else {
                        searchShopRelative.setVisibility(View.GONE);
                        searchShopMore.setVisibility(View.GONE);
                    }
                } else {
                    searchShopLinear.setVisibility(View.GONE);
                    if (!haveResult) {
                        searchFailedLinear.setVisibility(View.VISIBLE);
                    }
                }
                dismissPd();
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
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

}
