package com.example.zhang.touchapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

public class DoughnutView extends View implements Animation.AnimationListener {
  //View默认最小宽度
  private static final int DEFAULT_MIN_WIDTH = 400;

  //圆环颜色
  private int[] doughnutColors = new int[] {
      Color.parseColor("#F69005"), Color.parseColor("#F9B700"), Color.parseColor("#F69005")
  };

  private int width;
  private int height;
  private float currentValue = 0f;
  private Paint paint = new Paint();

  private boolean isShowing = true;

  private boolean isSstnum = true;

  public DoughnutView(Context context) {
    this(context, null);
  }

  public DoughnutView(Context context, AttributeSet attrs) {

    this(context, attrs, 0);
  }

  public DoughnutView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void resetParams() {
    width = getWidth();
    height = getHeight();
  }

  private void initPaint() {
    paint.reset();
    paint.setAntiAlias(true);
  }

  /**
   * 进度动画
   */
  private BarAnimation barAnimation;

  /**
   * 新的角度
   */
  private float mNewAngle = 0;

  @Override protected void onDraw(Canvas canvas) {
    resetParams();
    //画默认圆
    initPaint();
    float doughnutWidth = Math.min(width, height) / 2 * 0.15f;
    paint.setStrokeWidth(1f);
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.parseColor("#ADADAD"));
    paint.setAntiAlias(true);
    RectF rectF = new RectF((width > height ? Math.abs(width - height) / 2 : 0) + doughnutWidth / 2,
        (height > width ? Math.abs(height - width) / 2 : 0) + doughnutWidth / 2,
        width - (width > height ? Math.abs(width - height) / 2 : 0) - doughnutWidth / 2,
        height - (height > width ? Math.abs(height - width) / 2 : 0) - doughnutWidth / 2);
    canvas.drawArc(rectF, 0, 360, false, paint);
    //画运动圆
    initPaint();
    canvas.rotate(-90, width / 2, height / 2);
    paint.setStrokeWidth(doughnutWidth);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeCap(Paint.Cap.ROUND);
    if (doughnutColors.length > 1) {
      paint.setShader(new SweepGradient(width / 2, height / 2, doughnutColors, null));
    } else {
      paint.setColor(doughnutColors[0]);
    }
    canvas.drawArc(rectF, 0, mSweepAngle, false, paint);
    barAnimation = new BarAnimation();
    barAnimation.setAnimationListener(this);
    //画中间数值的背景
    int fontSize = 85;
    initPaint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.parseColor("#333333"));
    canvas.drawCircle(width / 2, height / 2, fontSize * 2, paint);
    //
    ////画中间数值
    //canvas.rotate(90, width / 2, height / 2);
    //initPaint();
    //paint.setColor(ColorUtils.getCurrentColor(currentValue / 360f, doughnutColors));
    //paint.setTextSize(fontSize);
    //paint.setTextAlign(Paint.Align.CENTER);
    //float baseLine = height / 2 - (paint.getFontMetrics().descent + paint.getFontMetrics().ascent) / 2;
    //canvas.drawText((int) (currentValue / 360f * 100) + "%", width / 2, baseLine, paint);
  }

  /**
   * 当布局为wrap_content时设置默认长宽
   */
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
  }

  private int measure(int origin) {
    int result = DEFAULT_MIN_WIDTH;
    int specMode = MeasureSpec.getMode(origin);
    int specSize = MeasureSpec.getSize(origin);
    if (specMode == MeasureSpec.EXACTLY) {
      result = specSize;
    } else {
      if (specMode == MeasureSpec.AT_MOST) {
        result = Math.min(result, specSize);
      }
    }
    return result;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
        Log.i("qaz", "2SetNum: "+isSstnum());
        break;
      case MotionEvent.ACTION_MOVE:
        break;
      case MotionEvent.ACTION_UP:

        break;
      case MotionEvent.ACTION_CANCEL:
        break;
    }
    super.onTouchEvent(event);
    return true;
  }

  public void setValue(float value) {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, value);
    valueAnimator.setDuration(1000);
    valueAnimator.setInterpolator(new AccelerateInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
        currentValue = (float) valueAnimator.getAnimatedValue();
        invalidate();
      }
    });
    valueAnimator.start();
  }

  /**
   * 新的旋转角度
   */
  public void changeAngle(float newAngle) {
    //mOldAngle = mNewAngle;
    if (newAngle == 0) {
      setShowing(true);
    } else {
      setShowing(false);
    }
    mNewAngle = newAngle;
    barAnimation.setDuration(1000);
    barAnimation.setInterpolator(new DecelerateInterpolator());
    startAnimation(barAnimation);
  }

  @Override public void onAnimationStart(Animation animation) {
    //setSstnum(true);
  }

  @Override public void onAnimationEnd(Animation animation) {
    changeAngle(0 * 1f);
   // setSstnum(false);
   // Log.i("qaz", "3SetNum: "+isSstnum());

    // setValue(360 * 1f);
  }

  @Override public void onAnimationRepeat(Animation animation) {
  }

  /**
   * 圆环转过的角度
   */
  private float mSweepAngle = 0;

  public class BarAnimation extends Animation {

    public BarAnimation() {
    }

    @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
      super.applyTransformation(interpolatedTime, t);
      //if (mNewAngle - mOldAngle >= 0) {
      //  // 正向
      mSweepAngle = interpolatedTime * (mNewAngle);
      //} else {
      //  //  逆向
      //  mSweepAngle = interpolatedTime * (mNewAngle - mOldAngle);
      //}
      postInvalidate();
    }
  }

  public boolean isShowing() {
    return isShowing;
  }

  public void setShowing(boolean showing) {
    isShowing = showing;
  }

  public boolean isSstnum() {
    return isSstnum;
  }

  public void setSstnum(boolean sstnum) {
    isSstnum = sstnum;
  }


}