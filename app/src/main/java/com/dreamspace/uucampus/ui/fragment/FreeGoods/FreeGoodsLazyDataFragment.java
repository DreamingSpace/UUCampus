package com.dreamspace.uucampus.ui.fragment.FreeGoods;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.dreamspace.uucampus.model.api.FreeGoodsInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreeGoodsLazyDataFragment extends FreeGoodsLazyListFragment<FreeGoodsInfo> {
    public static String TAG = "精选";

    public FreeGoodsLazyDataFragment() {
    }

    @Override
    public void onPullUp() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.i("onLoad", "on load complete");
                onPullUpFinished();
            }
        }, 3000);
    }

    @Override
    public void onPullDown() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onPullDownFinished();
            }
        }, 3000);
    }

    @Override
    public void onItemPicked(FreeGoodsInfo mEntity, int position) {
        Log.i("INFO", mEntity.toString());
    }

    @Override
    public void getInitData() {
        Log.i("INFO", "TAG IS :" + TAG);

        loadingInitData();
    }


    public void loadingInitData() {
//        ApiManager.getService(getActivity().getApplicationContext()).get
        List<FreeGoodsInfo> freeGoodsInfos =new ArrayList<FreeGoodsInfo>();
        for(int i=0;i<10;i++){
            FreeGoodsInfo l=new FreeGoodsInfo();
            l.setCourseName("Hello");
            freeGoodsInfos.add(l);
        }
        refreshDate(freeGoodsInfos);
    }

    public void onPageSelected(int position, String name) {
        TAG = name;
    }

}
