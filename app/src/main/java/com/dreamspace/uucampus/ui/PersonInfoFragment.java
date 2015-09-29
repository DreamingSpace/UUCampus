package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.person.MyCollectionActivity;
import com.dreamspace.uucampus.ui.person.MyStoreActivity;
import com.dreamspace.uucampus.ui.person.MyUselessActivity;
import com.dreamspace.uucampus.ui.person.PersonMessageActivity;
import com.dreamspace.uucampus.ui.person.SettingActivity;
import com.dreamspace.uucampus.ui.person.UserRegisterActivity;

/**
 * Created by zsh on 2015/9/15.
 */
public class PersonInfoFragment extends BaseLazyFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View resultView;
        resultView = inflater.inflate(R.layout.person_fragment_my, container, false);
        ImageView imageView = (ImageView)resultView.findViewById(R.id.head_portrait);
        imageView.setOnClickListener(new MyOnClickListener(0));
        RelativeLayout relativeLayout1 = (RelativeLayout)resultView.findViewById(R.id.my_useless);
        relativeLayout1.setOnClickListener(new MyOnClickListener(1));
        RelativeLayout relativeLayout2 = (RelativeLayout)resultView.findViewById(R.id.my_collection);
        relativeLayout2.setOnClickListener(new MyOnClickListener(2));
        RelativeLayout relativeLayout3 = (RelativeLayout)resultView.findViewById(R.id.my_store);
        relativeLayout3.setOnClickListener(new MyOnClickListener(3));
        RelativeLayout relativeLayout4 = (RelativeLayout)resultView.findViewById(R.id.my_setting);
        relativeLayout4.setOnClickListener(new MyOnClickListener(4));
        RelativeLayout relativeLayout5 = (RelativeLayout)resultView.findViewById(R.id.my_about);
        relativeLayout5.setOnClickListener(new MyOnClickListener(5));
        Button button1 = (Button)resultView.findViewById(R.id.register);
        button1.setOnClickListener(new MyOnClickListener(6));
        Button button2 = (Button)resultView.findViewById(R.id.login);
        button2.setOnClickListener(new MyOnClickListener(7));
        return resultView;
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v){
            if(index==0){
                readyGo(PersonMessageActivity.class);
            }
            if(index==1){
                readyGo(MyUselessActivity.class);
            }
            if(index==2){
                readyGo(MyCollectionActivity.class);
            }
            if(index==3){
                //readyGo(ApplyStoreActivity.class);
                readyGo(MyStoreActivity.class);
            }
            if(index==4){
                readyGo(SettingActivity.class);
            }
            if (index == 6){
                readyGo(UserRegisterActivity.class);
            }
            if (index == 7){
                //readyGo();
            }
        }

    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
