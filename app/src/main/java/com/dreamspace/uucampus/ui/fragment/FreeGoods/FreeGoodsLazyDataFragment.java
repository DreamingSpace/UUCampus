package com.dreamspace.uucampus.ui.fragment.FreeGoods;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.IdleItem;
import com.dreamspace.uucampus.model.api.SearchIdleRes;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreeGoodsLazyDataFragment extends FreeGoodsLazyListFragment<IdleItem> {
    private String category = ShareData.freeGoodsCategorys[0];
    private int page = 1;
    private String order = null;   //popupWindow对应选中的order
    private boolean isFragDestroy = false;

    public FreeGoodsLazyDataFragment() {
    }

    @Override
    public void onPullUp() {  //上拉刷新,加载更多
        loadingDataByPage(++page, new OnRefreshListener<IdleItem>() {
            @Override
            public void onFinish(List mEntities) {
                refreshDate(mEntities, FreeGoodsLazyListFragment.ADD);
                onPullUpFinished();
            }

            @Override
            public void onError() {
                onPullUpFinished();
            }
        });
    }

    @Override
    public void onPullDown() { //下拉刷新，加载最新的
        page = 1;
        loadingDataByPage(page, new OnRefreshListener<IdleItem>() {
            @Override
            public void onFinish(List<IdleItem> mEntities) {
                refreshDate(mEntities, FreeGoodsLazyListFragment.LOAD);
                onPullDownFinished();
            }

            @Override
            public void onError() {
                onPullDownFinished();
            }
        });
    }

    @Override
    public String onItemPicked(IdleItem mEntity, int position) {
        Log.i("INFO", mEntity.getIdle_id().toString());
        return mEntity.getIdle_id().toString();
    }

    @Override
    public void getInitData() {
        category = ShareData.freeGoodsCategorys[FragmentPagerItem.getPosition(getArguments())];
        Log.i("INFO", "TAG IS :" + category);

        loadingInitData();
    }

    public void loadingInitData() {
        toggleShowLoading(true, getString(R.string.common_loading_message));
        page = 1;
        loadingDataByPage(page, new OnRefreshListener<IdleItem>() {
            @Override
            public void onFinish(List<IdleItem> mEntities) {
                if (!isFragDestroy) {
                    if (mEntities != null && mEntities.size() == 0) {
                        toggleShowEmpty(true, getString(R.string.common_empty_msg), null);
                    } else {
                        toggleShowLoading(false, null);
                        refreshDate(mEntities, FreeGoodsLazyListFragment.LOAD);
                        TLog.i("获取的闲置列表：", mEntities.get(0).getIdle_id() + " useName:" + mEntities.get(0).getUser_name());
                    }
                }
            }

            @Override
            public void onError() {
                toggleShowError(true, getString(R.string.common_error_msg), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingInitData();
                    }
                });
            }
        });
    }

    public void loadingDataByPage(int page, final OnRefreshListener onRefreshListener) {
        if (NetUtils.isNetworkConnected(getActivity().getApplicationContext())) {
            ApiManager.getService(getActivity().getApplicationContext()).searchIdle(null, order, category, page, new Callback<SearchIdleRes>() {
                @Override
                public void success(SearchIdleRes searchIdleRes, Response response) {
                    if (searchIdleRes != null) {
                        onRefreshListener.onFinish(searchIdleRes.getResult());
                    } else {
                        showToast(response.getReason());
                        onRefreshListener.onError();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    onRefreshListener.onError();
                    showInnerError(error);
                }
            });
        } else {
            onRefreshListener.onError();
            showNetWorkError();
        }

    }

    //当更换排序方式时，activity对调用此方法
    public void orderChange(String order) {
        this.order = order;
        TLog.i("排序方式:", order);

        //更新当前页面数据
        loadingInitData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFragDestroy = true;
    }
}