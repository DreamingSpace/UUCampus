package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.api.AllGoodsCommentItemRes;
import com.dreamspace.uucampus.widget.RatingBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lx on 2015/10/12.
 */
public class GoodDetailCommentListAdapter extends BasisAdapter<AllGoodsCommentItemRes,GoodDetailCommentListAdapter.ViewHolder> {
    private Context mContext;
    private OnUsefulClickListener onUsefulClickListener;

    public GoodDetailCommentListAdapter(Context mContext, List<AllGoodsCommentItemRes> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
        this.mContext = mContext;
    }

    @Override
    protected void setDataIntoView(final ViewHolder holder, final AllGoodsCommentItemRes entity) {
        CommonUtils.showImageWithGlideInCiv(mContext, holder.avater, entity.getImage());
        holder.userName.setText(entity.getName());
        holder.publishTime.setText(entity.getDate());
        holder.commentContent.setText(entity.getContent());
        holder.commentRating.setmClickable(false);
        holder.commentRating.setStar(entity.getScore());
        holder.userful.setText(mContext.getString(R.string.useful) + entity.getUseful_number() + mContext.getString(R.string.useful2));
        if(entity.getUseful_number() == 1){
            holder.like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.comment_like_icon_p));
            holder.userful.setTextColor(mContext.getResources().getColor(R.color.app_theme_color));
        }else{
            holder.like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.comment_like_icon));
            holder.userful.setTextColor(mContext.getResources().getColor(R.color.text_normal));
        }

        //有用点击，调用回调函数
        holder.usefulLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onUsefulClickListener != null){
                    onUsefulClickListener.onUserfulClick(entity.getId(),entity.getUseful_clicked(),holder.like,holder.userful,entity);
                }
            }
        });
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.avater = (CircleImageView) convertView.findViewById(R.id.user_avatar_civ);
        holder.userName = (TextView) convertView.findViewById(R.id.user_name_tv);
        holder.publishTime = (TextView) convertView.findViewById(R.id.publis_time_tv);
        holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content_tv);
        holder.userful = (TextView) convertView.findViewById(R.id.userful_tv);
        holder.commentRating = (RatingBar) convertView.findViewById(R.id.comment_rating_bar);
        holder.like = (ImageView) convertView.findViewById(R.id.comment_like_iv);
        holder.usefulLl = (LinearLayout) convertView.findViewById(R.id.comment_useful_ll);
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
        RatingBar commentRating;
        ImageView like;
        LinearLayout usefulLl;
    }

    public void changeEntity(AllGoodsCommentItemRes entity){
//        setmEntities();
    }
    public interface OnUsefulClickListener{
        void onUserfulClick(String commentId,int usefulClick,ImageView likeIv,TextView likeTv,AllGoodsCommentItemRes entity);
    }

    public void setOnUsefulClickListener(OnUsefulClickListener onUsefulClickListener) {
        this.onUsefulClickListener = onUsefulClickListener;
    }
}
