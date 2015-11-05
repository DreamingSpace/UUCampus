package com.dreamspace.uucampus.ui.fragment.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodsListAdapter;
import com.dreamspace.uucampus.adapter.market.ShopListAdapter;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/15.
 */
public class CollectionFragment extends BaseLazyFragment {
    @Bind(R.id.collection_smlv)
    SwipeMenuListView collectionLv;

    private SwipeMenuCreator creator;

    private GoodsListAdapter goodsListAdapter;
    private ShopListAdapter shopListAdapter;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取type，从而判断当前fragment为了展示那个界面效果
        type = getArguments() == null? "null":getArguments().getString(MyCollectionAct.TYPE);
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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        //判断当前fragment是商品还是商家
        if (type.equals(getResources().getString(R.string.goods))){
            initGoodsViews();
        }else if(type.equals(getResources().getString(R.string.seller))){
            initSellerViews();
        }

        //为list item添加swipe menu item
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem openItem = new SwipeMenuItem(mContext);
                openItem.setBackground(R.color.price_text_color);
                openItem.setWidth((int)(90*getResources().getDisplayMetrics().density));
                openItem.setTitle("删除");
                openItem.setTitleSize((int)(14*getResources().getDisplayMetrics().density));
                openItem.setTitleColor(getResources().getColor(R.color.white));
                swipeMenu.addMenuItem(openItem);
            }
        };
        collectionLv.setMenuCreator(creator);

        //为menu item添加点击事件
        collectionLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                if(i1 == 0){
                    showToast("delete item");
                }
                return false;
            }
        });

        setListItemClickListeners();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_collection;
    }

    //初始化商品视图
    private void initGoodsViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        goodsListAdapter = new GoodsListAdapter(mContext,list,GoodsListAdapter.ViewHolder.class);
        collectionLv.setAdapter(goodsListAdapter);
    }

    //初始化商家视图
    private void initSellerViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
//        shopListAdapter = new ShopListAdapter(mContext,list,ShopListAdapter.ViewHolder.class);
//        collectionLv.setAdapter(shopListAdapter);
    }

    private void setListItemClickListeners(){
        if(type.equals(getResources().getString(R.string.goods))){
            collectionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    readyGo(GoodDetailAct.class);
                }
            });
        }else if(type.equals(getResources().getString(R.string.seller))){
            collectionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    readyGo(ShopShowGoodsAct.class);
                }
            });
        }
    }
}
