package com.dreamspace.uucampus.ui;

import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamspace.uucampus.R;

/**
 * Created by money on 2015/9/14.
 */
public class MarketFragment extends BaseLazyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
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
        return R.layout.fragment_third;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}