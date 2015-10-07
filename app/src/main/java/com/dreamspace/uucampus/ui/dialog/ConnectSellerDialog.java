package com.dreamspace.uucampus.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dreamspace.uucampus.R;

/**
 * 此dialog用来展示up dialog风格的选项：电话，短信，取消
 */
public class ConnectSellerDialog extends Dialog implements OnClickListener {
    private ImageView msgIv;
    private ImageView phoneIv;
    private String phoneNum;
    private String goodsName;
    private Context context;

    public ConnectSellerDialog(Context context, int theme, String goodsName, String phoneNum) {
        super(context, theme);
        this.context = context;
        this.goodsName = goodsName;
        this.phoneNum = phoneNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_consult);
        initView();
        setListener();
    }

    private void initView() {
        msgIv = (ImageView) findViewById(R.id.connect_dialog_msg);
        phoneIv = (ImageView) findViewById(R.id.connect_dialog_phone);
    }

    private void setListener() {
        msgIv.setOnClickListener(this);
        phoneIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_dialog_msg:
                String sms_content = "您好，我在悠悠校园上看到了您发布的 " + goodsName;
                Uri uri = Uri.parse("smsto:" + phoneNum);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", sms_content);
                context.startActivity(it);
                break;
            case R.id.connect_dialog_phone:
                Intent phoneIntent = new Intent("android.intent.action.DIAL",
                        Uri.parse("tel:" + phoneNum));
                context.startActivity(phoneIntent);
                break;
//            case R.id.connect_dialog_quit:
//                dismiss();
//                break;
        }
    }
}
