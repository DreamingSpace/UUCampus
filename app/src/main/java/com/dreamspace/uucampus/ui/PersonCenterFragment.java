package com.dreamspace.uucampus.ui;

import android.view.View;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.Order.MyOrderAct;
import com.dreamspace.uucampus.ui.activity.Personal.CouponCardAct;
import com.dreamspace.uucampus.ui.activity.Personal.FeedbackAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyFreeGoodsAct;
import com.dreamspace.uucampus.ui.activity.Personal.PersonalInfoAct;
import com.dreamspace.uucampus.ui.activity.Personal.SettingAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by money on 2015/9/14.
 */
public class PersonCenterFragment extends BaseLazyFragment {
    @Bind(R.id.personal_avatar_civ)
    CircleImageView avatarCiv;
    @Bind(R.id.my_free_goods_ll)
    LinearLayout freeGoodsLl;
    @Bind(R.id.my_collection_ll)
    LinearLayout collectionLl;
    @Bind(R.id.my_order_ll)
    LinearLayout orderLl;
    @Bind(R.id.apply_shop_ll)
    LinearLayout applyShopLl;
    @Bind(R.id.my_coupon_card_ll)
    LinearLayout couponCardLl;
    @Bind(R.id.setting_ll)
    LinearLayout settingLl;
    @Bind(R.id.feedback_ll)
    LinearLayout feedbackLl;

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
    protected void initViewsAndEvents() {
        avatarCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(PersonalInfoAct.class);
            }
        });

        freeGoodsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MyFreeGoodsAct.class);
            }
        });

        collectionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MyCollectionAct.class);
            }
        });

        orderLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MyOrderAct.class);
            }
        });

        applyShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        couponCardLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(CouponCardAct.class);
            }
        });

        settingLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(SettingAct.class);
            }
        });

        feedbackLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(FeedbackAct.class);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_second;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}