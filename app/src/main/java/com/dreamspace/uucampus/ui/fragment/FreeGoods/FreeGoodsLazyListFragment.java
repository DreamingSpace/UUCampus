package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.FreeGoods.FreeGoodsItemAdapter;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsActivity;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public abstract class FreeGoodsLazyListFragment<T> extends BaseLazyFragment {
    @Bind(R.id.load_more_lv)
    LoadMoreListView moreListView;
    private BasisAdapter mAdapter;
    @Bind(R.id.swiperefresh_id)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static final int ADD=2;
    public static final int LOAD=1;
    public static final int REQUEST_CODE=1;
    public static final int RESULT_CODE=200;
    public String order = null;   //popupWindow对应选中的order
    private boolean alreadyGetData = false;//判断是否已经获取了数据

    @Override
    protected void onFirstUserVisible() {
        if(!alreadyGetData){      //若果为false，还在加载数据
            order = ((FreeGoodsActivity)getActivity()).getOrder();
            getInitData();
        }else {
            if(mAdapter != null){
                moreListView.setAdapter(mAdapter);
            }else{
                toggleShowEmpty(true,getString(R.string.no_such_shop),getGoodsClickListener);
            }
        }
    }

    @Override
    protected void onUserVisible() {
        //排列方式变化，重新获取数据
        if(!order.equals(((FreeGoodsActivity)getActivity()).getOrder())){
            order = ((FreeGoodsActivity)getActivity()).getOrder();
            getInitData();
        }
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light, android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onPullDown();
            }
        });
        moreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onPullUp();
            }
        });

        moreListView.setOnTouchListener(new View.OnTouchListener() {
            float y = 0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        y = motionEvent.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //向下滚动式隐藏btn，向上显示
                        if(motionEvent.getY() - y > 0){
                            ((FreeGoodsActivity)getActivity()).setFloatActionBtnVisible(true);
                        }else{
                            ((FreeGoodsActivity)getActivity()).setFloatActionBtnVisible(false);
                        }
                        y = motionEvent.getY();
                        break;
                }
                return false;
            }
        });
//        moreListView.onScroll();
        initDatas();
    }
    public void initDatas() {

        mAdapter=new FreeGoodsItemAdapter(getActivity());
        moreListView.setAdapter(mAdapter);
        moreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("INFO", "position:  " + position);

                String idle_id = onItemPicked((T) mAdapter.getItem(position), position);
                Bundle bundle = new Bundle();
                bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID,idle_id);
                readyGoForResult(FreeGoodsDetailActivity.class, REQUEST_CODE, bundle);
            }
        });

    }

    protected abstract void getInitData();

    protected abstract String onItemPicked(T item, int position);

    protected abstract void onPullUp();

    protected abstract void onPullDown();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.base_list_fragment;
    }
    public void onPullUpFinished() {
        moreListView.setLoading(false);
    }
    public void onPullDownFinished(){
        mSwipeRefreshLayout.setRefreshing(false);
    }
    public void refreshDate(List<T> mEntities,int type) {
        switch (type){
            case LOAD:
                mAdapter.setmEntities(mEntities);
                break;
            case ADD:
                mAdapter.addEntities(mEntities);
                break;
        }
        mAdapter.notifyDataSetChanged();
    }


    public View.OnClickListener getGoodsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getInitData();
        }
    };
}
