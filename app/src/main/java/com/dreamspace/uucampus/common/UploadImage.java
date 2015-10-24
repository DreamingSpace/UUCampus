package com.dreamspace.uucampus.common;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;

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
    public static void upLoadImage(File file,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
        getUploadManager().put(file,key,token,completionHandler,options);
    }

    public static void upLoadImage(String path,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
        getUploadManager().put(path,key,token,completionHandler,options);
    }

    public static void upLoadImage(byte[] data,String key,String token,UpCompletionHandler completionHandler,UploadOptions options){
        getUploadManager().put(data,key,token,completionHandler,options);
    }
}
