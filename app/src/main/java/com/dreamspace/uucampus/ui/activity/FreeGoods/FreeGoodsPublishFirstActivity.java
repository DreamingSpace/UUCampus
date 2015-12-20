package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ImageCaptureManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.SelectPhotoDialog;

import butterknife.Bind;

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
    private SelectPhotoDialog selectPhotoDialog;
    private ImageCaptureManager captureManager;
    public static final int SELECT_AVATER = 1;
    public static final int TAKE_AVATER = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_first;
    }

    @Override
    protected void prepareDatas() {
        captureManager = new ImageCaptureManager(this);
    }

    @Override
    protected void initViews() {
        mPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSelectPhotoDialog();
                selectPhotoDialog.show();
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
        if(selectPhotoDialog != null){
            selectPhotoDialog.dismiss();
        }
        if(requestCode == SELECT_AVATER && resultCode == RESULT_OK){
            Uri uri = data.getData();
            mLocalImagePath = CommonUtils.getRealPathFromURI(this, uri);
            CommonUtils.showImageWithGlide(this, mPhotoIv, mLocalImagePath);
        }else if(requestCode == TAKE_AVATER && resultCode == RESULT_OK){
            if(captureManager.getCurrentPhotoPath() != null){
                mLocalImagePath = captureManager.getCurrentPhotoPath();
                CommonUtils.showImageWithGlide(this,mPhotoIv,mLocalImagePath);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        captureManager.onRestoreInstanceState(savedInstanceState);
    }

    public boolean isPhotoReady() {
        boolean photoReady = false;
        if(mLocalImagePath!=null){
            photoReady = true;
        }
        return photoReady;
    }

    private void initSelectPhotoDialog(){
        if(selectPhotoDialog != null){
            return;
        }
        selectPhotoDialog = new SelectPhotoDialog(this,captureManager,SELECT_AVATER,TAKE_AVATER);
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