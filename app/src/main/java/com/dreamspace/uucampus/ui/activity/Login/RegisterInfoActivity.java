package com.dreamspace.uucampus.ui.activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.LocationAllRes;
import com.dreamspace.uucampus.model.api.UpdateUserInfoReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/10/13.
 */
public class RegisterInfoActivity extends AbsActivity {

    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @Bind(R.id.register_personinfo_username)
    EditText userNameTextView;
    @Bind(R.id.register_personinfo_school)
    TextView schoolTextView;
    @Bind(R.id.personinfo_button)
    Button personInfoButton;
    @Bind(R.id.register_personinfo_enrolledtime)
    TextView enrolledTime;

    private String path;
    private String userName;
    private String school;
    private String year;
    public static final int AVATER = 1;
    ProgressDialog progressDialog;
    private ArrayList<String> locations;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_personinfo_page;
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        initListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AVATER && resultCode == RESULT_OK){
            path = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
            Glide.with(RegisterInfoActivity.this)
                    .load(path)
                    .error(R.drawable.default_error)
                    .centerCrop()
                    .into(ivUserIcon);
        }
    }

    private void initListeners(){
        ivUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoForResult(SelectPhotoActivity.class,AVATER);
            }
        });
        personInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerInfo();
            }
        });
        enrolledTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearDialog();
            }
        });
        schoolTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSchoolDialog();
            }
        });
    }

    //显示学校选择对话框
    private void showSchoolDialog(){
        getSchools();
    }

    //获取所有校区
    private void getSchools(){
        if(NetUtils.isNetworkConnected(this)){
            ApiManager.getService(this).getAllLocation(new Callback<LocationAllRes>() {
                @Override
                public void success(LocationAllRes locationAllRes, Response response) {
                    final WheelViewDialog inSchoolDialog = new WheelViewDialog(RegisterInfoActivity.this,locationAllRes.getLocation(),"选择校区");

                    inSchoolDialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inSchoolDialog.dismiss();
                        }
                    });

                    inSchoolDialog.setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            school = inSchoolDialog.getSelected();
                            schoolTextView.setText(school);
                            inSchoolDialog.dismiss();
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                }
            });
        }else{
            showNetWorkError();
        }
    }

    //显示年份选择对话框
    private void showYearDialog(){
        ArrayList<String> years = new ArrayList<>();
        for(int i = 2010;i < 2020;i++){
            years.add(i + "");
        }
        final WheelViewDialog inSchoolYeardialog = new WheelViewDialog(this,years,getString(R.string.select_in_school_year));

        inSchoolYeardialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inSchoolYeardialog.dismiss();
            }
        });

        inSchoolYeardialog.setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = inSchoolYeardialog.getSelected();
                enrolledTime.setText(year);
                inSchoolYeardialog.dismiss();
            }
        });
    }

    private boolean isValid(){
        school = schoolTextView.getText().toString();
        userName = userNameTextView.getText().toString();
        if(CommonUtils.isEmpty(path)){
            showToast("请选择一张图片~");
            return false;
        }
        if(CommonUtils.isEmpty(userName)){
            showToast("请填写您的昵称~");
            return false;
        }
        if(CommonUtils.isEmpty(school)){
            showToast("请选择您的学校~");
            return false;
        }
        return true;
    }

    //上传用户信息
    private void registerInfo(){
        if(isValid()) {
            progressDialog = ProgressDialog.show(this, "", "正在上传用户信息~~", true, false);
            if (NetUtils.isNetworkConnected(this)) {
                UpdateUserInfoReq updateUserInfoReq = new UpdateUserInfoReq();
                updateUserInfoReq.setImage(path);
                updateUserInfoReq.setEnroll_year(year);
                updateUserInfoReq.setLocation(school);
                updateUserInfoReq.setName(userName);

                ApiManager.getService(this.getApplicationContext()).updateUserInfo(updateUserInfoReq, new Callback<CommonStatusRes>() {
                    @Override
                    public void success(CommonStatusRes response, Response response2) {
                        progressDialog.dismiss();
                        showToast("用户信息上传成功~~");
                        readyGoThenKill(RegisterSucceedActivity.class);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        showInnerError(error);
                    }
                });
            } else {
                progressDialog.dismiss();
                showNetWorkError();
            }
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
