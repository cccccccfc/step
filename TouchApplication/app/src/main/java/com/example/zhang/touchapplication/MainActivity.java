package com.example.zhang.touchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhang.touchapplication.activity.StartActivity;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎页
 */
public class MainActivity extends Activity {

  int i = 0;
  @BindView(R.id.tv_loading) TextView tvLoading;
  private Timer mTimer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mTimer = new Timer();//计时器 3秒后关闭
    mTimer.schedule(new TimerTask() {
      @Override public void run() {
        if (i >= 5) {
          toFinish();
        }
        i++;
      }
    }, 0, 1000);
    initView();
  }

  private void initView() {
    tvLoading.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mTimer != null) {
          mTimer.cancel();
          mTimer = null;
        }
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
      }
    });
  }

  private void toFinish() {
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
    }
    Intent intent = new Intent(this, StartActivity.class);
    startActivity(intent);
    overridePendingTransition(0, 0);
    finish();
  }

}
