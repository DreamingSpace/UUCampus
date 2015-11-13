package com.dreamspace.uucampus.adapter.FreeGoods;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.FreeGoodsCommentItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wufan on 2015/9/24.
 */
public class FreeGoodsCommentItemAdapter extends BasisAdapter<FreeGoodsCommentItem, FreeGoodsCommentItemAdapter.viewHolder> {

//    private int mUseful;
//    private boolean bUseful = false;
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
        holder.bUseful =entity.isUseful_clicked();    //获取当前评论是否点击
        if ( holder.bUseful) {
            holder.mUsefulIv.setImageResource(R.drawable.comment_like_icon_p);

        } else {
            holder.mUsefulIv.setImageResource(R.drawable.comment_like_icon);
        }

        holder.mUseful = entity.getUseful_number();
        holder.mUsefulLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLog.i("当前评论状态:", " comment_id" + entity.getId() + "评论是否点击" +  holder.bUseful + " number" + holder.mUseful);
                if ( holder.bUseful) {    //取消有用
                    holder.bUseful = false;
                    holder.mUseful-=1;
                    //界面更新
                    holder.mUsefulTv.setText(String.valueOf(holder.mUseful));
                    holder.mUsefulIv.setImageResource(R.drawable.xiangqing_comment_btn_dianzan);
                    //缓冲数据更新
                    entity.setUseful_clicked(holder.bUseful);
                    entity.setUseful_number(holder.mUseful);
                    //后端数据更新
                    if (updateData != null) {
                        updateData.updateUsefulData(entity.getId(),  holder.bUseful);
                    }
                } else {          //添加有用
                    holder.bUseful = true;
                    holder.mUseful += 1;
                    holder.mUsefulTv.setText(String.valueOf(holder.mUseful));
                    holder.mUsefulIv.setImageResource(R.drawable.xiangqing_comment_btn_dianzan_p);
                    entity.setUseful_clicked(holder.bUseful);
                    entity.setUseful_number(holder.mUseful);
                    if (updateData != null) {
                        updateData.updateUsefulData(entity.getId(),  holder.bUseful);
                    }
                }
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

        private Boolean bUseful;
        private int mUseful;
    }

    public interface UpdateData {
        void updateUsefulData(String comment_id, boolean bUseful);
    }

    public void setUpdateData(UpdateData updateData) {
        this.updateData = updateData;
    }

}
