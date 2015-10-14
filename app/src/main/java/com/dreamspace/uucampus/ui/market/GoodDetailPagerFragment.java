package com.dreamspace.uucampus.ui.market;

import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodDetailCommentListAdapter;
import com.dreamspace.uucampus.adapter.market.GoodDetailInstListAdapter;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/10.
 */
public class GoodDetailPagerFragment extends BaseLazyFragment{
    @Bind(R.id.good_detail_pager_lv)
    LoadMoreListView listView;
    private String type;

    @Override
    protected void onFirstUserVisible() {
        type = getArguments().getString(GoodDetailAct.TYPE);
        if(type.equals(GoodDetailAct.DETAIL)){
            initDetailViews();
        }else if(type.equals(GoodDetailAct.COMMENT)){
            initCommentViews();
        }
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

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_good_detail_pager;
    }

    private void initDetailViews(){
        List<String> list = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            list.add(i+"");
        }
        GoodDetailInstListAdapter adapter = new GoodDetailInstListAdapter(mContext,list,GoodDetailInstListAdapter.ViewHolder.class);
        listView.setAdapter(adapter);
    }

    private void initCommentViews(){
        List<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i+"");
        }
        GoodDetailCommentListAdapter adapter = new GoodDetailCommentListAdapter(mContext,list,GoodDetailCommentListAdapter.ViewHolder.class);
        listView.setAdapter(adapter);
    }
}
