package com.uphealth.cn.ui.login.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.ui.login.eat.HateFoodActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.fragment.find.HottestPeopleActivity;
import com.uphealth.cn.ui.login.fragment.find.HottestReplyActivity;
import com.uphealth.cn.ui.login.sports.SportBefaultActivity;

/**
 * @description 发现
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint("SetJavaScriptEnabled")
public class CustomFragment extends Fragment {
    private View view ;
    private WebView webView ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_custom, container, false);
		return view ;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		init() ;
	}
	
	private void init(){
		webView = (WebView)view.findViewById(R.id.webView) ;
		webView.loadUrl(HtmlUrl.H7_2+"?accountId="+GlobalData.getUserId(getActivity()));
		WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        
        // 设置可以支持缩放 
        settings.setSupportZoom(true); 
        // 设置出现缩放工具 
        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
		//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
       webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
       });
       
       webView.addJavascriptInterface(new JSInterface(getActivity()), "jsAPI");
	}
	
	 private class JSInterface {
	        private Context mContxt;
	        public JSInterface(Context mContxt) {
	            this.mContxt = mContxt;
	        }
	        
	        @JavascriptInterface
	        public void openWindow(String value, String type,String title) {
	        	 System.out.println("openWindow" +value);
	        	 System.out.println("openWindow" +title);
	        	 System.out.println("openWindow" +type);
	        	
	        	 if(type.equals("url")){
	        		 Intent intent = new Intent(getActivity(), HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directory+"html/"+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	 }else if (type.equals("share")) {
				     
	        		 System.out.println("share"+value);
//	        		 initShareData(value) ;
	        		 
	        		 System.out.println("share");
	        		 // 分享
//	        		 showTemplate(YouTuiViewType.WHITE_GRID);
				 }else if (type.equals("url2")) {
					 Intent intent = new Intent(getActivity(), HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directoryTwo+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	     System.out.println("url2"+HtmlUrl.directoryTwo+value);
				 }else if (type.equals("beginSportPlan")) {
					  // 开始计划
					  Intent intent = new Intent(getActivity(), SportBefaultActivity.class) ;
					  intent.putExtra("id", value) ;  // 废弃了，自己传的
					  intent.putExtra("titleStr", "您需要准备的运动器材") ;
					  startActivity(intent);
				 }else if (type.equals("favorite")) {
					  // 食材偏好设置
					  Intent intent = new Intent(getActivity(), HateFoodActivity.class) ;
					  startActivity(intent);
				 }else if (type.equals("publish")) {
					  // 发布动态
					 GlobalData.dynamicTopic = "#输入话题#" ;
					 GlobalData.dynamicTopicId = "" ;
					 GlobalData.dynamicAddress = "所在位置" ;
					 GlobalData.dynamicVideoUrl = "" ;
					 Intent intent = new Intent(getActivity() , PhotoThumbActivity.class) ;
				 	 startActivity(intent);
					 
				 }else if (type.equals("hottestReply")) {
					 // 最热话题
					 Intent intent = new Intent(getActivity() , HottestReplyActivity.class) ;
				 	 startActivity(intent);
					 
				}else if (type.equals("hottestPeople")) {
					// 最热达人
					 Intent intent = new Intent(getActivity() , HottestPeopleActivity.class) ;
				 	 startActivity(intent);
					
				}else if (type.equals("hottestReply")) {
					// 搜索话题
					
					
				}else if(type.equals("hottestPeople")){
					// 搜索用户
					
				}
	        }
	  } 	

}
