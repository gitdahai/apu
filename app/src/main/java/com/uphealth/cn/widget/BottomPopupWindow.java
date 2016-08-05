package com.uphealth.cn.widget; 

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.uphealth.cn.R;

/*********************************************
 * @author jun.wang
 * @version 创建时间�?015-4-14 下午3:16:16 
 */
public class BottomPopupWindow extends PopupWindow {
	
	public BottomPopupWindow(Activity activity , View view){
		   super(activity) ;
		   this.setContentView(view) ;
		   this.setWidth(LayoutParams.MATCH_PARENT) ;
		   this.setHeight(LayoutParams.WRAP_CONTENT) ;
		   this.setFocusable(true) ;
		   this.setOutsideTouchable(false) ;
		   this.setAnimationStyle(R.style.AnimBottom) ; 
		   this.setInputMethodMode(InputMethodManager.HIDE_NOT_ALWAYS) ;
	}

}
 