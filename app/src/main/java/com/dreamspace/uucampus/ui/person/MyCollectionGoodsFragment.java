package com.dreamspace.uucampus.ui.person;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.MyCollectionGoodsAdapter;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

/**
 * Created by zsh on 2015/9/18.
 */
public class MyCollectionGoodsFragment extends BaseLazyFragment {
    private ListView listview;
    private Context mContext;
    private MyCollectionGoodsAdapter goodsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstaceState) {
        mContext = getActivity();
        View resultView = inflater.inflate(R.layout.peerson_fragment_listview, container, false);
        listview = (ListView)resultView.findViewById(R.id.person_info_listview);
        goodsAdapter = new MyCollectionGoodsAdapter(mContext);
        listview.setAdapter(goodsAdapter);
        return resultView;
    }
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
