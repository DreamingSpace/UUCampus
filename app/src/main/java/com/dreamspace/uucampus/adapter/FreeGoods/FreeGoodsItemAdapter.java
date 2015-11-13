package com.dreamspace.uucampus.adapter.FreeGoods;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.IdleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/27 0027.
 */
public class FreeGoodsItemAdapter extends BasisAdapter<IdleItem, FreeGoodsItemAdapter.viewHolder> {

    public FreeGoodsItemAdapter(Context context){
        super(context,new ArrayList<IdleItem>(),viewHolder.class);
    }
    public FreeGoodsItemAdapter(List<IdleItem> mFreeGoodsInfoList, Context context) {
        super(context, mFreeGoodsInfoList, viewHolder.class);
    }

    @Override
    protected void setDataIntoView(viewHolder holder, IdleItem entity) {
        CommonUtils.showImageWithGlide(getmContext(), holder.image, entity.getImage());
        holder.nameTv.setText(entity.getName());
        holder.userNameTv.setText(entity.getUser_name());
        holder.priceTv.setText("￥"+String.valueOf(entity.getPrice()));
        holder.viewNumTv.setText(String.valueOf(entity.getView_number())+"人感兴趣");
    }

    @Override
    protected void initViewHolder(View convertView, viewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.free_goods_item_image_iv);
        holder.nameTv = (TextView) convertView.findViewById(R.id.free_goods_item_name_tv);
        holder.userNameTv = (TextView) convertView.findViewById(R.id.free_goods_item_user_name_tv);
        holder.viewNumTv = (TextView) convertView.findViewById(R.id.free_goods_item_view_number_tv);
        holder.priceTv = (TextView) convertView.findViewById(R.id.free_goods_item_price_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_free_goods;
    }

    public static class viewHolder {
        private ImageView image;
        private TextView nameTv;
        private TextView userNameTv;
        private TextView viewNumTv;
        private TextView priceTv;
    }

}

