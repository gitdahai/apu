package com.uphealth.cn.ui.login.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.eat.BreakfastRankingActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;

/**
 * 无标题的H5窗体
 * @description 
 * @data 2016年7月12日

 * @author jun.wang
 */
@SuppressLint("SetJavaScriptEnabled")
public class HtmlNoTitleActivity extends BaseActivity implements OnClickListener{
	private String url = "" ;
	private WebView webView ;
	private TextView text_title ;
	private String titleStr = "" ;
	private String valueStr = "通过阿噗分享，查看最新的饮食、运动、美妆、生活方式资讯！" ;
	UMImage shareImage ;
	private String shareTitle = "" ;	
	private String shareUrl = "" ;	
	
	
    private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				Toast.makeText(HtmlNoTitleActivity.this, "收藏成功啦",Toast.LENGTH_SHORT).show();
				break;
				
			case 1002:
				Toast.makeText(HtmlNoTitleActivity.this,  "分享成功啦", Toast.LENGTH_SHORT).show();		
				break ;
				
			case 1003:
				Toast.makeText(HtmlNoTitleActivity.this, "分享失败啦", Toast.LENGTH_SHORT).show();	
				break ;
				
			case 1004:
				Toast.makeText(HtmlNoTitleActivity.this, "分享取消了", Toast.LENGTH_SHORT).show();	
				break ;	
				
			case 1005:
				showShareDialog();
				break ;

			default:
				break;
			}
			
		};
		
	} ;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_no_title);
		
		url = getIntent().getStringExtra("url") ;
		titleStr = getIntent().getStringExtra("titleStr") ;
		
		init() ;
		
		System.out.println("url=" + url);
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		text_title = (TextView)this.findViewById(R.id.title) ;
		text_title.setText(titleStr);
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
       
       webView.addJavascriptInterface(new JSInterface(this), "jsAPI");
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;

		default:
			break;
		}
	}
	
	 private class JSInterface {
	        private Context mContxt;
	        public JSInterface(Context mContxt) {
	            this.mContxt = mContxt;
	        }
	        @JavascriptInterface
	        public void openWindow(String value, String type,String title) {
	        	 System.out.println("value" +value);
	        	 System.out.println("value" +title);
	        	 System.out.println("value" +type);
	        	 
	        	 if (type.equals("pubTopicReply")) {
					 // 发布动态
	        		 
	        		 if(GlobalData.isH5SportClicik){
	        			 GlobalData.dynamicTopic = title ;
	        			 GlobalData.dynamicAddress = "所在位置" ;
	        			 GlobalData.dynamicVideoUrl = "" ;
	        			 Intent intent = new Intent(HtmlNoTitleActivity.this , PhotoThumbActivity.class) ;
						 startActivity(intent);
	        		 }
        			
				}else if (type.equals("share")) {
	        		 
	        		if(!TextUtils.isEmpty(value)){
	        			valueStr = value ;
	        		} 
	        		
	        		handler.sendEmptyMessage(1005) ; 	
	        		
				}else if (type.equals("url2")) {
					 Intent intent = new Intent(HtmlNoTitleActivity.this , HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directoryTwo+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	     System.out.println("url2"+HtmlUrl.directoryTwo+value);
				 }if(type.equals("url")){
	        		 Intent intent = new Intent(HtmlNoTitleActivity.this , HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directory+"html/"+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	 }	
	        }
	  } 	

	 private void showShareDialog(){
			if(null == shareImage){
				new ShareAction(HtmlNoTitleActivity.this).setDisplayList(SHARE_MEDIA.SINA,
		  				 SHARE_MEDIA.WEIXIN,
		  				 SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE)
		           .withTitle(shareTitle)
		           .withText(valueStr)
		           .withTargetUrl(shareUrl)
		           .setCallback(umShareListener)
		           .open();	
			}else {
				new ShareAction(HtmlNoTitleActivity.this).setDisplayList(SHARE_MEDIA.SINA,
		  				 SHARE_MEDIA.WEIXIN,
		  				 SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE)
		           .withTitle(shareTitle)
		           .withText(valueStr)
		           .withMedia(shareImage)
		           .withTargetUrl(shareUrl)
		           .setCallback(umShareListener)
		           .open();	
			}
	 }  
	 
	 private UMShareListener umShareListener = new UMShareListener() {
	        @Override
	        public void onResult(SHARE_MEDIA platform) {
	            Log.d("plat","platform"+platform);
	            
	            Toast.makeText(HtmlNoTitleActivity.this, platform+"", 400).show() ;
	            if(platform.name().equals("WEIXIN_FAVORITE")){
	            	handler.sendEmptyMessage(1001) ;
	            }else{
	            	handler.sendEmptyMessage(1002) ;
	            }
	        }

	        @Override
	        public void onError(SHARE_MEDIA platform, Throwable t) {
	        	handler.sendEmptyMessage(1003) ;
	            if(t!=null){
	                Log.d("throw","throw:"+t.getMessage());
	            }
	        }

	        @Override
	        public void onCancel(SHARE_MEDIA platform) {
	        	handler.sendEmptyMessage(1004) ;
	        }
	 }; 
	 
	 
}
