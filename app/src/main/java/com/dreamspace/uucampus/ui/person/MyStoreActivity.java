package com.dreamspace.uucampus.ui.person;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/20.
 */
public class MyStoreActivity extends AbsActivity {
    @Bind(R.id.head_pic)
    ImageView head_img;
    @Bind(R.id.relative)
    RelativeLayout relative;
    @Override
    protected  int getContentView() {
        return R.layout.person_activity_my_store;
    }

    @Override
    protected void prepareDatas() {

    }
    @Override
    protected void initViews() {
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ApplyStoreActivity.class);
            }
        });
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //readyGo(MyGoodsActivity.class);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
