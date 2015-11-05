package com.dreamspace.uucampus.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.activity.Market.FastInAct;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */

public class HomeFragment extends BaseLazyFragment {
    @Bind(R.id.home_page_banner)
    ConvenientBanner banner;
    @Bind(R.id.free_goods_ll)
    LinearLayout freeGoodsLl;
    @Bind(R.id.travel_ll)
    LinearLayout travelLl;
    @Bind(R.id.driver_school_ll)
    LinearLayout driverSchoolLl;
    @Bind(R.id.class_uniform_ll)
    LinearLayout classUniformLl;
    @Bind(R.id.studying_abroad_ll)
    LinearLayout studyAbroadLl;
    @Bind(R.id.personal_shop_ll)
    LinearLayout personalShopLl;
    @Bind(R.id.study_abroad_rl)
    RelativeLayout studyAbroadRl;
    @Bind(R.id.free_goods_rl)
    RelativeLayout freeGoodsRl;
    @Bind(R.id.travel_rl)
    RelativeLayout travelRl;
    @Bind(R.id.driver_school_rl)
    RelativeLayout driverSchoolRl;
    @Bind(R.id.personal_shop_rl)
    RelativeLayout personalShopRl;

    @Override
    protected void onFirstUserVisible() {
        initBanner();
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void initViewsAndEvents() {
        travelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.travel));
                readyGo(FastInAct.class,bundle);
            }
        });

        classUniformLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.class_uniform));
                readyGo(FastInAct.class,bundle);
            }
        });

        driverSchoolLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class,bundle);
            }
        });

        studyAbroadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class,bundle);
            }
        });

        personalShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class,bundle);
            }
        });

        freeGoodsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //快捷方式进入闲置页面
               readyGo(FreeGoodsActivity.class);
            }
        });

        travelRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.travel));
                readyGo(FastInAct.class,bundle);
            }
        });

        driverSchoolRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class,bundle);
            }
        });

        studyAbroadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class,bundle);
            }
        });

        personalShopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY,getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class,bundle);
            }
        });

        freeGoodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //快捷方式进入闲置页面
                readyGo(FreeGoodsActivity.class);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_first;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initBanner(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner1);
        list.add(R.drawable.banner2);
        list.add(R.drawable.banner1);
        list.add(R.drawable.banner2);
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }
        ,list)
        .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        banner.startTurning(3000);
    }

    public class LocalImageHolderView implements CBPageAdapter.Holder<Integer>{
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击事件
                    Toast.makeText(view.getContext(), "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}