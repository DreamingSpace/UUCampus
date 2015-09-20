package com.dreamspace.uucampus.ui.person;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;

/**
 * Created by zsh on 2015/9/15.
 */
public class PersonInfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resultView;
        resultView = inflater.inflate(R.layout.activity_my_person_info, container, false);
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

            }
            if(index==1){
                Intent intent1 = new Intent(getActivity(),MyUseless.class);
                startActivity(intent1);
            }
            if(index==2){
                Intent intent2 = new Intent(getActivity(),MyCollection.class);
                startActivity(intent2);
            }
            if(index==3){
                Intent intent3 = new Intent(getActivity(),ApplyStore.class);
                startActivity(intent3);
            }
        }

    }
}
