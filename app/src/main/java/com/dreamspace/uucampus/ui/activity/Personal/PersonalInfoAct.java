package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.UploadImage;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.LocationAllRes;
import com.dreamspace.uucampus.model.api.QnRes;
import com.dreamspace.uucampus.model.api.UpdateUserInfoReq;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.ui.PersonCenterFragment;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ChangNameDialog;
import com.dreamspace.uucampus.ui.dialog.MsgDialog;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.ui.dialog.WheelDialog;
import com.dreamspace.uucampus.ui.dialog.WheelViewDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/17.
 */
public class PersonalInfoAct extends AbsActivity {
    @Bind(R.id.avater_rl)
    RelativeLayout avaterRl;
    @Bind(R.id.nickname_rl)
    RelativeLayout nicknameRl;
    @Bind(R.id.bundling_phone_rl)
    RelativeLayout phoneRl;
    @Bind(R.id.school_rl)
    RelativeLayout schoolRl;
    @Bind(R.id.year_rl)
    RelativeLayout yearRl;
//    @Bind(R.id.weibo_rl)
//    RelativeLayout weiboRl;
//    @Bind(R.id.wechat_rl)
//    RelativeLayout wechatRl;
    @Bind(R.id.personal_info_avater)
    CircleImageView avaterCiv;
    @Bind(R.id.nickname_tv)
    TextView nickNameTv;
    @Bind(R.id.bundling_phone_tv)
    TextView phoneTv;
    @Bind(R.id.in_school_year_tv)
    TextView yearTv;
    @Bind(R.id.loaction_tv)
    TextView loactionTv;

    public static final int AVATER = 1;
    private UserInfoRes userInfo;
    private ChangNameDialog changNameDialog;
    private ProgressDialog progressDialog;
    private MsgDialog msgDialog;
    private ArrayList<String> loactions;
    private WheelDialog yearDialog;
    private WheelDialog loactionDialog;
    private boolean actDestory = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void prepareDatas() {
        Bundle data = getIntent().getExtras();
        userInfo = data.getParcelable(PersonCenterFragment.USER_INFO);

        //获取所有校区
        getLoactions();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.personal_info));

        //将从personalcenterframgent传递过来的userinfo显示到view
        CommonUtils.showImageWithGlideInCiv(this, avaterCiv, userInfo.getImage());
        nickNameTv.setText(userInfo.getName());
        phoneTv.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.PHONE));
        loactionTv.setText(userInfo.getLocation());
        yearTv.setText(userInfo.getEnroll_year());

        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        avaterRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGoForResult(SelectPhotoActivity.class, AVATER);
            }
        });

        nicknameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChangeNameDialog();
                changNameDialog.setText(userInfo.getName());
                changNameDialog.show();
            }
        });

        schoolRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loactions != null) {
                    initLocationDialog();
                    loactionDialog.show();
                } else {
                    initMsgDialog();
                    msgDialog.show();
                }
            }
        });

        yearRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initYearDialog();
                yearDialog.show();
            }
        });

//        weiboRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        wechatRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AVATER && resultCode == RESULT_OK){
            String path = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
            initProgressDilaog();
            progressDialog.setContent(getString(R.string.uploading_image));
            progressDialog.show();
            upLoadImage(path);
        }
    }

    //修改用户信息
    private void modifyUserInfo(String imagePath,String nickName,String campus,String enrollYear){
        if(!NetUtils.isNetworkConnected(this)){
            showToast(getString(R.string.network_error_tips));
            return;
        }

        UpdateUserInfoReq userInfo = new UpdateUserInfoReq();
        userInfo.setImage(imagePath);
        userInfo.setName(nickName);
        userInfo.setLocation(campus);
        userInfo.setEnroll_year(enrollYear);

        ApiManager.getService(this).updateUserInfo(userInfo, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes.getStatus().equals("ok")) {
                    //修改成功后及时获取最新用户信息
                    getUserInfo();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                progressDialog.dismiss();
                showToast(errorRes.getReason());
            }
        });
    }

    //获取用户信息
    private void getUserInfo(){
        if(!NetUtils.isNetworkConnected(this)){
            showToast(getString(R.string.network_error_tips));
            progressDialog.dismiss();
            return;
        }

        ApiManager.getService(this).getUserInfo(new Callback<UserInfoRes>() {
            @Override
            public void success(UserInfoRes userInfoRes, Response response) {
                //获取到最新的用户信息后将信息显示于界面
                userInfo = userInfoRes;
                if (userInfoRes != null && !actDestory) {
                    CommonUtils.showImageWithGlideInCiv(PersonalInfoAct.this, avaterCiv, userInfo.getImage());
                    nickNameTv.setText(userInfo.getName());
                    yearTv.setText(userInfo.getEnroll_year());
                    loactionTv.setText(userInfo.getLocation());
                    progressDialog.dismiss();
                    showToast(getString(R.string.modify_success));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                progressDialog.dismiss();
                showToast(errorRes.getReason());
            }
        });
    }

    //初始化修改昵称对话框
    private void initChangeNameDialog(){
        if(changNameDialog != null){
            return;
        }

        changNameDialog = new ChangNameDialog(this);
        changNameDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changNameDialog.dismiss();
            }
        });

        changNameDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDilaog();
                progressDialog.setContent(getString(R.string.nickname_in_change));
                //修改框隐藏，progressdialog显示
                changNameDialog.dismiss();
                progressDialog.show();
                modifyUserInfo(null, changNameDialog.getText(), null, null);
            }
        });
    }

    //初始化progressdialog
    private void initProgressDilaog(){
        if(progressDialog != null){
            return;
        }
        progressDialog = new ProgressDialog(this);
    }

    //获取所有校区
    private void getLoactions(){
        if(!NetUtils.isNetworkConnected(this)){
            showToast(getString(R.string.network_error_tips));
            return;
        }

        ApiManager.getService(this).getAllLocation(new Callback<LocationAllRes>() {
            @Override
            public void success(LocationAllRes locationAllRes, Response response) {
                if (locationAllRes != null && !actDestory) {
                    loactions = locationAllRes.getLocation();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    private void initYearDialog(){
        if(yearDialog != null){
            return;
        }
        ArrayList<String> years = new ArrayList<>();
        for(int i = 2010;i < 2020;i++){
            years.add(i + "");
        }
        yearDialog = new WheelDialog(this);
        yearDialog.setTitle(getString(R.string.select_in_school_year));
        yearDialog.setData(years);

        yearDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog.dismiss();
            }
        });

        yearDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDilaog();
                yearDialog.dismiss();
                progressDialog.setContent(getString(R.string.modifing));
                progressDialog.show();
                modifyUserInfo(null, null, null, yearDialog.getSelectedText());
            }
        });
    }

    private void initLocationDialog(){
        if(loactionDialog != null){
            return;
        }
        loactionDialog = new WheelDialog(this);
        loactionDialog.setTitle(getString(R.string.choose_enroll_year));
        loactionDialog.setData(loactions);

        loactionDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDilaog();
                loactionDialog.dismiss();
                progressDialog.setContent(getString(R.string.modifing));
                progressDialog.show();
                modifyUserInfo(null, null, loactionDialog.getSelectedText(), null);
            }
        });

        loactionDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loactionDialog.dismiss();
            }
        });
    }

    private void initMsgDialog(){
        if(msgDialog != null){
            return;
        }
        msgDialog = new MsgDialog(this);
        msgDialog.setContent(getString(R.string.get_loaction_error));
        msgDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoactions();
                msgDialog.dismiss();
            }
        });

        msgDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
            }
        });
    }

    //上传图片
    private void upLoadImage(final String path){
        if(!NetUtils.isNetworkConnected(this)){
            showToast(getString(R.string.network_error_tips));
        }

        ApiManager.getService(this).createQiNiuToken(new Callback<QnRes>() {
            @Override
            public void success(QnRes qnRes, Response response) {
                UploadImage.upLoadImage(path, qnRes.getKey(), qnRes.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if(info.isOK()){
                            modifyUserInfo(key,userInfo.getName(),userInfo.getLocation(),userInfo.getEnroll_year());
                        }
                    }
                },null);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actDestory = true;
    }

    @Override
    public void onBackPressed() {
        //返回最新的用户数据
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PersonCenterFragment.USER_INFO,userInfo);
        data.putExtras(bundle);
        setResult(RESULT_OK,data);
        super.onBackPressed();
    }
}
