package com.uphealth.cn;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.umeng.socialize.PlatformConfig;

/**
 * 配置文件
 * @description 
 * @data 2016年7月20日

 * @author jun.wang
 */
public class App extends MultiDexApplication {
	
	@Override
    public void onCreate() {
        super.onCreate();
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx054448050c3a660d", "4261071915907f9e3b5268a9464d9fd6");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("1289700876", "68671d5763a3d24e54e64ab1dedba641");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1105373742", "G7UOlh0Q0yeXx4ji");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");

    }

}
