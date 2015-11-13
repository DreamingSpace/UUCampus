package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.ShopListAdapter;
import com.dreamspace.uucampus.ui.MarketFragment;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/21.
 */
public class ShowShopsFragment extends BaseLazyFragment {
    @Bind(R.id.swiperefresh_id)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.load_more_lv)
    LoadMoreListView loadMoreListView;

    private String type;
    private ShopListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments() == null? "null":getArguments().getString(MarketFragment.TYPE_NAME);
//        System.out.println(type + " " + FragmentPagerItem.getPosition(getArguments()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_items, container, false);
        return view;
    }

    @Override
    protected void onFirstUserVisible() {
        List<String> list = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            list.add(i+"");
        }
        adapter = new ShopListAdapter(mContext,list,ShopListAdapter.ViewHolder.class);
        loadMoreListView.setAdapter(adapter);
        System.out.println("fi" + type);

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        loadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readyGo(ShopShowGoodsAct.class);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }
}
