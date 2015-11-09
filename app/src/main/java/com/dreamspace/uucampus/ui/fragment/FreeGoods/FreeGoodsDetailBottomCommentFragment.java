package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.FreeGoods.FreeGoodsCommentItemAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.FreeGoodsCommentItem;
import com.dreamspace.uucampus.model.api.AddIdleCommentRes;
import com.dreamspace.uucampus.model.api.CommentItem;
import com.dreamspace.uucampus.model.api.ContentReq;
import com.dreamspace.uucampus.model.api.IdleAllCommentRes;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.LoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wufan on 2015/9/21.
 */
public class FreeGoodsDetailBottomCommentFragment extends BaseLazyFragment {

    @Bind(R.id.goods_detail_bottom_comment_list_view)
    LoadMoreListView mListView;
    @Bind(R.id.free_goods_detail_comment_publish_button)
    Button mPublishBtn;
    @Bind(R.id.free_goods_detail_comment_edit_text)
    EditText mCommentEditText;

    private FreeGoodsCommentItemAdapter mAdapter;
    private List<CommentItem> data;
    private String idle_id = null;
    private int page = 1;
    private ContentReq req = new ContentReq();

    public static final int ADD = 2;
    public static final int LOAD = 1;

    public static FreeGoodsDetailBottomCommentFragment newInstance() {
        FreeGoodsDetailBottomCommentFragment fragment = new FreeGoodsDetailBottomCommentFragment();
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {
        idle_id = getArguments().getString(FreeGoodsDetailActivity.EXTRA_IDLE_ID);   //进入详情页面，此方法没有调用
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {
        idle_id = getArguments().getString(FreeGoodsDetailActivity.EXTRA_IDLE_ID);
        initCommentData();

        mAdapter = new FreeGoodsCommentItemAdapter(getActivity());
        adapterUsefulCallBack();   //adapter实现有用评论点击回调函数

        mListView.setAdapter(mAdapter);
        mListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onPullUp();
            }
        });

    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_free_goods_detail_bottom_comment;
    }

    @OnClick(R.id.free_goods_detail_comment_publish_button)
    void publishComment() {

        if (mCommentEditText.length() == 0) {
            showToast("请写评论");
        } else {
            req.setContent(mCommentEditText.getText().toString());
            mCommentEditText.setText("");
            final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "正在添加", true, false);
            if (NetUtils.isNetworkConnected(getActivity().getApplicationContext())) {
                ApiManager.getService(getActivity().getApplicationContext()).addIdleComment(idle_id, req, new Callback<AddIdleCommentRes>() {
                    @Override
                    public void success(AddIdleCommentRes addIdleCommentRes, Response response) {
                        initCommentData();
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            } else {
                showNetWorkError();
                pd.dismiss();
            }
        }
    }

    public void refreshDate(List<FreeGoodsCommentItem> mEntities, int type) {
        switch (type) {
            case LOAD:
                mAdapter.setmEntities(mEntities);
                break;
            case ADD:
                mAdapter.addEntities(mEntities);
                break;
            default:
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    public void initCommentData() {
        toggleShowLoading(true, getString(R.string.common_loading_message));
        page = 1;
        loadingCommentByPage(page, new OnRefreshListener<FreeGoodsCommentItem>() {
            @Override
            public void onFinish(List<FreeGoodsCommentItem> mEntities) {
                if (mEntities != null && mEntities.size() == 0) {
                    toggleShowEmpty(true, getString(R.string.common_empty_msg), null);
                } else {
                    toggleShowLoading(false, null);
                    refreshDate(mEntities, LOAD);
                }
            }

            @Override
            public void onError() {
                toggleShowError(true, getString(R.string.common_error_msg), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initCommentData();
                    }
                });
            }
        });
    }

    public void onPullUp() {  //上拉刷新,加载更多
        loadingCommentByPage(++page, new OnRefreshListener<FreeGoodsCommentItem>() {
            @Override
            public void onFinish(List mEntities) {
                refreshDate(mEntities,ADD);
                onPullUpFinished();
            }

            @Override
            public void onError() {
                onPullUpFinished();
            }
        });
    }

    public void onPullUpFinished() {
        mListView.setLoading(false);
    }

    void loadingCommentByPage(int page, final OnRefreshListener onRefreshListener) {
        if (NetUtils.isNetworkConnected(getActivity().getApplicationContext())) {
            ApiManager.getService(getActivity().getApplicationContext()).getIdleComment(idle_id, page, new Callback<IdleAllCommentRes>() {
                @Override
                public void success(IdleAllCommentRes idleAllCommentRes, Response response) {
                    if (idleAllCommentRes.getIdle_comment() != null) {
                        onRefreshListener.onFinish(idleAllCommentRes.getIdle_comment());
                    } else {
                        showToast(response.getReason());
                        onRefreshListener.onError();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    onRefreshListener.onError();
                    showInnerError(error);
                }
            });
        } else {
            showNetWorkError();
        }
    }

    //adapter实现有用评论点击回调函数
    private void adapterUsefulCallBack() {
        mAdapter.setUpdateData(new FreeGoodsCommentItemAdapter.UpdateData() {
            @Override
            public void updateUsefulData(String comment_id, boolean bUseful) {
                if (NetUtils.isNetworkConnected(getActivity().getApplicationContext())) {
                    if (bUseful) {  //评论有用
                        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "有用评论添加", true, false);
                        ApiManager.getService(getActivity().getApplicationContext()).addIdleCommentUseful(idle_id, comment_id, new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                //重新加载当前页面的数据
                                loadingCommentByPage(page, new OnRefreshListener() {
                                    @Override
                                    public void onFinish(List mEntities) {
//                                        refreshDate(mEntities,ADD);
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                                pd.dismiss();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                showInnerError(error);
                                pd.dismiss();
                            }
                        });
                    } else {  //评论无用
                        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "有用评取消", true, false);
                        ApiManager.getService(getActivity().getApplicationContext()).cancelIdleCommentUseful(idle_id, comment_id, new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                pd.dismiss();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                showInnerError(error);
                                pd.dismiss();
                            }
                        });
                    }
                } else {
                    showNetWorkError();
                }
            }
        });
    }
}
