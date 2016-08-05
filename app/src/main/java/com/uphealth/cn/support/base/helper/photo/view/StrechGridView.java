package com.uphealth.cn.support.base.helper.photo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @category
 * @author hu
 */
public class StrechGridView extends GridView {


	public StrechGridView(Context context) {
		super(context);
	}

	public StrechGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public StrechGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
	}

}
