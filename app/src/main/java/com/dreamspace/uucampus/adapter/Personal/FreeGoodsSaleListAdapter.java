package com.dreamspace.uucampus.adapter.Personal;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;

import java.util.List;

/**
 * Created by Lx on 2015/10/17.
 */
public class FreeGoodsSaleListAdapter extends BasisAdapter<String,FreeGoodsSaleListAdapter.ViewHolder>{

    public FreeGoodsSaleListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.good_image_iv);
        holder.name = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.price = (TextView) convertView.findViewById(R.id.good_price_tv);
        holder.edit = (Button) convertView.findViewById(R.id.edit_btn);
        holder.pullOff = (Button) convertView.findViewById(R.id.pull_off_btn);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_my_free_goods_sale;
    }

    public static class ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView price;
        public Button edit;
        public Button pullOff;
    }
}
