package com.uphealth.cn.ui.login.dynamic;

public class ApuHelper {

	public static void excuteThread(Runnable runnable){
		
		new Thread(runnable).start();
	}

}
