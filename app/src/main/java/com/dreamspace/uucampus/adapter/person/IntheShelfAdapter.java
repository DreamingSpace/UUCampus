package com.dreamspace.uucampus.adapter.person;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;
import com.dreamspace.uucampus.model.person.MyUselessData;
import com.dreamspace.uucampus.ui.person.EditGoodsInformationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2015/9/17.
 */
public class IntheShelfAdapter extends BasisAdapter<MyUselessData,IntheShelfAdapter.ViewHolder> {
    private Context mContext;
    public IntheShelfAdapter(Context mContext){
        this(mContext, new ArrayList<MyUselessData>());
    }
    public IntheShelfAdapter(Context mContext, List<MyUselessData> mEntities) {
        super(mContext, mEntities, ViewHolder.class);
    }

    @Override
    public int getItemLayout() {
        return R.layout.person_my_useless_ontheshelf_listview_item;
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.my_useless_name = (TextView) convertView.findViewById(R.id.my_useless_name);
        holder.my_useless_price = (TextView) convertView.findViewById(R.id.my_useless_price);
        holder.my_useless_img = (ImageView) convertView.findViewById(R.id.my_useless_goods);
        holder.edit_button = (Button) convertView.findViewById(R.id.my_useless_edit_button);
        holder.outofshelf_button = (Button) convertView.findViewById(R.id.my_useless_ontheshelf_button);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, MyUselessData entity) {
        holder.my_useless_name.setText(entity.getGoodsName());
        holder.my_useless_price.setText(entity.getGoodsPrice());
        holder.edit_button.setText("编 辑");
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,EditGoodsInformationActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.outofshelf_button.setText("下 架");
        holder.outofshelf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public static class ViewHolder {
        private TextView my_useless_name;
        private TextView my_useless_price;
        private Button edit_button;
        private Button outofshelf_button;
        private ImageView my_useless_img;
    }
}
