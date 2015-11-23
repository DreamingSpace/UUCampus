package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.UploadImage;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.CategoryItem;
import com.dreamspace.uucampus.model.api.AllCategoryRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.GetIdleInfoRes;
import com.dreamspace.uucampus.model.api.QnRes;
import com.dreamspace.uucampus.model.api.UpdateIdleReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.MsgDialog;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.ui.dialog.WheelDialog;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/11/21.
 */
public class IdleEditAct extends AbsActivity{
    @Bind(R.id.confirm_btn)
    Button confirmBtn;
    @Bind(R.id.good_image_iv)
    ImageView imageIv;
    @Bind(R.id.free_goods_name_et)
    EditText nameEt;
    @Bind(R.id.free_goods_price_et)
    EditText priceEt;
    @Bind(R.id.free_goods_classify_et)
    EditText categoryEt;
    @Bind(R.id.free_goods_detail_et)
    EditText detailEt;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    private ArrayList<CategoryItem> categorys;
    private MsgDialog msgDialog;
    private WheelDialog wheelDialog;
    private ProgressDialog progressDialog;
    private String idleId;
    private String imagePath;
    private boolean actDestory = false;
    private int is_active;

    private static final int IMAGE = 1;

    public static final String IDLE_ID = "IDLE_ID";
    public static final String IS_ACTIVE = "IS_ACTIVE";

    @Override
    protected int getContentView() {
        return R.layout.activity_idle_edit;
    }

    @Override
    protected void prepareDatas() {
        idleId = getIntent().getExtras().getString(IDLE_ID);
        is_active = getIntent().getExtras().getInt(IS_ACTIVE);
        getIdleDetail();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.idle_edit));
        initListeners();
    }

    private void initListeners(){
        categoryEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categorys == null){
                    initMsgDialog();
                    msgDialog.show();
                }else{
                    initWheelDialog();
                    wheelDialog.show();
                }
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog();
                progressDialog.show();
                if(imagePath != null){
                    uploadImage();
                }else{
                    updateIdleInfo(null);
                }
            }
        });

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGoForResult(SelectPhotoActivity.class,IMAGE);
            }
        });

        priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        priceEt.setText(charSequence);
                        priceEt.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    priceEt.setText(charSequence);
                    priceEt.setSelection(2);
                }

                if (charSequence.toString().startsWith("0")
                        && charSequence.toString().trim().length() > 1) {
                    if (!charSequence.toString().substring(1, 2).equals(".")) {
                        priceEt.setText(charSequence.subSequence(0, 1));
                        priceEt.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return scrollView;
    }

    private void getIdleCategory(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).getAllIdleCategory(new Callback<AllCategoryRes>() {
            @Override
            public void success(AllCategoryRes allCategoryRes, Response response) {
                if (allCategoryRes != null) {
                    categorys = allCategoryRes.getCategory();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    private void getIdleDetail(){
        toggleShowLoading(true,null);
        if(!NetUtils.isNetworkConnected(this)){
            toggleNetworkError(true, getIdleInfoClickListener);
            return;
        }

        ApiManager.getService(this).getIdleInfo(idleId, new Callback<GetIdleInfoRes>() {
            @Override
            public void success(GetIdleInfoRes getIdleInfoRes, Response response) {
                if (getIdleInfoRes != null && !actDestory) {
                    getIdleCategory();
                    toggleRestore();
                    priceEt.setText(getIdleInfoRes.getPrice() / 100+ "");
                    nameEt.setText(getIdleInfoRes.getName());
                    categoryEt.setText(getIdleInfoRes.getCategory());
                    detailEt.setText(getIdleInfoRes.getDescription());
                    CommonUtils.showImageWithGlide(IdleEditAct.this, imageIv, getIdleInfoRes.getImage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                toggleShowEmpty(true, null, getIdleInfoClickListener);
            }
        });
    }

    private void updateIdleInfo(String image){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        UpdateIdleReq idleReq = new UpdateIdleReq();
        idleReq.setName(nameEt.getText().toString());
        idleReq.setPrice((int)(Float.parseFloat(priceEt.getText().toString()) * 100) + "");
        idleReq.setCategory(categoryEt.getText().toString());
        idleReq.setDescription(detailEt.getText().toString());
        idleReq.setImage(image);
        idleReq.setIs_active(is_active);
        ApiManager.getService(this).updateIdleInfo(idleId, idleReq, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if(commonStatusRes != null && !actDestory){
                    progressDialog.dismiss();
                    showToast(getString(R.string.modify_success));
                    setResult(RESULT_OK);
                    IdleEditAct.this.finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    private void uploadImage(){
        if(!NetUtils.isNetworkConnected(this)){
            progressDialog.dismiss();
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).createQiNiuToken(new Callback<QnRes>() {
            @Override
            public void success(QnRes qnRes, Response response) {
                if(qnRes != null && !actDestory){
                    UploadImage.upLoadImage(imagePath, qnRes.getKey(), qnRes.getToken(), new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if(info.isOK()){
                                //图片上传成功，接着更新商品信息
                                updateIdleInfo(key);
                            }
                        }
                    },null);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    private void initWheelDialog(){
        if(wheelDialog != null){
            return;
        }

        wheelDialog = new WheelDialog(this);
        if(categorys != null){
            ArrayList<String> category = new ArrayList<>();
            for(CategoryItem item:categorys){
                category.add(item.getName());
            }
            wheelDialog.setData(category);
        }

        wheelDialog.setTitle(getString(R.string.catalog_list));
        wheelDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheelDialog.dismiss();
            }
        });

        wheelDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheelDialog.dismiss();
                categoryEt.setText(wheelDialog.getSelectedText());
            }
        });
    }

    private void initMsgDialog(){
        if(msgDialog != null){
            return;
        }

        msgDialog = new MsgDialog(this);
        msgDialog.setContent(getString(R.string.not_get_idle_category));
        msgDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgDialog.dismiss();
            }
        });

        msgDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIdleCategory();
                msgDialog.dismiss();
            }
        });
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setContent(getString(R.string.modifing));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE && resultCode == RESULT_OK){
            imagePath = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
            CommonUtils.showImageWithGlide(this,imageIv,imagePath);
        }
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    private View.OnClickListener getIdleInfoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getIdleDetail();
        }
    };
}
