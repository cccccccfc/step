package com.example.zhang.touchapplication.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import com.example.zhang.touchapplication.interpolator.SpringInterpolator;

/**
 * Created by zhang on 2018/3/14.
 */

public class RippleDrowable extends Drawable {

  private int mAlpha = 30;
  private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿的画笔
  private int mRippleColor = 0;
  private float mRipplePointX, mRipplePointY; //圆心坐标
  private float mRippleRadius = 0;//圆的半径
  private Bitmap bitmap;

  public RippleDrowable() {

    // this.bitmap = bitmap;
    mPaint.setAntiAlias(true);//抗锯齿
    mPaint.setDither(true);//防抖动

    setRippleColor(Color.RED);
    //设置滤镜
    //setColorFilter(new LightingColorFilter(0xffff0000 , 0x00330000));
  }

  public void setRippleColor(int color) {
    //不建议这样
    //mPaint.setColor(color);
    mRippleColor = color;
    setColorAlphaChange();
  }

  @Override public void draw(@NonNull Canvas canvas) {

    //canvas.drawColor(0x70FF0000);
    // canvas.drawBitmap(bitmap, 0, 0, mPaint);
    //绘制一个圆

    canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, mPaint);
  }

  private void setColorAlphaChange() {
    mPaint.setColor(mRippleColor);
    if (mAlpha != 255) {
      //得到颜色本身透明度  方法一
      int Alpha = mPaint.getAlpha();
      // 方法二
      // mAlpha = Color.alpha(mRippleColor);
      //获取真实的透明度
      int realAlpha = (int) (Alpha * (mAlpha / 255f));
      mPaint.setAlpha(realAlpha);
      // mPaint.getColor();
      // Log.i("qaz", "setColorAlphaChange: " + mAlpha + "realAlpha" + realAlpha);
    }

    invalidateSelf();
  }

  @Override public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
    ////设置Drawable透明度
    mAlpha = alpha;
    setColorAlphaChange();
  }

  //颜色滤镜
  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {
    if (mPaint.getColorFilter() != colorFilter) {
      mPaint.setColorFilter(colorFilter);
    }
  }

  //得到当前得Drowable 是否有透明度
  @Override public int getOpacity() {

    int alpha = mPaint.getAlpha();
    if (alpha == 255) {
      return PixelFormat.OPAQUE;  //不透明
    } else if (alpha == 0) {
      return PixelFormat.TRANSPARENT;  //全透明
    } else {
      return PixelFormat.TRANSLUCENT;  //半透明
    }
  }

  @Override public int getAlpha() {
    return mAlpha;
  }

  public void onTouch(MotionEvent event) {

    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
        onTouchDown(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_MOVE:
        onTouchMove(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_UP:
        onTouchUp(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_CANCEL:
        onTouchCancel(event.getX(), event.getY());
        break;
    }
  }

  private void onTouchCancel(float x, float y) {
  }

  private void onTouchUp(float x, float y) {
    //手指抬起停止绘制
    //  unscheduleSelf(mEnterRunnable);
  }

  private void onTouchMove(float x, float y) {

  }

  //按下时的点击点
  private float mDonePointX, mDonePointY;
  //控件的中心区域点
  private float mCenterPointX, mCenterPointY;

  private void onTouchDown(float x, float y) {
    mDonePointX = x;
    mDonePointY = y;
    startEnterRunnable();
  }

  //进入动画的进度值
  private float mEnterProgress = 0;

  private float mEnterIncrement = 16f / 700;
  //动画查值器 实现由快到慢的效果
  private Interpolator mEnterInterpolator = new SpringInterpolator();
  private Runnable mEnterRunnable = new Runnable() {
    @Override public void run() {
      //进度值
      mEnterProgress = mEnterProgress + mEnterIncrement;
      if (mEnterProgress > 1) {
        return;
      }

      float realProgress = mEnterInterpolator.getInterpolation(mEnterProgress);
      onEnterProgress(realProgress);
      //延迟16毫秒，保证界面的刷新频率接近69fps
      scheduleSelf(this, SystemClock.uptimeMillis() + 16);
    }
  };

  private void startEnterRunnable() {
    mEnterProgress = 0;
    unscheduleSelf(mEnterRunnable);
    scheduleSelf(mEnterRunnable, SystemClock.uptimeMillis());
  }

  @Override protected void onBoundsChange(Rect bounds) {
    super.onBoundsChange(bounds);
    mCenterPointX = bounds.centerX();
    mCenterPointY = bounds.centerY();
    mRipplePointX = bounds.centerX();
    mRipplePointY = bounds.centerY();
  }

  private void onEnterProgress(float progress) {
    mRippleRadius = 219 * progress;
    mRipplePointX = getProgressValue(mDonePointX, mCenterPointX, progress);
    mRipplePointY = getProgressValue(mDonePointY, mCenterPointY, progress);
    invalidateSelf();
  }

  private float getProgressValue(float start, float end, float progress) {

    return start + (end - start) * progress;
  }
}
