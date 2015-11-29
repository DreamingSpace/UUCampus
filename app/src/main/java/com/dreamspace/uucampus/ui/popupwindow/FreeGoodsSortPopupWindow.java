package com.dreamspace.uucampus.ui.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.dreamspace.uucampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/11/7.
 */
public class FreeGoodsSortPopupWindow {
    private Context mContext;
    private PopupWindow popupWindow;
    private List<LinearLayout> sortItemList;
    private LinearLayout popularLl;
    private LinearLayout recentPublishLl;
    private LinearLayout cheapestLl;
    private View shadowView;

    public FreeGoodsSortPopupWindow(Context context,View shadowView){
        mContext = context;
        this.shadowView = shadowView;
        initPopupWindow();
    }

    private void initPopupWindow(){
        sortItemList = new ArrayList<LinearLayout>();
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popupwindwo_free_goods_sort_view, null);
        popularLl = (LinearLayout) popupView.findViewById(R.id.free_goods_popup_window_most_popular_ll);
        recentPublishLl = (LinearLayout) popupView.findViewById(R.id.free_goods_popup_window_recent_publish_ll);
        cheapestLl = (LinearLayout) popupView.findViewById(R.id.free_goods_popup_window_cheapest_ll);

        sortItemList.add(popularLl);
        sortItemList.add(recentPublishLl);
        sortItemList.add(cheapestLl);

        popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim);
        popupItemSetSelect(1);

        shadowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showAsDropDown(View view){
        popupWindow.showAsDropDown(view);
        shadowView.setVisibility(View.VISIBLE);
    }

    public void dismiss(){
        popupWindow.dismiss();
        shadowView.setVisibility(View.INVISIBLE);
    }

    //选择当前选择了第几个条目
    public void popupItemSetSelect(int position){
        for(int i = 0;i < 3;i++){
            if(i == position){
                sortItemList.get(i).setSelected(true);
            }else{
                sortItemList.get(i).setSelected(false);
            }
        }
        dismiss();
    }

    public void setMostPopularOnClickListener(View.OnClickListener onClickListener){
        if(onClickListener != null){
            popularLl.setOnClickListener(onClickListener);
        }
    }

    public void setRecentPublishOnClickListener(View.OnClickListener onClickListener){
        if(onClickListener != null){
            recentPublishLl.setOnClickListener(onClickListener);
        }
    }

    public void setCheapestOnClickListener(View.OnClickListener onClickListener){
        if(onClickListener != null){
            cheapestLl.setOnClickListener(onClickListener);
        }
    }

    //返回popupwindow是否显示
    public boolean isShowing(){
        if(popupWindow != null){
            return popupWindow.isShowing();
        }
        return false;
    }
}
