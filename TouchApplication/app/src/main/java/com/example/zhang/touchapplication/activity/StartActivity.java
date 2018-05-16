package com.example.zhang.touchapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhang.touchapplication.R;
import com.example.zhang.touchapplication.utils.BaseActivity;

/**
 * 主页
 */
public class StartActivity extends BaseActivity {

  @BindView(R.id.iv_right) ImageView ivRight;
  @BindView(R.id.rl_title) RelativeLayout rlTitle;
  @BindView(R.id.tv_start) TextView tvStart;
  @BindView(R.id.tv_left) TextView tvLeft;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(true);//设置是否显示标题栏
    setState(true);//设置是否显示状态栏
    setContentView(R.layout.activity_start);
    ButterKnife.bind(this);
    initView();
  }

  public void initView() {
    tvStart.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(StartActivity.this, ActionActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
      }
    });
    tvLeft.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(StartActivity.this, ShareActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
      }
    });
    ivRight.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(StartActivity.this, RecordActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
      }
    });
  }

  private long firstTime = 0;
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    long secondTime = System.currentTimeMillis();
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if ( secondTime - firstTime < 2000) {
        System.exit(0);
      } else {
        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        firstTime = System.currentTimeMillis();
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

}
