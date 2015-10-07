package com.dreamspace.uucampus.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.CommentInfo;

import java.util.List;

/**
 * Created by wufan on 2015/9/24.
 */
public class GoodsCommentItemAdapter extends BasisAdapter<CommentInfo, GoodsCommentItemAdapter.viewHolder>{

    public GoodsCommentItemAdapter(Context mContext, List<CommentInfo> mEntities, Class<viewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(viewHolder holder, CommentInfo entity) {

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
