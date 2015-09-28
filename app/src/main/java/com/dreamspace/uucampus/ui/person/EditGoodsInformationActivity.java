package com.dreamspace.uucampus.ui.person;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.io.File;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/24.
 */
public class EditGoodsInformationActivity extends AbsActivity {
    private String[] items = new String[]{"","","","","",""};
    @Bind(R.id.edit1)
    EditText edit1;
    @Bind(R.id.edit2)
    EditText edit2;
    @Bind(R.id.edit3)
    TextView edit3;
    @Bind(R.id.edit4)
    EditText edit4;
    @Bind(R.id.commit)
    Button commit;
    @Override
    protected int getContentView() {
        return R.layout.person_activity_edit_goods;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择商品类型")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                edit3.setText(items[0]);
                                dialog.dismiss();
                                break;
                            case 1:
                                edit3.setText(items[1]);
                                dialog.dismiss();
                                break;

                            case 2:
                                edit3.setText(items[2]);
                                dialog.dismiss();
                                break;
                            case 3:
                                edit3.setText(items[3]);
                                dialog.dismiss();
                                break;

                            case 4:
                                edit3.setText(items[4]);
                                dialog.dismiss();
                                break;
                            case 5:
                                edit3.setText(items[5]);
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
