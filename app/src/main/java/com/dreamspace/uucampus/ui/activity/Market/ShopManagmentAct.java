package com.dreamspace.uucampus.ui.activity.Market;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Market.ClassifyPreferentialListFragment;
import com.dreamspace.uucampus.ui.fragment.Market.OnSalePullOffFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/23.
 */
public class ShopManagmentAct extends AbsActivity{
    @Bind(R.id.no_goods_ll)
    LinearLayout noGoodsLl;
    @Bind(R.id.have_goods_ll)
    LinearLayout haveGoodsLl;
    @Bind(R.id.my_shop_act_smarttablayout)
    SmartTabLayout smartTabLayout;
    @Bind(R.id.my_shop_act_view_pager)
    ViewPager pager;

    public static String MANAGEMENT_TYPE = "management_type";
    @Override
    protected int getContentView() {
        return R.layout.activity_shop_management;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_management,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_good){
            readyGo(AddGoodAct.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void prepareDatas() {
        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                                                .add(getResources().getString(R.string.catalog_list),ClassifyPreferentialListFragment.class,getBundle(0))
                                                .add(getResources().getString(R.string.preferential_list),ClassifyPreferentialListFragment.class,getBundle(1))
                                                .add(getResources().getString(R.string.on_sale),OnSalePullOffFragment.class,getBundle(2))
                                                .add(getResources().getString(R.string.already_pull_off),OnSalePullOffFragment.class,getBundle(3))
                                                .create());
        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        pager.setAdapter(adapter);
        smartTabLayout.setViewPager(pager);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = (TextView) mToolBar.findViewById(R.id.custom_title_tv);
        title.setText(getResources().getString(R.string.my_goods));
    }

    private Bundle getBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 4){
            switch (position){
                case 0:
                    bundle.putString(MANAGEMENT_TYPE,getResources().getString(R.string.catalog_list));
                    break;

                case 1:
                    bundle.putString(MANAGEMENT_TYPE,getResources().getString(R.string.preferential_list));
                    break;

                case 2:
                    bundle.putString(MANAGEMENT_TYPE,getResources().getString(R.string.on_sale));
                    break;

                case 3:
                    bundle.putString(MANAGEMENT_TYPE,getResources().getString(R.string.already_pull_off));
                    break;
            }
        }
        return  bundle;
    }
}
