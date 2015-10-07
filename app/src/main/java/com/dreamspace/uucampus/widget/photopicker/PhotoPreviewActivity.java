package com.dreamspace.uucampus.widget.photopicker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/29.
 */
public class PhotoPreviewActivity extends AbsActivity {
    @Bind(R.id.preview_photo_iv)
    ImageView previewIv;
    @Bind(R.id.preview_ll)
    LinearLayout previewLl;

    public static final String PREVIEW_PHOTO = "preview_photo";
    int llWidth;
    int llHeigth;
    @Override
    protected int getContentView() {
        return R.layout.activity_photo_preview;
    }

    @Override
    protected void prepareDatas() {
        previewLl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llWidth = previewLl.getWidth();
                llHeigth = previewLl.getHeight();
                Intent data = getIntent();
                String path = data.getStringExtra(PREVIEW_PHOTO);
                showImage(path);
                previewLl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void initViews() {
    }

    private void showImage(String imagePath){
        int showHeight,showWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true获取图片的初始大小
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, opts);
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;

        if(imageHeight > llHeigth && imageWidth > llWidth){
            showHeight = llHeigth - 10;
            showWidth = llWidth - 10;
        }else if(imageHeight > llHeigth && !(imageWidth > llWidth)){
            showHeight = llHeigth - 10;
            showWidth = imageWidth;
        }else if(!(imageHeight > llHeigth) && imageWidth > llWidth){
            showHeight = imageHeight;
            showWidth = llWidth - 10;
        }else{
            showHeight = imageHeight;
            showWidth = imageWidth;
        }
        System.out.println("height:" + showHeight);
        System.out.println("width:" + showWidth);
        File imageFile = new File(imagePath);
        Glide.with(this)
                .load(imageFile)
                .error(R.drawable.default_error)
                .override(showWidth,showHeight)
                .centerCrop()
                .into(previewIv);
    }
}