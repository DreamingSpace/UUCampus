package com.dreamspace.uucampus.ui.fragment.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodsListAdapter;
import com.dreamspace.uucampus.adapter.market.MarketListViewAdapter;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/15.
 */
public class CollectionFragment extends BaseLazyFragment {
    @Bind(R.id.collection_smlv)
    SwipeMenuListView collectionLv;
//    ListView collectionLv;

    private GoodsListAdapter goodsListAdapter;
    private MarketListViewAdapter marketListViewAdapter;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments() == null? "null":getArguments().getString(MyCollectionAct.TYPE);
    }

    @Override
    protected void onFirstUserVisible() {
//        System.out.println("fi");
//        if (type.equals(getResources().getString(R.string.goods))){
//            System.out.println("goods vi");
//            initGoodsViews();
//        }else if(type.equals(getResources().getString(R.string.seller))){
//            System.out.println("seller vi");
//            initSellerViews();
//        }
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
        System.out.println("fi");
        if (type.equals(getResources().getString(R.string.goods))){
            System.out.println("goods vi");
            initGoodsViews();
        }else if(type.equals(getResources().getString(R.string.seller))){
            System.out.println("seller vi");
            initSellerViews();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_collection;
    }

    private void initGoodsViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        goodsListAdapter = new GoodsListAdapter(mContext,list,GoodsListAdapter.ViewHolder.class);
        collectionLv.setAdapter(goodsListAdapter);
    }

    private void initSellerViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        marketListViewAdapter = new MarketListViewAdapter(mContext,list,MarketListViewAdapter.ViewHolder.class);
        collectionLv.setAdapter(marketListViewAdapter);
    }
}
