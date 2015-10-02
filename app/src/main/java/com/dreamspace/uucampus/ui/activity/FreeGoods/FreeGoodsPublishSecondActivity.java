package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.GoodsClassifyDialog;
import com.dreamspace.uucampus.ui.dialog.VerifyGoodsInfoDialog;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;

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
                            updateGoods();       //把所有信息写入后台
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
                final GoodsClassifyDialog dialog = new GoodsClassifyDialog(
                        FreeGoodsPublishSecondActivity.this,getClassifys());
                dialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setClassifyName();
                        mClassifyEt.setText(dialog.classifyName);
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

    private void updateGoods() {       //更新后台商品信息，启动发布成功页面，并传递该商品所在的页面和列表所在列数
        Bundle bundle = new Bundle();
        readyGo(FreeGoodsPublishSuccessActivity.class, bundle);
        finish();
    }


    //后台获取的商品分类
    public ArrayList<String> getClassifys() {
      String goodsClassify[] = new String[] { "数码电子", "生活用品",
            "书籍杂志", "出行车辆", "衣物饰品", "特色卖场", "其他" };
        ArrayList<String> classifys = new ArrayList<String>();
        Collections.addAll(classifys, goodsClassify);
        return classifys;
    }
}
