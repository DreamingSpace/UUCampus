package com.dreamspace.uucampus.adapter.FreeGoods;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.FreeGoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/27 0027.
 */
public class FreeGoodsItemAdapter extends BasisAdapter<FreeGoodsInfo, FreeGoodsItemAdapter.viewHolder> {


    public FreeGoodsItemAdapter(Context context){
        super(context,new ArrayList<FreeGoodsInfo>(),viewHolder.class);
    }
    public FreeGoodsItemAdapter(List<FreeGoodsInfo> mFreeGoodsInfoList, Context context) {
        super(context, mFreeGoodsInfoList, viewHolder.class);
    }

    @Override
    protected void setDataIntoView(viewHolder holder, FreeGoodsInfo entity) {
//        holder.mTextView.setText(entity.getCourseName());
//        holder.mTextView.setText("Hello Tab!");
    }

    @Override
    protected void initViewHolder(View convertView, viewHolder holder) {
//        holder.mTextView = (TextView) convertView.findViewById(R.id.course_name_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_free_goods;
    }

    public static class viewHolder {

        public TextView mTextView;
    }

}

