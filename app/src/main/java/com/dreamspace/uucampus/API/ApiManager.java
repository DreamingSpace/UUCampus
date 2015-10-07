package com.dreamspace.uucampus.API;

import android.content.Context;

import com.dreamspace.uucampus.common.utils.PreferenceUtils;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by zsh on 2015/9/29.
 */
public final class ApiManager {
    public static String BASE_URL = "http://api2.hloli.me:9777/v1.0";
    private static UUService mService;
    static volatile RestAdapter restAdapter = null;

    private ApiManager() {
    }

    public static RestAdapter getAdapter(final Context mContext) {
        if (restAdapter == null) {
            synchronized (ApiManager.class) {
                if (restAdapter == null) {
                    RequestInterceptor requestInterceptor = new RequestInterceptor() {
                        @Override
                        public void intercept(RequestInterceptor.RequestFacade request) {
                            request.addHeader(PreferenceUtils.Key.ACCESS, PreferenceUtils.getString(mContext, "access_token"));
                        }
                    };
                    restAdapter = new RestAdapter.Builder().setEndpoint(ApiManager.BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL).setRequestInterceptor(requestInterceptor)
                            .build();
                }
            }
        }
        return restAdapter;
    }

    public static void initRegionApi(Context mContext) {
        if (mService == null) {
            synchronized (ApiManager.class) {
                if (mService == null) {
                    mService = getAdapter(mContext).create(UUService.class);
                }
            }
        }
    }

    public static UUService getService(Context mContext) {
        initRegionApi(mContext);
        return mService;
    }

    public static void clear() {
        mService = null;
        restAdapter = null;
    }
}
