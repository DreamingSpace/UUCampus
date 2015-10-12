package com.dreamspace.uucampus.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.GoodsCommentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/9/24.
 */
public class GoodsCommentItemAdapter extends BasisAdapter<GoodsCommentItem, GoodsCommentItemAdapter.viewHolder>{

    public GoodsCommentItemAdapter(Context context){
        super(context,new ArrayList<GoodsCommentItem>(),viewHolder.class);
    }

    public GoodsCommentItemAdapter(Context mContext, List<GoodsCommentItem> mEntities, Class<viewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(viewHolder holder, GoodsCommentItem entity) {

    }

    @Override
    protected void initViewHolder(View convertView, viewHolder holder) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_comment;
    }

    public static class viewHolder{
        public TextView textView;
    }

}
