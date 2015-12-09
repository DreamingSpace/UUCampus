package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;

import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.UploadImage;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.FileUtils;
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
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Bind(R.id.school_rl)
    RelativeLayout schoolRl;
    @Bind(R.id.year_rl)
    RelativeLayout yearRl;
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
        //获取所有校区
        getLoactions();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.personal_info));

        //将从personalcenterframgent传递过来的userinfo显示到view
        CommonUtils.showImageWithGlideInCiv(this, avaterCiv, PreferenceUtils.getString(this,PreferenceUtils.Key.AVATAR));
        nickNameTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.NAME));
        phoneTv.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.PHONE));
        loactionTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.LOCATION));
        yearTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.ENROLL_YEAR));

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
                changNameDialog.setText(PreferenceUtils.getString(PersonalInfoAct.this,PreferenceUtils.Key.NAME));
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AVATER && resultCode == RESULT_OK){
            String path = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
            System.out.println("path" + path);
            Crop.of(Uri.fromFile(new File(path)),Uri.fromFile(FileUtils.createTmpFile(this))).asSquare().start(this);
        }else if(requestCode == Crop.REQUEST_CROP){
            handleCrop(resultCode,data);
        }
    }

    private void handleCrop(int resultCode,Intent result){
        if(resultCode == RESULT_OK){
            initProgressDilaog();
            progressDialog.setContent(getString(R.string.uploading_image));
            progressDialog.show();
            upLoadImage(Crop.getOutput(result).getPath());
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
                if (userInfoRes != null && !actDestory) {
                    CommonUtils.showImageWithGlideInCiv(PersonalInfoAct.this, avaterCiv, userInfoRes.getImage());
                    nickNameTv.setText(userInfoRes.getName());
                    yearTv.setText(userInfoRes.getEnroll_year());
                    loactionTv.setText(userInfoRes.getLocation());
                    //将最新信息缓存到本地
                    PreferenceUtils.putString(PersonalInfoAct.this, PreferenceUtils.Key.AVATAR, userInfoRes.getImage());
                    PreferenceUtils.putString(PersonalInfoAct.this, PreferenceUtils.Key.NAME, userInfoRes.getName());
                    PreferenceUtils.putString(PersonalInfoAct.this, PreferenceUtils.Key.ENROLL_YEAR, userInfoRes.getEnroll_year());
                    PreferenceUtils.putString(PersonalInfoAct.this, PreferenceUtils.Key.PHONE, userInfoRes.getPhone_num());
                    PreferenceUtils.putString(PersonalInfoAct.this,PreferenceUtils.Key.LOCATION,userInfoRes.getLocation());
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
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int yearEnd = year + 5;
        ArrayList<String> years = new ArrayList<>();
        for(int i = year - 10;i < yearEnd;i++){
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
                            modifyUserInfo(key,null,null,null);
                        }
                    }
                },null);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(getString(R.string.image_upload_failed));
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
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}