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
public class CatalogListAdapter extends BasisAdapter<String,CatalogListAdapter.ViewHolder> {
    private boolean showEdit = false;
    public CatalogListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
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
        holder.catalogInfo = (TextView) convertView.findViewById(R.id.catalog_info_tv);
        holder.catalogEdit = (ImageView) convertView.findViewById(R.id.catalog_edit_iv);
        holder.catalogDelete = (ImageView) convertView.findViewById(R.id.catalog_delete_iv);
        holder.editLl = (LinearLayout) convertView.findViewById(R.id.catalog_edit_delete_ll);
    }

    @Override
    public int getItemLayout() {
        return R.layout.catalog_list_item;
    }

    public static class ViewHolder{
        public TextView catalogInfo;
        public ImageView catalogEdit;
        public ImageView catalogDelete;
        public LinearLayout editLl;
    }
}
