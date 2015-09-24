package com.dreamspace.uucampus.ui.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/19.
 */
public class ApplyStoreActivity extends AbsActivity {
    private String[] items = new String[] {"","","","","",""};
    private Bitmap photo;
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private Context mContext;
    private String[] item = new String[] { "从手机相册选择", "拍照" };
    @Bind(R.id.store_image)
    ImageView imageView;
    @Bind(R.id.market_name)
    EditText edit1;
    @Bind(R.id.storekeeper_name)
    EditText edit2;
    @Bind(R.id.store_type)
    EditText edit3;
    @Bind(R.id.phonenum)
    EditText edit4;
    @Bind(R.id.address)
    EditText edit5;
    @Bind(R.id.store_introduction)
    EditText edit6;
    @Bind(R.id.commit_button)
    Button button;

    @Override
    protected int getContentView() {
        return R.layout.person_activity_apply_store;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });
        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                .setTitle("选择店铺类型")
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
    private void changeImage() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //从相册获取图片
                                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                                intent1.putExtra("photo", photo);
                                startActivityForResult(intent1, PHOTO_ZOOM);
                                break;
                            case 1:
                                //从拍照获取图片
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "temp.jpg")));
                                intent2.putExtra("photo",photo);
                                startActivityForResult(intent2, PHOTO_GRAPH);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTO_GRAPH) {
            // 设置文件保存路径
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html

                imageView.setImageBitmap(photo); //把图片显示在ImageView控件上
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 360);
        intent.putExtra("outputY", 360);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }
}
