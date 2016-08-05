package com.uphealth.cn.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;

/**
 * Description: activity manager
 * date: 2014-9-26
 * 
 * @author jun.wang.ma 
 * @version
 */
public class ActivityManager {
    
    private static Stack<Activity> activityStack ;
    
    private static ActivityManager instance ;
    
    private ActivityManager(){
        
    }
    
    /**
     * Description: get Instance
     * @return
     * @return ActivityManager
     */
    public static ActivityManager getInstance(){
        if(null == instance){
             instance = new ActivityManager() ;
        }
        return instance ;
    }
  
    /**
     * Description: push activity
     * 
     * @return void
     */
    public void pushActivity(Activity activity){
        if(null == activityStack){
             activityStack = new Stack<Activity>() ;
        }
        activityStack.add(activity) ;
    }
    
    /**
     * Description: pop activity
     * @param activity
     * @return void
     */
    public void popActivity(Activity activity){
        if(null != activity){
             activity.finish() ;
             
             if(null != activityStack){
                  activityStack.remove(activity) ;
             }
             
             activity = null ;
        }
    }
    
    /**
     * Description: pop all activity
     * @param classz
     * @return void
     */
    @SuppressWarnings("rawtypes")
    public void popAllActivity(Class classz){
        List<Activity> tempList = new ArrayList<Activity>();

        for (Activity activity : activityStack) {
                if (activity != null && !activity.getClass().equals(classz)) {
                        tempList.add(activity);
                }
        }

        for (Activity activity : tempList) {
                popActivity(activity);
        }
    }

}
