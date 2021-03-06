package com.dreamspace.uucampus.adapter.Personal;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.api.MyIdleItem;

import java.util.List;

/**
 * Created by Lx on 2015/10/17.
 */
public class FreeGoodsPullOffListAdapter extends BasisAdapter<MyIdleItem,FreeGoodsPullOffListAdapter.ViewHolder>{
    private Context mContext;
    private OnGoodSaleClickListener onGoodSaleClickListener;
    private OnEditClickListener onEditClickListener;

    public FreeGoodsPullOffListAdapter(Context mContext, List<MyIdleItem> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
        this.mContext = mContext;
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, final MyIdleItem entity) {
        CommonUtils.showImageWithGlide(mContext,holder.image,entity.getImage());
        holder.name.setText(entity.getName());
        holder.price.setText(mContext.getString(R.string.RMB) + (float)entity.getPrice() / 100);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onEditClickListener != null){
                    onEditClickListener.onEditClick(entity.getIdle_id());
                }
            }
        });

        holder.sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onGoodSaleClickListener != null){
                    onGoodSaleClickListener.onSaleClick(entity.getIdle_id(),getmEntities().indexOf(entity));
                }
            }
        });
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.good_image_iv);
        holder.name = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.price = (TextView) convertView.findViewById(R.id.good_price_tv);
        holder.edit = (TextView) convertView.findViewById(R.id.edit_tv);
        holder.sale = (TextView) convertView.findViewById(R.id.sale_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_free_goods_pull_off;
    }

    public static class ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView price;
        public TextView edit;
        public TextView sale;
    }

    public interface OnGoodSaleClickListener{
        void onSaleClick(String idle_id,int position);
    }

    public void setOnGoodSaleClickListener(OnGoodSaleClickListener onGoodSaleClickListener) {
        this.onGoodSaleClickListener = onGoodSaleClickListener;
    }

    public interface OnEditClickListener{
        void onEditClick(String idle_id);
    }

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }
}
