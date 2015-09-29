package com.dreamspace.uucampus.ui.person;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/29.
 */
public class UserRegisterActivity extends AbsActivity {
    @Bind(R.id.phonenum)
    EditText phonenum;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.commit)
    Button commit;

    private String phone_num;
    @Override
    protected int getContentView() {
        return R.layout.person_register;
    }

    @Override
    protected void prepareDatas() {
        phone_num = phonenum.getText().toString();

    }

    @Override
    protected void initViews() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
