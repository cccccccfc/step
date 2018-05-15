package com.example.zhang.touchapplication.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.zhang.touchapplication.R;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ShareOptionDialog extends Dialog implements View.OnClickListener{
    // 微信分享
    private static TextView imgbtn_share_weixin;
    // 微博分享
    private static TextView imgbtn_share_weibo;
    // qq空间分享
    private static TextView imgbtn_share_qq;
    // qq好友分享
    private static TextView imgbtn_share_qqfriends;
    // 微信朋友圈分享
    private static TextView imgbtn_share_friends;

    public interface OnQuickOptionformClick {
        void onQuickOptionClick(int id);
    }

    private OnQuickOptionformClick mListener;

    private ShareOptionDialog(Context context, boolean flag,
                              OnCancelListener listener) {
        super(context, flag, listener);
    }

    private TextView tv_cancle;

    @SuppressLint("InflateParams")
    private ShareOptionDialog(Context context, int defStyle) {
        super(context, defStyle);
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_share_option, null);
        imgbtn_share_weixin = (TextView) contentView
                .findViewById(R.id.imgbtn_share_weixin);
        imgbtn_share_weibo = (TextView) contentView
                .findViewById(R.id.imgbtn_share_weibo);
        imgbtn_share_qq = (TextView) contentView
                .findViewById(R.id.imgbtn_share_qq);
        imgbtn_share_friends = (TextView) contentView
                .findViewById(R.id.imgbtn_share_friends);

        contentView.findViewById(R.id.imgbtn_share_weixin).setOnClickListener(
                this);
        contentView.findViewById(R.id.imgbtn_share_weibo).setOnClickListener(
                this);
        contentView.findViewById(R.id.imgbtn_share_qq).setOnClickListener(this);
        contentView.findViewById(R.id.imgbtn_share_friends).setOnClickListener(
                this);
        tv_cancle = (TextView) contentView.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(this);

        LinearLayout pop_layout = (LinearLayout) contentView
                .findViewById(R.id.pop_layout);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShareOptionDialog.this.dismiss();
                return true;
            }
        });
        super.setContentView(contentView);

    }

    public static void TGDispaly() {
        imgbtn_share_weixin.setVisibility(View.VISIBLE);
        imgbtn_share_weibo.setVisibility(View.VISIBLE);
        imgbtn_share_qqfriends.setVisibility(View.VISIBLE);
    }

    public ShareOptionDialog(Context context) {
        this(context, R.style.quick_option_dialog);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.TOP);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    public void setOnQuickOptionformClickListener(OnQuickOptionformClick lis) {
        mListener = lis;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_cancle:
                dismiss();
                break;
            default:
                break;
        }
        if (mListener != null) {
            mListener.onQuickOptionClick(id);
        }
        dismiss();
    }
}
