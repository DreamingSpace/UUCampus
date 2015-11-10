package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.UploadImage;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.api.CreateIdleReq;
import com.dreamspace.uucampus.model.api.CreateIdleRes;
import com.dreamspace.uucampus.model.api.QnRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.VerifyGoodsInfoDialog;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wufan on 2015/9/22.
 */
public class FreeGoodsPublishSecondActivity extends AbsActivity {
    public static final String EXTRA_LOCAL_IMAGE_PATH = "goods_photo";

    @Bind(R.id.free_goods_publish_second_next_button)
    Button mNextBtn;
    @Bind(R.id.free_goods_publish_second_name_et)
    EditText mGoodsNameEt;
    @Bind(R.id.free_goods_publish_second_price_et)
    EditText mGoodsPriceEt;
    @Bind(R.id.free_goods_publish_second_detail_et)
    EditText mGoodsDetailEt;
    @Bind(R.id.free_goods_publish_second_classify_et)
    EditText mGoodsClassifyEt;

    private String mImagePath;
    private String mGoodsName;
    private String mGoodsPrice;
    private String mGoodsClassify;
    private String mGoodsDetail;
    private String mGoodsInfoWrong;   //输出填写错误信息
    private boolean isInfoCorrect;

//    private ProgressDialog pd;
    private com.dreamspace.uucampus.ui.dialog.ProgressDialog pd;
    private CreateIdleReq req = new CreateIdleReq();

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_second;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        mImagePath = bundle.getString(EXTRA_LOCAL_IMAGE_PATH);
    }

    @Override
    protected void initViews() {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGoodsInfoCorrect()) {    //确认填写信息无误
                    req.setName(mGoodsNameEt.getText().toString());
                    req.setCategory(mGoodsClassifyEt.getText().toString());
                    req.setPrice(Float.parseFloat(mGoodsPriceEt.getText().toString()));
                    req.setDescription(mGoodsDetailEt.getText().toString());
                    mGoodsName = "："+mGoodsNameEt.getText().toString();
                    mGoodsClassify = "："+mGoodsClassifyEt.getText().toString();
                    mGoodsPrice = "："+mGoodsPriceEt.getText().toString();
                    mGoodsDetail = "："+mGoodsDetailEt.getText().toString();

                    final VerifyGoodsInfoDialog dialog = new VerifyGoodsInfoDialog(FreeGoodsPublishSecondActivity.this);
                    dialog.setGoodsName(mGoodsName);
                    dialog.setGoodsPrice(mGoodsPrice);
                    dialog.setGoodsClassify(mGoodsClassify);
                    dialog.setGoodsDetail(mGoodsDetail);
                    dialog.setTitle("确认商品信息");
                    dialog.setPositiveButton("返回修改", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setNegativeButton("确认提交", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            upLoadImage(mImagePath);   //先上传图片
                                  //把所有信息写入后台
                        }
                    });
                } else {
                    Toast.makeText(FreeGoodsPublishSecondActivity.this, mGoodsInfoWrong, Toast.LENGTH_SHORT).show();  //错误提示
                }

            }
        });

        mGoodsClassifyEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WheelViewDialog dialog = new WheelViewDialog(
                        FreeGoodsPublishSecondActivity.this, getClassifys(), getString(R.string.goods_classify));
                dialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGoodsClassifyEt.setText(dialog.getSelected());
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    public boolean isGoodsInfoCorrect() {
        isInfoCorrect = false;
        if(mGoodsNameEt.length()==0){
            showToast("请填写商品名称");
        }else if(mGoodsPriceEt.length()==0) {
            showToast("请填写商品价格");
        }else if(mGoodsClassifyEt.length()==0){
            showToast("请选择商品分类");
        }else if(mGoodsDetailEt.getText().toString().length()<10){
            showToast("详情描述不少于10个字");
        }else {
            isInfoCorrect=true;
        }
        return isInfoCorrect;
    }

    private void updateGoods(CreateIdleReq req) {       //更新后台商品信息，启动发布成功页面，并传递该商品所在的页面和列表所在列数
        if (NetUtils.isNetworkConnected(FreeGoodsPublishSecondActivity.this)) {
            ApiManager.getService(getApplicationContext()).createIdle(req, new Callback<CreateIdleRes>() {
                @Override
                public void success(CreateIdleRes createIdleRes, Response response) {
                    String idle_id = createIdleRes.getIdle_id();
                    TLog.e("FreeGoods create Idle:", idle_id);
                    Bundle bundle = new Bundle();
                    bundle.putString(FreeGoodsPublishSuccessActivity.EXTRA_IDLE_ID, idle_id);
                    finish();
                    pd.dismiss();
                    readyGo(FreeGoodsPublishSuccessActivity.class, bundle);
                }

                @Override
                public void failure(RetrofitError error) {
                    pd.dismiss();
                    showInnerError(error);
                }
            });
        } else {
            pd.dismiss();
            showNetWorkError();
        }
    }

    //后台获取的商品分类
    public ArrayList<String> getClassifys() {
        ArrayList<String> classifys = new ArrayList<String>();
        Collections.addAll(classifys, ShareData.freeGoodsCategorys);
        return classifys;
    }

    //上传图片
    private void upLoadImage(final String path){
//        pd = ProgressDialog.show(FreeGoodsPublishSecondActivity.this, "", "正在创建", true, false);
        pd = new com.dreamspace.uucampus.ui.dialog.ProgressDialog(this);
        pd.setContent("正在创建");
        pd.show();
        if (NetUtils.isNetworkConnected(FreeGoodsPublishSecondActivity.this)) {
            ApiManager.getService(this).createQiNiuToken(new Callback<QnRes>() {
                @Override
                public void success(QnRes qnRes, Response response) {
                    UploadImage.upLoadImage(path, qnRes.getKey(), qnRes.getToken(), new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                req.setImage(key);
                                updateGoods(req);
                            } else if (info.isServerError()) {
                                pd.dismiss();
                                showToast("服务暂时不可用，请稍后重试");
                            }
                        }
                    }, null);
                }

                @Override
                public void failure(RetrofitError error) {
                    pd.dismiss();
                    showInnerError(error);
                }
            });
        }else{
            pd.dismiss();
            showNetWorkError();
        }
    }
}
