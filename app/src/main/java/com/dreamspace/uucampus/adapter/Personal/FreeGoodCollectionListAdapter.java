package com.dreamspace.uucampus.adapter.Personal;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.api.IdleCollectionItem;

import java.util.List;

/**
 * Created by Lx on 2015/11/3.
 */
public class FreeGoodCollectionListAdapter extends BasisAdapter<IdleCollectionItem,FreeGoodCollectionListAdapter.ViewHolder>{
    private Context mContext;
    public FreeGoodCollectionListAdapter(Context mContext, List<IdleCollectionItem> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
        this.mContext = mContext;
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, IdleCollectionItem entity) {
        CommonUtils.showImageWithGlide(mContext,holder.image,entity.getImage());
        holder.name.setText(entity.getName());
        holder.publisher.setText(entity.getUser_name());
        holder.interest.setText(entity.getView_number() + mContext.getString(R.string.x_people_like));
        holder.price.setText(mContext.getString(R.string.RMB) + (float)entity.getPrice() / 100);
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.idle_image_iv);
        holder.name = (TextView) convertView.findViewById(R.id.idle_name_tv);
        holder.publisher = (TextView) convertView.findViewById(R.id.publisher_name_tv);
        holder.interest = (TextView) convertView.findViewById(R.id.view_num_tv);
        holder.price = (TextView) convertView.findViewById(R.id.price_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_my_idle_collection;
    }

    public static class ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView publisher;
        public TextView interest;
        public TextView price;
    }
}
