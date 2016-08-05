package com.apu.interf;

import com.apu.widget.ObservableScrollView;

public interface ScrollViewListener {

	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);  
	
}
