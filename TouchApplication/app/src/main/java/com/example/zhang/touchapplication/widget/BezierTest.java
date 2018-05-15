package com.example.zhang.touchapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhang on 2018/3/8.
 */

public class BezierTest extends View {

  public BezierTest(Context context) {
    super(context);
    init();
  }

  public BezierTest(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BezierTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BezierTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Path mBezier = new Path();
  private Path mSrcPezier = new Path();

  private void init() {
    Paint paint = mPaint;
    //抗锯齿
    paint.setAntiAlias(true);
    //抗抖动
    paint.setDither(true);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(10);
    //初始化源贝塞尔曲线
    mSrcPezier.cubicTo(200, 700, 500, 1200, 700, 200);

    new Thread() {
      @Override public void run() {
        //初始化贝塞尔曲线三阶
        initBezier();
      }
    }.start();
  }

  private void initBezier() {
    //三阶贝塞尔曲线 四个坐标点
    float[] xPoints = new float[] { 0, 200, 500, 700 };
    float[] yPoints = new float[] { 0, 700, 1200, 200 };

    Path path = mBezier;

    int fps = 10000;

    for (int i = 0; i <= fps; i++) {
      //进度
      float progress = i / (float) fps;
      float x = calculateBezier(progress, xPoints);
      float y = calculateBezier(progress, yPoints);
      //使用链接的方式，当xy变动足够小时，就是平滑曲线
      path.lineTo(x, y);
      //刷新界面
      postInvalidate();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 计算某时刻的贝塞尔所处的值（x或y）
   *
   * @param t 时间（0 - 1）
   * @param values 贝塞尔点的集合
   * @return 当前t时刻的贝塞尔所处的点
   */
  private float calculateBezier(float t, float[] values) {
    //采用双层循环
    int len = values.length;
    for (int i = len - 1; i > 0; i--) {
      for (int j = 0; j < i; j++) {
        values[j] = values[j] + (values[j + 1] - values[j]) * t;
      }
    }
    //运算时结构保存在第一位
    return values[0];
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPaint.setColor(Color.BLACK);
    canvas.drawPath(mSrcPezier, mPaint);

    mPaint.setColor(Color.RED);
    canvas.drawPath(mBezier, mPaint);
  }
}
