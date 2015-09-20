package com.dreamspace.uucampus.ui.person;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.R;

/**
 * Created by zsh on 2015/9/19.
 */
public class ApplyStore extends AppCompatActivity{
    private Toolbar toolbar;
    private EditText edit1,edit2,edit3,edit4,edit5,edit6;
    private Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_store);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit1 = (EditText)findViewById(R.id.market_name);
        edit2 = (EditText)findViewById(R.id.storekeeper_name);
        edit3 = (EditText)findViewById(R.id.store_type);
        edit4 = (EditText)findViewById(R.id.phonenum);
        edit5 = (EditText)findViewById(R.id.address);
        edit6 = (EditText)findViewById(R.id.store_introduction);

        button = (Button)findViewById(R.id.commit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
