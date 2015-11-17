package com.dreamspace.uucampus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.activity.Order.MyOrderAct;
import com.dreamspace.uucampus.ui.activity.Personal.AboutAct;
import com.dreamspace.uucampus.ui.activity.Personal.CouponCardAct;
import com.dreamspace.uucampus.ui.activity.Personal.FeedbackAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyFreeGoodsAct;
import com.dreamspace.uucampus.ui.activity.Personal.PersonalInfoAct;
import com.dreamspace.uucampus.ui.activity.Personal.SettingAct;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/9/14.
 * 此页面不需要获取数据，全部读取缓存数据
 */
public class PersonCenterFragment extends BaseFragment {
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
    @Bind(R.id.about_ll)
    LinearLayout aboutLl;
    @Bind(R.id.personal_nickname_tv)
    TextView nickNameTv;
    @Bind(R.id.personal_center_content_ll)
    LinearLayout contentLl;

    private static final int AVATAR_OR_NAME_CHANGE = 1;
    private static final int SETTING = 2;
    private static final int LOGIN = 3;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    public void initViews(View view) {
        if(!PreferenceUtils.hasKey(getActivity(),PreferenceUtils.Key.LOGIN)
                || !PreferenceUtils.getBoolean(getActivity(),PreferenceUtils.Key.LOGIN)){
            //未登录状态
            initNoLoginViewsAndEvents();
        }else{
            //登录状态
            initLoginViewsAndEvents();
        }

        initListeners();
    }

    //在activtiy中调用此方法用来刷新view
    public void updateView(){
        if(!PreferenceUtils.hasKey(getActivity(),PreferenceUtils.Key.LOGIN)
                || !PreferenceUtils.getBoolean(getActivity(),PreferenceUtils.Key.LOGIN)){
            //未登录状态
            initNoLoginViewsAndEvents();
        }else{
            //登录状态
            initLoginViewsAndEvents();
        }
    }

    private void initListeners(){
        settingLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(SettingAct.class,SETTING);
            }
        });

        aboutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(AboutAct.class);
            }
        });

        applyShopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public void initDatas() {

    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //个人信息编辑页面退出后将最新用户信息传递到此页面
        if(requestCode == AVATAR_OR_NAME_CHANGE && resultCode == getActivity().RESULT_OK){
            showUserInfoIntoViews();
        }else if(requestCode == SETTING && resultCode == getActivity().RESULT_OK){
            //当用户选择登出时，将视图初始化为未登录状态
            if(!PreferenceUtils.hasKey(getActivity(),PreferenceUtils.Key.LOGIN)
                    || !PreferenceUtils.getBoolean(getActivity(),PreferenceUtils.Key.LOGIN)){
                initNoLoginViewsAndEvents();
            }else{
                initLoginViewsAndEvents();
            }
        }else if(requestCode == LOGIN && resultCode == getActivity().RESULT_OK){
            //登录成功，此activity结束
            initLoginViewsAndEvents();
        }
    }

    //将头像和昵称显示到视图中
    private void showUserInfoIntoViews(){
        CommonUtils.showImageWithGlideInCiv(getActivity(), avatarCiv, PreferenceUtils.getString(getActivity(),PreferenceUtils.Key.AVATAR));
        nickNameTv.setText(PreferenceUtils.getString(getActivity(), PreferenceUtils.Key.NAME));
    }

    //初始化登录过的视图和事件
    private void initLoginViewsAndEvents(){
        if(PreferenceUtils.hasKey(getActivity(),PreferenceUtils.Key.AVATAR)){
            CommonUtils.showImageWithGlideInCiv(getActivity(), avatarCiv,
                    PreferenceUtils.getString(getActivity(),PreferenceUtils.Key.AVATAR));
        }

        if(PreferenceUtils.hasKey(getActivity(),PreferenceUtils.Key.NAME)){
            nickNameTv.setText(PreferenceUtils.getString(getActivity(), PreferenceUtils.Key.NAME));
        }

        avatarCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //若用户改变头像则返回时也要将此页面头像改变
                readyGoForResult(PersonalInfoAct.class, AVATAR_OR_NAME_CHANGE);
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

        couponCardLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(CouponCardAct.class);
            }
        });
    }

    //初始化未登录过的视图和事件
    private void initNoLoginViewsAndEvents(){
        avatarCiv.setImageDrawable(getResources().getDrawable(R.drawable.register_icon_just_a_sign));
        nickNameTv.setText(getString(R.string.no_login));

        avatarCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoForResult(LoginActivity.class,LOGIN);
            }
        });

        freeGoodsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(LoginActivity.class, LOGIN);
            }
        });

        collectionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(LoginActivity.class, LOGIN);
            }
        });

        orderLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(LoginActivity.class, LOGIN);
            }
        });

        couponCardLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(LoginActivity.class, LOGIN);
            }
        });
    }
}