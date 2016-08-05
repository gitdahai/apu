package com.uphealth.cn.ui.login.fragment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.uphealth.cn.R;
import com.uphealth.cn.bean.VideoBean;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.download.FileDownloadThread;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.eat.BreakfastRankingActivity;
import com.uphealth.cn.ui.login.eat.HateFoodActivity;
import com.uphealth.cn.ui.login.sports.SportBefaultActivity;

/**
 * @description Html5的网页窗体  样式返回 标题 分享
 * @data 2016年5月26日

 * @author jun.wang
 */
@SuppressLint({ "SetJavaScriptEnabled", "HandlerLeak" })
public class HtmlActivity extends BaseActivity implements OnClickListener {
	private String titleStr = "" ;
	private String shareTitle = "" ;
	private String url = "" ;
	private String pitUrl = "" ;
	private String noRight = "" ;
	private String shareUrl = "" ;
	private WebView webView ;
	
	/** 是否使用积分系统 */
	private boolean usePointSys = false;

	/** 点击社交平台后，是否隐藏分享界面, 默认是显示的 */
	private boolean isShowSharePop = false;
	
	/** 分享的网络图片地址 */
	private static final String IMAGEURL = "http://cdnup.b0.upaiyun.com/media/image/default.png";

	private String valueStr = "通过阿噗分享，查看最新的饮食、运动、美妆、生活方式资讯！" ;
	UMImage shareImage ;
//	private Bitmap share_bimap ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				Toast.makeText(HtmlActivity.this, "收藏成功啦",Toast.LENGTH_SHORT).show();
				break;
				
			case 1002:
				Toast.makeText(HtmlActivity.this,  "分享成功啦", Toast.LENGTH_SHORT).show();		
				break ;
				
			case 1003:
				Toast.makeText(HtmlActivity.this, "分享失败啦", Toast.LENGTH_SHORT).show();	
				break ;
				
			case 1004:
				Toast.makeText(HtmlActivity.this, "分享取消了", Toast.LENGTH_SHORT).show();	
				break ;	
				
			case 1005:
				showShareDialog();
				break ;

			default:
				break;
			}
			
		};
		
	} ;
	
	private Button start_button ;   // 开始今天运动
	private ProgressBar download_progress ;
	private TextView text_download ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html);
		
		titleStr = getIntent().getStringExtra("titleStr") ;
		url = getIntent().getStringExtra("url") ;
		pitUrl = getIntent().getStringExtra("pitUrl") ;
		shareUrl = getIntent().getStringExtra("shareUrl") ;
		shareTitle = getIntent().getStringExtra("shareTitle") ;
		start_button = (Button)this.findViewById(R.id.start_button) ;
		start_button.setOnClickListener(this);
		download_progress = (ProgressBar)this.findViewById(R.id.download_progress) ;
		text_download = (TextView)this.findViewById(R.id.text_download) ;
		
		Bundle bundle = getIntent().getExtras() ;
		if(bundle.containsKey("noRight")){
			noRight = getIntent().getStringExtra("noRight") ;
		}
		
//		// 分享默认图
//		share_bimap = BitmapFactory.decodeResource(getResources(),
//				R.drawable.app_icon);
//		
//		shareImage = new UMImage(HtmlActivity.this,share_bimap);
		
		init() ;
		
		System.out.println("url=" + url);
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText(titleStr);
		findViewById(R.id.right).setOnClickListener(this);
		
//		if(noRight.equals("noRight")){
			findViewById(R.id.right).setVisibility(View.GONE);
//		}else {
//			findViewById(R.id.right).setVisibility(View.VISIBLE);
//		}
		
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
       
       if(!TextUtils.isEmpty(pitUrl)){
    	   shareImage = new UMImage(HtmlActivity.this,pitUrl); 
       }
       
       // 下载测试数据
       GlobalData.videoLists.clear();
       
       VideoBean bean = new VideoBean() ;
       bean.setVideoUrl("http://115.28.1.196/apu/userfiles/1/videos/2016/08/20160801113532.mp4");
       GlobalData.videoLists.add(bean) ;
       
       VideoBean bean2 = new VideoBean() ;
       bean2.setVideoUrl("http://115.28.1.196/apu/userfiles/1/videos/2016/08/20160801113532.mp4");
       GlobalData.videoLists.add(bean2) ;
       
       VideoBean bean3 = new VideoBean() ;
       bean3.setVideoUrl("http://115.28.1.196/apu/userfiles/1/videos/2016/08/20160801113532.mp4");
       GlobalData.videoLists.add(bean3) ;
       
//     initShareData(valueStr) ;
	}

//	private void initShareData(String value){
//		shareData = new ShareData();
//		shareData.setAppShare(false); // 是否为应用分享，如果为true，分享的数据需在友推后台设置
//		shareData.setDescription("阿噗分享");// 待分享内容的描述
//		shareData.setTitle(titleStr); // 待分享的标题
//		shareData.setText(value);// 待分享的文字
//		shareData.setImage(ShareData.IMAGETYPE_INTERNET, pitUrl);// 设置网络分享地址
//		shareData.setPublishTime(Utils.getCurrentDateTwo());
//		shareData.setTargetId(String.valueOf(100));
//		shareData.setTargetUrl(url);// 待分享内容的跳转链接
//		initTemplate() ;	
//	}
	
//	/**
//	 * 初始化分享界面
//	 */
//	private void initTemplate() {
//		template = new YtTemplate(this, YouTuiViewType.WHITE_GRID, usePointSys);
//		template.setShareData(shareData);
//		template.addListeners(listener);
//	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.right:
			 //TODO
			 Intent intent = new Intent(HtmlActivity.this , BreakfastRankingActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 开始今天运动	 
		case R.id.start_button:
			 startDown() ;
			 start_button.setVisibility(View.GONE);
			 text_download.setVisibility(View.VISIBLE);
			 download_progress.setVisibility(View.VISIBLE);
			 break ;

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
	        	 System.out.println("openWindow" +value);
	        	 System.out.println("openWindow" +title);
	        	 System.out.println("openWindow" +type);
	        	 System.out.println("openWindow" +pitUrl);
	        	
	        	 if(type.equals("url")){
	        		 Intent intent = new Intent(HtmlActivity.this , HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directory+"html/"+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	 }else if (type.equals("share")) {
	        		 
	        		if(!TextUtils.isEmpty(value)){
	        			valueStr = value ;
	        		} 
	        		
	        		handler.sendEmptyMessage(1005) ; 	
	        			
	        			// 分享
//						new ShareAction(HtmlActivity.this).setDisplayList(SHARE_MEDIA.SINA,
//	    	       				 SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,
//	    	       				 SHARE_MEDIA.WEIXIN_CIRCLE)
//	    	                .withTitle(titleStr)
//	    	                .withText(value)
//	    	                .withMedia(shareImage)
//	    	                .withTargetUrl(url)
//	    	                .setCallback(umShareListener)
//	    	                .open();
//	        		}else {
//	        			// 分享
//						new ShareAction(HtmlActivity.this).setDisplayList(SHARE_MEDIA.SINA,
//	    	       				 SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,
//	    	       				 SHARE_MEDIA.WEIXIN_CIRCLE)
//	    	                .withTitle(titleStr)
//	    	                .withText(value)
//	    	                .withTargetUrl(url)
//	    	                .setCallback(umShareListener)
//	    	                .open();
//					} 
	        		 
				 }else if (type.equals("url2")) {
					 Intent intent = new Intent(HtmlActivity.this , HtmlNoTitleActivity.class) ;
	        	     intent.putExtra("url", HtmlUrl.directoryTwo+value) ;
	        	     intent.putExtra("titleStr", title) ;
	        	     startActivity(intent);
	        	     System.out.println("url2"+HtmlUrl.directoryTwo+value);
				 }else 
				 if (type.equals("beginSportPlan")) {
					  // 开始计划
					  Intent intent = new Intent(HtmlActivity.this, SportBefaultActivity.class) ;
					  intent.putExtra("id", value) ;  // 废弃了，自己传的
					  intent.putExtra("titleStr", "您需要准备的运动器材") ;
					  startActivity(intent);
				 }else if (type.equals("favorite")) {
					  // 食材偏好设置
					  Intent intent = new Intent(HtmlActivity.this, HateFoodActivity.class) ;
					  startActivity(intent);
				}
	        }
	} 	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("resultHtml","onActivityResult");
    }
	 
	 private UMShareListener umShareListener = new UMShareListener() {
	        @Override
	        public void onResult(SHARE_MEDIA platform) {
	            Log.d("plat","platform"+platform);
	            
	            Toast.makeText(HtmlActivity.this, platform+"", 400).show() ;
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
	 
	 
	private void showShareDialog(){
		if(null == shareImage){
			new ShareAction(HtmlActivity.this).setDisplayList(SHARE_MEDIA.SINA,
	  				 SHARE_MEDIA.WEIXIN,
	  				 SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE)
	           .withTitle(shareTitle)
	           .withText(valueStr)
	           .withTargetUrl(shareUrl)
	           .setCallback(umShareListener)
	           .open();	
		}else {
			new ShareAction(HtmlActivity.this).setDisplayList(SHARE_MEDIA.SINA,
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
	 
	private void startDown(){
		
		
		
		// 获取SD卡路径
		String path = Environment.getExternalStorageDirectory()
				+ "/amosdownload/";
		File file = new File(path);
		// 如果SD卡目录不存在创建
		if (!file.exists()) {
			file.mkdir();
		}
		// 设置progressBar初始化
		download_progress.setProgress(0);

		// 简单起见，我先把URL和文件名称写死，其实这些都可以通过HttpHeader获取到
//		String downloadUrl = "http://gdown.baidu.com/data/wisegame/91319a5a1dfae322/baidu_16785426.apk";
		String downloadUrl = GlobalData.videoLists.get(0).getVideoUrl();
		String fileName = "baidu_16785426.mp4";
		int threadNum = 5;
		String filepath = path + fileName;
		Log.d("download", "download file  path:" + filepath);
		downloadTask task = new downloadTask(downloadUrl, threadNum, filepath);
		task.start();
	}
	
	/**
	 * 多线程文件下载
	 * 
	 * @author yangxiaolong
	 * @2014-8-7
	 */
	class downloadTask extends Thread {
		private String downloadUrl;// 下载链接地址
		private int threadNum;// 开启的线程数
		private String filePath;// 保存文件路径地址
		private int blockSize;// 每一个线程的下载量

		public downloadTask(String downloadUrl, int threadNum, String fileptah) {
			this.downloadUrl = downloadUrl;
			this.threadNum = threadNum;
			this.filePath = fileptah;
		}

		@Override
		public void run() {

			FileDownloadThread[] threads = new FileDownloadThread[threadNum];
			try {
				URL url = new URL(downloadUrl);
				Log.d("download", "download file http path:" + downloadUrl);
				URLConnection conn = url.openConnection();
				// 读取下载文件总大小
				int fileSize = conn.getContentLength();
				if (fileSize <= 0) {
					System.out.println("读取文件失败");
					return;
				}
				// 设置ProgressBar最大的长度为文件Size
				download_progress.setMax(fileSize);

				// 计算每条线程下载的数据长度
				blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
						: fileSize / threadNum + 1;

				Log.d("download", "fileSize:" + fileSize + "  blockSize:"+blockSize);

				File file = new File(filePath);
				for (int i = 0; i < threads.length; i++) {
					// 启动线程，分别下载每个线程需要下载的部分
					threads[i] = new FileDownloadThread(url, file, blockSize,
							(i + 1));
					threads[i].setName("Thread:" + i);
					threads[i].start();
				}

				boolean isfinished = false;
				int downloadedAllSize = 0;
				while (!isfinished) {
					isfinished = true;
					// 当前所有线程下载总量
					downloadedAllSize = 0;
					for (int i = 0; i < threads.length; i++) {
						downloadedAllSize += threads[i].getDownloadLength();
						if (!threads[i].isCompleted()) {
							isfinished = false;
						}
					}
					// 通知handler去更新视图组件
					Message msg = new Message();
					msg.getData().putInt("size", downloadedAllSize);
					mHandler.sendMessage(msg);
					// Log.d(TAG, "current downloadSize:" + downloadedAllSize);
					Thread.sleep(1000);// 休息1秒后再读取下载进度
				}
				Log.d("download", " all of downloadSize:" + downloadedAllSize);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 使用Handler更新UI界面信息
	 */
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			download_progress.setProgress(msg.getData().getInt("size"));

			float temp = (float) download_progress.getProgress()
					/ (float) download_progress.getMax();

			int progress = (int) (temp * 100);
			if (progress == 100) {
				Toast.makeText(HtmlActivity.this, "下载完成！", Toast.LENGTH_LONG).show();
			}
			text_download.setText("下载进度:" + progress + " %");
		}
	};	
	 
}
