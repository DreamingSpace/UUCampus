/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dreamspace.uucampus.common;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.CommonUtils;

public class VaryViewHelperController {

    private IVaryViewHelper helper;
    private View msgView;
    private View loadingView;
    private TextView msgTv;
    private TextView loadingTv;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        initMsgView();
//        View layout = helper.inflate(R.layout.message);
//        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        msgTv.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));

//        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
//        imageView.setImageResource(R.drawable.ic_exception);

        if (null != onClickListener) {
            msgView.setOnClickListener(onClickListener);
        }

        helper.showLayout(msgView);
    }

    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        initMsgView();
//        View layout = helper.inflate(R.layout.message);
//        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!CommonUtils.isEmpty(errorMsg)) {
            msgTv.setText(errorMsg);
        } else {
            msgTv.setText(helper.getContext().getResources().getString(R.string.common_error_msg));
        }

        ImageView imageView = (ImageView) msgView.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.ic_error);

        if (null != onClickListener) {
            msgView.setOnClickListener(onClickListener);
        }

        helper.showLayout(msgView);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        initMsgView();
//        View layout = helper.inflate(R.layout.message);
//        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!CommonUtils.isEmpty(emptyMsg)) {
            msgTv.setText(emptyMsg);
        } else {
            msgTv.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

//        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
//        imageView.setImageResource(R.drawable.ic_exception);

        if (null != onClickListener) {
            msgView.setOnClickListener(onClickListener);
        }

        helper.showLayout(msgView);
    }

    public void showLoading(String msg) {
        initLoadingView();
//        View layout = helper.inflate(R.layout.loading);
        if (!CommonUtils.isEmpty(msg)) {
//            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            loadingTv.setText(msg);
        }
        helper.showLayout(loadingView);
    }

    public void restore() {
        helper.restoreView();
    }

    private void initMsgView(){
        if(msgView != null){
            return;
        }
        msgView = helper.inflate(R.layout.message);
        msgTv = (TextView) msgView.findViewById(R.id.message_info);
    }

    private void initLoadingView(){
        if(loadingView != null){
            return;
        }
        loadingView = helper.inflate(R.layout.loading);
        loadingTv = (TextView) loadingView.findViewById(R.id.loading_msg);
    }
}
