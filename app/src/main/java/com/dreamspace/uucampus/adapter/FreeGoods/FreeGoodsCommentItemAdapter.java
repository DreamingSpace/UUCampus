package com.dreamspace.uucampus.adapter.FreeGoods;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.FreeGoodsCommentItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wufan on 2015/9/24.
 */
public class FreeGoodsCommentItemAdapter extends BasisAdapter<FreeGoodsCommentItem, FreeGoodsCommentItemAdapter.viewHolder> {

    private UpdateData updateData = null;

    public FreeGoodsCommentItemAdapter(Context context) {
        super(context, new ArrayList<FreeGoodsCommentItem>(), viewHolder.class);
    }

    public FreeGoodsCommentItemAdapter(Context mContext, List<FreeGoodsCommentItem> mEntities, Class<viewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(final viewHolder holder, final FreeGoodsCommentItem entity) {
        CommonUtils.showImageWithGlide(getmContext(), holder.mUserIv, entity.getImage());
        holder.mUserNameTv.setText(entity.getName());
        holder.mContentTv.setText(entity.getContent());
        holder.mUsefulTv.setText(String.valueOf(entity.getUseful_number()));
        holder.mDateTv.setText(entity.getDate());
        //初始化图标
        if (entity.isUseful_clicked()) {   //当前评论是否点击
            holder.mUsefulIv.setImageResource(R.drawable.comment_like_icon_p);

        } else {
            holder.mUsefulIv.setImageResource(R.drawable.comment_like_icon);
        }

        holder.mUsefulLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData.updateUsefulData(entity.getId(),entity.isUseful_clicked(), holder.mUsefulTv, holder.mUsefulIv, entity);
            }
        });
    }

    @Override
    protected void initViewHolder(View convertView, viewHolder holder) {
        holder.mUserIv = (CircleImageView) convertView.findViewById(R.id.free_goods_detail_comment_user_iv);
        holder.mUserNameTv = (TextView) convertView.findViewById(R.id.free_goods_detail_comment_user_name_tv);
        holder.mDateTv = (TextView) convertView.findViewById(R.id.free_goods_detail_comment_data_tv);
        holder.mContentTv = (TextView) convertView.findViewById(R.id.free_goods_detail_comment_content_tv);
        holder.mUsefulTv = (TextView) convertView.findViewById(R.id.free_goods_detail_comment_useful_tv);
        holder.mUsefulLl = (LinearLayout) convertView.findViewById(R.id.free_goods_detail_comment_useful_linear_layout);
        holder.mUsefulIv = (ImageView) convertView.findViewById(R.id.free_goods_detail_comment_useful_iv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_comment;
    }

    public static class viewHolder {
        private CircleImageView mUserIv;
        private TextView mUserNameTv;
        private TextView mDateTv;
        private TextView mContentTv;
        private TextView mUsefulTv;
        private LinearLayout mUsefulLl;
        private ImageView mUsefulIv;
    }

    public interface UpdateData {
        void updateUsefulData(String comment_id, boolean useful_clicked, TextView mUsefulTv, ImageView mUserIv, FreeGoodsCommentItem entity);
    }

    public void setUpdateData(UpdateData updateData) {
        this.updateData = updateData;
    }

}
