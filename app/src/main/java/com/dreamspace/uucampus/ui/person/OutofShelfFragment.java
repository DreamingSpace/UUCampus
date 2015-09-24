package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.OutofShelfAdapater;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

/**
 * Created by zsh on 2015/9/16.
 */
public class OutofShelfFragment extends BaseLazyFragment {
    private ListView listview;
    private OutofShelfAdapater outofShelfeAdapter;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View resultView = inflater.inflate(R.layout.peerson_fragment_listview, container, false);
        listview = (ListView)resultView.findViewById(R.id.person_info_listview);
        outofShelfeAdapter = new OutofShelfAdapater(mContext);
        listview.setAdapter(outofShelfeAdapter);
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
