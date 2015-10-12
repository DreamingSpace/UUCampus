package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.GoodsCommentItemAdapter;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.GoodsCommentItem;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/21.
 */
public class FreeGoodsDetailBottomCommentFragment extends BaseFragment{

    @Bind(R.id.goods_detail_bottom_comment_list_view)
    LoadMoreListView mListView;
    private BasisAdapter mAdapter;
    private List<GoodsCommentItem> data;

    public static FreeGoodsDetailBottomCommentFragment newInstance() {
        FreeGoodsDetailBottomCommentFragment fragment=new FreeGoodsDetailBottomCommentFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_free_goods_detail_bottom_comment;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initDatas() {
        mAdapter = new GoodsCommentItemAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        refreshDate(getData());
    }


    public void refreshDate(List<GoodsCommentItem> mEntities) {
        mAdapter.setmEntities(mEntities);
        mAdapter.notifyDataSetChanged();
    }

    public List<GoodsCommentItem> getData() {
        ArrayList<GoodsCommentItem> data = new ArrayList<GoodsCommentItem>();
        for(int i=0;i<10;i++){
            GoodsCommentItem item = new GoodsCommentItem();
            item.setContent("hello");
            item.setDate("2015-10-8");
            data.add(item);
        }
        return data;
    }
}
