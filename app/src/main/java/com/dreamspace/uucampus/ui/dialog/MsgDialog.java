package com.dreamspace.uucampus.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;

import java.util.Objects;

/**
 * Created by Lx on 2015/10/26.
 */
public class MsgDialog {
    private AlertDialog dialog;
    private TextView titleTv;
    private TextView contentTv;
    private LinearLayout confirmLl;
    private LinearLayout cancelLl;

    public MsgDialog(Context context){
        dialog = new AlertDialog.Builder(context).create();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_msg,null);
        titleTv = (TextView) dialogView.findViewById(R.id.title_tv);
        contentTv = (TextView) dialogView.findViewById(R.id.content_tv);
        confirmLl = (LinearLayout) dialogView.findViewById(R.id.confirm_ll);
        cancelLl = (LinearLayout) dialogView.findViewById(R.id.cancel_ll);
        dialog.setView(dialogView);
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void setContent(String content){
        contentTv.setText(content);
    }

    public void setTitle(String title){
        titleTv.setText(title);
    }

    public void setPositiveButton(View.OnClickListener listener){
        confirmLl.setOnClickListener(listener);
    }

    public void setNegativeButton(View.OnClickListener listener){
        cancelLl.setOnClickListener(listener);
    }
}
