package com.uphealth.cn.data; 

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/********************************************* 
 * 类说明   本地临时保存文件
 * @author jun.wang
 * @version 创建时间：2015-4-13 下午3:23:53 
 */
public class AppSharedPreferences {
	
	private static SharedPreferences sharedPreferences ;
	       
	 /**
     * 构造函数
     * @param context
     * @param user 用户标识 此处用手机号做为标识
     */
	public AppSharedPreferences(Context context , String user){
		sharedPreferences = context.getSharedPreferences(user, context.MODE_PRIVATE) ;
	}
	
	/**
     * Description: 提交数据
     * @param key String类型
     * @param value
     * @return void
     */
    public static void commit(String key , String value){
    	if(null != value && "".equals(value)){
    		
    	}
    	
        sharedPreferences.edit().putString(key, value).commit() ;
    }
    
    /**
     * Description: 提交数据
     * @param key Boolean类型
     * @param params
     * @return void
     */
    public static void commit(String key , boolean params){
        sharedPreferences.edit().putBoolean(key, params).commit() ;
    }
    
    /**
     * Description: 提交数据
     * @param key String[]类型
     * @param params
     * @return void
     */
    public static void commitArray(String key , List<String> params){
    	SharedPreferences.Editor editor = sharedPreferences.edit() ;
    	
    	editor.putInt(key, params.size()) ;
    	
    	for(int i=0;i<params.size();i++) {  
    		editor.remove(key + i);  
    		editor.putString(key + i, params.get(i));    
        }  
    	
    	editor.commit() ;
    }
 
    /**
     * description:得到数组列表
     * @param key
     * @param params
     * @return
     */
    public static List<String> getArray(String key , String params){
    	 try {
                List<String> result = new ArrayList<String>() ;
                 
                int size = sharedPreferences.getInt(key, 0) ;
                
                for(int i = 0 ; i < size ; i ++){
                	result.add(sharedPreferences.getString(key+i, null)) ;
                }
                
                return result ;
       }
       catch (ClassCastException e) {
           e.printStackTrace() ;
       }
       return null ;
    }
    
    /**
     * Description: 得到字符串类型的数据
     * @param key
     * @return
     * @return String
     */
    public static String getString(String key , String params){
        try {
              return sharedPreferences.getString(key, params) ;
        }
        catch (ClassCastException e) {
            e.printStackTrace() ;
        }
        return null ;
    }
    
    /**
     * Description: 得到boolean 类型的数据
     * @param key
     * @param flag
     * @return
     * @return boolean
     */
    public static boolean getBoolean(String key , boolean flag){
        try {
            return sharedPreferences.getBoolean(key, flag);
        }
            catch (ClassCastException e) {
            e.printStackTrace() ;
        }
      return false ;
    }
	
	

}
 