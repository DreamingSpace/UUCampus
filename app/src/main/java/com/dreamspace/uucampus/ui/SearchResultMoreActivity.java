package com.dreamspace.uucampus.ui;

import com.dreamspace.uucampus.ui.base.AbsActivity;

/**
 * Created by money on 2015/11/12.
 */
public class SearchResultMoreActivity extends AbsActivity{
    @Override
    protected void initViews() {

    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected int getContentView() {
        return 0;
    }

//    //搜索商品
//    private void searchGoods() {
////        final String keyWord = searchText.getText().toString();
//        //if (!CommonUtils.isEmpty(keyWord)) {
//            if (NetUtils.isNetworkConnected(this)) {
//                //ApiManager.getService(this).searchGoods(keyWord, "东南大学九龙湖校区", "1", new Callback<SearchGoodsRes>() {
//                    @Override
//                    public void success(SearchGoodsRes searchGoodsRes, Response response) {
////                        searchGoodsAdapter = new SearchGoodsAdapter(getApplicationContext(), searchGoodsRes.getResult(), SearchGoodsAdapter.ViewHolder.class);
////                        searchGoodsList.setAdapter(searchGoodsAdapter);
//                        searchIdle(keyWord);
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        showInnerError(error);
//                    }
//                });
//            } else {
//                showNetWorkError();
//            }
//        } else {
//            showToast("搜索关键字不能为空");
////            dismissPd();
//        }
//    }
//
//    //搜索闲置
//    private void searchIdle(final String keyword) {
//        ApiManager.getService(this).searchIdle(keyword, "东南大学九龙湖校区", "1", new Callback<SearchIdleRes>() {
//            @Override
//            public void success(SearchIdleRes searchIdleRes, Response response) {
////                searchIdleAdapter = new SearchIdleAdapter(getApplicationContext(), searchIdleRes.getResult(), SearchIdleAdapter.ViewHolder.class);
////                searchIdleList.setAdapter(searchIdleAdapter);
////                Log.d("TestData1", searchIdleRes.toString());
////                Log.d("TestData1", searchIdleRes.getResult().toString());
////                dismissPd();
//                //searchShop(keyword);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//    }
//
//    //搜索店铺
//    private void searchShop(String keyWord) {
//        ApiManager.getService(this).searchShop(keyWord,"东南大学九龙湖校区", "1", new Callback<SearchShopRes>() {
//            @Override
//            public void success(SearchShopRes searchShopRes, Response response) {
////                searchShopAdapter = new SearchShopAdapter(getApplicationContext(), searchShopRes.getResults(), SearchShopAdapter.ViewHolder.class);
////                searchShopList.setAdapter(searchShopAdapter);
//                Log.d("TestData2",searchShopRes.getResults().toString());
////                dismissPd();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                showInnerError(error);
//            }
//        });
//    }
}
