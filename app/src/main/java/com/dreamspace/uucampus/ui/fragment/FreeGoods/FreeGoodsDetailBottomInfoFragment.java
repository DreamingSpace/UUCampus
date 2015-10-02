package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.view.View;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.BaseFragment;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/21.
 */
public class FreeGoodsDetailBottomInfoFragment extends BaseFragment {
    @Bind(R.id.free_goods_detail_consult_linear_layout)
    LinearLayout mConsult;

    public static FreeGoodsDetailBottomInfoFragment newInstance() {
        FreeGoodsDetailBottomInfoFragment fragment = new FreeGoodsDetailBottomInfoFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_free_goods_detail_bottom_info;
    }

    @Override
    public void initViews(View view) {

        mConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = dialog = new ConnectSellerDialog(getActivity(),
                        R.style.UpDialog, "good name", "phone number");
                dialog.show();
            }
        });
    }

    @Override
    public void initDatas() {

    }
}
