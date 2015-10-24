package com.dreamspace.uucampus.ui.activity.Order;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.hedgehog.ratingbar.RatingBar;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/22.
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

    @Override
    protected int getContentView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.comment1));

        initListeners();
    }

    private void initListeners(){
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int i) {
                switch (i){
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

            }
        });
    }
}
