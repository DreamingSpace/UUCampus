package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/21.
 */
public class FreeGoodsDetailBottomCommentFragment extends BaseFragment{

    @Bind(R.id.goods_detail_bottom_comment_list_view)
    LoadMoreListView mListView;

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
        
    }
}
