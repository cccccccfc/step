package com.example.zhang.touchapplication.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhang.touchapplication.R;
import com.example.zhang.touchapplication.utils.BaseActivity;
import com.example.zhang.touchapplication.widget.ShareOptionDialog;

/**
 * 分享页
 */
public class ShareActivity extends BaseActivity {

  @BindView(R.id.iv_left) ImageView ivLeft;
  @BindView(R.id.iv_right) ImageView ivRight;
  @BindView(R.id.rl_title) RelativeLayout rlTitle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(true);//设置是否显示标题栏
    setState(true);//设置是否显示状态栏
    setContentView(R.layout.activity_share);
    ButterKnife.bind(this);

    ////初始化控件
    initView();
    ////设置数据
    initData();
  }

  private void initData() {

  }

  private void initView() {
    ivLeft.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    ivRight.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showQuickOption();
      }
    });
  }

  // 显示快速操作界面
  private void showQuickOption() {
    final ShareOptionDialog dialog = new ShareOptionDialog(ShareActivity.this);
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setOnQuickOptionformClickListener(new ShareOptionDialog.OnQuickOptionformClick() {
      @Override public void onQuickOptionClick(int id) {
        switch (id) {

          case R.id.imgbtn_share_weixin:// 微信
            // Logger.i("Test", "微信");
            if (isAppInstalled(ShareActivity.this, "com.tencent.mm")) {
              //shareToWechat( model.share);
            } else {
              Toast.makeText(ShareActivity.this, "请安装手机微信后分享", Toast.LENGTH_SHORT).show();
            }
            break;
          case R.id.imgbtn_share_weibo:// 微博
            // Logger.i("Test", "微博");
            if (isAppInstalled(ShareActivity.this, "com.sina.weibo")) {
              //shareToSina( model.share);
            } else {
              Toast.makeText(ShareActivity.this, "请安装新浪微博后分享", Toast.LENGTH_SHORT).show();
            }
            break;
          case R.id.imgbtn_share_qq:// qq
            // Logger.i("Test", "qq");
            if (isAppInstalled(ShareActivity.this, "com.tencent.mobileqq")) {
              //  shareToQQ(model.share);
            } else {
              Toast.makeText(ShareActivity.this, "请安装手机QQ后分享", Toast.LENGTH_SHORT).show();
            }
            break;
          case R.id.imgbtn_share_friends:// 朋友圈
            // Logger.i("Test", "朋友圈");
            if (isAppInstalled(ShareActivity.this, "com.tencent.mm")) {
              //shareToWechatfriend(model.share);
            } else {
              Toast.makeText(ShareActivity.this, "请安装手机微信后分享", Toast.LENGTH_SHORT).show();
            }
            break;
        }
      }
    });
    dialog.show();
  }

  private boolean isAppInstalled(Context context, String packagename) {
    PackageInfo packageInfo;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
    } catch (PackageManager.NameNotFoundException e) {
      packageInfo = null;
      e.printStackTrace();
    }
    if (packageInfo == null) {
      // System.out.println("没有安装");
      return false;
    } else {
      // System.out.println("已经安装");
      return true;
    }
  }
}
