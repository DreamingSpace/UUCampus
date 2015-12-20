package com.dreamspace.uucampus.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.DensityUtils;

/**
 * Created by Lx on 2015/10/24.
 */
public class ProgressDialog {
    private AlertDialog dialog;
    private TextView contentTv;

    public ProgressDialog(Context context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_view, null);
        contentTv = (TextView) dialogView.findViewById(R.id.dialog_title_tv);
        dialog = new AlertDialog.Builder(context).setView(dialogView).create();

        dialog.getWindow().getAttributes().alpha = 0.5f;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_dialog_bg);
        dialog.getWindow().setLayout(DensityUtils.dip2px(context, 260),WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void show(){
        dialog.show();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void setContent(String content){
        contentTv.setText(content);
    }
}
