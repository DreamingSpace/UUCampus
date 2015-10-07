package com.dreamspace.uucampus.ui.person;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dreamspace.uucampus.API.ApiManager;
import com.dreamspace.uucampus.API.UUService;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.person.ErrorRes;
import com.dreamspace.uucampus.model.person.UpdateUserMessageReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zsh on 2015/9/23.
 */
public class PersonMessageActivity extends AbsActivity {
    private Bitmap photo;
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private Context mContext;
    private String[] items = new String[] { "从手机相册选择", "拍照" };
    private String[] item1 = new String[] {"男", "女"};
    @Bind(R.id.portrait)
    ImageView portrait;
    @Bind(R.id.nick_name)
    EditText nickname;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.phonenum_edit)
    EditText phonenum;
    @Bind(R.id.school)
    Spinner school;
    @Bind(R.id.time)
    Spinner time;
    @Bind(R.id.relative_weibo)
    RelativeLayout weibo;
    @Bind(R.id.relative_weichat)
    RelativeLayout weichat;

    private UUService mService;
    @Override
    protected int getContentView() {
        return R.layout.person_activity_message;
    }

    @Override
    protected void prepareDatas() {
        mContext = this;
        mService = ApiManager.getService(mContext);
        portrait.setOnClickListener(new MyOnClickListener(0));
        sex.setOnClickListener(new MyOnClickListener(1));
        //school.setOnClickListener(new MyOnClickListener(2));

        String[] school_name = new String[]{"--请选择--","东南大学","南京大学","南京航天航空大学","南京理工大学","南京邮电大学",
                "河海大学","中国药科大学","南京信息工程大学","南京农业大学","南京林业大学","南京师范大学"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(PersonMessageActivity.this,android.R.layout.simple_spinner_item,
                school_name);
        school.setAdapter(arrayAdapter1);
        time.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        //time.setOnClickListener(new MyOnClickListener(3));
        Calendar c=Calendar.getInstance();
        int currentYear=c.get(Calendar.YEAR);
        String[] time_list=new String[]{"--请选择--",Integer.toString(currentYear),Integer.toString(currentYear-1),Integer.toString(currentYear-2),
                Integer.toString(currentYear-3),Integer.toString(currentYear-4),Integer.toString(currentYear-5),
                Integer.toString(currentYear-6), Integer.toString(currentYear-7)};

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(PersonMessageActivity.this,android.R.layout.simple_spinner_item,
                time_list);
        time.setAdapter(arrayAdapter2);
        time.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        weibo.setOnClickListener(new MyOnClickListener(4));
        weichat.setOnClickListener(new MyOnClickListener(5));

    }

    @Override
    protected void initViews() {
        getUserMessage();

    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            if (index == 0) {
                showDialog();
            }
            if (index == 1) {
                showDialog1();
            }
            if (index == 2) {


            }
            if (index == 3) {

            }
            if (index == 4) {

            }
            if (index == 5) {

            }
        }
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
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {
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
                                        .getExternalStorageDirectory(),"temp.jpg")));
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

                portrait.setImageBitmap(photo); //把图片显示在ImageView控件上
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
    private void showDialog1() {
        new AlertDialog.Builder(this).setItems(item1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        sex.setText("男");
                        break;
                    case 1:
                        sex.setText("女");
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
    void getUserMessage(){
        if (NetUtils.isNetworkConnected(mContext)){
            mService.getUserMessage(new Callback<UpdateUserMessageReq>() {
                @Override
                public void success(UpdateUserMessageReq updateUserMessageReq, Response response) {
                    if (response.getStatus() == 200){
                        updateUserMessageReq.setImage("image");
                        updateUserMessageReq.setBirthday("birthday");
                        updateUserMessageReq.setSchool("school");


                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    Log.i("INFO", res.toString());
                }
            });
        }
    }
}
