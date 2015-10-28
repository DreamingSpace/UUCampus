package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/22.
 */
public class FreeGoodsPublishFirstActivity extends AbsActivity {

    @Bind(R.id.free_goods_publish_first_up_photo)
    ImageView mPhotoIv;
    @Bind(R.id.free_goods_publish_first_next_button)
    Button mNextBtn;

    public static final int LOAD_USER_ICON = 0;
    public static final String TOAST_TEXT = "请先点击加号图标选择商品照片";
    private String imageUrl;
    private boolean bPhotoReady;
    private String mLocalImagePath = null;

    public static int PHOTO_REQUEST_CODE = 1;
    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_publish_first;
    }

    @Override
    protected void prepareDatas() {
        bPhotoReady = false;
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
                bPhotoReady = true;  //测试专用
                if (bPhotoReady) {
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
                String photoPath = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
//                ArrayList<String> photo = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Glide.with(FreeGoodsPublishFirstActivity.this)
                        .load(photoPath)
                        .centerCrop()
                        .into(mPhotoIv);
            }
        }
    }

}