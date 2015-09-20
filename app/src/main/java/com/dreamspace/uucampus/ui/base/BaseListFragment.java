package com.dreamspace.uucampus.ui.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2015/8/8 0008.
 */
public abstract class BaseListFragment<T> extends BaseFragment {
    private LoadMoreListView moreListView;
    private BasisAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Class<?extends BasisAdapter> mAClass;

    public BaseListFragment(Class<? extends BasisAdapter> mAClass) {
        this.mAClass = mAClass;
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_list_fragment;
    }
    @Override
    public void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_id);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onPullDown();
            }
        });
        moreListView = (LoadMoreListView) view.findViewById(R.id.load_more_lv);
        moreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onPullUp();
            }
        });

    }

    public void onPullUpFinished(){
        moreListView.setLoading(false);
    }
    public void onPullDownFinished(){
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void initDatas() {
        try {
            Constructor c=mAClass.getConstructor(Context.class);
            mAdapter= (BasisAdapter) c.newInstance(getActivity());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        moreListView.setAdapter(mAdapter);
        moreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemPicked((T)mAdapter.getItem(position),position);
                Log.i("INFO", "position:  " + position);
            }
        });
        getInitData();
    }
    public  void onItemPicked(T mEntity,int position){

    }


    public abstract void onPullUp();

    public abstract void onPullDown();

    public abstract void getInitData();


    public void refreshDate(List<T> mEntities) {
        mAdapter.setmEntities(mEntities);
        mAdapter.notifyDataSetChanged();
    }
}
