package com.dreamspace.uucampus.ui.fragment.Market;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.GoodDetailCommentListAdapter;
import com.dreamspace.uucampus.adapter.market.GoodDetailInstListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.AllGoodsCommentRes;
import com.dreamspace.uucampus.model.api.AllGoodsCommentItemRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.DescriptionItem;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/10.
 */
public class GoodDetailPagerFragment extends BaseLazyFragment{
    @Bind(R.id.good_detail_pager_lv)
    LoadMoreListView listView;
    private String type;
    private String goodId;
    private String description;
    private int commentPage = 1;
    private GoodDetailCommentListAdapter adapter;
    private boolean fragmentDestory = false;
    private boolean firstGetComment = true;//判断是不是第一次获取评论

    @Override
    protected void onFirstUserVisible() {
        type = getArguments().getString(GoodDetailAct.TYPE,"null");

        //判断当前fragment是用来显示商品详情还是评论的内容
        if(type.equals(GoodDetailAct.DETAIL)){
            description = getArguments().getString(GoodDetailAct.DETAIL);
            initDetailViews();
        }else if(type.equals(GoodDetailAct.COMMENT)){
            goodId = getArguments().getString(GoodDetailAct.COMMENT);
            getComments();
            initCommentViews();
        }
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

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_good_detail_pager;
    }

    private void initCommentViews(){
        //评论界面则为listview添加loadmore事件
        if(type.equals(GoodDetailAct.COMMENT)){
            listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    commentPage++;
                    getComments();
                }
            });
        }
    }

    private void initDetailViews(){
        listView.setDivider(null);
        ArrayList<DescriptionItem> descriptionItems;
        descriptionItems = new Gson().fromJson(description,new TypeToken<ArrayList<DescriptionItem>>(){}.getType());
        GoodDetailInstListAdapter adapter = new GoodDetailInstListAdapter(mContext,descriptionItems,GoodDetailInstListAdapter.ViewHolder.class);
        listView.setAdapter(adapter);
    }

    private void getComments(){
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            listView.setLoading(false);
            return;
        }

        ApiManager.getService(mContext).getAllGoodsComment(goodId, commentPage, new Callback<AllGoodsCommentRes>() {
            @Override
            public void success(AllGoodsCommentRes allGoodsCommentRes, Response response) {
                if (allGoodsCommentRes != null && !fragmentDestory) {
                    listView.setLoading(false);

                    //没有评论，无需进行下列步骤
                    if(allGoodsCommentRes.getGoods_comment().size() == 0){
                        return;
                    }

                    if (firstGetComment) {
                        //第一次获取评论
                        adapter = new GoodDetailCommentListAdapter(mContext, allGoodsCommentRes.getGoods_comment(), GoodDetailCommentListAdapter.ViewHolder.class);
                        listView.setAdapter(adapter);
                        adapter.setOnUsefulClickListener(new GoodDetailCommentListAdapter.OnUsefulClickListener() {
                            @Override
                            public void onUserfulClick(String commentId, int usefulClick, ImageView likeIv, TextView likeTv, AllGoodsCommentItemRes entity) {
                                if (usefulClick == 1) {
                                    commentUsefulCancel(commentId, likeIv, likeTv, entity);
                                } else {
                                    commentUsefulAdd(commentId, likeIv, likeTv, entity);
                                }
                            }
                        });
                        firstGetComment = false;
                    } else {
                        adapter.addEntities(allGoodsCommentRes.getGoods_comment());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                listView.setLoading(false);
            }
        });
    }

    private void commentUsefulAdd(String commentId, final ImageView likeIv,
                                  final TextView likeTv, final AllGoodsCommentItemRes entity){
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(mContext).addGoodsUseful(goodId, commentId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null && !fragmentDestory) {
                    likeIv.setImageDrawable(getResources().getDrawable(R.drawable.comment_like_icon_p));
                    likeTv.setTextColor(getResources().getColor(R.color.app_theme_color));
                    int num = entity.getUseful_number() + 1;
                    likeTv.setText(mContext.getString(R.string.useful) + num + mContext.getString(R.string.useful2));
                    //更改adapter中的数据，使其与服务器同步
                    adapter.getmEntities().get(adapter.getmEntities().indexOf(entity)).setUseful_clicked(1);
                    adapter.getmEntities().get(adapter.getmEntities().indexOf(entity)).setUseful_number(entity.getUseful_number() + 1);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    private void commentUsefulCancel(String commentId, final ImageView likeIv,
                                     final TextView likeTv, final AllGoodsCommentItemRes entity){
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(mContext).cancelGoodsUseful(goodId, commentId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if(commonStatusRes != null && !fragmentDestory){
                    likeIv.setImageDrawable(getResources().getDrawable(R.drawable.comment_like_icon));
                    likeTv.setTextColor(getResources().getColor(R.color.text_normal));
                    int num = entity.getUseful_number() - 1;
                    likeTv.setText(mContext.getString(R.string.useful) + num + mContext.getString(R.string.useful2));
                    //更改adapter中的数据，使其与服务器同步
                    adapter.getmEntities().get(adapter.getmEntities().indexOf(entity)).setUseful_clicked(0);
                    adapter.getmEntities().get(adapter.getmEntities().indexOf(entity)).setUseful_number(entity.getUseful_number() - 1);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    @Override
    public void onDestroy() {
        fragmentDestory = true;
        super.onDestroy();
    }
}
