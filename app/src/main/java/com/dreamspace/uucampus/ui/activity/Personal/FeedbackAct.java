package com.dreamspace.uucampus.ui.activity.Personal;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.CommitSuggestionRes;
import com.dreamspace.uucampus.model.api.ContentReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/15.
 */
public class FeedbackAct extends AbsActivity {
    @Bind(R.id.feedback_et)
    EditText feedBackEt;
    @Bind(R.id.feedback_commit_btn)
    Button feedBackBtn;

    private boolean actDestory = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.feedback));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtils.isEmpty(feedBackEt.getText().toString()) || feedBackEt.getText().toString().length() < 10){
                    showToast(getString(R.string.feed_back_cant_less_than_10));
                }else{
                    sendFeedBack();
                }
            }
        });
    }

    private void sendFeedBack(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ContentReq contentReq = new ContentReq();
        contentReq.setContent(feedBackEt.getText().toString());
        ApiManager.getService(this).commitSuggestion(contentReq, new Callback<CommitSuggestionRes>() {
            @Override
            public void success(CommitSuggestionRes commitSuggestionRes, Response response) {
                if(commitSuggestionRes != null && !actDestory){
                    Toast.makeText(FeedbackAct.this,getString(R.string.thx_your_feedback),Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }
}
