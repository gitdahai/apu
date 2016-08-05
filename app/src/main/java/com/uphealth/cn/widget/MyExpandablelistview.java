package com.uphealth.cn.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @description 自动扩展的二级列表
 * @data 2016年6月12日

 * @author jun.wang
 */
public class MyExpandablelistview extends ExpandableListView {
	
	public MyExpandablelistview(Context context) {
		super(context);
	}
	
	public MyExpandablelistview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override                        
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
