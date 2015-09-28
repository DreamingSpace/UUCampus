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
            holder.listView = (ExpandableListView)convertView.findViewById(R.id.expandablelistview);
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
        //holder.listView.setGroupIndicator(null);
        final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            int[] group_logo_array = new int[]{R.drawable.gerenxiaodian_icon_collect};
            // 一级标签上的标题数据源
            private String[] group_title_arry = new String[]{"5-10人，每人45元；11-20人，每人40元；21人以上每人35元。"};
            // 子视图显示文字
            private String[][] child_text_array_name = new String[][]{{"汤山温泉两天一夜游"}};
            private String[][] child_text_array_store = new String[][]{{"康辉旅行社"}};
            private String[][] child_text_array_look = new String[][]{{"65人已浏览"}};
            private String[][] child_text_array_thumb_up = new String[][]{{"15人赞"}};
            private String[][] child_text_array_coupons = new String[][]{{"5-10人，每人45元；11-20人，每人40元；21人以上每人35元."}};
            private String[][] child_text_array_price = new String[][]{{"230"}};

            // 重写 ExpandableListAdapter 中的各个方法
            @Override
            public int getGroupCount() {
                return group_title_arry.length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return group_title_arry[groupPosition];
            }

            //获取一级标签的ID
            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            //获取一级标签下二级标签的总数
            @Override
            public int getChildrenCount(int groupPosition) {
                return child_text_array_store[groupPosition].length;
            }

            //获取一级标签下二级标签的内容
            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return child_text_array_store[groupPosition][childPosition];
            }

            // 获取二级标签的ID
            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            //指定位置相应的组视图
            @Override
            public boolean hasStableIds() {
                return true;
            }

            //对一级标签进行设置
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                // 为视图对象指定布局
                convertView = (LinearLayout) LinearLayout.inflate(mContext, R.layout.person_group_item, null);

                // 新建一个ImageView对象，用来显示一级标签上的图片
                ImageView group_logo = (ImageView) convertView.findViewById(R.id.img_group);
                // 新建一个TextView对象，用来显示一级标签上的标题信息
                TextView group_title = (TextView) convertView.findViewById(R.id.text_group);
                // 设置要显示的图片
                group_logo.setBackgroundResource(group_logo_array[groupPosition]);
                // 设置标题上的文本信息
                group_title.setText(group_title_arry[groupPosition]);

                return convertView;
            }

            //对一级标签下的二级标签进行设置
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                // 为视图对象指定布局
                convertView = (RelativeLayout) RelativeLayout.inflate(mContext, R.layout.person_child_item, null);

                // 新建一个 TextView 对象，用来显示具体内容
                TextView child_text1 = (TextView) convertView.findViewById(R.id.goods_name);
                TextView child_text2 = (TextView) convertView.findViewById(R.id.store_name);
                TextView child_text3 = (TextView) convertView.findViewById(R.id.look_num);
                TextView child_text4 = (TextView) convertView.findViewById(R.id.thumb_up);
                TextView child_text5 = (TextView) convertView.findViewById(R.id.price);
                Button child_button = (Button) convertView.findViewById(R.id.button);
                // 设置要显示的文本信息
                child_text1.setText(child_text_array_name[groupPosition][childPosition]);
                child_text2.setText(child_text_array_store[groupPosition][childPosition]);
                child_text3.setText(child_text_array_look[groupPosition][childPosition]);
                child_text4.setText(child_text_array_thumb_up[groupPosition][childPosition]);
                child_text5.setText(child_text_array_price[groupPosition][childPosition]);
                child_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };
        holder.listView.setAdapter(adapter);
        if(holder.listView.isSelected()){
            notifyDataSetChanged();
        }
        return convertView;
    }
    public class ViewHolder {
        TextView my_good_name,my_market_name,my_look_num,my_thumb_up,my_privilege,my_money;
        ImageView good_img;
        ExpandableListView listView;
    }
}
