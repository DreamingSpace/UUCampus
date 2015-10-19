package com.dreamspace.uucampus.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.widget.WheelView;

import java.util.ArrayList;

/**
 * Created by wufan on 2015/9/22.
 */
public class WheelViewDialog{

    private Button commitButton;
    private Button cancelButton;
    private TextView titleTv;
    private WheelView goodsClassifyWV;
    private Context context;
    private ArrayList<String> classifys;
    private String title;
    private AlertDialog ad;
//    public String goodsClassify[] = new String[] { "数码电子", "生活用品",
//            "书籍杂志", "出行车辆", "衣物饰品", "特色卖场", "其他" };


    public WheelViewDialog(Context context, ArrayList<String> classifys, String title) {
        this.context = context;
        this.classifys=classifys;
        this.title = title;

        ad = new AlertDialog.Builder(context).create();
        ad.show();
        Window window = ad.getWindow();
        window.setContentView(R.layout.dialog_wheelview_choose);
        goodsClassifyWV = (WheelView)window.findViewById(R.id.goods_classify_wheel);
        titleTv = (TextView) window.findViewById(R.id.title_tv);
        titleTv.setText(title);
        goodsClassifyWV.setDefault(1);
        goodsClassifyWV.setData(classifys);

        commitButton = (Button) window.findViewById(R.id.goods_classify_ensure_btn);
        cancelButton = (Button) window.findViewById(R.id.goods_classify_cancel_btn);
    }

    public String getSelected(){
        return goodsClassifyWV.getSelectedText();
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text,
                                  final View.OnClickListener listener) {
        commitButton.setText(text);
        commitButton.setOnClickListener(listener);
    }

    public void dismiss(){
        ad.dismiss();
    }
    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text,
                                  final View.OnClickListener listener) {
        cancelButton.setText(text);
        cancelButton.setOnClickListener(listener);
    }

}
