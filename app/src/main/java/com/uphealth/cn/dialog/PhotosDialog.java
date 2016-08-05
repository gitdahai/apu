package com.uphealth.cn.dialog;

import com.uphealth.cn.R;
import com.uphealth.cn.dialog.CustomDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhotosDialog extends Dialog{
	
	public PhotosDialog(Context context) {
		super(context);
	}

	public PhotosDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context; // 上下文对象
		private String title; // 对话框标题
		private String message; // 对话框内容
		private String confirm_btnText; // 按钮名称“确定”
		private String cancel_btnText; // 按钮名称“取消”
		private String neutral_btnText; // 按钮名称“隐藏”
		public Builder(Context context) {
			this.context = context;
		}

		/* 设置对话框信息 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 设置对话框界面
		 * 
		 * @param v
		 *            View
		 * @return
		 */
		public Builder setContentView(View v) {
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setPositiveButton(int confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = (String) context.getText(confirm_btnText);
			return this;
		}

		/**
		 * Set the positive button and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setPositiveButton(String confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = confirm_btnText;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setNegativeButton(int cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = (String) context.getText(cancel_btnText);
			return this;
		}

		/**
		 * Set the negative button and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setNegativeButton(String cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = cancel_btnText;
			return this;
		}

		/**
		 * Set the netural button resource and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setNeutralButton(int neutral_btnText,
				DialogInterface.OnClickListener listener) {
			this.neutral_btnText = (String) context.getText(neutral_btnText);
			return this;
		}

		/**
		 * Set the netural button and it's listener
		 * 
		 * @param confirm_btnText
		 * @return
		 */
		public Builder setNeutralButton(String neutral_btnText,
				DialogInterface.OnClickListener listener) {
			this.neutral_btnText = neutral_btnText;
			return this;
		}

		public CustomDialog create() {
			final CustomDialog dialog = new CustomDialog(context,
					R.style.style_photo);
			
			return dialog;
		}

	}

}
