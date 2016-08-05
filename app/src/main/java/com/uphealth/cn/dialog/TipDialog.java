package com.uphealth.cn.dialog;

import java.util.List;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.StringAadpter;
import com.uphealth.cn.dialog.ListDialog.OnItemListener;
import com.uphealth.cn.dialog.VideoBeforeDialog.Builder;
import com.uphealth.cn.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * @description 信息提示对话框 
 * @data 2016年6月29日

 * @author jun.wang
 */
public class TipDialog extends Dialog {
	
	public TipDialog(Context context) {
		super(context);
	}

	public TipDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context; // 上下文对象
		private String title; // 对话框标题
		private String message; // 对话框内容
		private String confirm_btnText; // 按钮名称“确定”
		private String cancel_btnText; // 按钮名称“取消”
		private String neutral_btnText; // 按钮名称“隐藏”
		private View contentView; // 对话框中间加载的其他布局界面
		/* 按钮坚挺事件 */
		private DialogInterface.OnClickListener confirm_btnClickListener;
		private DialogInterface.OnClickListener cancel_btnClickListener;
		private DialogInterface.OnClickListener neutral_btnClickListener;

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
			this.contentView = v;
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
			this.confirm_btnClickListener = listener;
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
			this.confirm_btnClickListener = listener;
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
			this.cancel_btnClickListener = listener;
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
			this.cancel_btnClickListener = listener;
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
			this.neutral_btnClickListener = listener;
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
			this.neutral_btnClickListener = listener;
			return this;
		}

		public CustomDialog create() {
			final CustomDialog dialog = new CustomDialog(context,
					R.style.style_photo);
			
			return dialog;
		}

	}

}
