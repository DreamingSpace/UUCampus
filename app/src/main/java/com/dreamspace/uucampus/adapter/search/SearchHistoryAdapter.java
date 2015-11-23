package com.dreamspace.uucampus.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;

import java.util.List;

/**
 * Created by money on 2015/11/23.
 */
public class SearchHistoryAdapter extends BasisAdapter<String,SearchHistoryAdapter.ViewHolder>{

    public SearchHistoryAdapter(Context mContext,List<String> mEntities,Class classType ){
        super(mContext,mEntities,classType);
    }

    @Override
    protected void setDataIntoView(SearchHistoryAdapter.ViewHolder holder, String entity) {
        holder.searchHistoryTv.setText(entity);
    }

    @Override
    protected void initViewHolder(View convertView, SearchHistoryAdapter.ViewHolder holder) {
        holder.searchHistoryTv = (TextView)convertView.findViewById(R.id.search_history_item_text);
    }

    @Override
    public int getItemLayout() {
        return R.layout.search_history_item;
    }

    public static class ViewHolder{
        TextView searchHistoryTv;
    }
}
