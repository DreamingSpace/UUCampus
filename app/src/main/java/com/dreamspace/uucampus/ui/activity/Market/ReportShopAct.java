package com.dreamspace.uucampus.ui.activity.Market;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.CommitReportReq;
import com.dreamspace.uucampus.model.api.ReportRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/22.
 * 举报商家页面，进入此页面需传入相应商家的shopid
 */
public class ReportShopAct extends AbsActivity{
    @Bind(R.id.report_seller_et)
    EditText reportEt;
    @Bind(R.id.finish_report_btn)
    Button submitBtn;

    private String shopId;
    private boolean actDestory = false;
    public static final String SHOP_ID = "SHOP_ID";

    @Override
    protected int getContentView() {
        return R.layout.activity_report_seller;
    }

    @Override
    protected void prepareDatas() {
        shopId = getIntent().getExtras().getString(SHOP_ID);//获取shopid
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.report_shop1));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtils.isEmpty(reportEt.getText().toString()) ||
                            reportEt.getText().toString().length() < 10){
                    showToast(getString(R.string.report_cant_less_than_10));
                }else{
                    reportShop();
                }
            }
        });
    }

    private void reportShop(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        CommitReportReq commitReportReq = new CommitReportReq();
        commitReportReq.setShop_id(shopId);
        commitReportReq.setContent(reportEt.getText().toString());
        ApiManager.getService(this).commitReport(commitReportReq, new Callback<ReportRes>() {
            @Override
            public void success(ReportRes reportRes, Response response) {
                if(reportRes != null && !actDestory){
                    Toast.makeText(ReportShopAct.this,getString(R.string.report_success),Toast.LENGTH_SHORT).show();
                    ReportShopAct.this.finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }
}
