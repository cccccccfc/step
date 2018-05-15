package com.example.zhang.touchapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhang on 2018/3/7.
 */

public class Test extends View {
  public Test(Context context) {
    super(context);
    init();
  }

  public Test(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public Test(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public Test(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }
  private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private final Path mPath = new Path();
  private void init(){

    Paint paint = mPaint;
    paint.setDither(true);
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(5);

    //一阶贝塞尔曲线
    Path path = mPath;
    path.moveTo(50,50);
    path.lineTo(150,150);

    //二阶贝塞尔曲线  //前两个值是控制点坐标
    ////以原点0.0计算
    path.quadTo(300,50,400,150);
    //相对的实现 以初始点的坐标计算
    //path.rQuadTo(200,-300,400 ,0);

    //三阶贝塞尔曲线
    path.moveTo(200,400);
    //以原点0.0计算
    //path.cubicTo(250, 200, 350 ,600 ,400 , 400);

    //相对的实现 以初始点的坐标计算
    path.rCubicTo(50, -200, 150 ,200 ,200 , 0);

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(mPath,mPaint);

    //canvas.drawPoint(450,100,mPaint);
    canvas.drawPoint(250,200,mPaint);
    canvas.drawPoint(350,600,mPaint);
  }
}
