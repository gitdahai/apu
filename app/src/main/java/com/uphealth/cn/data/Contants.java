package com.uphealth.cn.data;

import android.os.Environment;

public class Contants {
	
	public static final String account = "apuHealth" ;
	public static final String phone = "phone" ;
	public static final String userId = "userId" ;
	public static final String firstSetInfo = "firstSetInfo" ;
	
	public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() ;
	
	public static String api = "http://115.28.1.196/apu/interface/" ;
	
	public static String sendCode = api+"sendSMSCode" ;
	
	public static String login = api+"login" ;
	
	public static String getPersonInfo = api+"account/get" ;
	
	public static String updatePersonInfo = api+"account/update";
	
	public static String getAreaList = api+"getAreaList"; 
	
	// 健康信息标签设置
	public static String getDemandTagList = api+"getDemandTagList" ;
	
	// 健康信息设置
	public static String getDictList = api+"getDictList" ;
	
	// 资讯列表
	public static String getArticleList =api+"article/getArticleList" ;
	
	// 饮食方案列表
	public static String getFoodPlanList = api+"plan/getFoodPlanList" ;
	
	// 运动方案列表
	public static String getSportPlanList = api+"plan/getSportPlanList" ;
	
	// 获取运动场景
	public static String getScene = api+"getDictList?type=scene_type" ; 
	
	// 添加食材营养信息+1
	public static String getMaterial= api+"account/addNutritionIndex" ;
	
	// 第三方登录
	public static String extLogin = api+"ext/login";
	
	// 体质测试题库
	public static String getBodyList = api+"test/getBodyList";
	
	// 护肤方案列表
	public static String getSkinPlanList = api+"plan/getSkinPlanList" ;
	
	// 获取美妆场景
	public static String getAdorn = api+"getDictList?type=scene_makeup_type" ;
	
	// 根据美妆场景获取美妆详情
	public static String getMakeupPlanDetailByScene = api+"plan/getMakeupPlanDetailByScene" ;
	
	// 添加体脂率和体重
	public static String addWeightHistory = api+"account/addWeightHistory" ;
	
	// 阿噗健康问
	public static String execQuestion= api+"community/execQuestion" ;
	
	// 用户收藏列表
	public static String getCollectionList = api+"account/getCollectionList" ;

	// 取消收藏
	public static String deleteCollection = api+"account/deleteCollection" ;
	
	// 创建动态
	public static String addReply = api+"community/addReply" ;
	
	// 获取美妆热门资讯图片
	public static String getMakeupHotReplyList = api+"community/getMakeupHotReplyList" ;
	
	// 运动详情
	public static String getSportPlanDetail = api+"plan/getSportPlanDetail" ;
	
	// 用户忌口的食材列表
	public static String getAvoidFoodList = api+"account/getAvoidFoodList" ;
	
	// 获取食材列表
	public static String getFoodMaterialList = api+"food/getFoodMaterialList" ;
	
	// 添加用户忌口食材
	public static String addAvoidFood = api+"account/addAvoidFood" ;
	
	// 删除用户的忌口食材
	public static String deleteAvoidFood = api+"account/deleteAvoidFood" ;
	
	// 意见反馈列表
	public static String getFeedbackList = api+"feedback/getFeedbackList" ;
	
	// 添加意见反馈
	public static String addFeedback = api+"feedback/addFeedback" ; 
	
	// 阿噗的餐单
	public static String getMyTakeFoodPlanList = api+"account/getMyTakeFoodPlanList" ;
	
	// 获取卡路里
	public static String getSuggestCalorie = api+"account/getSuggestCalorie" ;
	
	// 添加我的美丽指数
	public static String addSkinIndex = api+"account/addSkinIndex" ;
	
	// 最热话题列表
	public static String getHotTopicList = api+"community/getHotTopicList" ;
	
	// 最热达人列表
	public static String getHotMasterList = api+"getHotMasterList" ;
	
	// 获取当前我参与的方案
	public static String getTakeIngPlan = api+"account/getTakeIngPlan" ;
	
	// 获取关注、被关注成员数量
	public static String getFocusAccountCount = api+"account/getFocusAccountCount" ;
	
	// 方案完成进度
	public static String updatePlanProgress = api+"account/updatePlanProgress" ;
	
	
	
	
}
