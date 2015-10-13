package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lx on 2015/10/12.
 */
public class GoodDetailCommentListAdapter extends BasisAdapter<String,GoodDetailCommentListAdapter.ViewHolder> {


    public GoodDetailCommentListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.avater = (CircleImageView) convertView.findViewById(R.id.user_avatar_civ);
        holder.userName = (TextView) convertView.findViewById(R.id.user_name_tv);
        holder.publishTime = (TextView) convertView.findViewById(R.id.publis_time_tv);
        holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content_tv);
        holder.userful = (TextView) convertView.findViewById(R.id.userful_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.comment_list_item;
    }

    public static class ViewHolder{
        CircleImageView avater;
        TextView userName;
        TextView publishTime;
        TextView commentContent;
        TextView userful;
    }
}
