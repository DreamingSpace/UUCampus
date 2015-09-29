package com.dreamspace.uucampus.api;


import com.dreamspace.uucampus.model.LoginReq;
import com.dreamspace.uucampus.model.LoginRes;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public interface UUService {
    //用户创建
    @POST("/user")
    void createAccessToken(@Body LoginReq request, Callback<LoginRes> cb);



}
