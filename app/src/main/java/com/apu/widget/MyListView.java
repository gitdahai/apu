package com.apu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @author jun.wang
 *
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override                        
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	

}
