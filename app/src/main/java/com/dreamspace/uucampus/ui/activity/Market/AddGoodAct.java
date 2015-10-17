package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.widget.WheelView;
import com.dreamspace.uucampus.widget.photopicker.SelectPhotoActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/24.
 */
public class AddGoodAct extends AbsActivity {
    @Bind(R.id.add_good_image_iv)
    ImageView addGoodIv;
    @Bind(R.id.add_good_name_et)
    EditText goodnameEt;
    @Bind(R.id.add_good_price_et)
    EditText goodPriceEt;
    @Bind(R.id.add_good_preferential_tv)
    TextView goodPreTv;
    @Bind(R.id.add_good_classify_tv)
    TextView goodCatalogTv;
    @Bind(R.id.add_good_label_tv)
    TextView goodLabelTv;
    @Bind(R.id.add_good_detail_et)
    EditText goodDetail;
    @Bind(R.id.add_good_name_ll)
    LinearLayout goodNameLl;
    @Bind(R.id.add_good_price_ll)
    LinearLayout goodPriceLl;
    @Bind(R.id.add_good_preferential_ll)
    LinearLayout goodPreLl;
    @Bind(R.id.add_good_catalog_ll)
    LinearLayout goodCataLl;
    @Bind(R.id.add_good_label_ll)
    LinearLayout goodLabelLl;

    public static int PHOTO_REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_good;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                String photoPath = data.getStringExtra(SelectPhotoActivity.PHOTO_PATH);
//                ArrayList<String> photo = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Glide.with(AddGoodAct.this)
                        .load(photoPath)
                        .centerCrop()
                        .into(addGoodIv);
            }
        }
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = (TextView) mToolBar.findViewById(R.id.custom_title_tv);
        title.setText(getResources().getString(R.string.good_add));

        initListeners();
    }

    private void initListeners(){
        addGoodIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PhotoPickerIntent intent = new PhotoPickerIntent(AddGoodAct.this);
//                intent.setPhotoCount(1);
//                intent.setShowCamera(true);
//                startActivityForResult(intent, PHOTO_REQUEST_CODE);
                Intent intent = new Intent(AddGoodAct.this,SelectPhotoActivity.class);
                startActivityForResult(intent,PHOTO_REQUEST_CODE,null);
            }
        });

        goodNameLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodnameEt.requestFocus();
            }
        });

        goodPriceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodPriceEt.requestFocus();
            }
        });

        goodPreLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShowDiscountDialog();
            }
        });

        goodCataLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShowCataDialog();
            }
        });

        goodLabelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShowLabelDialog();
            }
        });
    }

    private void createShowDiscountDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alterdialog_preferential, null);
        WheelView preWheelView = (WheelView) dialogView.findViewById(R.id.preferential_wheelview);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.preferential_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.preferential_confirm_ll);
        ImageView addIv = (ImageView) dialogView.findViewById(R.id.new_pre_iv);

        ArrayList<String> list = new ArrayList<String>();
        list.add("5-10人，每人45元；11-2");
        list.add("5-10人，每人65元；11-2");
        list.add("8-10人，每人65元；11-2");
        list.add("5-10人，每人23元；11-2");
        list.add("5-10人，每人44元；11-2");
        list.add("5-10人，每人99元；11-2");
        list.add("5-10人，每人88元；11-2");

        preWheelView.setData(list);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog discountDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountDialog.dismiss();
            }
        });

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountDialog.dismiss();
                createShowNewDisDialog();
            }
        });

        discountDialog.show();
    }

    private void createShowNewDisDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alterdialog_new_preferential,null);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.new_preferential_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.new_preferential_confirm_ll);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog newDiscountDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiscountDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiscountDialog.dismiss();
            }
        });
        newDiscountDialog.show();
    }

    private void createShowCataDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alterdialog_catalog,null);
        WheelView cataWheelView = (WheelView) dialogView.findViewById(R.id.catalog_wheelview);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.catalog_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.catalog_confirm_ll);
        ImageView addCataIv = (ImageView) dialogView.findViewById(R.id.new_catalog_iv);

        ArrayList<String> list = new ArrayList<String>();
        list.add("电子数码");
        list.add("课本");
        list.add("自行车");
        list.add("门票");
        list.add("学习资料");
        list.add("乐器");

        cataWheelView.setData(list);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog cataDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cataDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cataDialog.dismiss();
            }
        });

        addCataIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cataDialog.dismiss();
                createShowNewCataDialog();
            }
        });
        cataDialog.show();
    }

    private void createShowNewCataDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alterdialog_new_catalog,null);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.new_catalog_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.new_catalog_confirm_ll);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog newCataDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCataDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCataDialog.dismiss();
            }
        });
        newCataDialog.show();
    }

    private void createShowLabelDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alterdialog_label,null);
        WheelView labelWheelView = (WheelView) dialogView.findViewById(R.id.label_wheelview);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.label_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.label_confirm_ll);

        ArrayList<String> list = new ArrayList<String>();
        list.add("一日游");
        list.add("两日一夜游");
        list.add("江浙游");
        list.add("北上广游");
        list.add("梦游");
        list.add("西域游");

        labelWheelView.setData(list);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog labelDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelDialog.dismiss();
            }
        });

        labelDialog.show();
    }
}
