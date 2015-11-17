package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.api.AddIdleCollectionRes;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wufan on 2015/9/21.
 */
public class FreeGoodsDetailBottomInfoFragment extends BaseLazyFragment {
    @Bind(R.id.free_goods_detail_consult_linear_layout)
    LinearLayout mConsult;
    @Bind(R.id.free_goods_detail_collect_linear_layout)
    LinearLayout mCollect;
    @Bind(R.id.free_goods_detail_bottom_collect_iv)
    ImageView mCollectIv;
    @Bind(R.id.free_goods_detail_bottom_collect_tv)
    TextView mCollectTv;
    @Bind(R.id.free_goods_detail_tv)
    TextView mDetailTv;

    private boolean bCollect=false;
    public static final String EXTRA_CONTENT="content";
    public static final String EXTRA_IS_COLLECTION="is_collection";
    private String idle_id=null;
    private String content=null;
    private String is_collection=null;

    public static FreeGoodsDetailBottomInfoFragment newInstance() {
        FreeGoodsDetailBottomInfoFragment fragment = new FreeGoodsDetailBottomInfoFragment();
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {
        idle_id =getArguments().getString(FreeGoodsDetailActivity.EXTRA_IDLE_ID);
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
        content =getArguments().getString(EXTRA_CONTENT);
        is_collection=getArguments().getString(EXTRA_IS_COLLECTION);
        mDetailTv.setText(content);
        if(Integer.parseInt(is_collection)==1){  //已收藏状态
            mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_p);
            mCollectTv.setTextColor(getResources().getColor(R.color.text_pressed));
            bCollect = true;
        }else{                                 //未收藏状态
            mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_n);
            mCollectTv.setTextColor(getResources().getColor(R.color.text_normal));
            bCollect = false;
        }

        mConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = dialog = new ConnectSellerDialog(getActivity(),
                        R.style.UpDialog, "good name", "phone number");
                Window window = dialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                layoutParams.y = (int)(80*getResources().getDisplayMetrics().density);
                window.setAttributes(layoutParams);
                dialog.show();
            }
        });
        mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PreferenceUtils.hasKey(mContext,PreferenceUtils.Key.LOGIN) ||
                        !PreferenceUtils.getBoolean(mContext,PreferenceUtils.Key.LOGIN)){
                    //未登录
                    readyGo(LoginActivity.class);
                }else{
                    if(bCollect){
                        mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_n);
                        mCollectTv.setTextColor(getResources().getColor(R.color.text_normal));
                        bCollect = false;
                        updateCollect(bCollect);
                    }else{
                        mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_p);
                        mCollectTv.setTextColor(getResources().getColor(R.color.text_pressed));
                        bCollect=true;
                        updateCollect(bCollect);
                    }
                }
            }
        });
    }

    private void updateCollect(boolean bCollect) {
        if(NetUtils.isNetworkConnected(getActivity().getApplicationContext())){
            if(bCollect) {
                final ProgressDialog pd = new ProgressDialog(mContext);
                pd.setContent("正在收藏");
                pd.show();
//                final ProgressDialog pd =ProgressDialog.show(getActivity(), "", "正在收藏", true, false);
                ApiManager.getService(getActivity().getApplicationContext()).addIdleCollection(idle_id, new Callback<AddIdleCollectionRes>() {
                    @Override
                    public void success(AddIdleCollectionRes addIdleCollectionRes, Response response) {
                        showToast("成功收藏");
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        TLog.i("error:", error.getMessage());
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }else{
                final ProgressDialog pd = new ProgressDialog(mContext);
                pd.setContent("取消收藏");
                pd.show();
//                final ProgressDialog pd =ProgressDialog.show(getActivity(), "", "取消收藏", true, false);
                ApiManager.getService(getActivity().getApplicationContext()).deleteIdleCollection(idle_id, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        showToast("取消收藏");
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }
        }else{
            showNetWorkError();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_free_goods_detail_bottom_info;
    }
}
