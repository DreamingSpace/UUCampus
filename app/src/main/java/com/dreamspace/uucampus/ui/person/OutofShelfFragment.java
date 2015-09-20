package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.OutofShelfAdapater;

/**
 * Created by zsh on 2015/9/16.
 */
public class OutofShelfFragment extends Fragment {
    private ListView listview;
    private OutofShelfAdapater outofShelfeAdapter;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View resultView = inflater.inflate(R.layout.activity_my_fragment_listview, container, false);
        listview = (ListView)resultView.findViewById(R.id.person_info_listview);
        outofShelfeAdapter = new OutofShelfAdapater(mContext);
        listview.setAdapter(outofShelfeAdapter);
        return resultView;
    }
}
