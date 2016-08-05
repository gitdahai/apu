package com.uphealth.cn.data;

import android.content.Context;

import com.uphealth.cn.bean.ProvinceBean;
import com.uphealth.cn.bean.ThirdBean;
import com.uphealth.cn.bean.VideoBean;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.model.CommonModel;
import com.uphealth.cn.model.TestBody;
import com.uphealth.cn.widget.ProgressBarView1;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 全局数据 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class GlobalData {
	
	public static int widthPixels = 0 ;
	public static int heightPixels = 0 ;
	
	// 保存用户信息
	public static AccountModel accountModel = new AccountModel() ;
	
	// 首页轮滑广告的个数
    public static int GALLERY_SIZE = 0;
    
    // 相册最大选择照片数
    public static int photosMax = 3 ;
    
    public static boolean isHead = false ;
    public static boolean isHeadTake = false ;
    public static String takePhotoPath = "" ;
    
    // H5运动模块控制标记
    public static boolean isH5SportClicik = true ;
    
    // 由于拍照集成在相册里，这里需要做一个区分 1.完善用户信息   2.个人信息设置  3.发布动态
    public static String takeToward = "1" ;
    
    // 个人信息缓存数据
//    public static PersonInfo personInfo = new PersonInfo() ;
    
    // 第三方登录个人信息
    public static ThirdBean thirdBean = new ThirdBean() ;
    
    // 第三方登录返回的账户Id
    public static String thirdAccountId =  "" ; 
    public static boolean isThird = false ;  // 是否是第三方登录
    
    // 题库测试缓存数据，退出应用时清除
    public static List<TestBody> testBodyList = new ArrayList<TestBody>() ;
    
    // 音视频资源
    public static List<VideoBean> videoLists = new ArrayList<>() ;
    
    // 运动方案Id
    public static String sportId = "" ;
    
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
	 * 返回当前用户的手机号
	 */
	@SuppressWarnings("static-access")
	public static String getUserId(Context context) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		return sharedPreferences.getString(Contants.userId, "");
	}
	
	/**
	 * 保存用户的手机号
	 */
	@SuppressWarnings("static-access")
	public static void savePhone(Context context , String params) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		sharedPreferences.commit(Contants.phone, params);
	}
	
	/**
	 * 保存用户的Id
	 */
	@SuppressWarnings("static-access")
	public static void saveUserId(Context context , String params) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		sharedPreferences.commit(Contants.userId, params);
	}
	
	/**
	 * 保存用户是否是首次设置个人信息
	 */
	@SuppressWarnings("static-access")
	public static void saveFirst(Context context , String params) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		sharedPreferences.commit(Contants.firstSetInfo, params);
	}
	
	/**
	 * 得到用户是否是首次设置个人信息
	 */
	@SuppressWarnings("static-access")
	public static String getFirst(Context context , String params) {
		AppSharedPreferences sharedPreferences = new AppSharedPreferences(
				context, Contants.account);
		return sharedPreferences.getString(Contants.firstSetInfo, "");
	}
	
	public static String getSex(String sex){
		if(sex.equals("1")){
			return "男" ;
		}else {
			return "女" ;
		}
	}
	
	public static String getInterfSex(String sex){
		if(sex.equals("男")){
			return "1" ;
		}else {
			return "2" ;
		}
	}
	
	// 首页滑动是否停留
	public static boolean isStay = true ;
	
	public static boolean maxTime = true ;
	
	// 视频准备界面Dialog创建标识
	public static boolean isCreateDialog = true ;
	
	// 是否请求城市接口
	public static boolean isRequestCity = true ;
	
	// 城市唯一编码
	public static String cityId = "" ;
	
	// 省份列表信息
	public static List<ProvinceBean> provinceLists = new ArrayList<ProvinceBean>() ;
	
	// 发布动态里面的数据  所在位置
	public static String dynamicAddress = "" ; 
	
	// 动态主题字符串
	public static String dynamicTopic = "" ; 
	
	// 动态主题Id
	public static String dynamicTopicId = "" ; 
	
	// 动态 视频url
	public static String dynamicVideoUrl = "" ; 
	
	// 健康问阿噗标志
	public static String apuAsk = "" ;
	
	// 上传的是图片
	public static boolean isPic = false ;
	
	// 健康标签list
	public static List<CommonModel> healthLebalList = new ArrayList<>() ;

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
	
    public static List<String> getBeijing(){
    	 List<String> cityList = new ArrayList<String>() ;
    	 cityList.add("东城区") ;
         cityList.add("西城区") ;
         cityList.add("海淀区") ;
         cityList.add("朝阳区") ;
         cityList.add("丰台区") ;
         cityList.add("门头沟区") ;
         cityList.add("石景山区") ;
         cityList.add("房山区") ;
         cityList.add("通州区") ;
         cityList.add("顺义区") ;
         cityList.add("昌平区") ;
         cityList.add("大兴区") ;
         cityList.add("怀柔区") ;
         cityList.add("平谷区") ;
         cityList.add("延庆区") ;
         cityList.add("密云区") ;
    	
    	return cityList ;
    }
    
    public static List<String> getShangHai(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("黄浦区");
    	citys.add("徐汇区") ;
    	citys.add("长宁区") ;
    	citys.add("静安区") ;
    	citys.add("普陀区") ;
    	citys.add("虹口区") ;
    	citys.add("杨浦区") ;
    	citys.add("闸北区") ;
    	citys.add("闵行区") ;
    	citys.add("宝山区") ;
    	citys.add("嘉定区") ;
    	citys.add("浦东区") ;
    	citys.add("金山区") ;
    	citys.add("松江区") ;
    	citys.add("青浦区") ;
    	citys.add("奉贤区") ;
    	
    	return citys ;
    }
    
    public static List<String> getTianJing(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("和平区");
    	citys.add("河东区") ;
    	citys.add("河西区") ;
    	citys.add("南开区") ;
    	citys.add("河北区") ;
    	citys.add("红桥区") ;
    	citys.add("滨海新区") ;
    	citys.add("东丽区") ;
    	citys.add("西青区") ;
    	citys.add("津南区") ;
    	citys.add("北辰区") ;
    	citys.add("武清区") ;
    	citys.add("宝坻区") ;
    	citys.add("宁河区") ;
    	citys.add("静海区") ;
    	citys.add("蓟县") ;
    	return citys ;
    }
    
    public static List<String> getChongqing(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("渝中区");
    	citys.add("大渡口区") ;
    	citys.add("江北区") ;
    	citys.add("沙坪坝区") ;
    	citys.add("九龙坡区") ;
    	citys.add("南岸区") ;
    	citys.add("北碚区") ;
    	citys.add("渝北区") ;
    	citys.add("巴南区") ;
    	citys.add("涪陵区") ;
    	citys.add("綦江区") ;
    	citys.add("大足区") ;
    	citys.add("长寿区") ;
    	citys.add("江津区") ;
    	citys.add("合川区") ;
    	citys.add("永川区") ;
    	citys.add("南川区") ;
    	citys.add("璧山区") ;
    	citys.add("铜梁区") ;
    	citys.add("潼南区") ;
    	citys.add("荣昌区") ;
    	citys.add("万州区") ;
    	citys.add("梁平县") ;
    	citys.add("城口县") ;
    	citys.add("丰都县") ;
    	citys.add("垫江县") ;
    	citys.add("忠县") ;
    	citys.add("开县") ;
    	citys.add("云阳县") ;
    	citys.add("奉节县") ;
    	citys.add("巫山县") ;
    	citys.add("巫溪县") ;
    	citys.add("黔江区") ;
    	citys.add("武隆县") ;
    	citys.add("石柱土家族自治县") ;
    	citys.add("秀山土家族苗族自治县") ;
    	citys.add("酉阳土家族苗族自治县") ;
    	citys.add("彭水苗族土家族自治县") ;
    	
    	return citys ;
    }
    
    public static List<String> getHebei(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("石家庄市");
    	citys.add("唐山市") ;
    	citys.add("秦皇岛市") ;
    	citys.add("邯郸市") ;
    	citys.add("邢台市") ;
    	citys.add("保定市") ;
    	citys.add("张家口市") ;
    	citys.add("承德市") ;
    	citys.add("沧州市") ;
    	citys.add("廊坊市") ;
    	citys.add("衡水市") ;
    	
    	return citys ;
    }
    
    public static List<String> getShanxi(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("太原市");
    	citys.add("大同市") ;
    	citys.add("阳泉市") ;
    	citys.add("长治市") ;
    	citys.add("晋城市") ;
    	citys.add("朔州市") ;
    	citys.add("晋中市") ;
    	citys.add("运城市") ;
    	citys.add("忻州市") ;
    	citys.add("临汾市") ;
    	citys.add("吕梁市") ;
    	
    	return citys ;
    }
    
    public static List<String> getLiaoning(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("太原市");
    	citys.add("大同市") ;
    	citys.add("阳泉市") ;
    	citys.add("长治市") ;
    	citys.add("晋城市") ;
    	citys.add("朔州市") ;
    	citys.add("晋中市") ;
    	citys.add("运城市") ;
    	citys.add("忻州市") ;
    	citys.add("临汾市") ;
    	citys.add("吕梁市") ;
    	
    	return citys ;
    }
    
    public static List<String> getJiangsu(){
    	List<String> citys = new ArrayList<String>() ;
    	citys.add("南京市");
    	citys.add("苏州市") ;
    	citys.add("无锡市") ;
    	citys.add("常州市") ;
    	citys.add("连云港市") ;
    	citys.add("镇江市") ;
    	citys.add("徐州市") ;
    	
    	return citys ;
    }
    
    public static List<String> getPersonHeight(){
    	List<String> lists = new ArrayList<>() ;
    	lists.clear(); 
    	lists.add("请选择身高");
    	
    	for(int i = 145 ; i <= 226 ; i ++ ){
    		  lists.add(i+"") ;
    	}
    	
    	return lists ;
    }
	
    public static List<String> getPersonWeight(){
    	List<String> lists = new ArrayList<>() ;
    	lists.clear();
    	lists.add("当前体重") ;
    	
    	for(int i = 30 ; i <= 120 ; i ++){
    		  lists.add(i+"") ;
    	}
    	
		return lists; 
    }
    
    public static List<String> getPersonGoalWeight(){
    	List<String> lists = new ArrayList<>() ;
    	lists.clear();
    	lists.add("目标体重") ;
    	
    	for(int i = 30 ; i <= 120 ; i ++){
    		  lists.add(i+"") ;
    	}
    	
		return lists; 
    }
    
    public static List<String> getWeight(){
    	List<String> lists = new ArrayList<>() ;
    	lists.clear();
    	lists.add("体重(公斤)") ;
    	
    	for(int i = 30 ; i <= 120 ; i ++){
    		  lists.add(i+"") ;
    	}
    	
		return lists; 
    }
    
    public static List<String> getWeightRate(){
    	List<String> lists = new ArrayList<>() ;
    	lists.clear();
    	lists.add("体脂率(%)") ;
    	
    	for(int i = 10 ; i <= 95 ; i ++){
    		  lists.add(i+"") ;
    	}
    	
		return lists; 
    }
    
    public static List<ProgressBarView1> getProgressList(Context context){
    	List<ProgressBarView1> list = new ArrayList<>() ;
    	
    	ProgressBarView1 progressBarView = new ProgressBarView1(context) ;
    	progressBarView.setMax(100);
    	progressBarView.setProgress(20);
    	list.add(progressBarView) ;
    	
    	return list ;
    }
    
    public static ProgressBarView1 getProgress(Context context){
    	
    	ProgressBarView1 progressBarView = new ProgressBarView1(context) ;
    	progressBarView.setMax(100);
    	progressBarView.setProgress(20);
    	
    	return progressBarView ;
    }
}
