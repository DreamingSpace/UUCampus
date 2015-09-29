package com.dreamspace.uucampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamspace.uucampus.R;

/**
 * Created by zsh on 2015/9/18.
 */
public class MyCollectionGoodsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Map<String, Object>> mListItem;
    private String[] good_name = new String[]{"11111","22222"};
    private String[] market_name = new String[]{"11111","22222"};
    private String[] look_num = new String[]{"0人已浏览","0人已浏览"};
    private String[] thumb_up = new String[]{"0人点赞","0人点赞"};
    private String[] money = new String[]{"￥230","￥230"};
    private String[] privilege = new String[]{"5-10人，每人45元；11-20人，每人40元；21人以上每人35元。",
            "5-10人，每人45元；11-20人，每人40元；21人以上每人35元。"};

    public MyCollectionGoodsAdapter(Context mContext) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        mListItem = getData();
    }
    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<good_name.length;i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("good_name",good_name[i]);
            map.put("market_name",market_name[i]);
            map.put("look_num",look_num[i]);
            map.put("thumb_up",thumb_up[i]);
            map.put("money",money[i]);
            map.put("privilege",privilege[i]);
            list.add(map);
        }
        return list;
    }
    @Override
    public int getCount() {
        return mListItem.size();
    }
    @Override
    public Object getItem(int arg0) {
        return null;
    }
    @Override
    public long getItemId(int arg0) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.person_my_collection_listview_item,null);
            holder.my_good_name = (TextView)convertView.findViewById(R.id.good_name);
            holder.my_market_name = (TextView)convertView.findViewById(R.id.market_name);
            holder.my_look_num = (TextView)convertView.findViewById(R.id.look_num);
            holder.my_thumb_up = (TextView)convertView.findViewById(R.id.thumb_up);
            holder.my_money = (TextView)convertView.findViewById(R.id.money);
            holder.my_privilege = (TextView)convertView.findViewById(R.id.privilege);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.my_good_name.setText((String)mListItem.get(position).get("good_name"));
        holder.my_market_name.setText((String)mListItem.get(position).get("market_name"));
        holder.my_look_num.setText((String)mListItem.get(position).get("look_num"));
        holder.my_thumb_up.setText((String)mListItem.get(position).get("thumb_up"));
        holder.my_money.setText((String)mListItem.get(position).get("money"));
        holder.my_privilege.setText((String) mListItem.get(position).get("privilege"));

        return convertView;
    }
    public class ViewHolder {
        TextView my_good_name,my_market_name,my_look_num,my_thumb_up,my_privilege,my_money;
        ImageView good_img;
    }
}
