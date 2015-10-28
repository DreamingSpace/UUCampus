package com.dreamspace.uucampus.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lx on 2015/10/25.
 */
public class WheelDialog{
    private AlertDialog dialog;
    private List<String> data;
    private WheelView wheelView;
    private LinearLayout confirmLl;
    private LinearLayout cancelLl;
    private TextView titleTv;

    public WheelDialog(Context context){
        dialog = new AlertDialog.Builder(context).create();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_wheel,null);
        titleTv = (TextView) dialogView.findViewById(R.id.dialog_title_tv);
        wheelView = (WheelView) dialogView.findViewById(R.id.dialog_wheelview);
        confirmLl = (LinearLayout) dialogView.findViewById(R.id.confirm_ll);
        cancelLl = (LinearLayout) dialogView.findViewById(R.id.cancel_ll);
        dialog.setView(dialogView);
    }

    public void setTitle(String title){
        titleTv.setText(title);
    }

    public void setData(ArrayList<String> data){
        this.data = data;
        wheelView.setDefault(0);
        wheelView.setData(data);
    }

    public String getSelectedText(){
        return wheelView.getSelectedText();
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void setPositiveButton(View.OnClickListener listener){
        confirmLl.setOnClickListener(listener);
    }

    public void setNegativeButton(View.OnClickListener listener){
        cancelLl.setOnClickListener(listener);
    }
}
