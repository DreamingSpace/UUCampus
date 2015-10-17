package com.dreamspace.uucampus.ui.fragment.Personal;

import android.os.Bundle;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.Personal.FreeGoodsPullOffListAdapter;
import com.dreamspace.uucampus.adapter.Personal.FreeGoodsSaleListAdapter;
import com.dreamspace.uucampus.ui.activity.Personal.MyFreeGoodsAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/17.
 */
public class MyFreeGoodsFragment extends BaseLazyFragment{
    @Bind(R.id.free_goods_smlv)
    SwipeMenuListView freeGoodsLv;

    private SwipeMenuCreator creator;
    private FreeGoodsSaleListAdapter saleListAdapter;
    private FreeGoodsPullOffListAdapter pullOffListAdapter;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取type，从而判断当前fragment为了展示那个界面效果
        type = getArguments() == null? "null":getArguments().getString(MyFreeGoodsAct.TYPE);
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
        if (type.equals(getResources().getString(R.string.on_sale))){
            initSaleViews();
        }else if(type.equals(getResources().getString(R.string.already_pull_off))){
            initPullOffViews();
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
        freeGoodsLv.setMenuCreator(creator);

        //为menu item添加点击事件
        freeGoodsLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
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
        return R.layout.fragment_my_free_goods;
    }

    private void setListItemClickListeners(){

    }

    private void initSaleViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        saleListAdapter = new FreeGoodsSaleListAdapter(mContext,list,FreeGoodsSaleListAdapter.ViewHolder.class);
        freeGoodsLv.setAdapter(saleListAdapter);
    }

    private void initPullOffViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add(i + "");
        }
        pullOffListAdapter = new FreeGoodsPullOffListAdapter(mContext,list,FreeGoodsPullOffListAdapter.ViewHolder.class);
        freeGoodsLv.setAdapter(pullOffListAdapter);
    }

}
