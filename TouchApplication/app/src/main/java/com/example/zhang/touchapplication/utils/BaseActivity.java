package com.example.zhang.touchapplication.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by zhang on 2018/5/7.
 */

public abstract class BaseActivity extends Activity {
  /***是否显示标题栏*/
  private boolean isshowtitle = true;
  /***是否显示标题栏*/
  private boolean isshowstate = true;
  /***封装toast对象**/
  private static Toast toast;
  /***获取TAG的activity名称**/
  protected final String TAG = this.getClass().getSimpleName();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isshowtitle) {
      requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    if (isshowstate) {
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    ////初始化控件
    //initView();
    ////设置数据
    //initData();
  }


  /**
   * 是否设置标题栏
   */
  public void setTitle(boolean ishow) {
    isshowtitle = ishow;
  }

  /**
   * 设置是否显示状态栏
   */
  public void setState(boolean ishow) {
    isshowstate = ishow;
  }

  /**
   * 显示长toast
   */
  public void toastLong(String msg) {
    if (null == toast) {
      toast = new Toast(this);
      toast.setDuration(Toast.LENGTH_LONG);
      toast.setText(msg);
      toast.show();
    } else {
      toast.setText(msg);
      toast.show();
    }
  }

  /**
   * 显示短toast
   */
  public void toastShort(String msg) {
    if (null == toast) {
      toast = new Toast(this);
      toast.setDuration(Toast.LENGTH_SHORT);
      toast.setText(msg);
      toast.show();
    } else {
      toast.setText(msg);
      toast.show();
    }
  }

}

