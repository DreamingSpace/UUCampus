package com.dreamspace.uucampus.widget.photopicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.model.entity.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX on 2015/8/8 0008.
 */
public class ImageFolderListAdapter extends BaseAdapter {
    private int lastSelected = 0;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<Folder> mFolders = new ArrayList<>();

    public ImageFolderListAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Folder> folders) {
        if(folders != null && folders.size()>0){
            mFolders = folders;
        }else{
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    private int getTotalImageSize(){
        int result = 0;
        if(mFolders != null && mFolders.size()>0){
            for (Folder f: mFolders){
                result += f.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if(lastSelected == i)
            return;
        lastSelected = i;
    }

    public int getSelectIndex(){
        return lastSelected;
    }

    @Override
    public int getCount() {
        return mFolders.size() + 1;
    }

    @Override
    public Folder getItem(int position) {
        if(position == 0) return null;
        return mFolders.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.select_image_folder_list_item,parent,false);
            holder = new ViewHolder();
            holder.cover = (ImageView) convertView.findViewById(R.id.folder_list_item_cover);
            holder.name = (TextView) convertView.findViewById(R.id.folder_name);
            holder.size = (TextView) convertView.findViewById(R.id.folder_size);
            holder.indicator = (ImageView) convertView.findViewById(R.id.folder_list_item_indicator);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position == 0){
            holder.name.setText(mContext.getResources().getString(R.string.all_image));
            holder.size.setText(getTotalImageSize() + "张");
            if(mFolders.size()>0){
                Folder f = mFolders.get(0);
                Glide.with(mContext)
                        .load(new File(f.cover.path))
                        .error(R.drawable.default_error)
                        .centerCrop()
                        .into(holder.cover);
            }
        }else{
            Folder folder = getItem(position);
            holder.name.setText(folder.name);
            holder.size.setText(folder.images.size()+"张");
            Glide.with(mContext)
                    .load(new File(folder.cover.path))
                    .error(R.drawable.default_error)
                    .centerCrop()
                    .into(holder.cover);
        }

        if(lastSelected == position){
            holder.indicator.setVisibility(View.VISIBLE);
        }else {
            holder.indicator.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;
    }
}
