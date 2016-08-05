package com.apu.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 全局数据 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class GlobalData {
	
	// 首页轮滑广告的个数
    public static int GALLERY_SIZE = 0;
    
    public static List<String> getGridOne(){
    	List<String> list = new ArrayList<String>() ;
    	list.clear();
    	
    	list.add("新人") ;
    	list.add("进阶") ;
    	list.add("达人") ;
    	
    	return list ;
    } 
    
    public static List<String> getGridTwo(){
    	List<String> list = new ArrayList<String>() ;
    	list.clear();
    	
    	list.add("塑形") ;
    	list.add("减肥") ;
    	list.add("增肌") ;
    	
    	return list ;
    } 
    
    public static List<String> getGridThree(){
    	List<String> list = new ArrayList<String>() ;
    	list.clear();
    	
    	list.add("裸妆") ;
    	list.add("淡妆") ;
    	list.add("浓妆") ;
    	
    	return list ;
    } 
    
    public static List<String> getGridFour(){
    	List<String> list = new ArrayList<String>() ;
    	list.clear();
    	
    	list.add("油性") ;
    	list.add("干性") ;
    	list.add("混合") ;
    	list.add("敏感") ;
    	
    	return list ;
    } 
    
    /**
	 * 返回当前用户的手机号
	 */
	@SuppressWarnings("static-access")
	public static String getPhone(Context context) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		return sharedPreferences.getString(Contants.phone, "");
	}
	
	/**
	 * 提交用户的手机号
	 */
	@SuppressWarnings("static-access")
	public static void commitPhone(Context context , String params) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		sharedPreferences.commit(Contants.phone, params);
	}
	
	// 首页滑动是否停留
	public static boolean isStay = true ;

}
