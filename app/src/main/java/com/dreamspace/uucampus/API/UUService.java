package com.dreamspace.uucampus.API;

import com.dreamspace.uucampus.model.person.CheckUpdateRes;
import com.dreamspace.uucampus.model.person.FeedbackContentReq;
import com.dreamspace.uucampus.model.person.RegistertokenReq;
import com.dreamspace.uucampus.model.person.RegistertokenRes;
import com.dreamspace.uucampus.model.person.SendVerifyReq;
import com.dreamspace.uucampus.model.person.UpdateUserMessageReq;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by zsh on 2015/9/29.
 */
public interface UUService {
    //用户创建
    //@POST("/user/")
    //void createAccessToken(@Body LoginReq request, Callback<LoginRes> cb);

    //给指定手机发送短信验证码
    @POST("/auth/code")
    void sendVerifyCode(@Body SendVerifyReq req, Callback<Response> cb);
    //用户创建
    @POST("/user")
    void createRegisterToken(@Body RegistertokenReq req, Callback<RegistertokenRes> cb);
    //用户信息更新
    @PUT("/user/")
    void updateUserMessage(@Body UpdateUserMessageReq req, Callback<Response> cb);
    //用户信息查看（自己）
    @PUT("/user/")
    void getUserMessage(Callback<UpdateUserMessageReq> cb);

    //意见提交
    @PUT("/suggestion/")
    void commitFeedback(@Body FeedbackContentReq req, Callback<Response> cb);
    //检查更新
    @GET("/check_update/{version}")
    void checkUpdate(@Path("version")String version,Callback<CheckUpdateRes> cb);

}
