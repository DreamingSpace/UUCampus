package com.dreamspace.uucampus.ui.activity.Order;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.AddGoodsCommentRes;
import com.dreamspace.uucampus.model.api.ContentReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.widget.RatingBar;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/22.
 * 商品评论界面，进入此界面需要传入order_id,good_id
 */
public class CommentAct extends AbsActivity{
    @Bind(R.id.comment_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.rating_tv)
    TextView ratingTv;
    @Bind(R.id.comment_et)
    EditText commentEt;
    @Bind(R.id.submit_comment_btn)
    Button submitBtn;

    private int stars = 0;
    private String orderId;
    private String goodId;
    private boolean actDestory = false;
    private ProgressDialog progressDialog;

    public static final String ORDER_ID = "ORDER_ID";
    public static final String GOOD_ID = "GOOD_ID";

    @Override
    protected int getContentView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void prepareDatas() {
        orderId = getIntent().getExtras().getString(ORDER_ID);
        goodId = getIntent().getExtras().getString(GOOD_ID);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.comment1));

        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int i) {
                switch (i) {
                    case 1:
                        ratingTv.setText(getString(R.string.very_unsatisfied));
                        stars = 1;
                        break;

                    case 2:
                        ratingTv.setText(getString(R.string.unsatisfied));
                        stars = 2;
                        break;

                    case 3:
                        ratingTv.setText(getString(R.string.soso));
                        stars = 3;
                        break;

                    case 4:
                        ratingTv.setText(getString(R.string.satisfied));
                        stars = 4;
                        break;

                    case 5:
                        ratingTv.setText(getString(R.string.very_satisfied));
                        stars = 5;
                        break;
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    initProgressDialog();
                    progressDialog.show();
                    addCommit(commentEt.getText().toString());
                }
            }
        });
    }

    //检查用户是否评星，评价
    private boolean isValid(){
        if(stars < 1){
            showToast(getString(R.string.plz_score));
            return false;
        }else if(CommonUtils.isEmpty(commentEt.getText().toString())
                || commentEt.getText().toString().length() < 10){
            showToast(getString(R.string.comment_cant_less_than_10));
            return false;
        }
        return true;
    }

    private void addCommit(String content){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        ContentReq contentReq = new ContentReq();
        contentReq.setOrder_id(orderId);
        contentReq.setContent(content);
        contentReq.setScore(stars);
        ApiManager.getService(this).addGoodsComment(goodId, contentReq, new Callback<AddGoodsCommentRes>() {
            @Override
            public void success(AddGoodsCommentRes addGoodsCommentRes, Response response) {
                if (addGoodsCommentRes != null && !actDestory) {
                    progressDialog.dismiss();
                    Toast.makeText(CommentAct.this, getString(R.string.comment_success), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setContent(getString(R.string.in_comment));
    }
}
