package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;

import java.util.List;

/**
 * Created by Lx on 2015/10/12.
 */
public class GoodDetailInstListAdapter extends BasisAdapter<String,GoodDetailInstListAdapter.ViewHolder> {
    public GoodDetailInstListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.condition = (TextView) convertView.findViewById(R.id.good_detail_condition);
        holder.content = (TextView) convertView.findViewById(R.id.good_detail_info);
    }

    @Override
    public int getItemLayout() {
        return R.layout.good_detail_inst_list_item;
    }

    public static class ViewHolder{
        public TextView condition;
        public TextView content;
    }
}
