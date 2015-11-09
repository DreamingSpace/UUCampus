package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.GoodsItem;

import java.util.List;

/**
 * Created by Lx on 2015/9/23.
 */
public class GoodsListAdapter extends BasisAdapter<GoodsItem,GoodsListAdapter.ViewHolder> {
    private Context mContext;
    public GoodsListAdapter(Context mContext, List mEntities, Class classType) {
        super(mContext, mEntities, classType);
        this.mContext = mContext;
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, GoodsItem entity) {
        CommonUtils.showImageWithGlide(mContext,holder.image,entity.getImage());
        holder.goodName.setText(entity.getName());
        holder.shopName.setText(entity.getShop_name());
        holder.orgPrice.setText(mContext.getString(R.string.RMB) + entity.getOriginal_price());
        holder.orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price.setText(mContext.getString(R.string.RMB) + entity.getPrice());
        holder.likeNum.setText(entity.getView_number() + mContext.getString(R.string.x_people_like));
        holder.perferential.setText(mContext.getString(R.string.yoyo_coupon_card_reduce) + entity.getDiscount()
                    + mContext.getString(R.string.RMB));
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.good_image_iv);
        holder.goodName = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.shopName = (TextView) convertView.findViewById(R.id.good_shop_name_tv);
        holder.likeNum = (TextView) convertView.findViewById(R.id.good_like_tv);
        holder.perferential = (TextView) convertView.findViewById(R.id.good_preferential_tv);
        holder.orgPrice = (TextView) convertView.findViewById(R.id.goods_org_price);
        holder.price = (TextView) convertView.findViewById(R.id.good_price_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.goods_list_item;
    }


    public static class ViewHolder{
        public ImageView image;
        public TextView goodName;
        public TextView shopName;
        public TextView likeNum;
        public TextView perferential;
        public TextView orgPrice;
        public TextView price;
    }
}
