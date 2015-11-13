package com.dreamspace.uucampus.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lx on 2015/10/24.
 */
public class UploadImage {
    private static UploadManager uploadManager;

    private static UploadManager getUploadManager(){
        if(uploadManager == null){
            uploadManager = new UploadManager();
        }
        return uploadManager;
    }

    /*上传图片到七牛云服务器
    @param file上传图片文件
    @param key 从后台获取的key
    @param token 从后台获取的token
    @param completionHandler 详情见七牛云文档
    @param options 详情见七牛云文档
     */
//    public static void upLoadImage(File file,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
//        getUploadManager().put(file,key,token,completionHandler,options);
//    }

    public static void upLoadImage(String path,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
        byte[] data = compressImage(path);
        getUploadManager().put(data,key,token,completionHandler,options);
    }

//    public static void upLoadImage(byte[] data,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
//        getUploadManager().put(data,key,token,completionHandler,options);
//    }

    //压缩图片体积
    private static byte[] compressImage(String imagePath){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true获取图片的初始大小
        opts.inJustDecodeBounds = true;
        Bitmap image = BitmapFactory.decodeFile(imagePath,opts);
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;

        opts.inJustDecodeBounds = false;

        //控制图片高宽中较低的一个在500像素左右
        if(Math.min(imageHeight,imageWidth) > 500){
            float ratio = Math.max(imageHeight,imageWidth)/500;
            opts.inSampleSize = Math.round(ratio);
        }
        Bitmap finalImage = BitmapFactory.decodeFile(imagePath,opts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //降低画质
        finalImage.compress(Bitmap.CompressFormat.JPEG,70,baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
