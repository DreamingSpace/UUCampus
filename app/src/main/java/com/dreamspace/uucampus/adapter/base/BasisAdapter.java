package com.dreamspace.uucampus.adapter.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3 0003.
 */
public abstract class BasisAdapter<T, K> extends BaseAdapter {
    private List<T> mEntities;
    private Context mContext;
    private Class<K> classType;


    public void setmEntities(List<T> mEntities) {
        this.mEntities = mEntities;
    }

    public BasisAdapter(Context mContext, List<T> mEntities, Class<K> classType) {
        this.mContext = mContext;
        this.mEntities = mEntities;
        this.classType = classType;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public T getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        K holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
            try {
                holder = classType.newInstance();
                Log.i("INFO", "Complete");
            } catch (InstantiationException e) {
                Log.i("INFO", "InstantiationException");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.i("INFO", "IllegalAccessException");
                e.printStackTrace();
            }
            initViewHolder(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (K) convertView.getTag();
        }
        T entity = mEntities.get(position);
        setDataIntoView(holder, entity);
        return convertView;
    }

    protected abstract void setDataIntoView(K holder, T entity);

    protected abstract void initViewHolder(View convertView, K holder);

    public abstract int getItemLayout();

    public void removeItem(int position) {
//        Animation animation= AnimationUtils.loadAnimation(mContext,android.R.anim.slide_out_right);
//        view.startAnimation(animation);
        mEntities.remove(position);
        notifyDataSetChanged();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mEntities.remove(position);
//                notifyDataSetChanged();
//            }
//        },2000);
    }

    public Context getmContext() {
        return mContext;
    }

    public void addEntities(List<T> mEntities){
        this.mEntities.addAll(mEntities);
    }

    public List<T> getmEntities(){
        return  mEntities;
    }
}
