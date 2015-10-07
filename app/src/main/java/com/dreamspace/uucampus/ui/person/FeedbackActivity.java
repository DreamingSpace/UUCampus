package com.dreamspace.uucampus.ui.person;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.API.ApiManager;
import com.dreamspace.uucampus.API.UUService;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.person.ErrorRes;
import com.dreamspace.uucampus.model.person.FeedbackContentReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zsh on 2015/9/22.
 */
public class FeedbackActivity extends AbsActivity {
    @Bind(R.id.feedback)
    EditText editText;
    @Bind(R.id.commit)
    Button button;

    private Context mContext;
    private UUService mService;
    private String text = null;
    @Override
    protected int getContentView() {
        return R.layout.person_activity_feedback;
    }

    @Override
    protected void prepareDatas() {
        mContext = this;
        mService = ApiManager.getService(mContext);
        text = editText.getText().toString();
    }

    @Override
    protected void initViews() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.equals(null)) {
                    Toast.makeText(mContext,"请输入您的意见",Toast.LENGTH_LONG).show();
                }else{
                    commitFeedback();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void commitFeedback(){
        if (NetUtils.isNetworkConnected(mContext)) {
            FeedbackContentReq req = new FeedbackContentReq();
            req.setContent(text);
            mService.commitFeedback(req, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    if (response.getStatus() == 200) {
                        Toast.makeText(mContext, "提交反馈成功", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
                    Log.i("INFO", error.getMessage());
                    Log.i("INFO", res.toString());
                }
            });
        }else{
            showNetWorkError();
        }
    }
}
