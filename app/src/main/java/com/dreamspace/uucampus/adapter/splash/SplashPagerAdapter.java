package com.dreamspace.uucampus.adapter.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.MainActivity;

import java.util.ArrayList;

/**
 * Created by Lx on 2015/11/28.
 */
public class SplashPagerAdapter extends PagerAdapter{
    private ArrayList<View> views;
    private Activity context;

    public SplashPagerAdapter(Activity context,ArrayList<View> views){
        this.context = context;
        this.views = views;
    }
    @Override
    public int getCount() {
        return views == null?0:views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position),0);
        ImageView bgIv = (ImageView) views.get(position).findViewById(R.id.bg_iv);
        switch (position){
            case 0:
                bgIv.setBackground(context.getResources().getDrawable(R.drawable.splash_bg_1));
                break;

            case 1:
                bgIv.setBackground(context.getResources().getDrawable(R.drawable.splash_bg_2));
                break;

            case 2:
                bgIv.setBackground(context.getResources().getDrawable(R.drawable.splash_bg_3));
                break;

            case 3:
                bgIv.setBackground(context.getResources().getDrawable(R.drawable.splash_bg_4));
                break;
        }

        if(position == views.size() - 1){
            Button startBtn = (Button) views.get(position).findViewById(R.id.start_btn);
            startBtn.setVisibility(View.VISIBLE);
            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    context.finish();
                }
            });
        }

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(views != null){
            container.removeView(views.get(position));
        }else{
            super.destroyItem(container,position,object);
        }
    }
}
