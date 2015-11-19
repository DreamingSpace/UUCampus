package com.dreamspace.uucampus.ui.activity.Search;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by money on 2015/11/19.
 */
public class SearchResultMoreActivity extends AbsActivity {
    @Bind(R.id.search_text)
    EditText searchText;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.search_goods_more_list)
    ListView searchGoodsMoreList;
    @Bind(R.id.search_goods_more_linear)
    LinearLayout searchGoodsMoreLinear;
    @Bind(R.id.search_idle_more_list)
    ListView searchIdleMoreList;
    @Bind(R.id.search_idle_more_linear)
    LinearLayout searchIdleMoreLinear;
    @Bind(R.id.search_shop_more_list)
    ListView searchShopMoreList;
    @Bind(R.id.search_shop_more_linear)
    LinearLayout searchShopMoreLinear;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result_more;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void searchGood(){

    }
    
    private void searchIdle(){

    }

    private void searchShop(){

    }
}
