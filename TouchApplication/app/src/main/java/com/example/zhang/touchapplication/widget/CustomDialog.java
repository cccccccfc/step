package com.example.zhang.touchapplication.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.zhang.touchapplication.R;

/**
 * Created by zhang on 2018/5/7.
 */

public class CustomDialog extends Dialog {
  public CustomDialog(@NonNull Context context) {
    super(context);
  }

  public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
    setCanceledOnTouchOutside(false);
    setCancelable(false);
  }

  protected CustomDialog(@NonNull Context context, boolean cancelable,
      @Nullable OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
  }

  //用Builder模式来构造Dialog
  public static class Builder {
    private Context mContext;
    private View contentView;
    private String title;
    private String message;
    private String positiveText;
    private String negativeText;
    private DialogInterface.OnClickListener positiviOnclickListener;
    private DialogInterface.OnClickListener negativeOnclickListener;

    public Builder(Context mContext) {
      this.mContext = mContext;
    }

    public Builder setContentView(View contentView) {//设置dialog的主view
      this.contentView = contentView;
      return this;
    }

    public Builder setTitle(String title) {//设置dialog的标题
      this.title = title;
      return this;
    }

    public Builder setMessage(String msg) {//设置dialig的内容
      this.message = msg;
      return this;
    }

    public Builder setNegativeButton(
        DialogInterface.OnClickListener negativeOnclickListener) {//dialog的取消按钮
      this.negativeOnclickListener = negativeOnclickListener;
      return this;
    }

    /**
     * 1,加载要显示的布局
     * 2，通过dialog的addContentView将布局添加到window中
     * 3，基本逻辑处理
     * 4，显示dialog的布局
     */
    public CustomDialog build() {
      LayoutInflater mInflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      final CustomDialog mCustomDialog =
          new CustomDialog(mContext, R.style.CustomDialog);//默认调用带style的构造
      mCustomDialog.setCanceledOnTouchOutside(false);//默认点击布局外不能取消dialog
      View view = mInflater.inflate(R.layout.custom_dialog, null);
      mCustomDialog.addContentView(view,
          new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT));//将我们的View添加到phonewindow里,这句话最后调用的是window.addContentView(view);
      if (!TextUtils.isEmpty(title)) {
        TextView titleView = (TextView) view.findViewById(R.id.tv_title);
        titleView.setText(title);
      }

      ImageView btn_cancle = (ImageView) view.findViewById(R.id.btn_cancle);
      if (negativeOnclickListener != null) {
        btn_cancle.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            negativeOnclickListener.onClick(mCustomDialog, BUTTON_NEGATIVE);
          }
        });
      }

      if (!TextUtils.isEmpty(message)) {
        TextView messageView = (TextView) view.findViewById(R.id.tv_message);
        messageView.setText(message);//显示的内容
      } else if (contentView != null) {//如果内容区域要显示其他的View的话
        LinearLayout mContentLayout = (LinearLayout) view.findViewById(R.id.content);
        mContentLayout.removeAllViews();
        mContentLayout.addView(contentView);
      }

      mCustomDialog.setContentView(view);
      return mCustomDialog;
    }
  }
}
