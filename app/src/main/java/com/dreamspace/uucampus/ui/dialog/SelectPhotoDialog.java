package com.dreamspace.uucampus.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ImageCaptureManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lx on 2015/12/20.
 */
public class SelectPhotoDialog {
    private AlertDialog dialog;
    public SelectPhotoDialog(final Activity context, final ImageCaptureManager manager,
                             final int SELECT_PHOTO_REQUEST_CODE, final int TAKE_PHOTO_REQUEST_CODE) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_select_image, null);
        TextView selectPhoto = (TextView) dialogView.findViewById(R.id.select_photo_tv);
        TextView takePhoto = (TextView) dialogView.findViewById(R.id.take_photo_tv);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                context.startActivityForResult(photoPickerIntent, SELECT_PHOTO_REQUEST_CODE);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = null;
                try {
                    cameraIntent = manager.dispatchTakePictureIntent();
                    if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivityForResult(cameraIntent,TAKE_PHOTO_REQUEST_CODE);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.no_camera), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = new AlertDialog.Builder(context).setView(dialogView).create();
    }

    public void show(){
        if(dialog != null){
            dialog.show();
        }
    }

    public void dismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
