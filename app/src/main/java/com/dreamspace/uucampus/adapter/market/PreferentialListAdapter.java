package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;

import java.util.List;

/**
 * Created by Lx on 2015/9/23.
 */
public class PreferentialListAdapter extends BasisAdapter<String,PreferentialListAdapter.ViewHolder> {
    private boolean showEdit = false;
    public PreferentialListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    public void showEdit(boolean showEdit){
        this.showEdit = showEdit;
        notifyDataSetChanged();
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {
        if(showEdit){
            holder.editLl.setVisibility(View.VISIBLE);
        }else{
            holder.editLl.setVisibility(View.GONE);
        }

        setListeners(holder);
    }

    private void setListeners(ViewHolder holder){

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.preferentialInfo = (TextView) convertView.findViewById(R.id.preferential_info_tv);
        holder.preferentialEdit = (ImageView) convertView.findViewById(R.id.preferential_edit_iv);
        holder.preferentialDelete = (ImageView) convertView.findViewById(R.id.preferential_delete_iv);
        holder.editLl = (LinearLayout) convertView.findViewById(R.id.preferential_edit_delete_ll);
    }

    @Override
    public int getItemLayout() {
        return R.layout.preferential_list_item;
    }

    public static class ViewHolder{
        public TextView preferentialInfo;
        public ImageView preferentialEdit;
        public ImageView preferentialDelete;
        public LinearLayout editLl;
    }
}
