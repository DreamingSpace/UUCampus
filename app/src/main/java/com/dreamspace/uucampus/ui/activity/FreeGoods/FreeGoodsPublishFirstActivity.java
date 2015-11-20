package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

import butterknife.Bind;
import me.iwf.photopicker.PhotoPickerActivity;

/**
 * Created by wufan on 2015/9/22.
 */
public class FreeGoodsPublishFirstActivity extends AbsActivity {

    @Bind(R.id.free_goods_publish_first_up_photo)
    ImageView mPhotoIv;
    @Bind(R.id.free_goods_publish_first_next_button)
    Button mNextBtn;

    public static final String TOAST_TEXT = "请先点击加号图标选择商品照片";
    private String mLocalImagePath = null;

    public static int PHOTO_REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_first;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        mPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(SelectPhotoActivity.class,PHOTO_REQUEST_CODE);
            }
        });
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhotoReady()) {
                    //把图片资源路径传到填写信息详细界面，再一起存放后台
                    Bundle bundle = new Bundle();
                    bundle.putString(FreeGoodsPublishSecondActivity.EXTRA_LOCAL_IMAGE_PATH, mLocalImagePath);
                    readyGo(FreeGoodsPublishSecondActivity.class, bundle);
                } else {
                    Toast.makeText(FreeGoodsPublishFirstActivity.this, TOAST_TEXT,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
               mLocalImagePath = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
                ArrayList<String> photo = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Glide.with(FreeGoodsPublishFirstActivity.this)
                        .load(mLocalImagePath)
                        .centerCrop()
                        .into(mPhotoIv);
            }
        }
    }

    public boolean isPhotoReady() {
        boolean photoReady = false;
        if(mLocalImagePath!=null){
            photoReady = true;
        }
        return photoReady;
    }

    BroadcastReceiver broadcastReceiver =new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();             //接收销毁该activity时销毁此activity
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //在当前activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("destroyActivity");
        this.registerReceiver(this.broadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.broadcastReceiver);
    }
}