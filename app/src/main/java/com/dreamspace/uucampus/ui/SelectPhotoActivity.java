package com.dreamspace.uucampus.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.ImageFolderListAdapter;
import com.dreamspace.uucampus.adapter.ImageGrideAdatper;
import com.dreamspace.uucampus.common.utils.FileUtils;
import com.dreamspace.uucampus.model.entity.Folder;
import com.dreamspace.uucampus.model.entity.Image;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Lx on 2015/9/28.
 */
public class SelectPhotoActivity extends AbsActivity {
    @Bind(R.id.photos_gv)
    GridView photoGv;
    @Bind(R.id.select_photo_bottom_bar)
    RelativeLayout bottomBar;
    @Bind(R.id.photo_folder_catalog_btn)
    Button folderBtn;
    @Bind(R.id.photo_preview_btn)
    Button previewBtn;
    @Bind(R.id.select_photo_shadow)
    RelativeLayout shadow;
    private static final int LOADER_ID = 0;
    public static final int REQUEST_CAMERA = 1;
    public static final String PHOTO_PATH = "image_path";
    private ArrayList<Folder> mResultFolders = new ArrayList<>();
    private ImageGrideAdatper imageGrideAdatper;
    private ImageFolderListAdapter folderAdapter;
    private ListPopupWindow folderPopupWindow;
    private int gvWidth,gvHeight;
    private File mTmpFile;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_photo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.select_finish){
            Image image = imageGrideAdatper.getSelectImage();
            if(image == null){
                showToast(getResources().getString(R.string.plz_select_photo));
            }else{
                Intent intent = new Intent();
                intent.putExtra(PHOTO_PATH,image.path);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void prepareDatas() {
        getSupportLoaderManager().initLoader(LOADER_ID,null,mLoaderCallBack);
        imageGrideAdatper = new ImageGrideAdatper(SelectPhotoActivity.this);
        photoGv.setAdapter(imageGrideAdatper);
        folderAdapter = new ImageFolderListAdapter(this);
    }

    @Override
    protected void initViews() {

        initListeners();
    }

    private void initListeners(){
        photoGv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = photoGv.getWidth();
                int height = photoGv.getHeight();

                gvHeight = height;
                gvWidth = width;

                int columnWidth = (width - photoGv.getHorizontalSpacing() * 2) / 3;
                imageGrideAdatper.setItemSize(columnWidth);
                photoGv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        photoGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    showCameraAction();
                } else {
                    imageGrideAdatper.seclect(position);
                    if (imageGrideAdatper.getSelectImage() != null) {
                        previewBtn.setEnabled(true);
                    } else {
                        previewBtn.setEnabled(false);
                    }
                }
            }
        });

        folderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderPopupWindow == null) {
                    createPopupFolderList(gvWidth, gvHeight);
                }
                folderPopupWindow.show();
                shadow.setVisibility(View.VISIBLE);
                int index = folderAdapter.getSelectIndex();
                index = index == 0 ? index : index - 1;
                folderPopupWindow.getListView().setSelection(index);
            }
        });

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPhotoActivity.this, PhotoPreviewActivity.class);
                intent.putExtra(PhotoPreviewActivity.PREVIEW_PHOTO, imageGrideAdatper.getSelectImage().path);
                startActivity(intent);
            }
        });
    }

    private void createPopupFolderList(int width, int height){
        folderPopupWindow = new ListPopupWindow(this);
        folderPopupWindow.setAdapter(folderAdapter);
        folderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        folderPopupWindow.setContentWidth(width);
        folderPopupWindow.setWidth(width);
        folderPopupWindow.setHeight(height * 3 / 4);
        folderPopupWindow.setAnchorView(bottomBar);
        folderPopupWindow.setModal(true);
        folderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                previewBtn.setEnabled(false);
                folderAdapter.setSelectIndex(position);
                folderPopupWindow.dismiss();
                getSupportLoaderManager().restartLoader(LOADER_ID, null, mLoaderCallBack);
                if (position == 0) {
                    folderBtn.setText(getResources().getString(R.string.all_photos));
                } else {
                    Folder folder = (Folder) parent.getAdapter().getItem(position);
                    folderBtn.setText(folder.name);
                    imageGrideAdatper.setData(folder.images);
                    photoGv.smoothScrollToPosition(0);
                }

            }
        });

        folderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                shadow.setVisibility(View.INVISIBLE);
            }
        });
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBack = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID };
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if(id == LOADER_ID) {
                CursorLoader cursorLoader = new CursorLoader(SelectPhotoActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            ArrayList<Image> allImages = new ArrayList<>();
            if(data != null){
                mResultFolders.clear();

                if(data.getCount() > 0){
                    data.moveToFirst();
                    do{
                        String path = data.getString(data.getColumnIndex(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndex(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndex(IMAGE_PROJECTION[2]));
                        Image image = new Image(path,name,dateTime);
                        allImages.add(image);

                        File imageFile = new File(path);
                        File folderFile = imageFile.getParentFile();
                        Folder folder = new Folder();
                        folder.name = folderFile.getName();
                        folder.path = folderFile.getAbsolutePath();
                        folder.cover = image;
                        if(!mResultFolders.contains(folder)){
                            ArrayList<Image> imageList = new ArrayList<>();
                            imageList.add(image);
                            folder.images = imageList;
                            mResultFolders.add(folder);
                        }else{
                            mResultFolders.get(mResultFolders.indexOf(folder)).images.add(image);
                        }
                    }while(data.moveToNext());
                    folderAdapter.setData(mResultFolders);
                    int index = folderAdapter.getSelectIndex();
                    if(index == 0){
                        imageGrideAdatper.setData(allImages);
                    }else{
                        imageGrideAdatper.setData(mResultFolders.get(index - 1).images);
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private void showCameraAction(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(this.getPackageManager()) != null){
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = FileUtils.createTmpFile(this);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }else{
            showToast(getResources().getString(R.string.no_camera));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 相机拍照完成后，返回图片路径
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    Intent intent = new Intent();
                    intent.putExtra(PHOTO_PATH, mTmpFile.getAbsolutePath());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        } else {
            if (mTmpFile != null && mTmpFile.exists()) {
                mTmpFile.delete();
            }
        }
    }
}
