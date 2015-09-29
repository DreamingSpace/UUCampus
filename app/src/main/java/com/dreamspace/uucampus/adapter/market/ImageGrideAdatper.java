package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.entity.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX on 2015/8/7 0007.
 */
public class ImageGrideAdatper extends BaseAdapter {
    private final int CAMERA_TYPE = 0;
    private final int IMAGE_TYPE = 1;
    private int selectIndex;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Image> mImages = new ArrayList<>();
    private Image mSelectImage;
    private int mItemSize;
    private GridView.LayoutParams mItemLayoutParams;

    public ImageGrideAdatper(Context context){
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }

    public void seclect(int position){
        if(position > 0){
            if(mSelectImage==null || selectIndex != position){
                mSelectImage = getItem(position);
                selectIndex = position;
            }else{
                mSelectImage = null;
                selectIndex = mImages.size() + 1;
            }
        }
        notifyDataSetChanged();
    }

    public Image getSelectImage(){
        return mSelectImage;
    }

    public void setData(List<Image> images){
        mImages.clear();
        if(images != null && images.size()>0){
            mImages = images;
        }else{
            mImages.clear();
        }
        selectIndex = mImages.size() + 1;
        mSelectImage = null;
        notifyDataSetChanged();
    }

    public void setItemSize(int columnWidth) {

        if(mItemSize == columnWidth){
            return;
        }

        mItemSize = columnWidth;

        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);

        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return CAMERA_TYPE;
        }else {
            return IMAGE_TYPE;
        }
    }

    @Override
    public int getCount() {
        return mImages.size() + 1;
    }

    @Override
    public Image getItem(int position) {
        if(position == 0){
            return null;
        }else{
            return mImages.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if(type == CAMERA_TYPE){
            convertView = layoutInflater.inflate(R.layout.grid_view_camera_item,parent,false);
        }else if(type == IMAGE_TYPE){
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.grid_view_image_item,parent,false);
                holder.bgIv = (ImageView) convertView.findViewById(R.id.gride_view_image_bg_iv);
                holder.imageIv = (ImageView) convertView.findViewById(R.id.gride_view_image_iv);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
                if(holder == null){
                    convertView = layoutInflater.inflate(R.layout.grid_view_image_item,parent,false);
                    holder = new ViewHolder();
                    holder.bgIv = (ImageView) convertView.findViewById(R.id.gride_view_image_bg_iv);
                    holder.imageIv = (ImageView) convertView.findViewById(R.id.gride_view_image_iv);
                    convertView.setTag(holder);
                }
            }

            if(position == selectIndex){
                holder.bgIv.setBackgroundResource(R.color.app_theme_color);
            }else{
                holder.bgIv.setBackgroundResource(R.color.white);
            }
            File imageFile = new File(getItem(position).path);

            if(mItemSize > 0){
                Glide.with(mContext)
                        .load(imageFile)
                        .centerCrop()
                        .placeholder(R.drawable.default_error)
                        .into(holder.imageIv);
            }
        }

        GridView.LayoutParams lp = (GridView.LayoutParams) convertView.getLayoutParams();
        if(lp.height != mItemSize){
            convertView.setLayoutParams(mItemLayoutParams);
        }
        return convertView;
    }

    class ViewHolder{
        public ImageView bgIv;
        public ImageView imageIv;
    }
}
