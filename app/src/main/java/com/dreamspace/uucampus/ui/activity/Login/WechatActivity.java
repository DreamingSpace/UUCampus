package com.dreamspace.uucampus.ui.activity.Login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.WeChatUser;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by money on 2015/10/29.
 */
public class WechatActivity extends AbsActivity {
    @Bind(R.id.weixin_img)
    CircleImageView weixinImg;
    @Bind(R.id.wechat_nickname)
    TextView wechatNickname;
    @Bind(R.id.weixin_register)
    Button weixinRegister;
    @Bind(R.id.weixin_bound)
    Button weixinBound;

    WeChatUser weChatUser;

    public static final String WECHAT_USER = "WECHAT_USER";

    @Override
    protected int getContentView() {
        Log.d("TestData","eeeee");
        return R.layout.acticity_weixin;
    }

    @Override
    protected void prepareDatas() {
        weChatUser = getIntent().getExtras().getParcelable(WECHAT_USER);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        CommonUtils.showImageWithGlide(this, weixinImg, ShareData.weChatUser.getHeadimgurl());
        wechatNickname.setText(ShareData.weChatUser.getNickname());
        weixinRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(BoundActvity.class);
            }
        });
        weixinBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(BoundSecondActivity.class);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
