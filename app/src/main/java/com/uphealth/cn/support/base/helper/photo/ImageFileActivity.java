package com.uphealth.cn.support.base.helper.photo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.uphealth.cn.support.base.helper.photo.adapter.FolderAdapter;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.support.base.helper.photo.util.PublicWay;
import com.uphealth.cn.support.base.helper.photo.util.Res;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;

public class ImageFileActivity extends Activity {

	private FolderAdapter folderAdapter;
	private Button bt_cancel;
	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("plugin_camera_image_file"));
		PublicWay.activityList.add(this);
		mContext = this;
		bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
		TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
		textView.setText(Res.getString("photo"));
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 鍙栨秷鎸夐挳鐨勭洃鍚�?
		public void onClick(View v) {
			//娓呯┖閫夋嫨鐨勫浘鐗�?
			Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent();
			intent.setClass(mContext, PhotoThumbActivity.class);
			startActivity(intent);
		}
	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(mContext, EditSquareActivity.class);
//			startActivity(intent);
//		}
//		
//		return true;
//	}

}
