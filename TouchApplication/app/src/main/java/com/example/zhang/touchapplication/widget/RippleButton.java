package com.example.zhang.touchapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhang on 2018/3/14.
 */

public class RippleButton extends View {

  private RippleDrowable mRippleDrowable;

  public RippleButton(Context context) {
    this(context, null);
  }

  public RippleButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    mRippleDrowable = new RippleDrowable();
    //BitmapFactory.decodeResource(getResources(), R.mipmap.img_bitmap_resources)
    //设置刷新接口
    mRippleDrowable.setCallback(this);
    //直接设置背景引用drowable
    //setBackgroundDrawable(new RippleDrowable());
  }

  @Override protected void onDraw(Canvas canvas) {
    //绘制一个圆
    mRippleDrowable.draw(canvas);
    super.onDraw(canvas);

    //
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    mRippleDrowable.onTouch(event);
    super.onTouchEvent(event);
    //invalidate();
    return true;
  }

  //验证Drowable是否OK
  @Override protected boolean verifyDrawable(@NonNull Drawable who) {
    return who == mRippleDrowable || super.verifyDrawable(who);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    //设置绘制和刷新的区域
    mRippleDrowable.setBounds(0, 0, getWidth(), getHeight());
  }

  //@Override public void setOnClickListener(@Nullable OnClickListener l) {
  //  super.setOnClickListener(l);
  //}
}
