package com.apu.ui.login.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description Html5的网页窗体  样式返回 标题 分享
 * @data 2016年5月26日

 * @author jun.wang
 */
@SuppressLint("SetJavaScriptEnabled")
public class HtmlActivity extends BaseActivity implements OnClickListener {
	private String titleStr = "" ;
	private String url = "" ;
	private String noRight = "" ;
	private WebView webView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html);
		
		titleStr = getIntent().getStringExtra("titleStr") ;
		url = getIntent().getStringExtra("url") ;
		
		Bundle bundle = getIntent().getExtras() ;
		if(bundle.containsKey("noRight")){
			noRight = getIntent().getStringExtra("noRight") ;
		}
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText(titleStr);
		findViewById(R.id.right).setOnClickListener(this);
		
		if(noRight.equals("noRight")){
			findViewById(R.id.right).setVisibility(View.GONE);
		}else {
			findViewById(R.id.right).setVisibility(View.VISIBLE);
		}
		
		webView = (WebView)this.findViewById(R.id.webView) ;
		webView.loadUrl(url);
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
			 break ;

		default:
			break;
		}
		
	}
	
	

}
