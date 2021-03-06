package com.dreamspace.uucampus.ui.fragment.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.Personal.FreeGoodsPullOffListAdapter;
import com.dreamspace.uucampus.adapter.Personal.FreeGoodsSaleListAdapter;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.IdleInfoRes;
import com.dreamspace.uucampus.model.api.MyIdleItem;
import com.dreamspace.uucampus.model.api.UpdateIdleReq;
import com.dreamspace.uucampus.ui.activity.FreeGoods.FreeGoodsDetailActivity;
import com.dreamspace.uucampus.ui.activity.Personal.IdleEditAct;
import com.dreamspace.uucampus.ui.activity.Personal.MyFreeGoodsAct;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.widget.SwipeMenuLoadMoreListView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/17.
 */
public class MyFreeGoodsFragment extends BaseFragment {
    @Bind(R.id.free_good_smlv)
    SwipeMenuLoadMoreListView freeGoodsLv;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private SwipeMenuCreator creator;
    private FreeGoodsSaleListAdapter saleListAdapter;
    private FreeGoodsPullOffListAdapter pullOffListAdapter;
    private ProgressDialog progressDialog;
    private String type;
    private boolean firstGetData = true;
    private int is_active = -1;
    private int page = 1;
    private static final int IDLE_EDIT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取type，从而判断当前fragment为了展示那个界面效果
        type = getArguments() == null? "null":getArguments().getString(MyFreeGoodsAct.TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_free_goods;
    }

    @Override
    public void initViews(View view) {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_theme_color));
    }

    @Override
    public void initDatas() {
        if(firstGetData){
            if(type.equals(getString(R.string.on_sale))){
                is_active = 1;
            }else if(type.equals(getString(R.string.already_pull_off))){
                is_active = 0;
            }
            System.out.println(is_active);
            getIdleList();
        }else if(((MyFreeGoodsAct)getActivity()).isPulloffOrSale()){
            //上下架情况有变需要刷新数据
            page = 1;
            firstGetData = true;
            getIdleList();
        }else{
            //起到一个缓存作用，不需要再次请求数据
            if(type.equals(getString(R.string.on_sale))){
                freeGoodsLv.setAdapter(saleListAdapter);
            }else if(type.equals(getString(R.string.already_pull_off))){
                freeGoodsLv.setAdapter(pullOffListAdapter);
            }
        }
        ((MyFreeGoodsAct)getActivity()).setPulloffOrSale(false);//将此变量设置为false，只有当上架或下架过才会变成true
        initListeners();
    }

    private void initListeners(){
        freeGoodsLv.setOnLoadMoreListener(new SwipeMenuLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getIdleList();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                firstGetData = true;
                getIdleList();
            }
        });

        //为list item添加swipe menu item
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
                openItem.setBackground(R.color.price_text_color);
                openItem.setWidth((int)(90*getResources().getDisplayMetrics().density));
                openItem.setTitle(getString(R.string.delete));
                openItem.setTitleSize((int)(8*getResources().getDisplayMetrics().density));
                openItem.setTitleColor(getResources().getColor(R.color.white));
                swipeMenu.addMenuItem(openItem);
            }
        };

        freeGoodsLv.setMenuCreator(creator);

        //为menu item添加点击事件
        freeGoodsLv.setOnMenuItemClickListener(new SwipeMenuLoadMoreListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                //i1为menu item的位置,i为listview的位置
                if (i1 == 0) {
                    initProgressDialog();
                    progressDialog.setContent(getString(R.string.in_delete));
                    progressDialog.show();
                    if (type.equals(getString(R.string.on_sale))) {
                        MyIdleItem idleItem = saleListAdapter.getItem(i);
                        idleDelete(idleItem.getIdle_id());
                    } else if (type.equals(getString(R.string.already_pull_off))) {
                        MyIdleItem idleItem = pullOffListAdapter.getItem(i);
                        idleDelete(idleItem.getIdle_id());
                    }
                }
                return false;
            }
        });

        freeGoodsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (type.equals(getString(R.string.on_sale))) {
                    bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID,saleListAdapter.getItem(position).getIdle_id());
                } else if (type.equals(getString(R.string.already_pull_off))) {
                    bundle.putString(FreeGoodsDetailActivity.EXTRA_IDLE_ID,pullOffListAdapter.getItem(position).getIdle_id());
                }
                readyGo(FreeGoodsDetailActivity.class,bundle);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    //获取我的闲置商品列表
    private void getIdleList(){
        if(firstGetData){
            toggleShowLoading(true,null);
        }

        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            if(firstGetData){
                toggleNetworkError(true,getIdleListClickListener);
            }
            freeGoodsLv.setLoading(false);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiManager.getService(getActivity()).getIdleList(page, is_active, new Callback<IdleInfoRes>() {
            @Override
            public void success(IdleInfoRes idleInfoRes, Response response) {
                if (idleInfoRes != null) {
                    freeGoodsLv.setLoading(false);
                    swipeRefreshLayout.setRefreshing(false);

                    if (idleInfoRes.getIdle().size() == 0 && page == 1) {
                        toggleShowEmpty(true, getString(R.string.no_such_good), null);
                        return;
                    }

                    //没有更多
                    if (idleInfoRes.getIdle().size() == 0 && page != 1) {
                        return;
                    }

                    //根据上下架状态不一
                    if (is_active == 1) {
                        if (firstGetData) {
                            saleListAdapter = new FreeGoodsSaleListAdapter(getActivity(), idleInfoRes.getIdle(), FreeGoodsSaleListAdapter.ViewHolder.class);
                            freeGoodsLv.setAdapter(saleListAdapter);
                            saleListAdapter.setOnGoodPullOffClickListener(new FreeGoodsSaleListAdapter.OnGoodPullOffClickListener() {
                                @Override
                                public void OnPullOff(String idle_id, int position) {
                                    initProgressDialog();
                                    progressDialog.setContent(getString(R.string.in_pull_off));
                                    progressDialog.show();
                                    updateIdleInfo(idle_id,0,position);
                                }
                            });
                            saleListAdapter.setOnGoodEditClickListener(new FreeGoodsSaleListAdapter.OnGoodEditClickListener() {
                                @Override
                                public void onEditClick(String idle_id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(IdleEditAct.IDLE_ID,idle_id);
                                    bundle.putInt(IdleEditAct.IS_ACTIVE,is_active);
                                    readyGoForResult(IdleEditAct.class, IDLE_EDIT, bundle);
                                }
                            });
                            firstGetData = false;
                            toggleRestore();
                        } else {
                            saleListAdapter.addEntities(idleInfoRes.getIdle());
                            saleListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (firstGetData) {
                            pullOffListAdapter = new FreeGoodsPullOffListAdapter(getActivity(), idleInfoRes.getIdle(), FreeGoodsPullOffListAdapter.ViewHolder.class);
                            freeGoodsLv.setAdapter(pullOffListAdapter);
                            pullOffListAdapter.setOnGoodSaleClickListener(new FreeGoodsPullOffListAdapter.OnGoodSaleClickListener() {
                                @Override
                                public void onSaleClick(String idle_id, int position) {
                                    initProgressDialog();
                                    progressDialog.setContent(getString(R.string.in_on_sale));
                                    progressDialog.show();
                                    updateIdleInfo(idle_id,1,position);
                                }
                            });
                            pullOffListAdapter.setOnEditClickListener(new FreeGoodsPullOffListAdapter.OnEditClickListener() {
                                @Override
                                public void onEditClick(String idle_id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(IdleEditAct.IDLE_ID,idle_id);
                                    bundle.putInt(IdleEditAct.IS_ACTIVE, is_active);
                                    readyGoForResult(IdleEditAct.class,IDLE_EDIT,bundle);
                                }
                            });
                            firstGetData = false;
                            toggleRestore();
                        } else {
                            pullOffListAdapter.addEntities(idleInfoRes.getIdle());
                            pullOffListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                freeGoodsLv.setLoading(false);
                swipeRefreshLayout.setRefreshing(false);
                if (firstGetData) {
                    toggleShowEmpty(true, null, getIdleListClickListener);
                } else {
                    showInnerError(error);
                }
            }
        });
    }

    //闲置删除
    private void idleDelete(String idle_id){
        if(!NetUtils.isNetworkConnected(getActivity())){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        ApiManager.getService(getActivity()).deleteIdle(idle_id, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null) {
                    progressDialog.dismiss();
                    showToast(getString(R.string.delete_success));
                    //刷新数据
                    page = 1;
                    firstGetData = true;
                    getIdleList();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    //上下架闲置商品
    private void updateIdleInfo(String idle_id,int is_active, final int position){
        if(!NetUtils.isNetworkConnected(getActivity())){
            progressDialog.dismiss();
            showNetWorkError();
            return;
        }

        UpdateIdleReq idleReq = new UpdateIdleReq();
        idleReq.setIs_active(is_active);
        ApiManager.getService(getActivity()).updateIdleInfo(idle_id, idleReq, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if(commonStatusRes != null){
                    progressDialog.dismiss();
                    //刷新数据
                    page = 1;
                    firstGetData = true;
                    getIdleList();
                    ((MyFreeGoodsAct)getActivity()).setPulloffOrSale(true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }
        progressDialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IDLE_EDIT && resultCode == getActivity().RESULT_OK){
            firstGetData = true;
            page = 1;
            getIdleList();
        }
    }

    private View.OnClickListener getIdleListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getIdleList();
        }
    };
}
