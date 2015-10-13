package com.dreamspace.uucampus.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dreamspace.uucampus.R;


/**
 * Created by wufan on 2015/8/9.
 */

/**
 * 自定义对话框:针对闲置商品发布的信息确认：标题，各种信息，确认和取消
 */
public class VerifyGoodsInfoDialog {

    private AlertDialog mAlertDialog;
    private TextView mTitleTv;
    private TextView mGoodsPriceTv;
    private TextView mGoodsClassifyTv;
    private TextView mGoodsNameTv;
    private TextView mGoodsDetailTv;
    private Button mCommitBtn;
    private Button mCancelBtn;

    public VerifyGoodsInfoDialog(Context context) {

        mAlertDialog=new AlertDialog.Builder(context).create();
        mAlertDialog.show();
        Window window =mAlertDialog.getWindow();
        window.setContentView(R.layout.dialog_verify_goods_info);
        mTitleTv= (TextView) window.findViewById(R.id.free_goods_publish_info_title_tv);
        mGoodsNameTv = (TextView) window.findViewById(R.id.free_goods_publish_info_name_tv);
        mGoodsPriceTv = (TextView) window.findViewById(R.id.free_goods_publish_info_price_tv);
        mGoodsClassifyTv = (TextView) window.findViewById(R.id.free_goods_publish_info_classify_tv);
        mGoodsDetailTv = (TextView) window.findViewById(R.id.free_goods_publish_info_detail_tv);
        mCommitBtn= (Button) window.findViewById(R.id.publish_free_goods_commit_button);
        mCancelBtn= (Button) window.findViewById(R.id.publish_free_goods_cancel_button);
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text,
                                  final View.OnClickListener listener) {
        mCommitBtn.setText(text);
        mCommitBtn.setOnClickListener(listener);
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text,
                                  final View.OnClickListener listener) {
        mCancelBtn.setText(text);
        mCancelBtn.setOnClickListener(listener);
    }

    /**
     * 关闭
     */
    public void dismiss() {
        mAlertDialog.dismiss();
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void setGoodsClassify(String classify) {
        mGoodsClassifyTv.setText(classify);
    }

    public void setGoodsName(String goodsName) {
        mGoodsNameTv.setText(goodsName);
    }

    public void setGoodsPrice(String goodsPrice) {
        mGoodsPriceTv.setText(goodsPrice);
    }

    public void setGoodsDetail(String goodsDetail){
        mGoodsDetailTv.setText(goodsDetail);
    }
}
