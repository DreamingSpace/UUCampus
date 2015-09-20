package com.dreamspace.uucampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsh on 2015/9/17.
 */
public class IntheShelfAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Map<String,Object>> mListItem;
    private String[] name = new String[]{"1111111","2222222"};
    private String[] price = new String[]{"30yuan","30yuam"};

    public IntheShelfAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        mListItem = getData();
    }
    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<name.length;i++)
        {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name", name[i]);
            map.put("price", price[i]);
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_my_useless_ontheshelf_item, null);
            holder.my_useless_name = (TextView) convertView.findViewById(R.id.my_useless_name);
            holder.my_useless_price = (TextView) convertView.findViewById(R.id.my_useless_price);
            holder.my_useless_img = (ImageView) convertView.findViewById(R.id.my_useless_goods);
            holder.edit_button = (Button) convertView.findViewById(R.id.my_useless_edit_button);
            holder.outofshelf_button = (Button) convertView.findViewById(R.id.my_useless_ontheshelf_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.my_useless_name.setText((String) mListItem.get(position).get("name"));
        holder.my_useless_price.setText((String) mListItem.get(position).get("price"));
        //
        holder.my_useless_img.setTag(position);

        holder.edit_button.setTag(position);
        holder.edit_button.setText("编 辑");
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.outofshelf_button.setTag(position);
        holder.outofshelf_button.setText("下 架");
        holder.outofshelf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
    public class ViewHolder {
        private TextView my_useless_name;
        private TextView my_useless_price;
        private Button edit_button;
        private Button outofshelf_button;
        private ImageView my_useless_img;
    }
}
