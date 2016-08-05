package com.uphealth.cn.ui.login.home;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

import com.uphealth.cn.R;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 最热窗体 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class HotActivity extends BaseActivity {
	private WebView webView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);
		
		init() ;
	}
	
	private void init(){
		webView = (WebView)this.findViewById(R.id.webView) ;
		webView.loadUrl(HtmlUrl.H7_2);
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
	}
	

}
