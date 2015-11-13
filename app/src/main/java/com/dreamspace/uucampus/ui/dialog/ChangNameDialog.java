package com.dreamspace.uucampus.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;

/**
 * Created by Lx on 2015/10/25.
 */
public class ChangNameDialog {
    private AlertDialog dialog;
    private EditText nameEt;
    private ImageView clearNameIv;
    private LinearLayout confirmLl;
    private LinearLayout cancelLl;

    public ChangNameDialog(Context context){
        dialog = new AlertDialog.Builder(context).create();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_name,null);
        nameEt = (EditText) dialogView.findViewById(R.id.name_et);
        clearNameIv = (ImageView) dialogView.findViewById(R.id.clear_text_iv);
        confirmLl = (LinearLayout) dialogView.findViewById(R.id.confirm_ll);
        cancelLl = (LinearLayout) dialogView.findViewById(R.id.cancel_ll);
        dialog.setView(dialogView);

        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    clearNameIv.setVisibility(View.INVISIBLE);
                } else {
                    clearNameIv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearNameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEt.setText("");
            }
        });
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void setPositiveButton(View.OnClickListener listener){
        confirmLl.setOnClickListener(listener);
    }

    public void setNegativeButton(View.OnClickListener listener){
        cancelLl.setOnClickListener(listener);
    }

    public String getText(){
        return nameEt.getText().toString();
    }

    public void setText(String text){
        nameEt.setText(text);
        nameEt.setSelection(text.length());
    }
}
