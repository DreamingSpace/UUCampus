package com.dreamspace.uucampus.ui.activity.Market;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/10.
 */
public class ShopDetailAct extends AbsActivity {
    @Bind(R.id.sale_range_tv)
    TextView saleRangeTv;
    @Bind(R.id.connect_phone_tv)
    TextView connectPhoneTv;
    @Bind(R.id.connect_address_tv)
    TextView connectAddressTv;
    @Bind(R.id.resume_tv)
    TextView resumeTv;
    @Bind(R.id.report_seller_btn)
    Button reportBtn;
    @Override
    protected int getContentView() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle("东大旅游吧");

        initListeners();
    }

    private void initListeners(){
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ReportSellerAct.class);
            }
        });
    }
}
