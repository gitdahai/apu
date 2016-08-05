package com.uphealth.cn.interf;

import com.uphealth.cn.widget.ObservableScrollView;

public interface ScrollViewListener {

	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);  
	
}
