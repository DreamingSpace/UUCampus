package com.dreamspace.uucampus.ui.person;

import android.app.Activity;
import android.os.Bundle;

import com.dreamspace.uucampus.R;

/**
 * Created by zsh on 2015/9/14.
 */
public class PersonInfoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initViews();
        //ActionBar actionBar = getActionBar();
        //actionBar.setTitle("个人中心");
    }
    protected void initViews() {
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();
        getFragmentManager().beginTransaction().add(R.id.person_info, personInfoFragment).commit();
    }
}
