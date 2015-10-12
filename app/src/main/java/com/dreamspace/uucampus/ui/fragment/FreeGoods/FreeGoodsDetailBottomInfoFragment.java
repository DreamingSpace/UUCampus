package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @Bind(R.id.free_goods_detail_collect_linear_layout)
    LinearLayout mCollect;
    @Bind(R.id.free_goods_detail_bottom_collect_image_view)
    ImageView mCollectIv;
    @Bind(R.id.free_goods_detail_bottom_collect_text_view)
    TextView mCollectTv;

    private boolean bCollect=false;

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
        mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bCollect){
                    bCollect = false;
                    mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_p);
                    mCollectTv.setTextColor(getResources().getColor(R.color.text_pressed));
                }else{
                    bCollect=true;
                    mCollectIv.setImageResource(R.drawable.xiangqing_tab_bar_collect_n);
                    mCollectTv.setTextColor(getResources().getColor(R.color.text_normal));
                }
            }
        });
    }

    @Override
    public void initDatas() {

    }

}
