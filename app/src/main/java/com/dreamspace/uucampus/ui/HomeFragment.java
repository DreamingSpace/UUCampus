package com.dreamspace.uucampus.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.AdItem;
import com.dreamspace.uucampus.model.ShopItem;
import com.dreamspace.uucampus.model.api.GetAdRes;
import com.dreamspace.uucampus.model.api.SearchShopRes;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsActivity;
import com.dreamspace.uucampus.ui.activity.Market.FastInAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    @Bind(R.id.free_goods_linear)
    LinearLayout freeGoodsLinear;
    @Bind(R.id.person_shop_Linear)
    LinearLayout personShopLinear;
    @Bind(R.id.driver_school_Linear)
    LinearLayout driverSchoolLinear;
    @Bind(R.id.study_abroad_Linear)
    LinearLayout studyAbroadLinear;
    @Bind(R.id.travel_Linear)
    LinearLayout travelLinear;
    @Bind(R.id.home_personal_shop_one_img)
    ImageView homePersonalShopOneImg;
    @Bind(R.id.home_personal_shop_one_text)
    TextView homePersonalShopOneText;
    @Bind(R.id.home_personal_shop_two_img)
    ImageView homePersonalShopTwoImg;
    @Bind(R.id.home_personal_shop_two_text)
    TextView homePersonalShopTwoText;
    @Bind(R.id.home_personal_shop_three_img)
    ImageView homePersonalShopThreeImg;
    @Bind(R.id.home_personal_shop_three_text)
    TextView homePersonalShopThreeText;
    @Bind(R.id.home_personal_shop_four_img)
    ImageView homePersonalShopFourImg;
    @Bind(R.id.home_personal_shop_four_text)
    TextView homePersonalShopFourText;
    @Bind(R.id.home_driver_school_one_img)
    ImageView homeDriverSchoolOneImg;
    @Bind(R.id.home_driver_school_one_text)
    TextView homeDriverSchoolOneText;
    @Bind(R.id.home_driver_school_two_img)
    ImageView homeDriverSchoolTwoImg;
    @Bind(R.id.home_driver_school_two_text)
    TextView homeDriverSchoolTwoText;
    @Bind(R.id.home_driver_school_three_img)
    ImageView homeDriverSchoolThreeImg;
    @Bind(R.id.home_driver_school_three_text)
    TextView homeDriverSchoolThreeText;

    private ArrayList<AdItem> ads;

    @Override
    protected void onFirstUserVisible() {
        if (ads == null && banner != null) {
            getAd();
        } else {
            initBanner();
        }
        setShop("人气小店");
        setShop("驾校");
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
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.travel));
                readyGo(FastInAct.class, bundle);
            }
        });

        classUniformLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.class_uniform));
                readyGo(FastInAct.class, bundle);
            }
        });

        driverSchoolLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class, bundle);
            }
        });

        studyAbroadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class, bundle);
            }
        });

        personalShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class, bundle);
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
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.travel));
                readyGo(FastInAct.class, bundle);
            }
        });

        driverSchoolRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class, bundle);
            }
        });

        studyAbroadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class, bundle);
            }
        });

        personalShopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class, bundle);
            }
        });

        freeGoodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //快捷方式进入闲置页面
                readyGo(FreeGoodsActivity.class);
            }
        });

        freeGoodsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(FreeGoodsActivity.class);
            }
        });

        personShopLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.personal_shop));
                readyGo(FastInAct.class, bundle);
            }
        });

        studyAbroadLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.study_abroad));
                readyGo(FastInAct.class, bundle);
            }
        });

        driverSchoolLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.driver_school));
                readyGo(FastInAct.class, bundle);
            }
        });

        travelLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FastInAct.CATEGORY, getResources().getString(R.string.travel));
                readyGo(FastInAct.class, bundle);
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

    private void initBanner() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            list.add(ads.get(i).getImage());
        }
        banner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }
                , list)
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        banner.startTurning(3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            CommonUtils.showImageWithGlide(context, imageView, data);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击广告栏，打开相应的网址
                    Uri uri = Uri.parse(ads.get(position).getLink());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
        }
    }

    private void setShop(final String category){
        if(NetUtils.isNetworkConnected(getActivity())){
            ApiManager.getService(getActivity()).searchShop(" ", null, category, 1, PreferenceUtils.getString(getActivity(),PreferenceUtils.Key.LOCATION), new Callback<SearchShopRes>() {
                @Override
                public void success(SearchShopRes searchShopRes, Response response) {
                    List<ShopItem> shops = new ArrayList<ShopItem>();
                    shops = searchShopRes.getResult();
                    List<ImageView> shopsImg = new ArrayList<ImageView>();
                    List<TextView> shopsText = new ArrayList<TextView>();
                    if(category.equals("人气小店")){
                        shopsImg.add(homePersonalShopOneImg);
                        shopsImg.add(homePersonalShopTwoImg);
                        shopsImg.add(homePersonalShopThreeImg);
                        shopsImg.add(homePersonalShopFourImg);
                        shopsText.add(homePersonalShopOneText);
                        shopsText.add(homePersonalShopTwoText);
                        shopsText.add(homePersonalShopThreeText);
                        shopsText.add(homePersonalShopFourText);
                    }else{
                        shopsImg.add(homeDriverSchoolOneImg);
                        shopsImg.add(homeDriverSchoolTwoImg);
                        shopsImg.add(homeDriverSchoolThreeImg);
                        shopsText.add(homeDriverSchoolOneText);
                        shopsText.add(homeDriverSchoolTwoText);
                        shopsText.add(homeDriverSchoolThreeText);
                    }
                    for(int i=0;i<shops.size();i++){
                        CommonUtils.showImageWithGlide(mContext,shopsImg.get(i),shops.get(i).getImage());
                        shopsText.get(i).setText(shops.get(i).getName());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                }
            });
        }else{
            showNetWorkError();
        }
    }

    //获取广告栏信息
    private void getAd() {
        if (NetUtils.isNetworkAvailable(getActivity())) {
            ApiManager.getService(getActivity()).getAd(new Callback<GetAdRes>() {
                @Override
                public void success(GetAdRes getAdRes, Response response) {
                    ads = new ArrayList<AdItem>();
                    ads = getAdRes.getAdvertisement();
                    initBanner();
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                }
            });
        } else {
            showNetWorkError();
        }
    }
}