package com.example.zhang.touchapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhang.touchapplication.recorddb.RecordDataBaseInfo;
import com.example.zhang.touchapplication.utils.BaseActivity;
import com.example.zhang.touchapplication.widget.CustomDialog;
import com.example.zhang.touchapplication.widget.DoughnutView;

import static com.example.zhang.touchapplication.utils.DateUtil.getCurrentDateTimeyyyyMMddHHmmss;
import static com.example.zhang.touchapplication.utils.DateUtil.getStringDateFromMilliseconds;
import static com.example.zhang.touchapplication.utils.DateUtil.getStringFromMillisSeconds;

/**
 * 计时 计数
 */
public class ActionActivity extends BaseActivity implements View.OnClickListener {

  @BindView(R.id.textView1) TextView tv_action_time1;
  @BindView(R.id.textView2) TextView tv_action_time2;
  @BindView(R.id.num_button) DoughnutView btn_anction_ripple;
  @BindView(R.id.num_text) TextView tv_action_num;
  @BindView(R.id.button_start) Button btn_action_start;
  @BindView(R.id.button_stop) TextView btn_action_stop;
  @BindView(R.id.iv_left) ImageView ivLeft;

  private long mStartTime;
  private long currentTime;
  private boolean mStart = false;
  private long tmpTime;
  private long mEndTime;
  private int numText;
  private CustomDialog mDialog;
  private long mFirstStartTime;
  private AlertDialog.Builder Adialog;
  //数据库
  private RecordDataBaseInfo recordDataBaseInfo;

  private int minsecond;
  private int hour;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setTitle(true);//设置是否显示标题栏
    setState(false);//设置是否显示状态栏
    setContentView(R.layout.activity_action);
    ButterKnife.bind(this);
    initView();
    initData();
  }

  //初始化控件
  public void initView() {
    recordDataBaseInfo = RecordDataBaseInfo.getInstance(this);
    btn_anction_ripple.setOnClickListener(this);
    btn_action_start.setOnClickListener(this);
    btn_action_stop.setOnClickListener(this);
    ivLeft.setOnClickListener(this);
    // btn_anction_ripple.setValue(-360 * 0.2f);
  }

  //初始化数据
  public void initData() {
    SetTimeStart();
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.num_button:
        // btn_anction_ripple.setValue(360 * 1.0f);
        SetNum();
        break;
      case R.id.button_start:
        SetTimeStart();
        break;
      case R.id.button_stop:
        SetTimeSuspend();
        // SetTimeStop();
        break;
      case R.id.iv_left:
        SetTimeSuspend();
        break;
      default:
        break;
    }
  }

  /**
   * 点击按钮开始计数
   */
  private void SetNum() {
    if (btn_anction_ripple.isShowing()) {
      btn_anction_ripple.changeAngle(360 * 1f);
      if (numText == 999) {
        return;
      }
      numText++;
      tv_action_num.setText(String.valueOf(numText));
    }

  }

  /**
   * 开始计时
   */
  private void SetTimeStart() {
    mStart = true;
    mStartTime = System.currentTimeMillis();
    mFirstStartTime = System.currentTimeMillis();
    btn_action_start.setText("开始");
    mHandler.sendEmptyMessage(1);
    //if (btn_action_start.getText() == "开始") {
    //  //mStart = true;
    //  //mStartTime = System.currentTimeMillis();
    //  //// Log.i("qaz", "onClick开始: " + mStartTime);
    //  //btn_action_start.setText("暂停");
    //  //mHandler.sendEmptyMessage(1);
    //} else if (btn_action_start.getText() == "暂停") {
    //  //mStart = false;
    //  //tmpTime = System.currentTimeMillis();
    //  //btn_action_start.setText("继续");
    //  //mHandler.sendEmptyMessage(0);
    //} else {
    //  //mStart = true;
    //  //long tmp = System.currentTimeMillis() - tmpTime;
    //  //mStartTime = mStartTime + tmp;
    //  //btn_action_start.setText("暂停");
    //  //mHandler.sendEmptyMessage(1);
    //}
  }

  /**
   * 停止计时
   */
  private void SetTimeStop() {
    mStart = false;
    mHandler.sendEmptyMessage(0);
    CreateDialog();
    showDialog();
  }

  //更新显示时间和显示记录的时间
  private void updateTime() {
    currentTime = System.currentTimeMillis();
    //Log.i("qaz", "updateTime1: "+currentTime);
    //Log.i("qaz", "updateTime2: "+mStartTime);
    long aTime = currentTime - mStartTime;
    StringBuilder[] sb1 = new StringBuilder[2];
    sb1[0] = new StringBuilder();
    sb1[1] = new StringBuilder();
    sb1 = getTimeFormat(aTime);
    tv_action_time1.setText(sb1[0]);
    tv_action_time2.setText(sb1[1]);
  } //把毫秒转为要显示的格式

  public StringBuilder[] getTimeFormat(long time) {
    long tmp = time;
    time = time / 1000;
    int second = (int) (time % 60);
    int minute = (int) (time / 60) % 60;
    hour = (int) (time / 3600);
    minsecond = (int) (tmp / 10 % 100);
    StringBuilder[] sb = new StringBuilder[2];
    sb[0] = new StringBuilder();
    sb[1] = new StringBuilder();
    if (hour < 10) {
      sb[0].append('0');
      sb[0].append(String.valueOf(hour));
    } else {
      sb[0].append(String.valueOf(hour));
    }
    sb[0].append(':');
    if (minute < 10) {
      sb[0].append('0');
      sb[0].append(String.valueOf(minute));
    } else {
      sb[0].append(String.valueOf(minute));
    }
    sb[0].append(':');
    if (second < 10) {
      sb[0].append('0');
      sb[0].append(String.valueOf(second));
    } else {
      sb[0].append(String.valueOf(second));
    }
    sb[1].append(':');
    if (minsecond < 10) {
      sb[1].append('0');
      sb[1].append(minsecond);
    } else {
      sb[1].append(minsecond);
    }
    return sb;
  }

  private Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 1:
          if (mStart) {
            updateTime();
            mHandler.sendEmptyMessage(1);
            if (hour >= 2) {
              SetTimeStop();
            }
          }
          break;
        case 0:
          break;
        default:
          break;
      }
    }
  };

  /**
   * 提示dialog
   */
  protected void ShowDialog() {
    Adialog = new AlertDialog.Builder(this);
    Adialog.setTitle("");
    Adialog.setCancelable(false);
    Adialog.setMessage("是否结束本次锻炼？");
    Adialog.setPositiveButton("继续做", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        //SetTimeContinue();
        dialog.dismiss();
      }
    });
    Adialog.setNegativeButton("不做了", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        SetTimeStop();
        dialog.dismiss();
      }
    });
    Adialog.create().show();
  }

  /**
   * 计时暂停
   */
  private void SetTimeSuspend() {
    ShowDialog();
  }

  /**
   * 计时继续
   */
  private void SetTimeContinue() {
    mStart = true;
    long tmp = System.currentTimeMillis() - tmpTime;
    mStartTime = mStartTime + tmp;
    mHandler.sendEmptyMessage(1);
  }

  /**
   * 总结信息dialog
   */
  public void CreateDialog() {
    String year = getCurrentDateTimeyyyyMMddHHmmss();
    asvedDb(year, getStringDateFromMilliseconds(mFirstStartTime),
        getStringDateFromMilliseconds(currentTime), numText + "");
    String mMessage = getStringFromMillisSeconds(currentTime - mFirstStartTime);

    mDialog = new CustomDialog.Builder(this).setTitle("")
        .setMessage(mMessage)
        .setTitle(numText + "")
        .setNegativeButton(new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
            backClick();
          }
        })
        .build();
    mDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
  }

  public void showDialog() {
    if (mDialog != null && !mDialog.isShowing()) mDialog.show();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      return true;
    }
    return false;
  }

  /**
   * 点击系统返回和页面返回键时的处理
   */
  private void backClick() {
    finish();
    // 定义出入场动画
    overridePendingTransition(0, R.anim.dialog_exit);
  }

  private void asvedDb(String year, String mFirstStartTime, String currentTime, String numText) {

    if (year != null && mFirstStartTime != null && currentTime != null && numText != null) {
      recordDataBaseInfo.addHistoryName(year, mFirstStartTime, currentTime, numText);
    }
    // recordDataBaseInfo.addHistoryName("", "", "", "");
    //Log.i("qaz", "recordDb: "+recordDataBaseInfo.queryEntit().size());
  }
}
