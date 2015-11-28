package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.splash.SplashPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lx on 2015/11/28.
 */
public class AppFirstInActivity extends AppCompatActivity{
    private ViewPager pager;
    private ImageView selectIv1;
    private ImageView selectIv2;
    private ImageView selectIv3;
    private ImageView selectIv4;
    private ArrayList<ImageView> ivs = new ArrayList<>();
    public static final String INDEX = "index";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_first_in);
        pager = (ViewPager) findViewById(R.id.first_in_pager);
        selectIv1 = (ImageView) findViewById(R.id.iv1);
        selectIv2 = (ImageView) findViewById(R.id.iv2);
        selectIv3 = (ImageView) findViewById(R.id.iv3);
        selectIv4 = (ImageView) findViewById(R.id.iv4);
        ivs.add(selectIv1);
        ivs.add(selectIv2);
        ivs.add(selectIv3);
        ivs.add(selectIv4);
        ivs.get(0).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_select_white));
        for (int i = 1;i < ivs.size();i++){
            ivs.get(i).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_unselect_white));
        }
        initListeners();
    }

    private void initListeners(){
        ArrayList<View> views = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            View view = getLayoutInflater().inflate(R.layout.view_app_first_in,null);
            views.add(view);
        }

        pager.setAdapter(new SplashPagerAdapter(this,views));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    ivs.get(0).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_select_white));
                    for (int i = 1;i < ivs.size();i++){
                        ivs.get(i).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_unselect_white));
                    }
                }else{
                    for (int i = 0;i < ivs.size();i++){
                        if(i == position){
                            ivs.get(i).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_select));
                        }else{
                            ivs.get(i).setImageDrawable(getResources().getDrawable(R.drawable.first_in_pager_unselect));
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
