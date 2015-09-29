package com.dreamspace.uucampus.API;

import retrofit.RestAdapter;

/**
 * Created by zsh on 2015/9/29.
 */
public final class ApiManager {
    public static String BASE_URL = "http://api2.hloli.me:9777/v1.0";
    private static SupermanService mService;
    static volatile RestAdapter restAdapter = null;

    private ApiManager() {

    }

    public static RestAdapter getAdapter() {
        if (restAdapter == null) {
            synchronized (ApiManager.class) {
                if (restAdapter == null) {
                    restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL)
                            .build();
                }
            }
        }
        return restAdapter;
    }
    public static void initRegionApi() {
        if (mService == null) {
            synchronized (ApiManager.class) {
                if (mService == null) {
                    mService = getAdapter().create(SupermanService.class);
                }
            }
        }
    }
    public static SupermanService getService() {
        initRegionApi();
        return mService;
    }
}
