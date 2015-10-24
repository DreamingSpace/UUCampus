package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.api.CreateIdleReq;
import com.dreamspace.uucampus.model.api.CreateIdleRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.VerifyGoodsInfoDialog;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;

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
    @Bind(R.id.free_goods_publish_second_classify_et)
    EditText mClassifyEt;

    private String mGoodsName;
    private String mGoodsPrice;
    private String mGoodsClassify;
    private String mGoodsDetail;
    private String mGoodsInfoWrong;   //输出填写错误信息
    private boolean isInfoCorrect;

    private ProgressDialog pd;
    private CreateIdleReq req=new CreateIdleReq();
    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_second;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req.setName("吉他");
                req.setDescription("高音响低音量，手感不错");
                mGoodsName = "：吉他";
                mGoodsClassify = "：其他";
                mGoodsPrice = "：150元";
                mGoodsDetail = "：高音响低音量，手感不错";
                if (isGoodsInfoCorrect()) {    //确认填写信息无误
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
                            pd = ProgressDialog.show(FreeGoodsPublishSecondActivity.this, "", "正在创建", true, false);
                            updateGoods(req);       //把所有信息写入后台
                        }
                    });
                } else {
                    Toast.makeText(FreeGoodsPublishSecondActivity.this, mGoodsInfoWrong, Toast.LENGTH_SHORT).show();  //错误提示
                }

            }
        });

        mClassifyEt.setOnClickListener(new View.OnClickListener() {
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
                        mClassifyEt.setText(dialog.getSelected());
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public boolean isGoodsInfoCorrect() {
        mGoodsInfoWrong = "";   //校验填写信息
        isInfoCorrect = true;
        return isInfoCorrect;
    }

    private void updateGoods(CreateIdleReq req) {       //更新后台商品信息，启动发布成功页面，并传递该商品所在的页面和列表所在列数
        if (NetUtils.isNetworkConnected(FreeGoodsPublishSecondActivity.this)) {
            ApiManager.getService(getApplicationContext()).createIdle(req, new Callback<CreateIdleRes>() {
                @Override
                public void success(CreateIdleRes createIdleRes, Response response) {
                    String idle_id = createIdleRes.getIdle_id();
                    TLog.e("FreeGoods create Idle:",idle_id);
                    Bundle bundle = new Bundle();
                    readyGo(FreeGoodsPublishSuccessActivity.class, bundle);
                    finish();
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
        String goodsClassify[] = new String[]{"数码电子", "生活用品",
                "书籍杂志", "出行车辆", "衣物饰品", "特色卖场", "其他"};
        ArrayList<String> classifys = new ArrayList<String>();
        Collections.addAll(classifys, goodsClassify);
        return classifys;
    }
}
