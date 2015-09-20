package com.dreamspace.uucampus.ui.person;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.MyCollectionMarketsAdapter;

/**
 * Created by zsh on 2015/9/18.
 */
public class MyCollectionMarketsFragment extends Fragment {
    private ListView listView;
    private MyCollectionMarketsAdapter marketsAdapter;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View resultView = inflater.inflate(R.layout.activity_my_fragment_listview,null);
        listView = (ListView)resultView.findViewById(R.id.person_info_listview);
        marketsAdapter = new MyCollectionMarketsAdapter(mContext);
        listView.setAdapter(marketsAdapter);
        return resultView;
    }
}
