package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.AddShopCollectionRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.ShopInfoRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/10.
 */
public class ShopDetailAct extends AbsActivity {
    @Bind(R.id.sale_range_tv)
    TextView saleRangeTv;
    @Bind(R.id.connect_phone_tv)
    TextView connectPhoneTv;
    @Bind(R.id.connect_address_tv)
    TextView connectAddressTv;
    @Bind(R.id.shop_description_et)
    EditText descriptionEt;
    @Bind(R.id.shop_detail_image_civ)
    CircleImageView shopImageCiv;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.report_seller_btn)
    Button reportBtn;

    public static final String SHOP_INFO = "shop_info";
    public static final String CURRENT_COLLECTION_STATE = "collection_state";

    private boolean actDestory = false;
    private ShopInfoRes shopInfo;
    private String shopId;

    @Override
    protected int getContentView() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void prepareDatas() {
        shopInfo = getIntent().getExtras().getParcelable(SHOP_INFO);
        shopId = getIntent().getExtras().getString(ShopShowGoodsAct.SHOP_ID);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(shopInfo.getName());
        CommonUtils.showImageWithGlideInCiv(this, shopImageCiv, shopInfo.getImage());
        shopNameTv.setText(shopInfo.getName());
        saleRangeTv.setText(shopInfo.getMain());
        connectPhoneTv.setText(shopInfo.getPhone_num());
        connectAddressTv.setText(shopInfo.getAddress());
        descriptionEt.setText(shopInfo.getDescription());
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入举报商家页面，并传入shopid
                Bundle bundle = new Bundle();
                bundle.putString(ReportShopAct.SHOP_ID,shopId);
                readyGo(ReportShopAct.class,bundle);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(shopInfo != null){
            if(shopInfo.getIs_collected() == 1){
                menu.findItem(R.id.action_collect).setIcon(R.drawable.xiangqing_btn_shoucang_n);
            }else{
                menu.findItem(R.id.action_collect).setIcon(R.drawable.xiangqing_btn_shoucang_p);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_collect && shopInfo != null){
            if(shopInfo.getIs_collected() == 1){
                cancelShopCollection(item);
            }else{
                addShopCollection(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //店铺收藏添加
    private void addShopCollection(final MenuItem item){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).addCollection(shopId , new Callback<AddShopCollectionRes>() {
            @Override
            public void success(AddShopCollectionRes addShopCollectionRes, Response response) {
                if (addShopCollectionRes != null && !actDestory) {
                    showToast(getString(R.string.collect_success));
                    item.setIcon(getResources().getDrawable(R.drawable.xiangqing_btn_shoucang_n));
                    shopInfo.setIs_collected(1);//使本地数据与服务器同步
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    //取消商铺收藏
    private void cancelShopCollection(final MenuItem item){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).deleteShopCollection(shopId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null && !actDestory) {
                    showToast(getString(R.string.collect_cancel));
                    item.setIcon(getResources().getDrawable(R.drawable.xiangqing_btn_shoucang_p));
                    shopInfo.setIs_collected(0);
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

    @Override
    public void onBackPressed() {
        //返回用户当前对此店铺的收藏状态，使得商铺界面的收藏图标与此同步
        Intent data = new Intent();
        data.putExtra(CURRENT_COLLECTION_STATE,shopInfo.getIs_collected());
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }
}