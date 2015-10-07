package com.dreamspace.uucampus.ui.person;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.person.MyCollectionMarketsAdapter;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

/**
 * Created by zsh on 2015/9/18.
 */
public class MyCollectionMarketsFragment extends BaseLazyFragment {
    private ListView listView;
    private MyCollectionMarketsAdapter marketsAdapter;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View resultView = inflater.inflate(R.layout.person_fragment_listview,null);
        listView = (ListView)resultView.findViewById(R.id.person_info_listview);
        marketsAdapter = new MyCollectionMarketsAdapter(mContext);
        listView.setAdapter(marketsAdapter);
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
