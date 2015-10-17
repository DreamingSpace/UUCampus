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
 * Created by Lx on 2015/10/17.
 */
public class GoodsSortPopupWindow {
    private Context mContext;
    private PopupWindow popupWindow;
    private List<LinearLayout> sortItemList;
    private LinearLayout popularLl;
    private LinearLayout highestAppraiesLl;
    private LinearLayout recentPublishLl;
    private LinearLayout cheapestLl;
    private View shadowView;

    public GoodsSortPopupWindow(Context context,View shadowView){
        mContext = context;
        this.shadowView = shadowView;
        initPopupWindow();
    }

    private void initPopupWindow(){
        sortItemList = new ArrayList<LinearLayout>();
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_goods_sort_view, null);
        popularLl = (LinearLayout) popupView.findViewById(R.id.most_popular_ll);
        highestAppraiesLl = (LinearLayout) popupView.findViewById(R.id.highest_appraise_ll);
        recentPublishLl = (LinearLayout) popupView.findViewById(R.id.recent_publish_ll);
        cheapestLl = (LinearLayout) popupView.findViewById(R.id.cheapest_ll);

        sortItemList.add(popularLl);
        sortItemList.add(highestAppraiesLl);
        sortItemList.add(recentPublishLl);
        sortItemList.add(cheapestLl);

        popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim);
        popupItemSetSelect(0);

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
        for(int i = 0;i < 4;i++){
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

    public void setHighestAppraiesOnClickListener(View.OnClickListener onClickListener){
        if(onClickListener !=null){
            highestAppraiesLl.setOnClickListener(onClickListener);
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
