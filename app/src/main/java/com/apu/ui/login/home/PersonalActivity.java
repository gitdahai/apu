package com.apu.ui.login.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.apu.ui.login.center.ActivitySetting;
import com.uphealth.cn.R;

/**
 * @description 个人中心界面
 * @data 2016年5月27日

 * @author jun.wang
 */
public class PersonalActivity extends BaseActivity implements OnClickListener {
	private WebView webView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("詹姆斯");
		((TextView)findViewById(R.id.right)).setText("设置");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        ((TextView)findViewById(R.id.text_back)).setText("");
        ((TextView)findViewById(R.id.text_back)).setBackgroundResource(R.drawable.back);
        
        initWebView() ;
	}
	
	private void initWebView(){
		webView = (WebView)this.findViewById(R.id.webView) ;
		webView.loadUrl("https:www.baidu.com");
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

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.right:
			 Intent intent = new Intent(PersonalActivity.this , ActivitySetting.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}

}
