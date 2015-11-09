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
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.ui.activity.Order.MyOrderAct;
import com.dreamspace.uucampus.ui.activity.Personal.AboutAct;
import com.dreamspace.uucampus.ui.activity.Personal.CouponCardAct;
import com.dreamspace.uucampus.ui.activity.Personal.FeedbackAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyCollectionAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyFreeGoodsAct;
import com.dreamspace.uucampus.ui.activity.Personal.PersonalInfoAct;
import com.dreamspace.uucampus.ui.activity.Personal.SettingAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    @Bind(R.id.about_ll)
    LinearLayout aboutLl;
    @Bind(R.id.personal_nickname_tv)
    TextView nicnNameTv;

    private UserInfoRes userInfo;
    private boolean fragmentDestory = false;

    public static final String USER_INFO = "user_info";
    public static final int AVATAR_OR_NAME_CHANGE = 1;

    @Override
    protected void onFirstUserVisible() {
        getUserInfo();
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
                Bundle bundle = new Bundle();
                bundle.putParcelable(USER_INFO,userInfo);
                //若用户改变头像则返回时也要将此页面头像改变
                readyGoForResult(PersonalInfoAct.class,AVATAR_OR_NAME_CHANGE,bundle);
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

        aboutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(AboutAct.class);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_second;
    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(getActivity(),R.id.personal_center_content_ll);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //个人信息编辑页面退出后将最新用户信息传递到此页面
        if(requestCode == AVATAR_OR_NAME_CHANGE && resultCode == getActivity().RESULT_OK){
            Bundle changeData = data.getExtras();
            UserInfoRes userInfo = changeData.getParcelable(USER_INFO);
            showUserInfoIntoViews(userInfo);
        }
    }

    private void getUserInfo(){
        toggleShowLoading(true,"");
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            toggleNetworkError(true,getInfoClickListeners);
            return;
        }

        ApiManager.getService(mContext).getUserInfo(new Callback<UserInfoRes>() {
            @Override
            public void success(UserInfoRes userInfoRes, Response response) {
                if(userInfoRes != null && !fragmentDestory){
                    showUserInfoIntoViews(userInfoRes);
                    toggleRestore();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowEmpty(true, null, getInfoClickListeners);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDestory = true;
    }

    private View.OnClickListener getInfoClickListeners = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getUserInfo();
        }
    };

    //将头像和昵称显示到视图中
    private void showUserInfoIntoViews(UserInfoRes userInfo){
        CommonUtils.showImageWithGlideInCiv(mContext, avatarCiv, userInfo.getImage());
        nicnNameTv.setText(userInfo.getName());
        this.userInfo = userInfo;
    }
}