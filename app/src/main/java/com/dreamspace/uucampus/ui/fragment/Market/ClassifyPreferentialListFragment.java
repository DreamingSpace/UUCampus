package com.dreamspace.uucampus.ui.fragment.Market;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.market.CatalogListAdapter;
import com.dreamspace.uucampus.adapter.market.PreferentialListAdapter;
import com.dreamspace.uucampus.ui.activity.Market.ShopManagmentAct;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/23.
 */
public class ClassifyPreferentialListFragment extends BaseLazyFragment{
    @Bind(R.id.preferential_catalog_lv)
    ListView listView;
    @Bind(R.id.new_edit_catalog_ll)
    LinearLayout newEditCataLl;
    @Bind(R.id.finish_edit_btn)
    Button finishEditBtn;
    @Bind(R.id.new_catalog_btn)
    Button newCatalogBtn;
    @Bind(R.id.edit_catalog_btn)
    Button editCatalogBtn;

    private PreferentialListAdapter preAdapter;
    private CatalogListAdapter catalogAdapter;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle == null? "null":bundle.getString(ShopManagmentAct.MANAGEMENT_TYPE);
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        if(type.equals(getResources().getString(R.string.catalog_list))){
            initCatalogViews();
            initCatalogListeners();
        }else if(type.equals(getResources().getString(R.string.preferential_list))){
            initPreferentialViews();
            initPreferentialListeners();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_goods_classify_preferential;
    }

    private void initCatalogViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            list.add(i+"");
        }
        catalogAdapter = new CatalogListAdapter(mContext,list,CatalogListAdapter.ViewHolder.class);
        listView.setAdapter(catalogAdapter);
        newEditCataLl.setVisibility(View.VISIBLE);
        finishEditBtn.setVisibility(View.INVISIBLE);
    }

    private void initPreferentialViews(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            list.add(i+"");
        }
        preAdapter = new PreferentialListAdapter(mContext,list,PreferentialListAdapter.ViewHolder.class);
        listView.setAdapter(preAdapter);
        newEditCataLl.setVisibility(View.INVISIBLE);
        finishEditBtn.setVisibility(View.INVISIBLE);
    }

    private void initCatalogListeners(){
        editCatalogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogAdapter.showEdit(true);
            }
        });

        newCatalogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShowNewCataDialog();
            }
        });
    }

    private void initPreferentialListeners(){

    }

    private void createShowNewDisDialog(){
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.alterdialog_new_preferential,null);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.new_preferential_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.new_preferential_confirm_ll);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog newDiscountDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiscountDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiscountDialog.dismiss();
            }
        });
        newDiscountDialog.show();
    }

    private void createShowNewCataDialog(){
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.alterdialog_new_catalog, null);
        LinearLayout cancelLl = (LinearLayout) dialogView.findViewById(R.id.new_catalog_cancel_ll);
        LinearLayout confirmLl = (LinearLayout) dialogView.findViewById(R.id.new_catalog_confirm_ll);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog newCataDialog = builder.setView(dialogView).create();

        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCataDialog.dismiss();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCataDialog.dismiss();
            }
        });
        newCataDialog.show();
    }
}
