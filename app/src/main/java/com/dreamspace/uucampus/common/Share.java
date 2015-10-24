package com.dreamspace.uucampus.common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by Lx on 2015/10/23.
 */
public class Share {
    private UMSocialService mController;
    private Context context;

    public Share(AbsActivity context){
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA);
        //添加微信好友
        UMWXHandler wxHandler = new UMWXHandler(context, ShareData.WechatAppId,ShareData.WechatAppSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context,ShareData.WechatAppId,ShareData.WechatAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //添加QQ好友
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((AbsActivity)context, ShareData.QQAppId,
                ShareData.QQAppSecret);
        qqSsoHandler.addToSocialSDK();
        //添加QQ空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((AbsActivity)context, ShareData.QQAppId,
                ShareData.QQAppSecret);
        qZoneSsoHandler.addToSocialSDK();

        //设置新浪SSO handler
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        this.context = context;
    }

    /*微信好友分享网络图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageUrl 网络图片url
     */
    public void ShareInWechat(String title,String content,String targetUrl,String imageUrl){
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置title
        weixinContent.setTitle(title);
        //设置分享内容跳转URL
        weixinContent.setTargetUrl(targetUrl);
        //设置分享文字
        weixinContent.setShareContent(content);
        //设置分享图片
        weixinContent.setShareImage(new UMImage(context, imageUrl));
        mController.setShareMedia(weixinContent);
    }

    /*微信好友分享本地图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageId 本地图片id
     */
    public void ShareInWechat(String title,String content,String targetUrl,int imageId){
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置title
        weixinContent.setTitle(title);
        //设置分享内容跳转URL
        weixinContent.setTargetUrl(targetUrl);
        //设置分享文字
        weixinContent.setShareContent(content);
        //设置分享图片
        weixinContent.setShareImage(new UMImage(context, imageId));
        mController.setShareMedia(weixinContent);
    }

    /*微信朋友圈分享网络图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageUrl 网络图片url
     */
    public void ShareInWechatCircle(String title,String content,String targetUrl,String imageUrl){
        CircleShareContent circleMedia = new CircleShareContent();
        //设置朋友圈title
        circleMedia.setTitle(title);
        //设置目的跳转url
        circleMedia.setTargetUrl(targetUrl);
        //设置微信朋友圈分享内容
        circleMedia.setShareContent(content);
        //设置分享图片
        circleMedia.setShareImage(new UMImage(context, imageUrl));
        mController.setShareMedia(circleMedia);
    }

    /*微信朋友圈分享本地图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageId 本地图片id
     */
    public void ShareInWechatCircle(String title,String content,String targetUrl,int imageId){
        CircleShareContent circleMedia = new CircleShareContent();
        //设置朋友圈title
        circleMedia.setTitle(title);
        //设置目的跳转url
        circleMedia.setTargetUrl(targetUrl);
        //设置微信朋友圈分享内容
        circleMedia.setShareContent(content);
        //设置分享图片
        circleMedia.setShareImage(new UMImage(context, imageId));
        mController.setShareMedia(circleMedia);
    }

    /*QQ好友分享网络图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageUrl 网络图片url
     */
    public void ShareInQQ(String title,String content,String targetUrl,String imageUrl){
        QQShareContent qqShareContent = new QQShareContent();
        //设置分享title
        qqShareContent.setTitle(title);
        //设置分享内容
        qqShareContent.setShareContent(content);
        //设置分享图片
        qqShareContent.setShareImage(new UMImage(context, imageUrl));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(qqShareContent);
    }

    /*QQ好友分享本地图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageId 本地图片id
     */
    public void ShareInQQ(String title,String content,String targetUrl,int imageId){
        QQShareContent qqShareContent = new QQShareContent();
        //设置分享title
        qqShareContent.setTitle(title);
        //设置分享内容
        qqShareContent.setShareContent(content);
        //设置分享图片
        qqShareContent.setShareImage(new UMImage(context, imageId));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(qqShareContent);
    }

    /*QQ空间分享网络图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageUrl 网络图片url
     */
    public void ShareInQZone(String title,String content,String targetUrl,String imageUrl){
        QZoneShareContent qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent(content);
        //设置点击消息的跳转URL
        qzone.setTargetUrl(targetUrl);
        //设置分享内容的标题
        qzone.setTitle(title);
        //设置分享图片
        qzone.setShareImage(new UMImage(context, imageUrl));
        mController.setShareMedia(qzone);
    }

    /*QQ空间分享本地图片
    @param title 分享标题
    @param content 分享内容
    @param targetUrl 点击分享内容跳转url
    @param imageId 本地图片id
     */
    public void ShareInQZone(String title,String content,String targetUrl,int imageId){
        QZoneShareContent qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent(content);
        //设置点击消息的跳转URL
        qzone.setTargetUrl(targetUrl);
        //设置分享内容的标题
        qzone.setTitle(title);
        //设置分享图片
        qzone.setShareImage(new UMImage(context, imageId));
        mController.setShareMedia(qzone);
    }

    /*sina微博分享网络图片
    @param content 分享内容
    @param imageUrl 网络图片url
     */
    public void ShareInSina(String content,String imageUrl){
        mController.setShareContent(content);
        //设置分享图片
        mController.setShareImage(new UMImage(context, imageUrl));
    }

    /*sina微博分享网络图片
    @param content 分享内容
    @param imageId 本地图片Id
     */
    public void ShareInSina(String content,int imageId){
        mController.setShareContent(content);
        //设置分享图片
        mController.setShareImage(new UMImage(context, imageId));
    }

    public UMSocialService getController(){
        return mController;
    }
}
